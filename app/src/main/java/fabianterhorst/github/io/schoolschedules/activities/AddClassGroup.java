package fabianterhorst.github.io.schoolschedules.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.ClassGroup;

public class AddClassGroup extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classgroup);

        final EditText classGroupName = (EditText) findViewById(R.id.classGroupName);
        final EditText classGroupShortName = (EditText) findViewById(R.id.classGroupShortName);

        getSchoolSchedulesApplication().setSettingsBool("group_add",true);

        findViewById(R.id.classGroupAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassGroup classGroup = new ClassGroup(classGroupName.getEditableText().toString(), classGroupShortName.getEditableText().toString(), getDataStore().getUser().getEmail());
                getDataStore().createClassGroup(classGroup, new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        startActivity(new Intent(AddClassGroup.this ,MainActivity.class));
                        return false;
                    }
                }));
            }
        });

    }
}
