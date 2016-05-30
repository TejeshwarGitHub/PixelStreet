package pixelstreet.com.pixelstreet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;

public class AppIntroActivity extends AppIntro {

    public void getStarted(View v){
        startActivity(new Intent(AppIntroActivity.this,LoginActivity.class));
    }
    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {
/*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "pixelstreet.com.pixelstreet",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(IntroFragment.newInstance(R.layout.intro));
        addSlide(IntroFragment.newInstance(R.layout.intro2));
        addSlide(IntroFragment.newInstance(R.layout.intro3));
        addSlide(IntroFragment.newInstance(R.layout.intro4));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        startActivity(new Intent(AppIntroActivity.this,LoginActivity.class));
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        startActivity(new Intent(AppIntroActivity.this,LoginActivity.class));
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }
}