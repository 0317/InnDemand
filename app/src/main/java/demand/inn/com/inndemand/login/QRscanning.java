package demand.inn.com.inndemand.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.Config;
import demand.inn.com.inndemand.constants.MarshMallowPermission;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.BaseActivity;
import demand.inn.com.inndemand.welcome.SplashScreen;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanning extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    //Utitlity Class call
    NetworkUtility nu;
    AppPreferences prefs;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 3;

    GoogleApiClient mGoogleApiClient;

    MarshMallowPermission marshMallowPermission;

    Button button;

    //Others
    String splitData;
    String hotelID, roomID, qrCode;

    //Date & Time
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.qrscanning);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);


        marshMallowPermission = new MarshMallowPermission(this);

        prefs.setIs_task_completed(true);
        prefs.setIs_In_Hotel(false);

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
            mScannerView = new ZXingScannerView(QRscanning.this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);
        }catch (Exception e){
            e.printStackTrace();
        }

        getPhotoFromCamera();

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
    }

    public void getPhotoFromCamera() {
        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mScannerView.setResultHandler(QRscanning.this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();
            }catch(Exception e){
                e.printStackTrace();
            }
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

        splitData = rawResult.getText();

        if(rawResult.getText().contains("-")) {
            String[] details = splitData.split("-");
            hotelID = details[0];
            roomID = details[1];
//            qrCode = details[2];

            // show the scanner result into dialog box.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hotel Details");
            builder.setMessage("Hotel Details are fetched. Please press Ok to enjoy Services");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    makeJsonObjectRequest();
                    Intent in = new Intent(QRscanning.this, DashBoard.class);
                    startActivity(in);
                    finish();
                }
            });

            AlertDialog alert1 = builder.create();
            alert1.show();

        }else{
            // show the scanner result into dialog box.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Details");
            builder.setMessage(rawResult.getText());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    makeJsonObjectRequest();
//                    Intent in = new Intent(QRscanning.this, HotelDetails.class);
//                    startActivity(in);
//                    finish();
                }
            });

            AlertDialog alert1 = builder.create();
            alert1.show();
        }

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case CAMERA_PERMISSION_REQUEST_CODE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        mScannerView.setResultHandler(QRscanning.this); // Register ourselves as a handler for scan results.
                        mScannerView.startCamera();
                    } else {
                        Toast.makeText(QRscanning.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
            }
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
                    prefs.clearPref();
                    LoginManager.getInstance().logOut();
                Toast.makeText(QRscanning.this, "Successfully logged-out", Toast.LENGTH_LONG).show();
                prefs.setFacebook_logged_In(false);
                Intent in = new Intent(QRscanning.this, SplashScreen.class);
                startActivity(in);
                finish();

            }else if(prefs.getGoogle_logged_In() == true){
                prefs.setGoogle_logged_In(false);
//                if(mGoogleApiClient.isConnected())
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                prefs.clearPref();
                                Toast.makeText(QRscanning.this, "Successfully logged-out", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(QRscanning.this, SplashScreen.class);
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

    /**
     * Method to make json object post call
     * */

    private void makeJsonObjectRequest() {

        JSONObject obj = new JSONObject();
        try{
            obj.put("customer", prefs.getCustomer_Id());
            obj.put("hotel", hotelID);
            obj.put("room", "1");
            obj.put("qr", roomID);
            obj.put("checkin_time", formattedDate);

        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("Check Json", obj.toString());

        prefs.setHotel_id(hotelID);
        postJsonData(Config.innDemand+"checkins/checkin/", obj.toString());

    }

    public void postJsonData(String url, String userData){

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        final String requestBody = userData;

        System.out.println("inside post json data====="+requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("yohaha=success==="+response);

                try {
                    JSONObject object = new JSONObject(response);

                    String checkinId = object.getString("checkin_id");
                    prefs.setCheckin_Id(checkinId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
//        mRequestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

