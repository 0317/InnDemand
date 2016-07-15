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

    //UI
    EditText detail_name, detail_email, detail_phone;
    ImageView fb_dp;

    //Preference Area
    SharedPreferences settings;
    Bundle getBundle = null;

    // Progress dialog
    private ProgressDialog pDialog;

    //Others
    String name, email, dp, l_name, gender, bDay, gGender, fb_location, gToken = "none";
    String gName, gEmail, gDP, gbBday = "none", gLoc = "none";
    String mName, mEmail, phoneNo = "none";
    String tokenss = "";
    String customer_id = "";
    int yourAge;
    StringBuilder strBuild;

    // temporary string to show the parsed response
    private String jsonResponse;
    private static String TAG = CheckDetails.class.getSimpleName();

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.checkdetails);

        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        pDialog = new ProgressDialog(CheckDetails.this);

        //UI Initialized here
        detail_name = (EditText) findViewById(R.id.fb_name);
        detail_email = (EditText) findViewById(R.id.fb_email);

        //if the device is registered
       /* if(isRegistered()){
            startService(new Intent(this, NotificationListener.class));
        }

//        if the device is not already registered
        if (!isRegistered()) {
//            registering the device
            registerDevice();
        } else {
//            if the device is already registered
//            displaying a toast
            Toast.makeText(CheckDetails.this, "Already registered...", Toast.LENGTH_SHORT).show();
        }*/

        fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation
        detail_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

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


        //details set from facebook/google data
        if(name == null)
            detail_name.setText(gName);
        else
        detail_name.setText(name+" "+l_name);

        detail_name.setEnabled(false);

        if(email == null)
        detail_email.setText(gEmail);
        else
        detail_email.setText(email);

        if(dp == null) {
            Picasso.with(this).load(prefs.getUser_gpicture()).transform(new CircleTransform()).into(fb_dp);
        }
        else {
            Picasso.with(this).load(dp).transform(new CircleTransform()).into(fb_dp);
        }
    }

    public void verifyDetails(View view){

        //String initialized to get above mentioned edittext values
        mName = detail_name.getText().toString();            //can't change name
        mEmail = detail_email.getText().toString();          //can change email

        if(mName == null || mName.equalsIgnoreCase("")){
            Snackbar.make(view, "Please Enter Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(mEmail == null || !mEmail.contains("@")){
            Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            makeJsonObjectRequest();
            Intent in = new Intent(CheckDetails.this, QRscanning.class);
            in.putExtra("customer_id", customer_id);
            startActivity(in);
            finish();
        }
    }

    /**
     * Method to make json object post call
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
//                    Toast.makeText(CheckDetails.this, customer, Toast.LENGTH_LONG).show();

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
//        mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    //Notification Area

    private boolean isRegistered() {
        //Getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);

        //Getting the value from shared preferences
        //The second parameter is the default value
        //if there is no value in sharedprference then it will return false
        //that means the device is not registered
        return sharedPreferences.getBoolean(Config.REGISTERED, false);
    }

   /* private void registerDevice() {
        //Creating a firebase object

        tokenss =  FirebaseInstanceId.getInstance().getToken();
        prefs.setRefreshToken(tokenss);
//        Firebase firebase = new Firebase(Config.FIREBASE_APP);

        //Pushing a new element to firebase it will automatically create a unique id
//        Firebase newFirebase = firebase.push();

        //Creating a map to store name value pair
        Map<String, String> val = new HashMap<>();

        //pushing msg = none in the map
        val.put("msg", "none");

        //saving the map to firebase
//        newFirebase.setValue(val);

        //Getting the unique id generated at firebase
//        String uniqueId = newFirebase.getKey();

        Log.d("FCM Unique ID", tokenss);

        //Finally we need to implement a method to store this unique id to our server
//        sendIdToServer(uniqueId);
    }

    private void sendIdToServer(final String uniqueId) {
        //Creating a progress dialog to show while it is storing the data on server
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering device...");
        progressDialog.show();

        //getting the email entered
        final String email = detail_email.getText().toString().trim();

        //Creating a string request
        StringRequest req = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //dismissing the progress dialog
                        progressDialog.dismiss();

                        //if the server returned the string success
                        if (response.trim().equalsIgnoreCase("success")) {
                            //Displaying a success toast
                            Toast.makeText(CheckDetails.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                            //Opening shared preference
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);

                            //Opening the shared preferences editor to save values
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Storing the unique id
                            editor.putString(Config.UNIQUE_ID, uniqueId);

                            //Saving the boolean as true i.e. the device is registered
                            editor.putBoolean(Config.REGISTERED, true);

                            //Applying the changes on sharedpreferences
                            editor.apply();

                            //Starting our listener service once the device is registered
                            startService(new Intent(getBaseContext(), NotificationListener.class));
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //adding parameters to post request as we need to send firebase id and email
                params.put("firebaseid", uniqueId);
                params.put("email", email);
                return params;
            }
        };

        //Adding the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(prefs.getFacebook_logged_In() == true){
            if(LoginManager.getInstance()!=null)
                prefs.clearPref();
            LoginManager.getInstance().logOut();
            prefs.setFacebook_logged_In(false);
//            Intent in = new Intent(CheckDetails.this, SplashScreen.class);
//            startActivity(in);
            finish();

        }else if(prefs.getGoogle_logged_In() == true){
            prefs.setGoogle_logged_In(false);
//                if(mGoogleApiClient.isConnected())
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            prefs.clearPref();
//                            Intent in = new Intent(CheckDetails.this, SplashScreen.class);
//                            startActivity(in);
                            finish();
                        }
                    });
//                mGoogleApiClient.disconnect();
        }
    }
}
