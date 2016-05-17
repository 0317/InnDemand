
package demand.inn.com.inndemand.roomservice;

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
public class BedTea extends AppCompatActivity {

    //Utility class call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    TextView tea, coffee;
    ImageView teaPlus, teaMinus, coffeePlus, coffeeMinus;
    LinearLayout confirmDemand;

    //Others
    int count = 0;
    String teaText, coffeeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedtea);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI Initialize area
        tea = (TextView) findViewById(R.id.teaCount_bedTea);
        coffee = (TextView) findViewById(R.id.coffeeCount_bedTea);
        confirmDemand = (LinearLayout) findViewById(R.id.confirm_demand_click_bedTea);

        //Click call area (ImageView)
        teaMinus = (ImageView) findViewById(R.id.teaMinus_bedTea);
        teaPlus = (ImageView) findViewById(R.id.teaPlus_bedTea);
        coffeeMinus = (ImageView) findViewById(R.id.coffeeMinus_bedTea);
        coffeePlus = (ImageView) findViewById(R.id.coffeePlus_bedTea);
    }

    public void teaMinus(){
        teaText = tea.getText().toString().trim();
        if(teaText == "0"){
            teaMinus.setEnabled(false);
        }else {
            count--;
            tea.setText(count);
        }
    }

    public void teaPlus(){
        count++;
        tea.setText(count);
    }

    public void coffeeMinus(){
        coffeeText = coffee.getText().toString().trim();
        if(coffeeText == "0"){
            coffeeMinus.setEnabled(false);
        }else {
            count--;
            coffee.setText(count);
        }
    }

    public void coffeePlus(){
        count--;
        coffee.setText(count);
    }

    public void confirmDemand(){
        if(tea == null && coffee == null){
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
