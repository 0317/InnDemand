package demand.inn.com.inndemand.welcome;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import demand.inn.com.inndemand.utility.AppPreferences;

/**
 *
 */
public class InnApplication  extends Application{

    AppPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        prefs = new AppPreferences(base);
        /**
         * get langauage form pref accordig to user setting
         */
        String pref_lang = prefs.getLocaleset();
        Locale locale = new Locale(pref_lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
