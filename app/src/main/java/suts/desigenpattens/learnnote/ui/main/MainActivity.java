package suts.desigenpattens.learnnote.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import suts.desigenpattens.learnnote.BuildConfig;
import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.base.BaseActivity;
import suts.desigenpattens.learnnote.data.db.model.Question;
import suts.desigenpattens.learnnote.ui.login.LoginActivity;

/**
 * Tips：
 *   为toolbar添加menu：onCreateOptionsMenu(Menu menu)
 *   给toolbar的每一个menu设置点击动画：onOptionsItemSelected(MenuItem item)
 *
 */
public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_view)
    DrawerLayout drawerView;
    @BindView(R.id.cards_container)
    SwipePlaceHolderView cardsContainer;

    private CircleImageView mProfileImageView;
    private TextView mNameTextView;
    private TextView mEmailTextView;

    private ActionBarDrawerToggle mDrawerToggole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnbinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    /**为toolbar添加menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**给toolbar的每一个menu设置点击动画！*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_cut:
                return true;
            case R.id.action_copy:
                return true;
            case R.id.action_delete:
                return true;
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        mDrawerToggole = new ActionBarDrawerToggle(
                this,
                drawerView,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hidKeyBoard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerView.addDrawerListener(mDrawerToggole);
        mDrawerToggole.syncState();
        setupNavMenu();
        mPresenter.onNavMenuCreated();
        setupSwipPlaceHolderView();
        mPresenter.onViewInitialized();
    }

    private void setupSwipPlaceHolderView(){
        cardsContainer.getBuilder().setDisplayViewCount(3);

        cardsContainer.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                Toast.makeText(MainActivity.this, "count="+count, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setupNavMenu() {
        View headerLayout = navigationView.getHeaderView(0);
        mProfileImageView = headerLayout.findViewById(R.id.iv_profile_pic);
        mNameTextView = headerLayout.findViewById(R.id.tv_name);
        mEmailTextView = headerLayout.findViewById(R.id.tv_email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerView.closeDrawer(GravityCompat.START);
                switch (menuItem.getItemId()) {
                    case R.id.nav_item_about:
                        mPresenter.onDrawerOptionAboutClick();
                        return true;
                    case R.id.nav_item_rate_us:
                        mPresenter.onDrawerRateUsClick();
                        return true;
                    case R.id.nav_item_feed:
                        mPresenter.onDrawerMyFeedClick();
                        return true;
                    case R.id.nav_item_logout:
                        mPresenter.onDrawerOptionLogoutClick();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showAboutFragment() {

    }

    @Override
    public void refreshQuestionnaire(List<Question> questionList) {

    }

    @Override
    public void reloadQuestionnaire(List<Question> questionList) {

    }

    @Override
    public void updateUserName(String currentUserName) {
        mNameTextView.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mEmailTextView.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {
        //TODO load image (glide)
    }

    @Override
    public void updateAppVersion() {
        String version = R.string.version + "  " + BuildConfig.VERSION_NAME;
        tvAppVersion.setText(version);
    }

    @Override
    public void showRateUsDialog() {

    }

    @Override
    public void openMyFeedActivity() {

    }

    @Override
    public void closeNavigationDrawer() {
        if (drawerView!=null){
            drawerView.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void lockDrawer() {
        if (drawerView!=null){
            drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void unlockDrawer() {
        if (drawerView!=null){
            drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

}
