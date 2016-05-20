package demand.inn.com.inndemand.roomservice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Cache;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import demand.inn.com.inndemand.R;
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

    //UI call area
    LinearLayout backpress;
    EditText say_something;
    TextView now, pickTime;
    private Calendar calendar;
    TimePicker timePick;

    //Others
    String saySomething;
    private String format = "";

    //Class call Area
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        appController = new AppController();

        getSupportActionBar().hide();

        //UI initialize area
        backpress = (LinearLayout) findViewById(R.id.backPress_laundry);
        say_something = (EditText) findViewById(R.id.say_something_laundry);

        //TextView UI Initialize area
        now = (TextView) findViewById(R.id.now_laundry);
        pickTime = (TextView) findViewById(R.id.pickTime_laundry);

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                final int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(Laundry.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
////                eReminderTime.setText( selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
                new TimePickerFragment();
            }
        });

        //TImePicker UI Initialize area
        timePick = new TimePicker(this);

        //Calender Area
        calendar = Calendar.getInstance();

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get("");
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
        // Cached response doesn't exists. Make network call here
        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void nowPick(View view){

    }

    public void setTime(View view) {
        int hour = timePick.getCurrentHour();
        int min = timePick.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
//        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
//                .append(" ").append(format));
    }

    public void backPress(View view){
        onBackPressed();
    }

}
