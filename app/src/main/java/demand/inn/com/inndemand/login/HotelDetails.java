package demand.inn.com.inndemand.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.cartarea.MyCart;
import demand.inn.com.inndemand.mapdirection.Mapping;
import demand.inn.com.inndemand.roomservice.Beverages;
import demand.inn.com.inndemand.roomservice.Restaurant;
import demand.inn.com.inndemand.roomservice.RoomServices;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;
import demand.inn.com.inndemand.welcome.SplashScreen;

/**
 * Created by akash
 */

public class HotelDetails extends AppCompatActivity {

    //Utility Class Area
    NetworkUtility nu;
    AppPreferences prefs;

    //UI Call
    Button checkout;
    TextView hotel_Name, hotel_Address;
    ImageLoader imageLoader;
    NetworkImageView imageView;
    ImageView main_backdrop;

    //Others
    String callHotel;
    String URL, TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoteldetails);
        nu = new NetworkUtility(HotelDetails.this);
        prefs = new AppPreferences(HotelDetails.this);

        URL = "";

        imageLoader = AppController.getInstance().getImageLoader();
//        imageView.setImageUrl(URL, imageLoader);

        //Title set for Collapsing Toolbar
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("InnDemand");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        main_backdrop = (ImageView) findViewById(R.id.main_backdrop);
        // If you are using normal ImageView
        imageLoader.get(URL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    main_backdrop.setImageBitmap(response.getBitmap());
                }
            }
        });

        //UI initialize
        hotel_Name = (TextView) findViewById(R.id.hotel_Name);
        hotel_Address = (TextView) findViewById(R.id.hotel_Address);

        //set Hotel name over hotel_Name TextView
        hotel_Name.setText(prefs.getHotel_Name());

        //variable to get Hotel contact number
        callHotel = "";

    }

    //Button onClicklistener to Checkout from the hotel & redirect to Splash Screen
    public void checkOut(View view) {
        prefs.setCheckout("1");
        Intent in = new Intent(HotelDetails.this, SplashScreen.class);
        startActivity(in);
        finish();
    }

    public void callHotel(View view) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("+919729305557"));
//        startActivity(callIntent);
    }

    public void direction(View view){
        Intent in = new Intent(HotelDetails.this, Mapping.class);
        startActivity(in);
    }

    //OnClick to go to Restaurant Screen
    public void restaurantClick(View view){
        Intent in = new Intent(HotelDetails.this, Restaurant.class);
        startActivity(in);

    }

    //OnClick to go to Room Services Screen
    public void roomServiceClick(View view){
        Intent in = new Intent(HotelDetails.this, RoomServices.class);
        startActivity(in);
    }

    //OnClick to go to Bar Screen
    public void barClick(View view){
//        Intent in = new Intent(HotelDetails.this, Beverages.class);
//        startActivity(in);

    }

    //OnClick to go to Spa Screen
    public void spaClick(View view){
//        Intent in = new Intent(HotelDetails.this, Restaurant.class);
//        startActivity(in);

    }
}
