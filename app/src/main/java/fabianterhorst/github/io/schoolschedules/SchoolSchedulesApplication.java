package fabianterhorst.github.io.schoolschedules;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.Iconics;

import fabianterhorst.github.io.schoolschedules.adapter.RestApiAdapter;
import fabianterhorst.github.io.schoolschedules.api.PictoriusApi;
import fabianterhorst.github.io.schoolschedules.models.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class SchoolSchedulesApplication extends Application {

    protected static SchoolSchedulesApplication mSingleInstance;
    protected SharedPreferences mSettings;
    protected DataStore mDataStore;
    protected Realm mRealm;
    protected Activity mSplashActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mSingleInstance = this;
        mSingleInstance.initializeInstance();
    }

    protected void initializeInstance(){
        Iconics.init(getApplicationContext());
        Iconics.registerFont(new CommunityMaterial());
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public DataStore getDataStore() {
        if (mDataStore == null)
            mDataStore = new DataStore();
        return mDataStore;
    }

    public Realm getRealm() {
        try {
            if (mRealm == null)
                mRealm = Realm.getInstance(getRealmConfiguration());
        } catch (RealmMigrationNeededException ex) {
            ex.printStackTrace();
        }
        return mRealm;
    }

    public RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder(this)
                .name("schoolschedules.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public static SchoolSchedulesApplication getInstance() {
        return mSingleInstance;
    }

    public PictoriusApi getApi() {
        return RestApiAdapter.getInstance().create(PictoriusApi.class);
    }

    public void register(final String className) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new User(className));
            }
        });
        getDataStore().callUserCallbacks();
    }

    public void setSplashActivity(Activity activity) {
        this.mSplashActivity = activity;
    }

    public Activity getSplashActivity() {
        return mSplashActivity;
    }

    public User getUser() {
        return getRealm().where(User.class).findFirst();
    }

    public String getUserClassName() {
        if (getUser() != null)
            return getUser().getClassName();
        logout();
        return "";
    }

    public void logout() {
        //clear all user settings
        clearSettings();
        final User user = mRealm.where(User.class).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                user.removeFromRealm();
            }
        });
        Intent intent = new Intent(this, getSplashActivity().getClass());
        intent.addFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void clearSettings() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }

}
