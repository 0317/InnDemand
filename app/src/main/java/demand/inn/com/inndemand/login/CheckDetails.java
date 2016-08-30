package demand.inn.com.inndemand.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
//import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CircleTransform;
import demand.inn.com.inndemand.constants.Config;
//import demand.inn.com.inndemand.fcm.NotificationListener;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.BaseActivity;
import demand.inn.com.inndemand.welcome.SplashScreen;

/**
 * Created by akash
 */

public class CheckDetails extends AppCompatActivity {

    //Utility Class call
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Class area used in this screen
    EditText et_detail_name, et_detail_email;
    ImageView img_fb_dp;

    //Preferences Class Area
    SharedPreferences settings;
    Bundle getBundle = null;

    // Progress dialog (to show dialog if required)
    private ProgressDialog pDialog;

    //Others
    //String values to get/set details either from FB/google
    String name, email, dp, l_name, gender, bDay, gGender, fb_location, gToken = "none";
    String gName, gEmail, gDP, gbBday = "none", gLoc = "none";
    String mName, mEmail, phoneNo = "none";
    String tokenss = "";
    String customer_id = "";
    int yourAge;
    StringBuilder strBuild;

    //temporary string to show the parsed response
    private String jsonResponse;
    private static String TAG = CheckDetails.class.getSimpleName();

    //Google Client to know the status (If need)
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.checkdetails);
        //Initialisation of the Utility Classes and Preferences Classes
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);

        //Google Class call code to check/get status of the User (If Sign-In then User can Sign-Out)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        //pop-up dialog
        pDialog = new ProgressDialog(CheckDetails.this);

        //UI shown on the screen Initialized here
        et_detail_name = (EditText) findViewById(R.id.fb_name);
        et_detail_email = (EditText) findViewById(R.id.fb_email);
        img_fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation (to check email contains requirements or not)
        et_detail_email.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


      /*
       * Bundle Class area
       * Here we are getting all details fetched from Google and managing them to send to Server
       */
        getBundle = this.getIntent().getExtras();
        if(getBundle == null) {
            gName = prefs.getUser_gname();
            gEmail = prefs.getUser_gemail();
            gDP = prefs.getUser_gpicture();
            gGender = String.valueOf(prefs.getGoogle_gender());
            gbBday = prefs.getGoogle_bday();
            gLoc  =prefs.getGoogle_location();
            gToken = prefs.getG_Token();

            if(gGender.equalsIgnoreCase("0"))
                gGender = "0";
            else
                gGender = "1";

            if(gbBday == "")
                gbBday = "none";
            else
                gbBday = prefs.getGoogle_bday();

            if(gLoc == "")
                gLoc = "none";
            else
                gLoc = prefs.getGoogle_location();

            if(gToken == "")
                gToken = "none";
            else
                gToken = prefs.getG_Token();

            Log.d("Check", "Check bday "+prefs.getGoogle_bday());
            Log.d("Check", "Check Loc "+prefs.getGoogle_location());
        }

        /*
         * Bundle area for facebook
         * Here we are getting all details fetched from Facebook and managing them to send to Server
         */
        else {
            name = getBundle.getString("first_name");
            email = getBundle.getString("email");
            dp = getBundle.getString("profile_pic");
            l_name = getBundle.getString("last_name");
            gender = getBundle.getString("gender");
            bDay = getBundle.getString("birthday");
            fb_location = getBundle.getString("location");
            prefs.setUser_fbemail(email);
            prefs.setUser_fbname(name+" "+l_name);
            prefs.setUser_fbpic(dp);

            if (gender.equalsIgnoreCase("Male"))
                gender = "0";
            else
                gender = "1";

            String dob = bDay;
            String dobSplit[] = dob.split("/");
            String day = dobSplit[0];
            String month = dobSplit[1];
            String year = dobSplit[2];

            int days =  Integer.parseInt(day);
            int months =  Integer.parseInt(month);
            int years =  Integer.parseInt(year);

            strBuild = new StringBuilder();

            // enter your date of birth
            Calendar dateOfYourBirth = new GregorianCalendar(years , months , days);
            Calendar today = Calendar.getInstance();
            yourAge = today.get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR);
            dateOfYourBirth.add(Calendar.YEAR, yourAge);
            if (today.before(dateOfYourBirth)) {
                yourAge--;
            }

            strBuild.append(yourAge);
        }


        //these details get set from facebook/google data
        if(name == null)
            et_detail_name.setText(gName);
        else
            et_detail_name.setText(name+" "+l_name);

        et_detail_name.setEnabled(false);

        if(email == null)
            et_detail_email.setText(gEmail);
        else
            et_detail_email.setText(email);

        if(dp == null) {
            Picasso.with(this).load(prefs.getUser_gpicture()).transform(new CircleTransform())
                    .into(img_fb_dp);
        }
        else {
            Picasso.with(this).load(dp).transform(new CircleTransform()).into(img_fb_dp);
        }
    }

    /*
     * Method to check if the Name & Email is filled in EditText or not
     * If not then show a Toast else fire Intent to next screen
     */
    public void verifyDetails(View view){

        //String initialized to get above mentioned edittext values
        mName = et_detail_name.getText().toString();            //can't change name
        mEmail = et_detail_email.getText().toString();          //can change email

        if(mName == null || mName.equalsIgnoreCase("")){
            Snackbar.make(view, "Please Enter Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(mEmail == null || !mEmail.contains("@")){
            Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            makeJsonObjectRequest();
            Intent in = new Intent(CheckDetails.this, QrEntry.class);
            in.putExtra("customer_id", customer_id);
            startActivity(in);
            finish();
        }
    }

    /**
     * Method to make json object post call to send details to server
     * */

    private void makeJsonObjectRequest() {

        //Json parsing(FB/Google details)
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", mName);
            obj.put("email", mEmail);

            if (dp == null)
                obj.put("image", prefs.getUser_gpicture());
            else
                obj.put("image", dp);

            obj.put("mobile_number", phoneNo);

            if(bDay == null)
                obj.put("age", gbBday);
            else
                obj.put("age", strBuild);

            if(fb_location == null)
                obj.put("city", gLoc);
            else
                obj.put("city", fb_location);

            if(gender == null)
                obj.put("gender", gGender);
            else
                obj.put("gender", gender);

            if (prefs.getFb_Token() == null)
                obj.put("facebook_auth", gToken);
            else
                obj.put("facebook_auth", prefs.getFb_Token());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Check Data", obj.toString());

        //post call to send credentials to server (FB/Google)
        postJsonData(Config.innDemand+"customers/save/", obj.toString());

    }

    /*
     * main method to hit server with all the details either from FB/Google.
     * Getting Customer ID as a Response in below method
     */
    public void postJsonData(String url, String userData){

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data====="+requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("yohaha=custmor==success==="+response);

                try {
                    JSONObject object = new JSONObject(response);

                    customer_id = object.getString("customer_id");
                    prefs.setCustomer_Id(customer_id);
                    //Toast.makeText(CheckDetails.this, customer, Toast.LENGTH_LONG).show();

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        //mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /*
     * Method to back press from the device
     * Either to go to last screen or If the last screen working finished then close the app
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(prefs.getFacebook_logged_In() == true){
            if(LoginManager.getInstance()!=null)
                prefs.clearPref();
            LoginManager.getInstance().logOut();
            prefs.setFacebook_logged_In(false);
            finish();

        }else if(prefs.getGoogle_logged_In() == true){
            prefs.setGoogle_logged_In(false);
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            prefs.clearPref();
                            finish();
                        }
                    });
        }
    }
}
