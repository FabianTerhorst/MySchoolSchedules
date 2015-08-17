package fabianterhorst.github.io.schoolschedules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private SchoolSchedulesApplication mApplication;
    private DataStore mDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplication = SchoolSchedulesApplication.getInstance();
        mDataStore = mApplication.getDataStore();
    }

    public SchoolSchedulesApplication getSchoolSchedulesApplication(){
        return this.mApplication;
    }

    public DataStore getDataStore(){
        return this.mDataStore;
    }
}
