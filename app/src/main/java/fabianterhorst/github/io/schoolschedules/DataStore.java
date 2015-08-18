package fabianterhorst.github.io.schoolschedules;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fabianterhorst.github.io.schoolschedules.adapter.RestApiAdapter;
import fabianterhorst.github.io.schoolschedules.api.PictoriusApi;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;
import fabianterhorst.github.io.schoolschedules.models.Homework;
import fabianterhorst.github.io.schoolschedules.models.Lesson;
import fabianterhorst.github.io.schoolschedules.models.Representation;
import fabianterhorst.github.io.schoolschedules.models.RepresentationResult;
import fabianterhorst.github.io.schoolschedules.models.SchoolClass;
import fabianterhorst.github.io.schoolschedules.models.SchoolClassResult;
import fabianterhorst.github.io.schoolschedules.models.Teacher;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataStore {

    public interface SchoolClassesDataChangeCallback {
        void onSchoolClassesDataChange();
    }

    protected transient ArrayList<DataChangeCallback> mCallbacks;
    protected transient ArrayList<SchoolClassesDataChangeCallback> mSchoolClassesCallbacks;
    protected String mSchoolClassName;

    public DataStore() {
        mCallbacks = new ArrayList<>();
        mSchoolClassesCallbacks = new ArrayList<>();
        setSchoolClassName();
    }

    public void setSchoolClassName(){
        //check if user object set in database
        SchoolSchedulesApplication app = SchoolSchedulesApplication.getInstance();
        if(app.getUser() != null)
            mSchoolClassName = app.getUserClassName();
    }

    public void updateOrAddTeacher(final Teacher teacher){
        SchoolSchedulesApplication.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                teacher.setId(Math.round(realm.where(Teacher.class).maximumInt("id") + 1));
                realm.copyToRealmOrUpdate(teacher);
            }
        });
        callTeacherCallbacks();
    }

    public void updateOrAddLesson(final Lesson lesson){
        SchoolSchedulesApplication.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                lesson.setId(Math.round(realm.where(Lesson.class).maximumInt("id") + 1));
                realm.copyToRealmOrUpdate(lesson);
            }
        });
        callLessonCallbacks();
    }

    public void updateOrAddHomework(final Homework homework){
        SchoolSchedulesApplication.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                homework.setId(Math.round(realm.where(Homework.class).maximumInt("id") + 1));
                realm.copyToRealmOrUpdate(homework);
            }
        });
        callHomeworkCallbacks();
    }

    public void deleteTeacherById(int id){
        getTeacherById(id).removeFromRealm();
        callTeacherCallbacks();
    }

    public void deleteLessonById(int id){
        getLessonById(id).removeFromRealm();
        callLessonCallbacks();
    }

    public void deleteHomeworkById(int id){
        getHomeworkById(id).removeFromRealm();
        callHomeworkCallbacks();
    }

    public void refreshSchoolClasses() {
        final SchoolSchedulesApplication app = SchoolSchedulesApplication.getInstance();
        app.getApi().getClasses(new Callback<SchoolClassResult>() {
            @Override
            public void success(final SchoolClassResult schoolClassResult, Response response) {
                app.getRealm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (SchoolClass schoolClass : schoolClassResult.getResults())
                            if (schoolClass.getClass_name().equals(mSchoolClassName))
                                realm.copyToRealmOrUpdate(schoolClass);
                    }
                });
                callSchoolClassesCallbacks();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getMessage() != null)
                    Toast.makeText(app, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void refreshClassRepresentations(final SchoolClass schoolClass) {
        final SchoolSchedulesApplication app = SchoolSchedulesApplication.getInstance();
        RestApiAdapter.getPictoriusInstance().create(PictoriusApi.class).getRepresentations(schoolClass.getLink(), new Callback<RepresentationResult>() {
            @Override
            public void success(final RepresentationResult representationResult, Response response) {
                app.getRealm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int count = 0;
                        for (Representation representation : representationResult.getResults()) {
                            count++;
                            representation.setId(count);
                            realm.copyToRealmOrUpdate(representation);
                        }
                    }
                });
                callCallbacks(schoolClass, representationResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getMessage() != null)
                    Toast.makeText(app, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerDataChangeCallback(DataChangeCallback dataChangeCallback) {
        mCallbacks.add(dataChangeCallback);
    }

    public void registerSchoolClassesDataChangeCallback(SchoolClassesDataChangeCallback schoolClassesDataChangeCallback) {
        mSchoolClassesCallbacks.add(schoolClassesDataChangeCallback);
    }

    public void registerDataChangeCallbackForSchoolClassName(String className, DataChangeCallback dataChangeCallback) {
        dataChangeCallback.setClassName(className);
        mCallbacks.add(dataChangeCallback);
    }

    public void registerDataChangeCallbackForCurrentSchoolClass(DataChangeCallback dataChangeCallback){
        registerDataChangeCallbackForSchoolClassName(mSchoolClassName, dataChangeCallback);
    }

    public void callSchoolClassesCallbacks() {
        for (SchoolClassesDataChangeCallback schoolClassesDataChangeCallback : mSchoolClassesCallbacks)
            schoolClassesDataChangeCallback.onSchoolClassesDataChange();
    }

    public void callTeacherCallbacks() {
        for (DataChangeCallback dataChangeCallback : mCallbacks)
            dataChangeCallback.onTeacherDataChange();
    }

    public void callLessonCallbacks() {
        for (DataChangeCallback dataChangeCallback : mCallbacks)
            dataChangeCallback.onLessonDataChange();
    }

    public void callHomeworkCallbacks() {
        for (DataChangeCallback dataChangeCallback : mCallbacks)
            dataChangeCallback.onHomeworkDataChange();
    }

    public void callCallbacks(SchoolClass schoolClass, List<Representation> representations) {
        for (DataChangeCallback dataChangeCallback : mCallbacks)
            if (dataChangeCallback.getClassName() != null)
                if (dataChangeCallback.getClassName().equals(schoolClass.getClass_name()))
                    dataChangeCallback.onRepresentationsDataChange(representations);
    }

    public void callUserCallbacks() {
        for (DataChangeCallback dataChangeCallback : mCallbacks)
            dataChangeCallback.onUserDataChange();

        //set new school class name when user changed
        setSchoolClassName();
    }

    public RealmResults<Representation> getRepresentationsByClassName(String className) {
        return SchoolSchedulesApplication.getInstance().getRealm().where(Representation.class).equalTo("class_name", className).findAll();
    }

    public RealmResults<Representation> getRepresentationsFromCurrentSchoolClass(){
        return getRepresentationsByClassName(mSchoolClassName);
    }

    public SchoolClass getSchoolClassByName(String name) {
        return SchoolSchedulesApplication.getInstance().getRealm().where(SchoolClass.class).equalTo("class_name", name).findFirst();
    }

    public SchoolClass getCurrentSchoolClass(){
        return getSchoolClassByName(mSchoolClassName);
    }

    public RealmResults<Teacher> getTeachers(){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Teacher.class).findAll();
    }

    public RealmResults<Lesson> getLessons(){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Lesson.class).findAll();
    }

    public RealmResults<Homework> getHomeworks(){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Homework.class).findAll();
    }

    public Teacher getTeacherById(int id){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Teacher.class).equalTo("id", id).findFirst();
    }

    public Lesson getLessonById(int id){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Lesson.class).equalTo("id",id).findFirst();
    }

    public Homework getHomeworkById(int id){
        return SchoolSchedulesApplication.getInstance().getRealm().where(Homework.class).equalTo("id",id).findFirst();
    }

}
