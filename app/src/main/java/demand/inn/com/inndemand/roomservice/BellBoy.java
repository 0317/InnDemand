package demand.inn.com.inndemand.roomservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
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

    //Class call
    AppController appController;

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

    public void backPress(View view){
        onBackPressed();
    }


    public void confirmDemand(View view){
        //string call to get value of edittext
        saySomething = say_something.getText().toString().trim();


    }
}
