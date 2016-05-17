package demand.inn.com.inndemand.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import demand.inn.com.inndemand.DashBoard;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

/**
 * Created by akash
 */

public class QrScan extends AppCompatActivity {

    //Utility Call Area
    NetworkUtility nu;
    AppPreferences prefs;

    Bitmap myQRCode;
    BarcodeDetector barcodeDetector;
    Detector.Detections<Barcode> detections;

    //UI Call
    SurfaceView cameraView;
    TextView barcodeInfo;
    CameraSource cameraSource;
    LinearLayout next;

    //Others
    String hotelName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan);
        nu = new NetworkUtility(QrScan.this);
        prefs = new AppPreferences(QrScan.this);

        prefs.setIs_task_completed(true);

        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);

        next = (LinearLayout) findViewById(R.id.scan_text);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(QrScan.this, HotelDetails.class);
                startActivity(in);
            }
        });

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QrScan.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
            }
        });

//        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
//
//        if (barcodes.size() != 0) {
//            barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
//                public void run() {
//                    barcodeInfo.setText(barcodes.valueAt(0).displayValue); // Update the TextView
//                }
//            });
//        }
//
//        hotelName = barcodes.valueAt(0).displayValue;
//        prefs.setHotel_Name(hotelName);
    }
}
