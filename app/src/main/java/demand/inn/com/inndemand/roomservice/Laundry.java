package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 4/5/16.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI initialize area
        backpress = (LinearLayout) findViewById(R.id.backPress_laundry);
        say_something = (EditText) findViewById(R.id.say_something_laundry);

        //TextView UI Initialize area
        now = (TextView) findViewById(R.id.now_laundry);
        pickTime = (TextView) findViewById(R.id.pickTime_laundry);

        //TImePicker UI Initialize area
        timePick = new TimePicker(this);

        //Calender Area
        calendar = Calendar.getInstance();

    }

    public void nowPick(){

    }

    public void pickTime(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Laundry.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            //    eReminderTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
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

    public void backPress(){
        onBackPressed();
    }
}
