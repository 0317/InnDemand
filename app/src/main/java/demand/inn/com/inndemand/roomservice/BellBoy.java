package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 4/5/16.
 */

public class BellBoy extends AppCompatActivity {

    //Utility call area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI call area
    LinearLayout backPress;
    EditText say_something;

    //Others
    String saySomething;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bellboy);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().hide();

        //UI initialize arae
        backPress = (LinearLayout) findViewById(R.id.backpress_bell);
        say_something = (EditText) findViewById(R.id.say_something_bell);

    }

    public void backPress(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void confirmDemand(){
        //string call to get value of edittext
        saySomething = say_something.getText().toString().trim();


    }
}
