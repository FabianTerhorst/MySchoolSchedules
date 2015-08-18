package fabianterhorst.github.io.schoolschedules.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import fabianterhorst.github.io.schoolschedules.DataStore;
import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.fragments.RepresentationsFragment;

public class MainActivity extends BaseActivity {

   @SuppressWarnings("unused")
    private static String TAG = MainActivity.class.getName();
    private AccountHeader mHeader;
    private Drawer mDrawer;
    private Toolbar mToolbar;
    private Fragment mLastFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mFragmentManager = getFragmentManager();

        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSelectionListEnabled(false)
                .withHeaderBackground(R.drawable.material_drawer_shadow_top)
                .addProfiles(
                        new ProfileDrawerItem().withName("").withEmail("").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_account_circle).color(Color.WHITE).sizeDp(IconicsDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP)).withEnabled(false).withIdentifier(1)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        return false;
                    }
                })
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withFullscreen(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_timetable).withIdentifier(1).withIcon(CommunityMaterial.Icon.cmd_timetable),
                        new PrimaryDrawerItem().withName(R.string.drawer_representations).withIdentifier(2).withIcon(CommunityMaterial.Icon.cmd_image_filter_frames),
                        new PrimaryDrawerItem().withName(R.string.drawer_lessons).withIdentifier(3).withIcon(CommunityMaterial.Icon.cmd_file_document_box),
                        new PrimaryDrawerItem().withName(R.string.drawer_teacher).withIdentifier(4).withIcon(CommunityMaterial.Icon.cmd_school),
                        new PrimaryDrawerItem().withName(R.string.drawer_homework).withIdentifier(5).withIcon(CommunityMaterial.Icon.cmd_clipboard_text).withBadgeStyle(new BadgeStyle().withColorRes(R.color.md_red_500).withTextColorRes(R.color.md_white_1000).withCorners(30)).withBadge("5")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int i, final IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            mLastFragment = null;
                            switch (drawerItem.getIdentifier()) {
                                case 2: {
                                    mLastFragment = new RepresentationsFragment();
                                    break;
                                }
                            }

                            if(mLastFragment != null)
                                mFragmentManager.beginTransaction().replace(R.id.content_frame, mLastFragment).commit();

                            if (drawerItem instanceof Nameable) {
                                mToolbar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
                            }

                        }
                        return true;
                    }

                })
                .withSavedInstance(savedInstanceState)
                .build();

        mDrawer.setSelection(2);

        getDataStore().registerSchoolClassesDataChangeCallback(new DataStore.SchoolClassesDataChangeCallback() {
            @Override
            public void onSchoolClassesDataChange() {
                updateProfile();
            }
        });

        updateProfile();

        getDataStore().refreshSchoolClasses();
    }

    private void updateProfile(){
        if(getDataStore().getCurrentSchoolClass() != null) {
            IProfile profileDrawerItem = mHeader.getActiveProfile();
            profileDrawerItem.withName(getDataStore().getCurrentSchoolClass().getClass_name());
            mHeader.updateProfileByIdentifier(profileDrawerItem);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = mDrawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = mHeader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            getSchoolSchedulesApplication().logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
