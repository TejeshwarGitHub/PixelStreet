package pixelstreet.com.pixelstreet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Arrays;

import app.AccountManager;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,AccountManager.Callback {

    private static final int RC_SIGN_IN = 246;
    private static final int FB_SIGN = 123;
    private static final String TAG = "LoginActivity";
    GoogleApiClient mGoogleApiClient;
    ProgressDialog dialog;
    Realm realm;
    CallbackManager mCallbackManager;

    Button btn_fb_login;
    SignInButton signInButton;

    AccountManager accountManager;
    //facebook
    //facebook
    //facebook
    //facebook
    //facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

        btn_fb_login = (Button) findViewById(R.id.fb_sign_in_button);
        signInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Fetching data");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setInverseBackgroundForced(false);

        accountManager = new AccountManager(this, this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });


        // facebook
        // facebook
        // facebook
        // facebook
        // facebook

        if (accountManager.isLoggedIn() != AccountManager.NO_ACCOUNT) {
            logIn();
        }
        mCallbackManager = CallbackManager.Factory.create();


        final LoginManager loginManager = LoginManager.getInstance();

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        });

        loginManager.registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.e("LoginFacebook", "Success");
                        Profile profile = Profile.getCurrentProfile();
                        handleSignInResult(loginResult, profile);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void logIn() {
        Intent intent = new Intent(this, ProfessionsActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);

            }
        } else {
            //facebook

            mCallbackManager.onActivityResult(requestCode, resultCode, data);


        }
    }

    private void handleSignInResult(final LoginResult loginResult, final Profile profile) {
        dialog.show();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                accountManager.sendUserToServer(object, loginResult);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();


    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // Snackbar.make() .setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Toast.makeText(LoginActivity.this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();
//            accountManager.sendUserToServer(acct);
            dialog.show();
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
//
    @Override
    public void successfulSignIn() {
        dialog.hide();
        logIn();
    }

    /**
     * Error occurred during communicating with backend server
     *
     * @param error {@link AccountManager#NO_INTERNET_CONNECTION} or {@link AccountManager#UNKNOWN_ERROR}
     */
    @Override
    public void signInError(int error) {
        String errorMessage = "";
        dialog.hide();
        if (error == AccountManager.UNKNOWN_ERROR)
            errorMessage = "Unknown Error occurred, please retry";
        else {
            errorMessage = "Please check your internet connection and retry";
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

        //todo remove this when we have a server
        logIn();
    }

    @Override
    public void loggedOut() {

    }
}
