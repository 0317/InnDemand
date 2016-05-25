package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
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
//        confirm = (LinearLayout) findViewById(R.id.confirm_demand_click_cab);

        //UI Initialize RadioButton area
        today = (RadioButton) findViewById(R.id.radioToday_cab);
        tomorrow = (RadioButton) findViewById(R.id.radioTomorrow_cab);
        now = (RadioButton) findViewById(R.id.radioNow_cab);
        hour = (RadioButton) findViewById(R.id.radioHour_cab);

        today.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(today.isChecked()){
                    today.setChecked(true);
                    tomorrow.setChecked(false);
                }else{
                    today.setChecked(false);
                }
            }
        });

        tomorrow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(tomorrow.isChecked()){
                    tomorrow.setChecked(true);
                    today.setChecked(false);
                }else{
                    tomorrow.setChecked(false);
                }
            }
        });

        now.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(now.isChecked()){
                    now.setChecked(true);
                    hour.setChecked(false);
                }else{
                    now.setChecked(false);
                }
            }
        });

        hour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(hour.isChecked()){
                    hour.setChecked(true);
                    now.setChecked(false);
                }else{
                    hour.setChecked(false);
                }
            }
        });

    }


    //Select time to pick-up for cab
    public void pickTime_cab(View view){
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

    public void confirmDemand(View view){
        if(today.isChecked() == false && tomorrow.isChecked() == false || now.isChecked() == false && hour.isChecked() == false){
            Snackbar.make(view, "Please Select First", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {

        }
    }

    public void backPress(View view){
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
