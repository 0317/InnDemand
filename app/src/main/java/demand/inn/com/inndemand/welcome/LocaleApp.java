package demand.inn.com.inndemand.welcome;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.model.LocaleUtils;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class LocaleApp extends Application {

    AppPreferences prefs;
    Locale myLocale;
    private Locale locale = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            Locale.setDefault(locale);
            Configuration config = new Configuration(newConfig);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = settings.getString(getString(R.string.app_name), "");
        changeLang(lang);
    }

    public void changeLang(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {

            SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(this).edit();
            ed.putString(getString(R.string.app_name), lang);
            ed.commit();

            locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public String getLang(){
        return PreferenceManager.getDefaultSharedPreferences(this).getString(this.getString(R.string.app_name), "");
    }



    /*public void onCreate() {
        super.onCreate();
        prefs = new AppPreferences(this);

        setLocale(prefs.getLocaleset());
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SplashScreen.class);
        startActivity(refresh);
        finish();
    }*/

}
