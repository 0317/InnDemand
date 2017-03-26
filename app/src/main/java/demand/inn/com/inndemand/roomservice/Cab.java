package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class Cab extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //   static int key to match current time/date code
    static final int TIME_DIALOG_ID = 1111;

    //UI call area
    LinearLayout ll_confirm;
    Toolbar toolbar;
    TextView txt_now, txt_pickTime, txt_changeTime, txt_setTime, txt_set_loc;
    TextView txt_note;
    CoordinatorLayout coordinatorLayout;
    LinearLayout ll_choose_destination, ll_cancel_wakeUp;
    EditText et_say_something;

    //Others
    String saySomething;

//    String and others to get current time and date
    Calendar c;
    SimpleDateFormat df, date;
    String formattedDate, getDate;
    String finalTime;
    String getTime;
    private int hour;
    private int minute;

    //Google Place AutoComplete API
    Activity mActivity;
    int PLACE_PICKER_REQUEST = 1;
    String choosenAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cab);
//        Utility Class Initialisation
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        method to hide default toolbar
        getSupportActionBar().hide();

//        Custom toolbar Class call
//        Setting Title and icons in toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.cab_call);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Google place picker Initialisation
        final PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        mActivity = this;

        //UI Initialize area
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        txt_note = (TextView) findViewById(R.id.note_cab);
        txt_note.setText(prefs.getCabnote());
        txt_now = (TextView) findViewById(R.id.now_cab);
        txt_pickTime = (TextView) findViewById(R.id.currentTime_cab);
        txt_setTime = (TextView) findViewById(R.id.set_time);
        txt_set_loc = (TextView) findViewById(R.id.set_location);
        txt_changeTime = (TextView) findViewById(R.id.changeTime_cab);
        ll_choose_destination = (LinearLayout) findViewById(R.id.choose_destination);
        ll_cancel_wakeUp = (LinearLayout) findViewById(R.id.cancel_wakeUp);
        et_say_something = (EditText) findViewById(R.id.say_something);
        txt_changeTime.setVisibility(View.GONE);

        ll_confirm = (LinearLayout) findViewById(R.id.confirm_demand_click_cab);

        //Settings static values for the activity (fetched from server)
        txt_note.setText(prefs.getCabnote());

//        Click to open Google Place picker API screen to select location
        ll_choose_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                        startActivityForResult(builder.build(mActivity), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
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

        txt_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                // formattedDate have current date/time
                finalTime = formattedDate;
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
//        Sending all requirements to server with this click
        ll_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //string call to get value of edittext
                saySomething = et_say_something.getText().toString().trim();

                if (saySomething == null) {

                } else {

                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("checkin_id", prefs.getCheckin_Id());
                        obj.put("request_time", formattedDate);
                        obj.put("req_time", finalTime);
                        obj.put("comments", saySomething);
                        obj.put("destination", choosenAddress);

                        postJsonData(Config.innDemand + "cab/save/", obj.toString());

                        et_say_something.getText().clear();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        If wake-up call is set it is to cancel that call
        ll_cancel_wakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_setTime.setText("");
                txt_changeTime.setVisibility(View.GONE);
                txt_pickTime.setText("PICK A TIME:");
                txt_pickTime.setEnabled(true);
            }
        });

    }

//    Final result comes in this method for Google place API when User selects Location to go.
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final LatLng latLng = place.getLatLng();
            final CharSequence address = place.getAddress();
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }
            choosenAddress = address.toString();
            System.out.println("Place Data===="+address+"====="+latLng.longitude+"===="+latLng.latitude);
            txt_set_loc.setText(address);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

        txt_setTime.setText(getTime);
        txt_changeTime.setVisibility(View.VISIBLE);
        txt_pickTime.setText("CAB TIME:");
        txt_pickTime.setEnabled(false);

        finalTime = getDate+" "+getTime;
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
