
package demand.inn.com.inndemand.roomservice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */
public class BedTea extends AppCompatActivity {

    //Utility class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    TextView tea, coffee;
    ImageView teaPlus, teaMinus, coffeePlus, coffeeMinus;
    LinearLayout confirmDemand;

    //Others
    int tea_count = 0, coffee_count = 0;
    String teaText = null, coffeeText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedtea);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI Initialize area
        tea = (TextView) findViewById(R.id.teaCount_bedTea);
        teaText = tea.getText().toString().trim();
        coffee = (TextView) findViewById(R.id.coffeeCount_bedTea);
        coffeeText = coffee.getText().toString().trim();
//        confirmDemand = (LinearLayout) findViewById(R.id.confirm_demand_click_bedTea);

        //Click call area (ImageView)
        teaMinus = (ImageView) findViewById(R.id.teaMinus_bedTea);
        teaPlus = (ImageView) findViewById(R.id.teaPlus_bedTea);
        coffeeMinus = (ImageView) findViewById(R.id.coffeeMinus_bedTea);
        coffeePlus = (ImageView) findViewById(R.id.coffeePlus_bedTea);

        teaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(teaText == "0"){
                    teaMinus.setEnabled(false);
                }else {
                    --tea_count;
                    tea.setText(String.valueOf(tea_count));
                }
            }
        });
        teaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++tea_count;
                tea.setText(String.valueOf(tea_count));
            }
        });
    }

    public void coffeeMinus(View view){

        if(coffee.getText().toString() == "0"){
            coffeeMinus.setEnabled(false);
            coffee.setText("0");
        }else {
            --coffee_count;
            coffee.setText(String.valueOf(coffee_count));
        }
    }

    public void coffeePlus(View view){
        ++coffee_count;
        coffee.setText(String.valueOf(coffee_count));
    }

    public void confirmDemand(View view){
        if(teaText == null && coffeeText == null || teaText == "0" && coffeeText == "0"){
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

    public void backPress(View view){
        onBackPressed();
    }
}
