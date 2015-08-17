package fabianterhorst.github.io.schoolschedules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;
import fabianterhorst.github.io.schoolschedules.models.User;

public abstract class AbstractSplashActivity extends BaseActivity {

    private static final String TAG = AbstractSplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSchoolSchedulesApplication().setSplashActivity(this);

        final EditText className = (EditText) findViewById(R.id.className);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (className.length() > 0) {
                    getSchoolSchedulesApplication().register(className.getEditableText().toString());
                }
            }
        });

        getDataStore().registerDataChangeCallback(new DataChangeCallback() {
            @Override
            public void onUserDataChange() {
                if(getSchoolSchedulesApplication().getUser() != null)
                    boot();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "start");
        User user = SchoolSchedulesApplication.getInstance().getUser();
        if (user != null)
            boot();
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
