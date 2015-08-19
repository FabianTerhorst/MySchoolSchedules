package fabianterhorst.github.io.schoolschedules.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Homework;
import fabianterhorst.github.io.schoolschedules.models.Lesson;
import fabianterhorst.github.io.schoolschedules.models.Teacher;

public class AddActivity extends BaseActivity {

    private type mType;
    private EditText mName, mTitle, mDescription, mSalutation, mShortname, mSurname;
    public static final String TYPE = "TYPE";

    public enum type {
        TEACHER, HOMEWORK, LESSON
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = type.valueOf(getIntent().getStringExtra(TYPE));
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(layoutParams);
        container.setOrientation(LinearLayout.VERTICAL);
        setContentView(container);
        View toolbarView = LayoutInflater.from(this).inflate(R.layout.toolbar, null);
        container.addView(toolbarView);
        Toolbar toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeButtonEnabled(true);
        }
        switch (mType) {
            case TEACHER: {
                mSurname = new EditText(this);
                mSurname.setHint("surname");
                container.addView(mSurname);
                mSalutation = new EditText(this);
                mSalutation.setHint("salutation");
                container.addView(mSalutation);
                break;
            }
            case HOMEWORK: {
                mTitle = new EditText(this);
                mTitle.setHint("title");
                container.addView(mTitle);
                mDescription = new EditText(this);
                mDescription.setHint("description");
                container.addView(mDescription);
                break;
            }
            case LESSON: {
                mName = new EditText(this);
                mName.setHint("name");
                container.addView(mName);
                mShortname = new EditText(this);
                mShortname.setHint("shortname");
                container.addView(mShortname);
                break;
            }
        }
    }

    private void createObject() {
        switch (mType) {
            case TEACHER: {
                Teacher teacher = new Teacher();
                teacher.setSurname(mSurname.getEditableText().toString());
                teacher.setSalutation(mSalutation.getEditableText().toString());
                getDataStore().addTeacher(teacher);
                break;
            }
            case HOMEWORK: {
                Homework homework = new Homework();
                homework.setTitle(mTitle.getEditableText().toString());
                homework.setDescription(mDescription.getEditableText().toString());
                getDataStore().addHomework(homework);
                break;
            }
            case LESSON: {
                Lesson lesson = new Lesson();
                lesson.setName(mName.getEditableText().toString());
                lesson.setShortname(mShortname.getEditableText().toString());
                getDataStore().addLesson(lesson);
                break;
            }
        }
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        switch (mType) {
            case TEACHER: {
                startActivity(createIntent(MainActivity.startfragment.TEACHER));
                break;
            }
            case HOMEWORK: {
                startActivity(createIntent(MainActivity.startfragment.HOMEWORK));
                break;
            }
            case LESSON: {
                startActivity(createIntent(MainActivity.startfragment.LESSON));
                break;
            }
        }
    }

    private String getToolbarTitle() {
        switch (mType) {
            case TEACHER: {
                return getString(R.string.add_teacher);
            }
            case HOMEWORK: {
                return getString(R.string.add_homework);
            }
            case LESSON: {
                return getString(R.string.add_lesson);
            }
            default: {
                return null;
            }
        }
    }

    private Intent createIntent(MainActivity.startfragment startFragment) {
        Intent intent = (new Intent(this, MainActivity.class));
        intent.putExtra(MainActivity.STARTFRAGMENT, startFragment.toString().toUpperCase());
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save: {
                createObject();
                break;
            }
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
