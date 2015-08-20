package fabianterhorst.github.io.schoolschedules.activities;

public class SplashActivity extends AbstractSplashActivity {

    @Override
    public Class<?> getTarget() {
        return getSchoolSchedulesApplication().getSettingsBool("group_add",false)?MainActivity.class:AddClassGroup.class;
    }

}
