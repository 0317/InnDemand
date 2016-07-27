package demand.inn.com.inndemand.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */
public class Settings extends AppCompatActivity {

    //Utility class area
    NetworkUtility nu;
    AppPreferences prefs;
    Locale myLocale;

    View view;

    //UI call Area
    Toolbar toolbar;
    ToggleButton notiHotel, notiInndemand;
    Button deleteAccount;
    Spinner languages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

//        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_settings);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addListenerOnSpinnerItemSelection();

        //UI Initialize
        notiHotel = (ToggleButton) findViewById(R.id.hotel_noti);
        notiInndemand = (ToggleButton) findViewById(R.id.inndemand_noti);

        SharedPreferences sharedPrefs = getSharedPreferences("demand.inn.com.inndemand", MODE_PRIVATE);
        boolean noti_hotel = sharedPrefs.getBoolean("noti_hotel", false);
        boolean noti_inndemand = sharedPrefs.getBoolean("noti_inndemand", false);

        if(noti_hotel = true)
            notiHotel.setChecked(true);
        else if(noti_hotel = false)
            notiHotel.setChecked(false);

        if(noti_inndemand = true)
            notiInndemand.setChecked(true);
        else
            notiInndemand.setChecked(false);


        notiHotel.setChecked(sharedPrefs.getBoolean("noti_hotel", true));
        notiInndemand.setChecked(sharedPrefs.getBoolean("noti_inndemand", true));

        deleteAccount = (Button) findViewById(R.id.delete_account);

        notiHotel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (notiHotel.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("demand.inn.com.inndemand", MODE_PRIVATE).edit();
                    editor.putBoolean("noti_hotel", true);
                    editor.commit();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("demand.inn.com.inndemand", MODE_PRIVATE).edit();
                    editor.putBoolean("noti_hotel", false);
                    editor.commit();
                }
            }
        });

        notiInndemand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (notiInndemand.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("demand.inn.com.inndemand", MODE_PRIVATE).edit();
                    editor.putBoolean("noti_inndemand", true);
                    editor.commit();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("demand.inn.com.inndemand", MODE_PRIVATE).edit();
                    editor.putBoolean("noti_inndemand", false);
                    editor.commit();
                }
            }
        });

    }

    public void addListenerOnSpinnerItemSelection() {
        languages = (Spinner) findViewById(R.id.languages);
        languages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 1){

                }else if(pos == 2){
                    Toast.makeText(Settings.this,
                            "Language: German", Toast.LENGTH_SHORT).show();
                    setLocale("de");
                    prefs.setLocaleset("de");
                }else if(pos == 3){
                    Toast.makeText(Settings.this,
                            "Language: French", Toast.LENGTH_SHORT).show();
                    setLocale("fr");
                    prefs.setLocaleset("fr");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
        finish();
    }

    public void deleteAccount(View view){
        new AlertDialog.Builder(Settings.this).setMessage("Its seems like you want to delete your account.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
