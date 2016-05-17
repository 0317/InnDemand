package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 4/5/16.
 */
public class Beverages extends AppCompatActivity {

    //Utility class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    TextView water, soda, ice, glass;
    ImageView waterPlus, waterMinus, sodaPlus, sodaMinus, icePlus, iceMinus, glassPlus, glassMinus;
    LinearLayout confirmDemand;

    //Others
    int count = 0;
    String waterText, sodaText, iceText, glassText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beverages);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI Initialize area
        water = (TextView) findViewById(R.id.waterCount_beverage);
        soda = (TextView) findViewById(R.id.sodaCount_beverage);
        ice = (TextView) findViewById(R.id.iceCount_beverage);
        glass = (TextView) findViewById(R.id.glassCount_beverage);
        confirmDemand = (LinearLayout) findViewById(R.id.confirm_demand_click_bedTea);

        //Click call area (ImageView)
        waterMinus = (ImageView) findViewById(R.id.waterMinus_beverage);
        waterPlus = (ImageView) findViewById(R.id.waterPlus_beverage);
        sodaMinus = (ImageView) findViewById(R.id.sodaMinus_beverage);
        sodaPlus = (ImageView) findViewById(R.id.sodaPlus_beverage);
        iceMinus = (ImageView) findViewById(R.id.iceMinus_beverage);
        icePlus = (ImageView) findViewById(R.id.icePlus_beverage);
        glassMinus = (ImageView) findViewById(R.id.glassMinus_beverage);
        glassPlus = (ImageView) findViewById(R.id.glassPlus_beverage);
    }

    public void waterMinus(){
        waterText = water.getText().toString().trim();
        if(waterText == "0"){
            waterMinus.setEnabled(false);
        }else {
            count--;
            water.setText(count);
        }
    }

    public void waterPlus(){
        count++;
        water.setText(count);
    }

    public void sodaMinus(){
        sodaText = soda.getText().toString().trim();
        if(sodaText == "0"){
            sodaMinus.setEnabled(false);
        }else {
            count--;
            soda.setText(count);
        }
    }

    public void sodaPlus(){
        count--;
        soda.setText(count);
    }

    public void iceMinus(){
        iceText = water.getText().toString().trim();
        if(iceText == "0"){
            iceMinus.setEnabled(false);
        }else {
            count--;
            ice.setText(count);
        }
    }

    public void icePlus(){
        count++;
        ice.setText(count);
    }

    public void glassMinus(){
        glassText = water.getText().toString().trim();
        if(glassText == "0"){
            glassMinus.setEnabled(false);
        }else {
            count--;
            glass.setText(count);
        }
    }

    public void glassPlus(){
        count--;
        glass.setText(count);
    }

    public void confirmDemand(){
        if(water == null && soda == null && ice == null && glass == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

        }
    }

    public void backPress(){
        onBackPressed();
    }
}
