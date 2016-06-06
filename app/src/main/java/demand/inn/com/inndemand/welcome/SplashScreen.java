package demand.inn.com.inndemand.welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.login.HotelDetails;
import demand.inn.com.inndemand.login.Loginscreen;
import demand.inn.com.inndemand.login.QRscanning;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class SplashScreen extends BaseActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //Preferences call
    SharedPreferences settings;

    //UI call area
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;

    //Others

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        nu = new NetworkUtility(SplashScreen.this);
        prefs = new AppPreferences(SplashScreen.this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

//        getSupportActionBar().hide();

        prefs.setCheckout("1");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //To add shortcut App icon on the desktop of mobile
        addShortcut();
        call();
    }

    public void call(){
//        if (nu.isConnectingToInternet()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (nu.isConnectingToInternet()) {
                        if (prefs.getIs_task_completed() == false) {
                            Intent in = new Intent(SplashScreen.this, Loginscreen.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        } else if (prefs.getIs_task_completed() == true) {
                            Intent in = new Intent(SplashScreen.this, QRscanning.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }/* else if(prefs.getCheckout() == "2"){
                            Intent in = new Intent(SplashScreen.this, HotelDetails.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }*/
                    }else{
                        snackbar = Snackbar.make(coordinatorLayout, "Oops! No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
//                        View snackbarView = snackbar.getView();
//                        snackbarView.setBackgroundColor(Color.YELLOW);
//                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        textView.setTextColor(getResources().getColor(R.color.confirm_demand_click));
                        snackbar.show();
                    }
                }
            }, 3000 /* 3sec delay*/);
    }

    private void addShortcut() {
        // TODO Auto-generated method stub
        Intent shortcutIntent = new Intent(getApplicationContext(), SplashScreen.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.permission.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

    }
}
