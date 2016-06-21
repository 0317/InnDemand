package demand.inn.com.inndemand.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.welcome.BaseActivity;

/**
 * Created by akash
 */

public class Loginscreen extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    //Utility class area
    NetworkUtility nu;
    AppPreferences prefs;

    //Socail Integration UI call area
    //Facebook
    LoginButton loginButton;
    CallbackManager callbackManager;

    //Google
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    //Cache Data Call
    SharedPreferences settings;
    Bundle bundle;
    String accessToken;

    //UI call
    ImageView logo_next;
    ProgressDialog progressDialog;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    TextView terms_conditions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.loginscreen);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        //UI initialized
//        logo_next = (ImageView) findViewById(R.id.logo_next);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        //Socail Integration Facebook/Google is Implemented Below
        //Facebook integration initially n Google after it
        //Separations are method in a comment section

        //Facebook Integration in Inndemand Application
        //Getting Data from facebook in a registerCallback method & calling details in a separate method defined below
        //Please go through the Facebook code if required & FB code end point is mentioned below.

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends, user_photos, user_location"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                accessToken = loginResult.getAccessToken().getToken();
                prefs.setFb_Token(loginResult.getAccessToken().getToken());
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Loginscreen.this, "Exception occurred during Facebook Signin", Toast.LENGTH_SHORT).show();
            }
        });

        //Facebook integration END Point & Only onActivityResult call for FB is below after Google Plus Button Code.

        //Google Integration Area In the App
        //Google Plus implementation coding below providing different method of calling google api
        //End point of the code is mentioned below

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(Loginscreen.this);


        //Conditons/Terms of use Text at bottom Call Programatically
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String gray = "By signing-up you agree to our ";
        SpannableString graySpannable= new SpannableString(gray);
        graySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, gray.length(), 0);
        builder.append(graySpannable);

        String yellow = "terms of service ";
        SpannableString yellowSpannable= new SpannableString(yellow);
        yellowSpannable.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, yellow.length(), 0);
        builder.append(yellowSpannable);

        String grays = "& ";
        SpannableString graysSpannable = new SpannableString(grays);
        graysSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, grays.length(), 0);
        builder.append(graysSpannable);

        String yellows = "privacy policy";
        SpannableString yellowsSpannables= new SpannableString(yellows);
        yellowsSpannables.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, yellows.length(), 0);
        builder.append(yellowsSpannables);

        terms_conditions.setText(builder, TextView.BufferType.SPANNABLE);

        facebookPost();

    }

    private void facebookPost() {
        //check login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Log.d(TAG, ">>>" + "Signed Out");

        } else {
            Log.d(TAG, ">>>" + "Signed In");
            LoginManager.getInstance().logOut();
        }
    }

    private Bundle getFacebookData(JSONObject object) {

        bundle = new Bundle();
        String id = null;
        try {
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            Log.i("profile_pic", profile_pic + "");
            bundle.putString("profile_pic", profile_pic.toString());
//            prefs.setUser_picture(bundle.getString("profile_pic"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        bundle.putString("idFacebook", id);
        if (object.has("first_name"))
            try {
                bundle.putString("first_name", object.getString("first_name"));
//                prefs.setUser_fbname(bundle.getString("first_name"));
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("fb_name", bundle.getString("first_name"));
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (object.has("last_name"))
            try {
                bundle.putString("last_name", object.getString("last_name"));
                prefs.setUser_lname(bundle.getString("last_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (object.has("email"))
            try {
                bundle.putString("email", object.getString("email"));
//                prefs.setUser_email(bundle.getString("email"));
                prefs.setUser_fbemail(bundle.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (object.has("gender"))
            try {
                bundle.putString("gender", object.getString("gender"));
                prefs.setUser_gender(bundle.getString("gender"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (object.has("birthday"))
            try {
                bundle.putString("birthday", object.getString("birthday"));
                prefs.setUser_bday(bundle.getString("birthday"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (object.has("location"))
            try {
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

//        Toast.makeText(Loginscreen.this, bundle.getString("location"), Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(Loginscreen.this);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        Intent in = new Intent(Loginscreen.this, CheckDetails.class);
        in.putExtras(bundle);
        prefs.setFacebook_logged_In(true);
        startActivity(in);
        finish();

        return bundle;
    }


    //Google Override Methods
    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
    // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
    // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
    // If the user has not previously signed in on this device or the sign-in has expired,
    // this asynchronous branch will attempt to sign in the user silently. Cross-device
    // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
    // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            System.out.print("Google Details========"+result.getSignInAccount().getDisplayName());
            prefs.setUser_gname(result.getSignInAccount().getDisplayName());
            prefs.setUser_gemail(result.getSignInAccount().getEmail());
//            prefs.setUser_gpicture(result.getSignInAccount().getPhotoUrl().toString());
//            prefs.setUser_gpicture(acct.getPhotoUrl().toString());
//            prefs.setG_Token(acct.getIdToken());
            Log.d("G_Token", "check Pic"+ acct.getPhotoUrl());
            Log.d("G_Token", "ID"+ acct.getId());
            Log.d("G_Token", "check"+ acct.getServerAuthCode());
            Person person  = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            prefs.setGoogle_bday(person.getBirthday());
            prefs.setGoogle_gender(person.getGender());
            JSONObject obj = null;
            try {
                obj = new JSONObject(String.valueOf(person.getImage()));
                String img = obj.getString("url");
                prefs.setUser_gpicture(img);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("G_Token", "check Pic Plus"+ person.getImage());
            prefs.setGoogle_location(person.getCurrentLocation());
            Log.d("Age range:", "Check "+person.getBirthday());
            Log.d("Bday range:", "Check "+person.getGender());
            Log.d("Loc range:", "Check "+person.getCurrentLocation());
            hideProgressDialog();
            Intent in = new Intent(Loginscreen.this, CheckDetails.class);
            prefs.setGoogle_logged_In(true);
            startActivity(in);
            finish();
            updateUI(true);
        } else {

    // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
// [START_EXCLUDE]
                        updateUI(false);
// [END_EXCLUDE]
                    }
                });
    }
// [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
// [START_EXCLUDE]
                        updateUI(false);
// [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void updateUI(boolean signedIn) {
        if (signedIn) {

        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.verifycredentials));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
