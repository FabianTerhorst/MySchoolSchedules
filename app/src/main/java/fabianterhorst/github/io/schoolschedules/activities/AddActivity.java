package fabianterhorst.github.io.schoolschedules.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import fabianterhorst.github.io.schoolschedules.models.Homework;
import fabianterhorst.github.io.schoolschedules.models.Lesson;
import fabianterhorst.github.io.schoolschedules.models.Teacher;

public class AddActivity extends BaseActivity {

    private type mType;
    private EditText mName, mTitle, mDescription, mSalutation, mShortname, mSurname;
    public static final String TYPE = "TYPE";
    public enum type{
        TEACHER,HOMEWORK,LESSON
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        setContentView(container);
        mType = type.valueOf(getIntent().getStringExtra(TYPE));
        switch(mType){
            case TEACHER:{
                mSurname = new EditText(this);
                mSurname.setHint("surname");
                container.addView(mSurname);
                mSalutation = new EditText(this);
                mSalutation.setHint("salutation");
                container.addView(mSalutation);
                break;
            }
            case HOMEWORK:{
                mTitle = new EditText(this);
                mTitle.setHint("title");
                container.addView(mTitle);
                mDescription = new EditText(this);
                mDescription.setHint("description");
                container.addView(mDescription);
                break;
            }
            case LESSON:{
                mName = new EditText(this);
                mName.setHint("name");
                container.addView(mName);
                mShortname = new EditText(this);
                mShortname.setHint("shortname");
                container.addView(mShortname);
                break;
            }
        }
        Button add = new Button(this);
        add.setText("add");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                createObject();
            }
        });
        container.addView(add);
    }

    private void createObject(){
        switch(mType){
            case TEACHER:{
                Teacher teacher = new Teacher();
                teacher.setSurname(mSurname.getEditableText().toString());
                teacher.setSalutation(mSalutation.getEditableText().toString());
                getDataStore().addTeacher(teacher);
                startActivity(createIntent(MainActivity.startfragment.TEACHER));
                break;
            }
            case HOMEWORK:{
                Homework homework = new Homework();
                homework.setTitle(mTitle.getEditableText().toString());
                homework.setDescription(mDescription.getEditableText().toString());
                getDataStore().addHomework(homework);
                startActivity(createIntent(MainActivity.startfragment.HOMEWORK));
                break;
            }
            case LESSON:{
                Lesson lesson = new Lesson();
                lesson.setName(mName.getEditableText().toString());
                lesson.setShortname(mShortname.getEditableText().toString());
                getDataStore().addLesson(lesson);
                startActivity(createIntent(MainActivity.startfragment.LESSON));
                break;
            }
        }
    }

    private Intent createIntent(MainActivity.startfragment startFragment){
        Intent intent = (new Intent(this, MainActivity.class));
        intent.putExtra(MainActivity.STARTFRAGMENT,startFragment.toString().toUpperCase());
        return intent;
    }

}
