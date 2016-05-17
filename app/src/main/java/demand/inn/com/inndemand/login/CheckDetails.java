package demand.inn.com.inndemand.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class CheckDetails extends AppCompatActivity {

    //UI
    EditText fb_name, fb_email, fb_phone;
    ImageView fb_dp;

    //Preference Area
    SharedPreferences settings;
    AppPreferences prefs;

    //Others
    String name, email, phone;
    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkdetails);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);

        //UI Initialized here
        fb_name = (EditText) findViewById(R.id.fb_name);
        fb_email = (EditText) findViewById(R.id.fb_email);
        fb_phone = (EditText) findViewById(R.id.fb_phone);

        fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation
        fb_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //details set from facebook data
//
        fb_name.setText(settings.getString("fb_name", "")); //facebook name
//        fb_name.setEnabled(false);
//
        fb_email.setText(settings.getString("email", ""));  //facebook email
//        fb_email.setEnabled(true);

//        Glide.with(this).load(settings.getString("image", "")).into(fb_dp); //facebook DP

    }

    public void verifyDetails(View view){

        //String initialized to get above mentioned edittext values

        name = fb_name.getText().toString(); //can't change name
        email = fb_email.getText().toString(); ////can change email
        phone = fb_phone.getText().toString(); ////can change phone

        prefs.setUser_fname(name);
        prefs.setUser_email(email);
//        prefs.setUser_phone(Integer.parseInt(phone));

        if(name == null || name.equalsIgnoreCase("")){
            Snackbar.make(view, "Please Enter Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(email == null || !email.contains("@")){
            Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(phone == null || phone.length() <= 9){
            Snackbar.make(view, "Please Enter Correct Phone Number", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Intent in = new Intent(CheckDetails.this, QrScan.class);
            startActivity(in);
            finish();
        }
    }
}
