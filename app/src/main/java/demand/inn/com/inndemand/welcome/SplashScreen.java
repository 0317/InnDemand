package demand.inn.com.inndemand.welcome;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.login.Loginscreen;
import demand.inn.com.inndemand.login.QRscanning;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class SplashScreen extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;


    //Preferences call
    SharedPreferences settings;

    //UI call area
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;

    //Others
    Context context;

    //Notification
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    GoogleCloudMessaging gcm;

    String SENDER_ID = "551529436128";
    String regid = "";
    private final static String TAG = "Splash Screen";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

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

//        ((LocaleApp)getApplicationContext()).changeLang(prefs.getLocaleset());

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //GCM Area (Notifications)
//        if (checkPlayServices()) {
//            gcm = GoogleCloudMessaging.getInstance(this);
//            regid = getRegistrationId(context);//GCM Token ID
//            prefs.setReg_ID(regid);
//            Log.i(TAG, "REG ID "+regid);
//
//            if (regid.isEmpty()) {
//
//                registerInBackground();
//            }else{
//                if(nu.isConnectingToInternet()){
//                    DeviceUpdate update = new DeviceUpdate();
//                    update.execute(regid);
//                    prefs.setReg_ID(regid);
//                    Log.i(TAG, "REG ID "+regid);
//                }
//            }
//        } else {
//            Log.i(TAG, "No valid Google Play Services APK found.");
//        }

      /*  if(prefs.getHotel_check() == true){
            Intent in = new Intent(SplashScreen.this, DashBoard.class);
            startActivity(in);
        }*/

        //To add shortcut App icon on the desktop of mobile
        addShortcut();

    }

    @Override
    protected void onResume() {
        super.onResume();
        call();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void call(){
//        if (nu.isConnectingToInternet()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    if (nu.isConnectingToInternet()) {
                        if (prefs.getIs_task_completed() == false) {
                            Intent in = new Intent(SplashScreen.this, Loginscreen.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        } else if (prefs.getIs_task_completed() == true) {
                            if(prefs.getIs_In_Hotel() == false) {
                                Intent in = new Intent(SplashScreen.this, QRscanning.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }else if(prefs.getIs_In_Hotel() == true){
                            Intent in = new Intent(SplashScreen.this, DashBoard.class);
                            startActivity(in);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                            }
                        }
//                    }else{
//                        networkClick();
//
//                    }
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

    //GCM call
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                try {
                    regid = gcm.register(SENDER_ID);
                    prefs.setReg_ID(regid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                msg = regid;
                Log.d("ID mesg:", msg);

                // You should send the registration ID to your server over
                // HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your
                // app.
                // The request to your server should be authenticated if
                // your app
                // is using accounts.
                // sendRegistrationIdToBackend();

                // For this demo: we don't need to send it because the
                // device
                // will send upstream messages to a server that echo back
                // the
                // message using the 'from' address in the message.

                // Persist the regID - no need to register again.
                storeRegistrationId(context, regid);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                if(!msg.equalsIgnoreCase("Error")){
                    if(nu.isConnectingToInternet()){
                        DeviceUpdate update = new DeviceUpdate();
                        update.execute(regid);
                    }
                }
            }
        }.execute();
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context
     *            application's context.
     * @param regId
     *            registration ID
     */

    private void storeRegistrationId(Context context, String regId) {

        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);

        prefs.setAppVersion(appVersion);
        prefs.setDeviceToken(regId);

    }



    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {

        String registrationId = prefs.getDeviceToken();
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getAppVersion();
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    //Updation of device
    public class DeviceUpdate extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(SplashScreen.this);

        String keyResponce = "";
        String jsonstr;

        @Override
        protected String doInBackground(String... param) {

            JSONObject json = new JSONObject();
            if (json != null) {
                try {
                    keyResponce = param[0];

                    jsonstr = json.getString("response");
                    if (json.getBoolean("res")) {
                        String id = json.getString("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    //Check Play Services in Device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //   finish();
            }
            return false;
        }
        return true;
    }

    //Custom pop-up for Network Click
    public void networkClick(){
        // custom dialog
        final Dialog dialog = new Dialog(SplashScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.network);

        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Button checkout = (Button) dialog.findViewById(R.id.ok_click);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog.show();
    }

}
