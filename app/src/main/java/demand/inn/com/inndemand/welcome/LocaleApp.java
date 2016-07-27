package demand.inn.com.inndemand.welcome;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

import demand.inn.com.inndemand.model.LocaleUtils;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */

public class LocaleApp extends Application {

    AppPreferences prefs;

    public void onCreate() {
        super.onCreate();
        prefs = new AppPreferences(this);

        LocaleUtils.setLocale(new Locale(prefs.getLocaleset()));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }
}
