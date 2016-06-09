package pixelstreet.com.pixelstreet;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            AccessToken.setCurrentAccessToken(currentAccessToken);
           setProfile();

        }
    };
    ProfileTracker profileTracker=new ProfileTracker() {
        @Override
        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            Profile.setCurrentProfile(currentProfile);
            setProfile();
        }
    };

    CircleImageView profilePic;
    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        setContentView(R.layout.activity_landing);

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_landing);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    //case 0:
                    //    return MaterialRecyclerViewFragment.newInstance();
                    //case 1:
                    //    return MaterialRecyclerViewFragment.newInstance();
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        return MaterialRecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 7;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 7) {
                    case 0:
                        return "All";
                    case 1:
                        return "Weddings";
                    case 2:
                        return "Portraits";
                    case 3:
                        return "Parties";
                    case 4:
                        return "Functions";
                    case 5:
                        return "Stage Shows";
                    case 6:
                        return "Architecture";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://bits-pearl.org/Events/img/Photog%20Retrospective%20WebBanner.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://bits-pearl.org/Events/img/Photog-Guess-What-Macro-WebBanner.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://bits-pearl.org/Events/img/Photog---Photo-Story-Webbanner.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://bits-pearl.org/Events/img/Photog-Talks-and-Workshop-Webabanner.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://bits-pearl.org/Events/img/soul'o.jpg");
                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://bits-pearl.org/Events/img/yourh%20sabha.jpg");
                    case 6:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://bits-pearl.org/Events/img/stand%20up.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_landing);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view_landing);
        navigationView.setNavigationItemSelectedListener(this);
        profilePic = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        setProfile();
    }

    private void setProfile() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(90, 90)).into(profilePic);
            if(Profile.getCurrentProfile()!=null) {
              TextView textView=  ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName));
                textView.setText("Welcome " + Profile.getCurrentProfile().getFirstName());
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_landing);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_landing);

        switch (id) {
            case R.id.nav_home:
                /*Fragment fragment= new Open_Land_Fragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.add(R.id.landing_container,fragment).commit();*/
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
