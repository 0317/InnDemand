package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */
public class Beverages extends AppCompatActivity {

    //Utility class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    CheckBox water_check, soda_check, ice_check, glass_check;
    LinearLayout confirmDemand;

    //Linearlayout for Options provided for beverages side services (water, soda, ice, glass)
    LinearLayout water_bottle, soda_bottle, ice_bottle, glass_bottle;

    EditText say_Something;

    //Others
    String water_value = "0", soda_value= "0", ice_value = "0", glass_value = "0";

    //Date & Time
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beverages);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.beverages);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize area
        water_bottle = (LinearLayout) findViewById(R.id.water_bottle);
        soda_bottle = (LinearLayout) findViewById(R.id.soda_bottle);
        ice_bottle = (LinearLayout) findViewById(R.id.ice_bottle);
        glass_bottle = (LinearLayout) findViewById(R.id.glass_bottle);

        confirmDemand = (LinearLayout) findViewById(R.id.confirm_demand_click_beverages);
        water_check = (CheckBox) findViewById(R.id.water_bottles_beverages);
        soda_check = (CheckBox) findViewById(R.id.soda_bottles_beverages);
        ice_check = (CheckBox) findViewById(R.id.ice_buckets_beverages);
        glass_check = (CheckBox) findViewById(R.id.glasses_beverages);

        say_Something = (EditText) findViewById(R.id.say_something_bell);


        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

        //Condition to show which options hotel provides
        if(prefs.getWater() == false)
            water_bottle.setVisibility(View.GONE);
        else
            water_bottle.setVisibility(View.VISIBLE);

        if(prefs.getSoda() == false)
            soda_bottle.setVisibility(View.GONE);
        else
            soda_bottle.setVisibility(View.VISIBLE);

        if(prefs.getIce_bucket() == false)
            ice_bottle.setVisibility(View.GONE);
        else
            ice_bottle.setVisibility(View.VISIBLE);

        if(prefs.getGlass() == false)
            glass_bottle.setVisibility(View.GONE);
        else
            glass_bottle.setVisibility(View.VISIBLE);

        //Click call area (ImageView)
        //Selection of items(Glass/ Water/ Soda/ Ice) for the room
        water_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(water_check.isChecked()){
                    water_check.setChecked(true);
                    water_value = "1";
                }else{
                    water_check.setChecked(false);
                    water_value = "0";
                }
            }
        });

        soda_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(soda_check.isChecked()){
                    soda_check.setChecked(true);
                    soda_value = "1";
                }else{
                    soda_check.setChecked(false);
                    soda_value = "0";
                }
            }
        });

        ice_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(ice_check.isChecked()){
                    ice_check.setChecked(true);
                    ice_value = "1";
                }else{
                    ice_check.setChecked(false);
                    ice_value = "0";
                }
            }
        });

        glass_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(glass_check.isChecked()){
                    glass_check.setChecked(true);
                    glass_value = "1";
                }else{
                    glass_check.setChecked(false);
                    glass_value = "0";
                }
            }
        });


        confirmDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saySomething = say_Something.getText().toString().trim();

                if(water_check.isChecked() == false && soda_check.isChecked() == false && ice_check.isChecked() == false && glass_check.isChecked() == false){

                }else{
                    JSONObject obj  =new JSONObject();
                    try {
                        obj.put("checkin_id", prefs.getCheckin_Id());
                        obj.put("request_time", formattedDate);
                        obj.put("comments", saySomething);
                        obj.put("water", water_value);
                        obj.put("soda", soda_value);
                        obj.put("ice", ice_value);
                        obj.put("glass", glass_value);

                        postJsonData(Config.innDemand+"water/save/", obj.toString());

                        say_Something.getText().clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    //API call method to POST data to the server
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
