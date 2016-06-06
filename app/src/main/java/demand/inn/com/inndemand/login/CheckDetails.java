package demand.inn.com.inndemand.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CircleTransform;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.BaseActivity;

/**
 * Created by akash
 */

public class CheckDetails extends BaseActivity {

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
    String name, email, dp, l_name, gender, bDay, fb_location;
    String gName, gEmail, gDP;
    String mName, mEmail;
    // temporary string to show the parsed response
    private String jsonResponse;
    private static String TAG = CheckDetails.class.getSimpleName();
    View view;

    //Current Time
    Calendar cal;
    Date currentLocalTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkdetails);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);

        prefs.setIs_task_completed(true);

        cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Delhi"));
        currentLocalTime = cal.getTime();

        pDialog = new ProgressDialog(CheckDetails.this);


        //UI Initialized here
        detail_name = (EditText) findViewById(R.id.fb_name);
        detail_email = (EditText) findViewById(R.id.fb_email);

        fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation
        detail_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        getBundle = this.getIntent().getExtras();
        if(getBundle == null) {
            gName = prefs.getUser_gname();
            gEmail = prefs.getUser_gemail();
            gDP = prefs.getUser_gpicture();
        }
        else{
            name = getBundle.getString("first_name");
            email = getBundle.getString("email");
            dp = getBundle.getString("profile_pic");
            l_name = getBundle.getString("last_name");
            gender = getBundle.getString("gender");
            bDay = getBundle.getString("birthday");
            fb_location = getBundle.getString("location");
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
//            Intent in = new Intent(CheckDetails.this, QRscanning.class);
//            startActivity(in);
//            finish();
        }
    }

    /**
     * Method to make json object post call
     * */

    private void makeJsonObjectRequest() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("name", mName);
            obj.put("email", mEmail);
            if (dp == null) {
                obj.put("image", prefs.getUser_gpicture());
            } else {
                obj.put("image", dp);
            }
            obj.put("mobile_number", "9899123456");
            obj.put("age", bDay);
            obj.put("city", fb_location);
            obj.put("gender", gender);
            if (prefs.getFb_Token() == null)
                obj.put("facebook_auth", prefs.getG_Token());
            else
                obj.put("facebook_auth", prefs.getFb_Token());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Check Data", obj.toString());

//        String url = Config.innDemand+"customers/save/";

        postJsonData(Config.innDemand + "customers/save/", obj.toString());

//        final ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                url , null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//                        pDialog.hide();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                pDialog.hide();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name","" );
//                params.put("email", "");
//                params.put("", "");
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//    }
    }
}
