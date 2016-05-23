package demand.inn.com.inndemand.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import demand.inn.com.inndemand.R;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by akash on 23/5/16.
 */
public class QRbar extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanning);

        try{
//            // Here, thisActivity is the current activity
//            if (ContextCompat.checkSelfPermission(QRbar.this, Manifest.permission.CAMERA)
//                    == PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
//                if (ActivityCompat.shouldShowRequestPermissionRationale(QRbar.this,
//                        Manifest.permission.CAMERA)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    scannerView = new ZBarScannerView(this);
                    setContentView(scannerView);

                    scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                    scannerView.startCamera();         // Start camera
//                }
//            }
            }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(final Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hotel Details");
//        builder.setMessage(rawResult.getText());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(QRbar.this, HotelDetails.class);
//                prefs.setHotel_Name(rawResult.getText());
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
