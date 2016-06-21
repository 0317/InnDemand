package demand.inn.com.inndemand.mapdirection;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;
import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.constants.GPSTracker;
import demand.inn.com.inndemand.constants.LocationAddress;
import demand.inn.com.inndemand.utility.AppPreferences;

/**
 * Created by akash
 */
public class MapArea extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    //Utility
    AppPreferences prefs;

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    Location location;
    double mLatitude, dLatitude;
    double mLongitude, dLongitude;

    // flag for GPS status
    boolean isGPSEnabled = true;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    GPSTracker gps;

    LocationAddress locationAddress, locAddress;

    String getAddress;

    LatLng lan = new LatLng(mLatitude,mLongitude);
    LatLng lon = new LatLng(28.4452895,77.0650809);

    String val = "New Delhi";
    String vol = "Gurgaon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        prefs = new AppPreferences(this);
        gps = new GPSTracker(MapArea.this);

        GPSTracker gps = new GPSTracker(this);

        mLatitude = gps.getLatitude();
        mLongitude = gps.getLongitude();

        String lati = getIntent().getStringExtra("latitude");
        String longi = getIntent().getStringExtra("longitude");

        double dLatitude = Double.parseDouble(lati);
        double dLongitude = Double.parseDouble(longi);

//        new AlertDialog.Builder(MapArea.this).setMessage(dLatitude +" "+dLongitude).create().show();

        // check if GPS enabled
        if(gps.canGetLocation()){

            locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(mLatitude, mLongitude,
                    getApplicationContext(), new GeocoderHandler());

            locAddress.getAddressFromLocation(dLatitude, dLongitude, getApplicationContext(), new GeocoderHandlers());

//            try {
//                new DirectionFinder(this, prefs.getCurrentLocation(), prefs.getDestination()).execute();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            try {
                new DirectionFinder(this, val, vol).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            gps.showSettingsAlert();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcmus = new LatLng(mLatitude, mLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Your Location")
                .position(hcmus)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
           /* ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);*/

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_red))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_red))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            getAddress = locationAddress;
            String sp[] = getAddress.split(":");
//            String main = sp[3];
//            prefs.setCurrentLocation(main);
            new AlertDialog.Builder(MapArea.this).setMessage(locationAddress).create().show();
//            Toast.makeText(MapArea.this, getAddress+" ", Toast.LENGTH_LONG).show();

        }
    }

    private class GeocoderHandlers extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            getAddress = locationAddress;
            String sp[] = getAddress.split(":");
//            String main = sp[3];
//            prefs.setDestination(main);
            new AlertDialog.Builder(MapArea.this).setMessage(locationAddress).create().show();
//            Toast.makeText(MapArea.this, getAddress+" ", Toast.LENGTH_LONG).show();

        }
    }
}

