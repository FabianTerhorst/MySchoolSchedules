package fabianterhorst.github.io.schoolschedules;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.FirebaseSimpleLoginError;
import com.firebase.simplelogin.FirebaseSimpleLoginUser;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
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
    protected Firebase mFirebase;

    @Override
    public void onCreate() {
        super.onCreate();
        mSingleInstance = this;
        mSingleInstance.initializeInstance();
    }

    protected void initializeInstance() {
        Firebase.setAndroidContext(this);
        Iconics.init(getApplicationContext());
        Iconics.registerFont(new CommunityMaterial());
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public DataStore getDataStore() {
        if (mDataStore == null)
            mDataStore = new DataStore();
        return mDataStore;
    }

    public Firebase getFirebase() {
        if (mFirebase == null)
            mFirebase = new Firebase("https://flickering-heat-6338.firebaseio.com/");
        return mFirebase;
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

    public void register(final String userName, final String password) {
        SimpleLogin authClient = new SimpleLogin(getFirebase(), this);
        authClient.createUser(userName, password, new SimpleLoginAuthenticatedHandler() {
            public void authenticated(FirebaseSimpleLoginError error, FirebaseSimpleLoginUser user) {
                if (error != null) {
                    // There was an error creating this account
                    Toast.makeText(SchoolSchedulesApplication.this, error.getCode() + " " + error.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // We created a new user account
                    getDataStore().setUser(user);
                }
            }
        });
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
        Intent intent = new Intent(this, getSplashActivity().getClass());
        intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void clearSettings() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }

    public void setSettingsString(String key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSettingsString(String key) {
        return mSettings.getString(key, "");
    }

    public boolean getSettingsBool(String key, boolean defaultBoolean) {
        return mSettings.getBoolean(key, defaultBoolean);
    }

    public void setSettingsBool(String key, boolean bool){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

}
