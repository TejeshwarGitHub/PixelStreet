package pixelstreet.com.pixelstreet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.viewpagerindicator.CirclePageIndicator;

import pixelstreet.com.pixelstreet.anim.FacebookButtonTransformer;

public class MainActivity extends AppCompatActivity {


    ImageView background;
    ViewPager mViewPager;
    TextView skip;
    View loginWrapper;


    CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setContentView(R.layout.activity_main);

        background = (ImageView) findViewById(R.id.loginBackground);
        mViewPager = (ViewPager) findViewById(R.id.loginTextContainer);
        skip = (TextView) findViewById(R.id.skipLogin);
        loginWrapper = findViewById(R.id.loginWrapper);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_indicators);

        final SectionPagerAdapter mPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        circlePageIndicator.setViewPager(mViewPager);




        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mViewPager.getCurrentItem();
                if (pos < mPagerAdapter.getCount() - 1) {
                    mViewPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                } else {
//                    checkLoggedIn();
                }
            }
        });
        mViewPager.setPageTransformer(true, new FacebookButtonTransformer(this, loginWrapper, circlePageIndicator));

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
