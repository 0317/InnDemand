package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 4/5/16.
 */
public class Cab extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    RadioButton today, tomorrow, now, hour;
    LinearLayout backPress, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cab);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI Initialize area
        backPress = (LinearLayout) findViewById(R.id.backpress_cab);
        confirm = (LinearLayout) findViewById(R.id.confirm_demand_click_cab);

        //UI Initialize RadioButton area
        today = (RadioButton) findViewById(R.id.radioToday_cab);
        tomorrow = (RadioButton) findViewById(R.id.radioTomorrow_cab);
        now = (RadioButton) findViewById(R.id.radioNow_cab);
        hour = (RadioButton) findViewById(R.id.radioHour_cab);

    }

    //onSelect method for today RadioButton for Cab
    public void todayClick(){
        if(today.isChecked()){
            today.setChecked(true);
            tomorrow.setChecked(false);
        }else{
            today.setChecked(false);
        }
    }

    //onSelect method for tomorrow RadioButton for Cab
    public void tomorrowClick(){
        if(tomorrow.isChecked()){
            tomorrow.setChecked(true);
            today.setChecked(false);
        }else{
            tomorrow.setChecked(false);
        }
    }

    //onSelect method for now pick RadioButton for Cab
    public void nowClick(){
        if(now.isChecked()){
            now.setChecked(true);
            hour.setChecked(false);
        }else{
            now.setChecked(false);
        }
    }

    //onSelect method for hour pick RadioButton for Cab
    public void hourClick(){
        if(hour.isChecked()){
            hour.setChecked(true);
            now.setChecked(false);
        }else{
            hour.setChecked(false);
        }
    }

    //Select time to pick-up for cab
    public void pickTime_cab(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Cab.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //    eReminderTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void confirmDemand(){
        if(today.isChecked() == false && tomorrow.isChecked() == false || now.isChecked() == false && hour.isChecked() == false){

        }else {

        }
    }

    public void backPress(){
        onBackPressed();
    }
}
