package demand.inn.com.inndemand.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */
public class PrivacyPolicy extends AppCompatActivity {

    //Utiltiy Class area
    NetworkUtility nu;
    AppPreferences prefs;

    //Header call Toolbar
    Toolbar toolbar;

    //UI Call textView
    TextView text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacypolicy);
        nu = new NetworkUtility(PrivacyPolicy.this);
        prefs = new AppPreferences(PrivacyPolicy.this);

        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.privacy_policy);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //UI Initialize
        text = (TextView) findViewById(R.id.privacycontent);
    }
}
