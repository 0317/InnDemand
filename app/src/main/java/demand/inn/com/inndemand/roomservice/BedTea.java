
package demand.inn.com.inndemand.roomservice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.TimeZone;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */
public class BedTea extends AppCompatActivity {

    //Utility class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //   static int key to match current time/date code
    static final int TIME_DIALOG_ID = 1111;

    //UI call area
    TextView txt_tea, txt_coffee;
    ImageView img_teaPlus, img_teaMinus, img_coffeePlus, img_coffeeMinus;
    LinearLayout confirmDemand;
    EditText et_say_something_bell;
    Toolbar toolbar;
    TextView txt_now, txt_pickTime;

    //Linearlayout for (Tea/Coffee) options by Hotel
    LinearLayout ll_bed_tea, ll_bed_coffee;


//    int and String count and value for Tea & coffee
    int tea_count = 0, coffee_count = 0;
    String teaText = null, coffeeText = null;

//    String and others to get current time and date
    Calendar c;
    SimpleDateFormat df, date;
    String formattedDate, getDate;
    String finalTime;
    String getTime;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedtea);
//        Utility Class Initialisation
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        method to hide default toolbar
        getSupportActionBar().hide();

//        Custom toolbar Class call
//        Setting Title and icons in toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bed_tea_call);
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
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        getDate = date.format(c.getTime());
        // formattedDate have current date/time

        //UI Initialize area
        ll_bed_tea = (LinearLayout) findViewById(R.id.bed_tea);
        ll_bed_coffee = (LinearLayout) findViewById(R.id.bed_coffee);

        txt_tea = (TextView) findViewById(R.id.teaCount_bedTea);
        txt_coffee = (TextView) findViewById(R.id.coffeeCount_bedTea);
        confirmDemand = (LinearLayout) findViewById(R.id.confirm_demand_click_bedTea);

        //TextView UI Initialize area
        txt_now = (TextView) findViewById(R.id.now_bedtea);
        txt_pickTime = (TextView) findViewById(R.id.pickTime_bedtea);

        et_say_something_bell = (EditText) findViewById(R.id.say_something_bell);

        //Click call area (ImageView)
        img_teaMinus = (ImageView) findViewById(R.id.teaMinus_bedTea);
        img_teaPlus = (ImageView) findViewById(R.id.teaPlus_bedTea);
        img_coffeeMinus = (ImageView) findViewById(R.id.coffeeMinus_bedTea);
        img_coffeePlus = (ImageView) findViewById(R.id.coffeePlus_bedTea);

        //Area to set Bed Tea/Coffe Options
        if(prefs.getTea() == false)
            ll_bed_tea.setVisibility(View.GONE);
        else
            ll_bed_tea.setVisibility(View.VISIBLE);

        if(prefs.getCoffee() == false)
            ll_bed_coffee.setVisibility(View.GONE);
        else
            ll_bed_coffee.setVisibility(View.VISIBLE);

//        Tea count ordered by a User
//        Number of Tea ordered
        img_teaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1=(Integer.parseInt(txt_tea.getText().toString()));

                if(s1 < 0) {
                    img_teaMinus.setEnabled(false);
                } else if(s1 > 0) {
                    img_teaMinus.setEnabled(true);
                    --tea_count;
                    txt_tea.setText(String.valueOf(tea_count));
                }
            }
        });
        img_teaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++tea_count;
                txt_tea.setText(String.valueOf(tea_count));
            }
        });

//        Now click to pick current time and send to server
        txt_now.setOnClickListener(new View.OnClickListener() {
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

//        Open pop-ups by matching key and allows to set time
        txt_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

//        Button Click at the bottom of the screen
//    Sending all requirements to server with this click
        confirmDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teaText == null && coffeeText == null || teaText == "0" && coffeeText == "0"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BedTea.this);
                    builder.setMessage("Please select atleast one item");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Can't Process");
                    dialog.show();
                }else{

                    String teaData = txt_tea.getText().toString();
                    String coffeeData = txt_coffee.getText().toString();
                    String saySomething = et_say_something_bell.getText().toString().trim();

                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("checkin_id", prefs.getCheckin_Id());
                        obj.put("request_time", formattedDate);
                        obj.put("req_time", finalTime);
                        obj.put("tea_count", teaData);
                        obj.put("coffee_count", coffeeData);
                        obj.put("comments", saySomething);

                        postJsonData(Config.innDemand+"teacoffee/save/", obj.toString());

                        et_say_something_bell.getText().clear();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    //    Coding(different method to get current time/Date in required format)
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

//    Coffee count when user orders a coffee
    public void coffeeMinus(View view){

        int s2=(Integer.parseInt(txt_coffee.getText().toString()));

        if(s2 < 0) {
            img_coffeeMinus.setEnabled(false);
        } else if(s2 > 0) {
            img_coffeeMinus.setEnabled(true);
            --coffee_count;
            txt_coffee.setText(String.valueOf(coffee_count));
        }

    }

    public void coffeePlus(View view){
        ++coffee_count;
        txt_coffee.setText(String.valueOf(coffee_count));
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
