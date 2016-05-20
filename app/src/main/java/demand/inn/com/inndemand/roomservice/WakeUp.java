package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class WakeUp extends AppCompatActivity {

    //Utility class call
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call
    LinearLayout back_press, confirm_demand, changeTime, cancel_wakeUp;
    RadioButton today, tomorrow;
    TextView currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wakemeup);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI initlaized
//        back_press = (LinearLayout) findViewById(R.id.back_press_wakeup);
        confirm_demand = (LinearLayout) findViewById(R.id.confirm_demand_click_wakeup);
//        changeTime = (LinearLayout) findViewById(R.id.changeTime_wakeup);
//        cancel_wakeUp = (LinearLayout) findViewById(R.id.cancel_wakeUp);

        today = (RadioButton) findViewById(R.id.radioToday_wakeup);
        tomorrow = (RadioButton) findViewById(R.id.radioTomorrow_wakeup);

        currentTime = (TextView) findViewById(R.id.currentTime_wakeup);

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
    }

    //cancel wake-up call set in room services
    public void cancelWakeup(View view){

    }

    public void confirmDemand(View view){
//        if(today.isChecked() == false && tomorrow.isChecked() == false){
//
//        }else{
//
//        }
    }

    public void backPress(){
        onBackPressed();
    }
}
