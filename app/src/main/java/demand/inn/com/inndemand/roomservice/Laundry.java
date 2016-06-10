package demand.inn.com.inndemand.roomservice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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

public class Laundry extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    static final int TIME_DIALOG_ID = 1111;

    //UI call area
    LinearLayout backpress;
    EditText say_something;
    TextView now, pickTime;
    private Calendar calendar;
    TimePicker timePick;
    Toolbar toolbar;

    //Others
    String saySomething;
    private String format = "";
    String getTime;
    private int hour;
    private int minute;

    //Class call Area
    AppController appController;

    //Date & Time
    Calendar c;
    SimpleDateFormat df, date;
    String formattedDate, getDate;
    String finalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        appController = new AppController();

        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Laundry");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI initialize area
        say_something = (EditText) findViewById(R.id.say_something_laundry);

        //TextView UI Initialize area
        now = (TextView) findViewById(R.id.now_laundry);
        pickTime = (TextView) findViewById(R.id.pickTime_laundry);

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        getDate = date.format(c.getTime());
        // formattedDate have current date/time

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());

                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                // formattedDate have current date/time
                finalTime =formattedDate;
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add Click Listener
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        true);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub

            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour,minute);

        }
    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        getTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(":").append("00").toString();

        finalTime = getDate+" "+getTime;
    }

    public void confirmDemand(View view){
        //string call to get value of edittext
        saySomething = say_something.getText().toString().trim();

        if(saySomething == null){

        }else {

            JSONObject obj = new JSONObject();

            try {
                obj.put("checkin_id", prefs.getCheckin_Id());
                obj.put("request_time", formattedDate);
                obj.put("req_time", finalTime);
                obj.put("comments", saySomething);

                postJsonData(Config.innDemand + "laundry/save/", obj.toString());

                say_something.getText().clear();

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
