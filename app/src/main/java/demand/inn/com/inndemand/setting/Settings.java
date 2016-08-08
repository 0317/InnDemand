package demand.inn.com.inndemand.setting;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.util.Util;

import java.util.Locale;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.model.Translate;
import demand.inn.com.inndemand.model.Utils;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.welcome.SplashScreen;

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
    TextView selected_language;

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
        selected_language = (TextView) findViewById(R.id.language_selected);
        selected_language.setText(" "+prefs.getSelectedLanguage());

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
                    Toast.makeText(Settings.this,
                            "Language: English", Toast.LENGTH_SHORT).show();
//                    setLocale("en");
                    settingLocale("en");
                    prefs.setLocaleset("en");
                    prefs.setSelectedLanguage("English");
                }else if(pos == 2){
                    Toast.makeText(Settings.this,
                            "Language: French", Toast.LENGTH_SHORT).show();
//                    setLocale("fr");
                    settingLocale("fr");
                    prefs.setLocaleset("fr");
                    prefs.setSelectedLanguage("French");
                }else if(pos == 3){
                    Toast.makeText(Settings.this,
                            "Language: German", Toast.LENGTH_SHORT).show();
//                    setLocale("de");
                    settingLocale("de");
                    prefs.setLocaleset("de");
                    prefs.setSelectedLanguage("German");
                }else if(pos == 4){
                    Toast.makeText(Settings.this,
                            "Language: Arabic", Toast.LENGTH_SHORT).show();
//                    setLocale("ar");
                    settingLocale("ar");
                    prefs.setLocaleset("ar");
                    prefs.setSelectedLanguage("Arabic");
                }else if(pos == 5){
                    Toast.makeText(Settings.this,
                            "Language: Portuguese", Toast.LENGTH_SHORT).show();
//                    setLocale("pt");
                    settingLocale("pt");
                    prefs.setLocaleset("pt");
                    prefs.setSelectedLanguage("Portuguese");
                }else if(pos == 6){
                    Toast.makeText(Settings.this,
                            "Language: Russian", Toast.LENGTH_SHORT).show();
//                    setLocale("ru");
                    settingLocale("ru");
                    prefs.setLocaleset("ru");
                    prefs.setSelectedLanguage("Russian");
                }else if(pos == 7){
                    Toast.makeText(Settings.this,
                            "Language: Chinese", Toast.LENGTH_SHORT).show();
//                    setLocale("b+chk");
                    settingLocale("b+chk");
                    prefs.setLocaleset("b+chk");
                    prefs.setSelectedLanguage("Chinese");
                }else if(pos == 8){
                    Toast.makeText(Settings.this,
                            "Language: Japanese", Toast.LENGTH_SHORT).show();
//                    setLocale("ja");
                    settingLocale("ja");
                    prefs.setLocaleset("ja");
                    prefs.setSelectedLanguage("Japanese");
                }else if(pos == 9){
                    Toast.makeText(Settings.this,
                            "Language: Spanish", Toast.LENGTH_SHORT).show();
//                    setLocale("es");
                    settingLocale("es");
                    prefs.setLocaleset("es");
                    prefs.setSelectedLanguage("Spanish");
                }else if(pos == 10){
                    Toast.makeText(Settings.this,
                            "Language: Javanese", Toast.LENGTH_SHORT).show();
//                    setLocale("jv");
                    settingLocale("jv");
                    prefs.setLocaleset("jv");
                    prefs.setSelectedLanguage("Javanese");
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

    public void settingLocale(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        new AlertDialog.Builder(this).setMessage(R.string.restart_app)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent refresh = new Intent(Settings.this, SplashScreen.class);
                        startActivity(refresh);
                        finish();
                        DashBoard.dash.finish();
                    }
                })
                .setNegativeButton(R.string.promocancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
    }

    public void deleteAccount(View view){
        new AlertDialog.Builder(Settings.this).setMessage(R.string.delete_account)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.promocancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
