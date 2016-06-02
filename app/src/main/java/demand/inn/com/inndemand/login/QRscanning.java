package demand.inn.com.inndemand.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.zxing.Result;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.welcome.SplashScreen;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanning extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    //Utitlity Class call
    NetworkUtility nu;
    AppPreferences prefs;

    GoogleApiClient mGoogleApiClient;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanning);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

        getSupportActionBar().setTitle("InnDemand");
        getSupportActionBar().invalidateOptionsMenu();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

                try {
                    if (ContextCompat.checkSelfPermission(QRscanning.this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                        mScannerView = new ZXingScannerView(QRscanning.this);   // Programmatically initialize the scanner view
                        setContentView(mScannerView);

                        mScannerView.setResultHandler(QRscanning.this); // Register ourselves as a handler for scan results.
                        mScannerView.startCamera();         // Start camera
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(final Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details");
        builder.setMessage(rawResult.getText());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(QRscanning.this, HotelDetails.class);
                prefs.setHotel_Name(rawResult.getText());
                startActivity(in);
                finish();
            }
        });

        AlertDialog alert1 = builder.create();
        alert1.show();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.qr_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            if(prefs.getFacebook_logged_In() == true){
                if(LoginManager.getInstance()!=null)
                    LoginManager.getInstance().logOut();
                Toast.makeText(QRscanning.this, "Successfully logged-out", Toast.LENGTH_LONG).show();
                prefs.setFacebook_logged_In(false);
//                LoginManager.getInstance().logOut();
                Intent in = new Intent(QRscanning.this, SplashScreen.class);
                prefs.clearPref();
                startActivity(in);
                finish();

            }else if(prefs.getGoogle_logged_In() == true){
                prefs.setGoogle_logged_In(false);
//                if(mGoogleApiClient.isConnected())
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Toast.makeText(QRscanning.this, "Successfully logged-out", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(QRscanning.this, SplashScreen.class);
                                prefs.clearPref();
                                startActivity(in);
                                finish();
                            }
                        });
//                mGoogleApiClient.disconnect();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

