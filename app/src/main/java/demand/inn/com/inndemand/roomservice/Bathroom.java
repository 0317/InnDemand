package demand.inn.com.inndemand.roomservice;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    //UI call area
    CheckBox towels, soap, maintainance;
    LinearLayout backPress, confirm;
    Snackbar snackbar;
    EditText say_something_bell;

    //Class call
    AppController appController;

    //Date & Time
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    //Others
    String towel_value = "0", soap_value= "0", main_value = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bathroom);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI Initialize area
        backPress = (LinearLayout) findViewById(R.id.backpress_bath);
        confirm = (LinearLayout) findViewById(R.id.confirm_demand_click_bath);

        //UI CheckBox Initialize area
        towels = (CheckBox) findViewById(R.id.towels_bath);
        soap = (CheckBox) findViewById(R.id.soap_bath);
        maintainance = (CheckBox) findViewById(R.id.maintainance_bath);

        say_something_bell = (EditText) findViewById(R.id.say_something_bell);

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

        //Selection of items(Towels/Soap/maintainance) for the room
        towels.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(towels.isChecked()){
                    towels.setChecked(true);
                    towel_value = "1";
                }else{
                    towels.setChecked(false);
                    towel_value = "0";
                }
            }
        });

        soap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(soap.isChecked()){
                    soap.setChecked(true);
                    soap_value = "1";

                }else{
                    soap.setChecked(false);
                    soap_value = "0";
                }
            }
        });

        maintainance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(maintainance.isChecked()){
                    maintainance.setChecked(true);
                    main_value = "1";

                }else{
                    maintainance.setChecked(false);
                    main_value = "0";
                }
            }
        });
    }

    //OnClick to confirm demands for the room
    public void confirmDemand(View view){

        String comment = say_something_bell.getText().toString().trim();

        if(towels.isChecked() || soap.isChecked() || maintainance.isChecked()){

            JSONObject obj  =new JSONObject();
            try {
                obj.put("checkin_id", "2");
                obj.put("request_time", formattedDate);
                obj.put("comments", comment);
                obj.put("towels", towel_value);
                obj.put("toiletries", soap_value);
                obj.put("maintenance", main_value);

                postJsonData(Config.innDemand+"bathessentials/save/", obj.toString());
                say_something_bell.getText().clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(towels.isChecked() == false && soap.isChecked() == false && maintainance.isChecked() == false){
//            snackbar = Snackbar.make(coordinatorLayout, "Please fill to confirm", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null);
////                        View snackbarView = snackbar.getView();
////                        snackbarView.setBackgroundColor(Color.YELLOW);
////                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
////                        textView.setTextColor(getResources().getColor(R.color.confirm_demand_click));
//            snackbar.show();
        }
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
