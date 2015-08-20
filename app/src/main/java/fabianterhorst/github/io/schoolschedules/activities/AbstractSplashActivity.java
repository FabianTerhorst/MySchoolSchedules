package fabianterhorst.github.io.schoolschedules.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public abstract class AbstractSplashActivity extends BaseActivity {

    private static final String TAG = AbstractSplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSchoolSchedulesApplication().setSplashActivity(this);

        //final EditText className = (EditText) findViewById(R.id.className);
        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (userName.length() > 0 && password.length() > 0)
                    getSchoolSchedulesApplication().register(userName.getEditableText().toString(), password.getEditableText().toString());
            }
        });
        getDataStore().registerDataChangeCallback(new DataChangeCallback() {
            @Override
            public void onUserDataChange() {
                boot();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "start");
        //User user = SchoolSchedulesApplication.getInstance().getUser();
        //if (user != null)
        //    boot();
    }

    private void boot() {
        Log.d(TAG, "boot");
        if (this.getTarget() != null) {
            Intent intent = new Intent(this, this.getTarget());
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    protected abstract Class<?> getTarget();

}
