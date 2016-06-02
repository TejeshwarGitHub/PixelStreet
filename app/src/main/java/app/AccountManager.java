package app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.UserModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by admin on 01-06-2016.
 */
public class AccountManager {

    /**
     * Social Account Type. Used to specify Facebook Account
     */
    public static final int FACEBOOK_ACCOUNT = 1;
    /**
     * Social Account Type. Used to specify Google Account
     */
    public static final int GOOGLE_ACCOUNT = 2;

    /**
     * Account Type. Used to specify Sign in has not yet been configured
     */
    public static final int NO_ACCOUNT = 0;

    /**
     * Error Type. Used to specify there is no Internet Connection
     */
    public static final int NO_INTERNET_CONNECTION = 0;
    /**
     * Error Type. Used when there is an unknown error as in invalid JSON parsing or so.
     */
    public static final int UNKNOWN_ERROR = -1;
    private static final String MY_PREFS = "com.example.harsu.AccountManager";
    Callback callback;
    Realm realm;
    RealmConfiguration realmConfig;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    private int DEFAULT_IMAGE_WIDTH = 96;
    private int DEFAULT_IMAGE_HEIGHT = 96;
    private Context context;
    private int current_account;

    /**
     * Constructor to initialize AccountManager
     *
     * @param context
     * @param callback implements the {@link Callback}.
     * @see {@link Callback} for more detalis
     */

    public AccountManager(Context context, Callback callback) {
        this.context = context;
        current_account = getCurrentAccount(context);

        realmConfig = new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
        this.callback = callback;
    }

    /**
     * Return the current Active account {@link #FACEBOOK_ACCOUNT}, {@link #GOOGLE_ACCOUNT} or
     * {@link #NO_ACCOUNT}
     *
     * @return {@link #FACEBOOK_ACCOUNT}, {@link #GOOGLE_ACCOUNT} if the account is successfully signed up
     * else returns {@link #NO_ACCOUNT}
     */

    public static int getCurrentAccount(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("account", NO_ACCOUNT);
    }

    /**
     * to check if the user is logged in or not.
     *
     * @return {@link #NO_ACCOUNT} if not signed in, else returns {@link #FACEBOOK_ACCOUNT} or {@link #GOOGLE_ACCOUNT}
     */

    public int isLoggedIn() {
        RealmQuery<UserModel> query = realm.where(UserModel.class);
        RealmResults<UserModel> result1 = query.findAll();
        if (result1.size() > 0) {
            return result1.get(0).getSocialAccount();
        } else
            return NO_ACCOUNT;
    }

    /**
     * Can use it to alter the default image size used to obtain ImageURI from {@link Profile#getProfilePictureUri(int, int)}
     *
     * @param imageWidth
     * @param imageHeight
     */
    public void setImageSize(int imageWidth, int imageHeight) {
        this.DEFAULT_IMAGE_WIDTH = imageWidth;
        this.DEFAULT_IMAGE_HEIGHT = imageHeight;
    }

    /**
     * Set the account that the user is using to sign In with. Required before using the functions.
     *
     * @param current_account Account used. Use 1 or {@link #FACEBOOK_ACCOUNT} for using Facebook
     *                        Account , {@link #GOOGLE_ACCOUNT} if using Google Account and
     *                        {@link #NO_ACCOUNT} if no account has been configured yet
     */

    public void setCurrent_account(int current_account) {
        this.current_account = current_account;
    }

    public void startTracking() {
        if (current_account == FACEBOOK_ACCOUNT) {

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(
                        AccessToken oldAccessToken,
                        AccessToken currentAccessToken) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                }
            };
            profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(
                        Profile oldProfile,
                        Profile currentProfile) {
                    Profile.setCurrentProfile(currentProfile);
                }
            };
            accessTokenTracker.startTracking();
        } else if (current_account == GOOGLE_ACCOUNT) {

        }
    }

    public void stopTracking() {
        if (current_account == FACEBOOK_ACCOUNT) {
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
        }
    }

    public void sendUserToServer(final JSONObject object, final LoginResult loginResult) {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ControllerConstants.url_users, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("Response", jsonObject.toString());
                        //{"_id":"571ea6ea71b6acf21921902f","username":"Harshit Agarwal","email":"harsu.ag@gmail.com","__v":0,"updated_at":"2016-04-25T23:23:22.361Z","logs":[]}
                        //response
                        storeData(jsonObject, object);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.toString());
                callback.signInError(NO_INTERNET_CONNECTION);

            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", object.getString("name"));
                    jsonObject.put("email", object.getString("email"));

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

    private void storeData(JSONObject responseObject, JSONObject profileData) {
        Profile profile = Profile.getCurrentProfile();
        if (profile == null)
            callback.signInError(UNKNOWN_ERROR);
        try {
            realm.beginTransaction();
            UserModel userModel = realm.createObject(UserModel.class);
            userModel.setEmail(profileData.getString("email"));
            userModel.setImage_url(profile.getProfilePictureUri(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT) == null ?
                    "" : profile.getProfilePictureUri(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT).toString());
            userModel.setName(profile.getName());
            userModel.setProfile_id(profile.getId());
            userModel.setServer_id(responseObject.getString("_id"));
            userModel.setSocialAccount(FACEBOOK_ACCOUNT);
            realm.commitTransaction();

            setAccount(FACEBOOK_ACCOUNT);

            //call successfull sign in has occured
            callback.successfulSignIn();
        } catch (JSONException e) {
            e.printStackTrace();
            callback.signInError(UNKNOWN_ERROR);
        } catch (RealmPrimaryKeyConstraintException e) {
            realm.cancelTransaction();
            callback.signInError(UNKNOWN_ERROR);
        }

    }

    private void setAccount(int account) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("account", account);
        editor.commit();
    }

    public void sendUserToServer(final GoogleSignInAccount result) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ControllerConstants.url_users, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("Response", jsonObject.toString());
                        //{"_id":"571ea6ea71b6acf21921902f","username":"Harshit Agarwal","email":"harsu.ag@gmail.com","__v":0,"updated_at":"2016-04-25T23:23:22.361Z","logs":[]}
                        //response
                        storeData(jsonObject, result);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.toString());
                callback.signInError(NO_INTERNET_CONNECTION);
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

            setAccount(GOOGLE_ACCOUNT);

            callback.successfulSignIn();
        } catch (JSONException e) {
            e.printStackTrace();
            callback.signInError(UNKNOWN_ERROR);
        } catch (RealmPrimaryKeyConstraintException e) {
            callback.signInError(UNKNOWN_ERROR);
            realm.cancelTransaction();
        }


    }

    public interface Callback {
        /**
         * Called when successful sign in is done. You can Implement your startNewActivity login here
         */
        public void successfulSignIn();

        /**
         * Error occurred during communicating with backend server
         *
         * @param error {@link AccountManager#NO_INTERNET_CONNECTION} or {@link AccountManager#UNKNOWN_ERROR}
         */
        public void signInError(int error);

        /**
         * Called when the user is successfully logged out.
         */
        public void loggedOut();
    }
}
