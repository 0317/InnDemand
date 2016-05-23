package demand.inn.com.inndemand.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanning extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    //Utitlity Class call
    NetworkUtility nu;
    AppPreferences prefs;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanning);
        nu = new NetworkUtility(this);
        prefs = new AppPreferences(this);

                try {
                    mScannerView = new ZXingScannerView(QRscanning.this);   // Programmatically initialize the scanner view
                    setContentView(mScannerView);

                    mScannerView.setResultHandler(QRscanning.this); // Register ourselves as a handler for scan results.
                    mScannerView.startCamera();         // Start camera
                }catch(NoClassDefFoundError e){
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
        builder.setTitle("Hotel Details");
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
}

