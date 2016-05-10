package demand.inn.com.inndemand.login;

import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import demand.inn.com.inndemand.R;

/**
 * Created by akash
 */

    public class LoginScreen extends AppCompatActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    LoginButton loginButton;
    CallbackManager callbackManager;

    private static final int RC_SIGN_IN = 0;

    // Google client to communicate with Google
    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    private SignInButton signinButton;

    //Cache Data Call
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.loginscreen);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();


        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        settings =  PreferenceManager.getDefaultSharedPreferences(this);

        //Google Login Area
        signinButton = (SignInButton) findViewById(R.id.signin_google);
        signinButton.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends, user_mobile_phone, user_photos"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                try {
                                    System.out.println("FBDetails=========" + loginResult.getAccessToken().getToken());

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.putString("fb_id", object.getString("id"));
                                    editor.putString("fb_name", object.getString("name"));
                                    editor.putString("user_mobile_phone", object.getString("user_mobile_phone"));
                                    editor.putString("fb_dob", object.has("birthday")==false?"01/01/1990" : object.getString("birthday"));
                                    editor.putString("fb_gender", object.getString("gender"));
                                    editor.commit();

                                    System.out.println("DataDetails=========" + loginResult.getAccessToken());

                                } catch(JSONException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,user_mobile_phone, picture.type(large)");

                new GraphRequest(loginResult.getAccessToken(), "me", parameters, HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                if (response != null) {
                                    try {
                                        System.out.println("before========="+response.getRawResponse());
                                        JSONObject data = response.getJSONObject();
                                        if (data.has("picture")) {
                                            String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                            //Bitmap profilePic = getFacebookProfilePicture(profilePicUrl);

                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("image", profilePicUrl);
                                            editor.putString("user_mobile_phone", data.getString("user_mobile_phone"));
                                            editor.putString("name", data.getString("name"));
                                            editor.putString("email", data.getString("email"));
                                            editor.commit();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();


                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginScreen.this, "Exception occurred during Facebook Signin", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
             private void resolveSignInError() {
              if (mConnectionResult.hasResolution()) {
                      try {
                               mIntentInProgress = true;
                             mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
                           } catch (IntentSender.SendIntentException e) {
                              mIntentInProgress = false;
                             mGoogleApiClient.connect();
                        }
                }
       }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        signedInUser = false;
         Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
               updateProfile(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                   case R.id.signin_google:
                       googlePlusLogin();
                          break;
                 }
    }

    public void signIn(View v) {
        googlePlusLogin();
        	    }

             public void logout(View v) {
            googlePlusLogout();
          }


     private void googlePlusLogin() {
           if (!mGoogleApiClient.isConnecting()) {
                     signedInUser = true;
                     resolveSignInError();
           }
       }

           private void googlePlusLogout() {
         if (mGoogleApiClient.isConnected()) {
                   Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                      mGoogleApiClient.disconnect();
                  mGoogleApiClient.connect();
                  updateProfile(false);
                 }
          }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
             return;
                   }

               if (!mIntentInProgress) {
                      // store mConnectionResult
                       mConnectionResult = connectionResult;

                       if (signedInUser) {
                              resolveSignInError();
                           }
                }
    }

    private void updateProfile(boolean isSignedIn) {
           if (isSignedIn) {
//                    signinFrame.setVisibility(View.GONE);
//                    profileFrame.setVisibility(View.VISIBLE);

              } else {
//                    signinFrame.setVisibility(View.VISIBLE);
//                    profileFrame.setVisibility(View.GONE);
                   }
    }

    private void getProfileInformation() {
         try {
                 if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                             String personName = currentPerson.getDisplayName();
                           String personPhotoUrl = currentPerson.getImage().getUrl();
//                              String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

//                          username.setText(personName);
//                             emailLabel.setText(email);

                          //     new LoadProfileImage(image).execute(personPhotoUrl);
                                    // update profile frame with new info about Google Account
                             updateProfile(true);
                           }
                  } catch (Exception e) {
                        e.printStackTrace();
                   }
           }

}
