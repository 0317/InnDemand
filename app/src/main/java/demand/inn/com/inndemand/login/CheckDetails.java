package demand.inn.com.inndemand.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.adapter.CircleTransform;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class CheckDetails extends AppCompatActivity {

    //Utility Class call
    NetworkUtility nu;
    AppPreferences prefs;

    //UI
    EditText detail_name, detail_email, detail_phone;
    ImageView fb_dp;

    //Preference Area
    SharedPreferences settings;
    Bundle getBundle = null;

    //Others
    String name, email, dp;
    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkdetails);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);

//        prefs.setIs_task_completed(true);

        //UI Initialized here
        detail_name = (EditText) findViewById(R.id.fb_name);
        detail_email = (EditText) findViewById(R.id.fb_email);
//        detail_phone = (EditText) findViewById(R.id.fb_phone);

        fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation
        detail_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        getBundle = this.getIntent().getExtras();
        if(getBundle == null) {
            name = prefs.getUser_gname();
            email = prefs.getUser_gemail();
            dp = prefs.getUser_gpicture();
        }
        else{
            name = getBundle.getString("first_name");
            email = getBundle.getString("email");
            dp = getBundle.getString("profile_pic");
        }

        //details set from facebook/google data
        if(name == null)
            detail_name.setText(prefs.getUser_gname());
        else
        detail_name.setText(name);

        if(email == null)
        detail_email.setText(prefs.getUser_gemail());
        else
        detail_email.setText(email);

        if(dp == null) {
            Picasso.with(this).load(prefs.getUser_gpicture()).transform(new CircleTransform()).into(fb_dp);
        }
        else {
            Picasso.with(this).load(dp).transform(new CircleTransform()).into(fb_dp);
        }
    }

    public void verifyDetails(View view){

        //String initialized to get above mentioned edittext values

        name = detail_name.getText().toString();            //can't change name
        email = detail_email.getText().toString();          //can change email
//        phone = detail_phone.getText().toString();          //can change phone

        if(name == null || name.equalsIgnoreCase("")){
            Snackbar.make(view, "Please Enter Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(email == null || !email.contains("@")){
            Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Intent in = new Intent(CheckDetails.this, QRscanning.class);
            startActivity(in);
            finish();
        }
        /*else if(phone == null || phone.length() <= 9){
            Snackbar.make(view, "Please Enter Correct Phone Number", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }*/

    }
}
