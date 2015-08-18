package fabianterhorst.github.io.schoolschedules.callbacks;

import android.util.Log;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.models.Representation;

public abstract class DataChangeCallback {

    private static final String TAG = DataChangeCallback.class.getName();
    private String mClassName;

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        this.mClassName = className;
    }


    public void onRepresentationsDataChange(List<Representation> representations) {
        Log.d(TAG, "Representations count " + Integer.toString(representations.size()));
    }

    public void onUserDataChange() {
        Log.d(TAG, "Userdata changed");
    }

    public void onTeacherDataChange() {
        Log.d(TAG, "Teacher changed");
    }

    public void onLessonDataChange() {
        Log.d(TAG, "Lesson changed");
    }

    public void onHomeworkDataChange() {
        Log.d(TAG, "Homework changed");
    }

}
