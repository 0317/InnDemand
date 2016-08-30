package demand.inn.com.inndemand.welcome;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

public class Utils extends AppCompatActivity {

    //Utility Class Call Area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Class Call Area
    Button bt_submit;
    TextView txt_sub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xtrautils);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);



    }
}