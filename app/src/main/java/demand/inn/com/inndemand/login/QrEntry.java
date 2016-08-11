package demand.inn.com.inndemand.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.welcome.SplashScreen;

/**
 * Created by akash
 */
public class QrEntry extends AppCompatActivity{

    //Utility Class call Area
    AppPreferences prefs;
    Locale myLocale;


    //UI Class Area
    //Button to start QRScan
    Button click_OK;

    //Spinner to select language option
    Spinner languages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrentry);
        prefs = new AppPreferences(this);

        click_OK = (Button) findViewById(R.id.click_ok);
        addListenerOnSpinnerItemSelection();

    }

    public void click_OK(View view){
        Intent in = new Intent(QrEntry.this, QRscanning.class);
        startActivity(in);
        finish();
    }


    public void addListenerOnSpinnerItemSelection() {
        languages = (Spinner) findViewById(R.id.languages);
        languages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 1){
                    Toast.makeText(QrEntry.this,
                            "Language: English", Toast.LENGTH_SHORT).show();
//                    setLocale("en");
                    settingLocale("en");
                    prefs.setLocaleset("en");
                    prefs.setSelectedLanguage("English");
                }else if(pos == 2){
                    Toast.makeText(QrEntry.this,
                            "Language: French", Toast.LENGTH_SHORT).show();
//                    setLocale("fr");
                    settingLocale("fr");
                    prefs.setLocaleset("fr");
                    prefs.setSelectedLanguage("French");
                }else if(pos == 3){
                    Toast.makeText(QrEntry.this,
                            "Language: German", Toast.LENGTH_SHORT).show();
//                    setLocale("de");
                    settingLocale("de");
                    prefs.setLocaleset("de");
                    prefs.setSelectedLanguage("German");
                }else if(pos == 4){
                    Toast.makeText(QrEntry.this,
                            "Language: Arabic", Toast.LENGTH_SHORT).show();
//                    setLocale("ar");
                    settingLocale("ar");
                    prefs.setLocaleset("ar");
                    prefs.setSelectedLanguage("Arabic");
                }else if(pos == 5){
                    Toast.makeText(QrEntry.this,
                            "Language: Portuguese", Toast.LENGTH_SHORT).show();
//                    setLocale("pt");
                    settingLocale("pt");
                    prefs.setLocaleset("pt");
                    prefs.setSelectedLanguage("Portuguese");
                }else if(pos == 6){
                    Toast.makeText(QrEntry.this,
                            "Language: Russian", Toast.LENGTH_SHORT).show();
//                    setLocale("ru");
                    settingLocale("ru");
                    prefs.setLocaleset("ru");
                    prefs.setSelectedLanguage("Russian");
                }else if(pos == 7){
                    Toast.makeText(QrEntry.this,
                            "Language: Chinese", Toast.LENGTH_SHORT).show();
//                    setLocale("b+chk");
                    settingLocale("b+chk");
                    prefs.setLocaleset("zh-CN");
                    prefs.setSelectedLanguage("Chinese");
                }else if(pos == 8){
                    Toast.makeText(QrEntry.this,
                            "Language: Japanese", Toast.LENGTH_SHORT).show();
//                    setLocale("ja");
                    settingLocale("ja");
                    prefs.setLocaleset("ja");
                    prefs.setSelectedLanguage("Japanese");
                }else if(pos == 9){
                    Toast.makeText(QrEntry.this,
                            "Language: Spanish", Toast.LENGTH_SHORT).show();
//                    setLocale("es");
                    settingLocale("es");
                    prefs.setLocaleset("es");
                    prefs.setSelectedLanguage("Spanish");
                }else if(pos == 10){
                    Toast.makeText(QrEntry.this,
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
        Intent refresh = new Intent(this, QRscanning.class);
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
        /*Intent refresh = new Intent(QrEntry.this, QRscanning.class);
                        startActivity(refresh);
                        finish();*/
//                        DashBoard.dash.finish();
    }
}
