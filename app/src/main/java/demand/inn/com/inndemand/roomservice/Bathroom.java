package demand.inn.com.inndemand.roomservice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class Bathroom extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area foir the screen
    CheckBox cb_towels, cb_soap, cb_maintainance;
    LinearLayout ll_confirm;
    EditText et_say_something_bell;
    Toolbar toolbar;

    //Linearlayout to show/hide options provided by hotel (Towel/Soap/Maintenance)
    LinearLayout ll_bath_towel, ll_bath_soap, ll_bath_maintenance;

//    String and others to get current time and date
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

//    String vaues assigned 0 initally
//    0 show no request for anything from User (if 1 that means User requested for any of these
//      equals value 1)
    String towel_value = "0", soap_value= "0", main_value = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bathroom);
//        Utility Class Initialisation
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        method to hide default toolbar
        getSupportActionBar().hide();

//        Custom toolbar Class call
//        Setting Title and icons in toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bath_essentials);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize area
        ll_confirm = (LinearLayout) findViewById(R.id.confirm_demand_click_bath);
        ll_bath_towel = (LinearLayout) findViewById(R.id.bath_towel);
        ll_bath_soap = (LinearLayout) findViewById(R.id.bath_soap);
        ll_bath_maintenance = (LinearLayout) findViewById(R.id.bath_maintenance);

        //UI CheckBox Initialize area
        cb_towels = (CheckBox) findViewById(R.id.towels_bath);
        cb_soap = (CheckBox) findViewById(R.id.soap_bath);
        cb_maintainance = (CheckBox) findViewById(R.id.maintainance_bath);

        et_say_something_bell = (EditText) findViewById(R.id.say_something_bell);

//        Coding to get current time/date
        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

        if(prefs.getBath_towel() == false)
            ll_bath_towel.setVisibility(View.GONE);
        else
            ll_bath_towel.setVisibility(View.VISIBLE);

        if(prefs.getBath_toiletries() == false)
            ll_bath_soap.setVisibility(View.GONE);
        else
            ll_bath_soap.setVisibility(View.VISIBLE);

        if(prefs.getBath_maintenance()== false)
            ll_bath_maintenance.setVisibility(View.GONE);
        else
            ll_bath_maintenance.setVisibility(View.VISIBLE);


        //Selection of items(Towels/Soap/maintainance) for the room
//        Boolean value check = true and int value = 1 means User demamds for towel
//        Boolean value check = false and int value = 0 means no demand
        cb_towels.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(cb_towels.isChecked()){
                    cb_towels.setChecked(true);
                    towel_value = "1";
                }else{
                    cb_towels.setChecked(false);
                    towel_value = "0";
                }
            }
        });

        cb_soap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(cb_soap.isChecked()){
                    cb_soap.setChecked(true);
                    soap_value = "1";

                }else{
                    cb_soap.setChecked(false);
                    soap_value = "0";
                }
            }
        });

        cb_maintainance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_maintainance.isChecked()){
                    cb_maintainance.setChecked(true);
                    main_value = "1";

                }else{
                    cb_maintainance.setChecked(false);
                    main_value = "0";
                }
            }
        });
    }

//    Button Click at the bottom of the screen
//    Sending all requirements to server with this click
    public void confirmDemand(View view){

        String comment = et_say_something_bell.getText().toString().trim();

        if(cb_towels.isChecked() || cb_soap.isChecked() || cb_maintainance.isChecked()){

            JSONObject obj  =new JSONObject();
            try {
                obj.put("checkin_id", prefs.getCheckin_Id());
                obj.put("request_time", formattedDate);
                obj.put("comments", comment);
                obj.put("towels", towel_value);
                obj.put("toiletries", soap_value);
                obj.put("maintenance", main_value);

                postJsonData(Config.innDemand+"bathessentials/save/", obj.toString());
                et_say_something_bell.getText().clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(cb_towels.isChecked() == false && cb_soap.isChecked() == false
                && cb_maintainance.isChecked() == false){
//            snackbar = Snackbar.make(coordinatorLayout, "Please fill to confirm",
// Snackbar.LENGTH_LONG)
//                    .setAction("Action", null);
////                        View snackbarView = snackbar.getView();
////                        snackbarView.setBackgroundColor(Color.YELLOW);
////                        TextView textView = (TextView) snackbarView.findViewById(android.s
// upport.design.R.id.snackbar_text);
////                        textView.setTextColor(getResources().getColor(R.color.
// confirm_demand_click));
//            snackbar.show();
        }
    }

//    Volley Library main Method to POST data to the server
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
                System.out.println("yohaha=success==="+response);
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

    public void backPress(View view){
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
