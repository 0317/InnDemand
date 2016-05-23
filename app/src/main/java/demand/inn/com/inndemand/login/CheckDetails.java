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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import demand.inn.com.inndemand.R;
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

    //Others
    String name, email, phone;
    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkdetails);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);
        settings =  PreferenceManager.getDefaultSharedPreferences(this);
        prefs  =new AppPreferences(CheckDetails.this);

        //UI Initialized here
        detail_name = (EditText) findViewById(R.id.fb_name);
        detail_email = (EditText) findViewById(R.id.fb_email);
        detail_phone = (EditText) findViewById(R.id.fb_phone);

        fb_dp = (ImageView) findViewById(R.id.fb_dp);

        //Email validation
        detail_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //details set from facebook/google data

        detail_name.setText(settings.getString("google_name", ""));     //google name
//        detail_name.setText(settings.getString("fb_name", ""));         //facebbok name
        detail_name.setText("Akash");
//        detail_name.setEnabled(false);
//
        detail_email.setText(settings.getString("google_email", ""));   //google email
//        detail_email.setText(settings.getString("fb_email", ""));       //facebook email
        detail_email.setEnabled(true);

        System.out.print("google_data"+ settings.getString("google_name", "") +"email"+settings.getString("google_email", ""));

        Picasso.with(this).load(R.drawable.ic_menu_camera).into(fb_dp);

    }

    public void verifyDetails(View view){

        //String initialized to get above mentioned edittext values

        name = detail_name.getText().toString();            //can't change name
        email = detail_email.getText().toString();          //can change email
        phone = detail_phone.getText().toString();          //can change phone

        prefs.setUser_fname(name);
        prefs.setUser_email(email);
//        prefs.setUser_phone(Integer.parseInt(phone));

//        if(name == null || name.equalsIgnoreCase("")){
//            Snackbar.make(view, "Please Enter Name", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }else if(email == null || !email.contains("@")){
//            Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }else if(phone == null || phone.length() <= 9){
//            Snackbar.make(view, "Please Enter Correct Phone Number", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }else {
            Intent in = new Intent(CheckDetails.this, QRbar.class);
            startActivity(in);
            finish();
//        }
    }
}
