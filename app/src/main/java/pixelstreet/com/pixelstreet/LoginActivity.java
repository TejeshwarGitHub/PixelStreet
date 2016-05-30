package pixelstreet.com.pixelstreet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.AppController;
import app.ControllerConstants;
import helper.UserModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 246;
    private static final int FB_SIGN = 123;
    private static final String TAG = "LoginActivity";
    GoogleApiClient mGoogleApiClient;
    ProgressDialog dialog;
    Realm realm;
    CallbackManager mCallbackManager;

    Button btn_fb_login;
    SignInButton signInButton;
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

        realmConfig = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm=Realm.getInstance(realmConfig);


        // facebook
        // facebook
        // facebook
        // facebook
        // facebook

        checkLoggedIn();
        mCallbackManager = CallbackManager.Factory.create();

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

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
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }


    //facebook
    //facebook
    //facebook
    //facebook
    //facebook

    AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            AccessToken.setCurrentAccessToken(currentAccessToken);
            checkLoggedIn();
        }
    };

    private void logIn() {
        Intent intent = new Intent(this, ProfessionsActivity.class);
        startActivityForResult(intent,FB_SIGN);
        finish();
    }

    private void checkLoggedIn() {

        if (AccessToken.getCurrentAccessToken()!=null) {
            logIn();
        }
    }


    RealmConfiguration realmConfig;

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
        }else if (requestCode==FB_SIGN){
            //facebook
            if (resultCode==RESULT_OK) {
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // Snackbar.make() .setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Toast.makeText(LoginActivity.this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            sendUserToServer(acct);
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    private void sendUserToServer(final GoogleSignInAccount result) {
        dialog.show();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ControllerConstants.url_users, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("Response", jsonObject.toString());
                        //{"_id":"571ea6ea71b6acf21921902f","username":"Harshit Agarwal","email":"harsu.ag@gmail.com","__v":0,"updated_at":"2016-04-25T23:23:22.361Z","logs":[]}
                        //response
                        storeData(jsonObject, result);
                        dialog.hide();
                        //todo start activity

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.toString());
                Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                dialog.hide();
            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", result.getDisplayName());
                    jsonObject.put("email", result.getEmail());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String body = jsonObject.toString();
                return body.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(objectRequest);
    }

    private void storeData(JSONObject jsonObject, GoogleSignInAccount account) {
        try {
            realm.beginTransaction();
            UserModel userModel = realm.createObject(UserModel.class);
            userModel.setEmail(account.getEmail());
            userModel.setImage_url(account.getPhotoUrl() == null ? "" : account.getPhotoUrl().toString());
            userModel.setName(account.getDisplayName());
            userModel.setProfile_id(account.getId());
            userModel.setServer_id(jsonObject.getString("_id"));
            realm.commitTransaction();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RealmPrimaryKeyConstraintException e) {

            realm.cancelTransaction();
        }


    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
