package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class BellBoy extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Class call for the screen
    LinearLayout ll_confirm_demand_click;
    EditText et_say_something;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    //Others to comment in the area provided in the screen
    String saySomething;

//    String and others to get current time and date
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bellboy);
//        Utility Class Initialisation
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        method to hide default toolbar
        getSupportActionBar().hide();

//        Custom toolbar Class call
//        Setting Title and icons in toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bell_boy);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Coding to get current time/date
        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

//        UI Class Initialisation for the screen
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        et_say_something = (EditText) findViewById(R.id.say_something_bell);

//        Button Click at the bottom of the screen
//        Sending all requirements to server with this click
        ll_confirm_demand_click = (LinearLayout) findViewById(R.id.confirm_demand_click);
        ll_confirm_demand_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //string call to get value of edittext
                saySomething = et_say_something.getText().toString().trim();

                if(saySomething == null || saySomething.equalsIgnoreCase("")){
//            snackbar = Snackbar.make(coordinatorLayout, "Please fill to confirm", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null);
//                        View snackbarView = snackbar.getView();
//                        snackbarView.setBackgroundColor(Color.YELLOW);
//                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        textView.setTextColor(getResources().getColor(R.color.confirm_demand_click));
//            snackbar.show();
                }else {

                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("checkin_id", prefs.getCheckin_Id());
                        obj.put("request_time", formattedDate);
                        obj.put("comments", saySomething);

                        postJsonData(Config.innDemand + "bellboy/save/", obj.toString());

                        et_say_something.getText().clear();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
