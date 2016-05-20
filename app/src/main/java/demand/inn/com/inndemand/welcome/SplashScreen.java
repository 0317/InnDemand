package demand.inn.com.inndemand.welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.login.LoginScreen;
import demand.inn.com.inndemand.login.QRscanning;
import demand.inn.com.inndemand.login.QrScan;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash on 3/5/16.
 */

public class SplashScreen extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //Preferences call
    SharedPreferences settings;

    //Others
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        nu = new NetworkUtility(SplashScreen.this);
        prefs = new AppPreferences(SplashScreen.this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

//        getSupportActionBar().hide();

        //To add shortcut App icon on the desktop of mobile
        addShortcut();
        call(view);
    }

    public void call(View view){
        if (nu.isConnectingToInternet()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (nu.isConnectingToInternet()) {
                        if (prefs.getIs_task_completed() == false) {
                            Intent in = new Intent(SplashScreen.this, LoginScreen.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        } else if (prefs.getIs_task_completed() == true) {
                            Intent in = new Intent(SplashScreen.this, LoginScreen.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }

                    }


                }
            }, 3000 /* 3sec delay*/);

        } else {
//            Snackbar.make(view, "Oops, No Internet Connection..", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
            new AlertDialog.Builder(this).setTitle("No Internet")
                    .setMessage("It seems no Internet Connection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }

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
