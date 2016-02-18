package pixelstreet.com.pixelstreet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Arrays;

import pixelstreet.com.pixelstreet.anim.FacebookButtonTransformer;

public class MainActivity extends AppCompatActivity {


    ImageView background;
    ViewPager mViewPager;
    TextView skip;
    View loginWrapper;
    AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            AccessToken.setCurrentAccessToken(currentAccessToken);
            checkLoggedIn();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        accessTokenTracker.startTracking();
        checkLoggedIn();
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.startTracking();
    }

    CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLoggedIn();
        setContentView(R.layout.activity_main);
        mCallbackManager = CallbackManager.Factory.create();
        background = (ImageView) findViewById(R.id.loginBackground);
        mViewPager = (ViewPager) findViewById(R.id.loginTextContainer);
        skip = (TextView) findViewById(R.id.skipLogin);
        loginWrapper = findViewById(R.id.loginWrapper);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_indicators);

        final SectionPagerAdapter mPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        circlePageIndicator.setViewPager(mViewPager);

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    private ProfileTracker mProfileTracker;

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("LoginFacebook", "Success");
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                                Log.e("facebook - profile", profile1.getFirstName());
                                Profile.setCurrentProfile(profile1);
                                mProfileTracker.stopTracking();
                            }
                        };
                        mProfileTracker.startTracking();
                        logIn();

                        // TODO: 21-10-2015  send user data to server,
                        // TODO: 21-10-2015 add user data to table
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Button btn_fb_login = (Button) findViewById(R.id.login_button);

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mViewPager.getCurrentItem();
                if (pos < mPagerAdapter.getCount() - 1) {
                    mViewPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                } else {
                    checkLoggedIn();
                }
            }
        });
        mViewPager.setPageTransformer(true, new FacebookButtonTransformer(this, loginWrapper, circlePageIndicator));

    }

    private void logIn() {
        Intent intent = new Intent(this, ProfessionsActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkLoggedIn() {

        if (AccessToken.getCurrentAccessToken()!=null) {
            logIn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
       /* if(mViewPager.getCurrentItem()!=0){
            mViewPager.setCurrentItem(0);
        }
        else*/
        super.onBackPressed();
    }

    class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d("get Item", position + " ");
            Fragment fragment = new LoginFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
