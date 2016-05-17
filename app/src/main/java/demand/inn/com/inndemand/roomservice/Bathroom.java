package demand.inn.com.inndemand.roomservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 4/5/16.
 */

public class Bathroom extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    CheckBox towels, soap, maintainance;
    LinearLayout backPress, confirm;

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

        //Selection of items(Towels/Soap/maintainance) for the room
        towels.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(towels.isChecked()){
                    towels.setChecked(true);
                }else{
                    towels.setChecked(false);
                }
            }
        });

        soap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(soap.isChecked()){
                    soap.setChecked(true);

                }else{
                    soap.setChecked(false);

                }
            }
        });

        maintainance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(maintainance.isChecked()){
                    maintainance.setChecked(true);

                }else{
                    maintainance.setChecked(false);

                }
            }
        });
    }

    //OnClick to confirm demands for the room
    public void confirmDemand(){
        if(towels.isChecked() || soap.isChecked() || maintainance.isChecked()){

        }else if(towels.isChecked() == false && soap.isChecked() == false && maintainance.isChecked() == false){

        }
    }

    public void backPress(){
        onBackPressed();
    }
}
