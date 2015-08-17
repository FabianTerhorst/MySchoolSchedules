package fabianterhorst.github.io.schoolschedules;

import android.app.Fragment;
import android.os.Bundle;

public class BaseFragment extends Fragment {

    private DataStore mDataStore;
    private SchoolSchedulesApplication mApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = SchoolSchedulesApplication.getInstance();
        mDataStore = mApplication.getDataStore();
    }

    public DataStore getDataStore(){
        return this.mDataStore;
    }

    public SchoolSchedulesApplication getApplication() {
        return mApplication;
    }
}
