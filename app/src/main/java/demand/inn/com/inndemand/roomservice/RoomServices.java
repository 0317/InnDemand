package demand.inn.com.inndemand.roomservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.android.gms.games.multiplayer.realtime.Room;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash on 4/5/16.
 */

public class RoomServices extends AppCompatActivity {

    //Utility Call Area
    NetworkUtility nu;
    AppPreferences prefs;


    //UI Initialize
    LinearLayout beverages_click, laundry_click, cab_click, bed_tea_click, bath_essentials_click, bell_boy_click,
            room_clean_click, wake_up_click, backPress;

    //Class call
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomservices);
        nu = new NetworkUtility(RoomServices.this);
        prefs = new AppPreferences(RoomServices.this);

//        getSupportActionBar().hide();
//
        //UI elements call

        //Linearlayout click call for different services
//        beverages_click = (LinearLayout) findViewById(R.id.beverages_click);
//        laundry_click = (LinearLayout) findViewById(R.id.laundry_click);
//        cab_click = (LinearLayout) findViewById(R.id.cab_click);
//        bed_tea_click = (LinearLayout) findViewById(R.id.bed_tea_click);
//        bath_essentials_click = (LinearLayout) findViewById(R.id.bath_essentials_click);
//        bell_boy_click = (LinearLayout) findViewById(R.id.bell_boy_click);
//        room_clean_click = (LinearLayout) findViewById(R.id.roomclean_click);
//        wake_up_click = (LinearLayout) findViewById(R.id.wake_up_click);
    }

    //onClick method for back-press or cancel
    public void backPress(View view){
        onBackPressed();
    }

        //onClick Method Call for different services (Linearlayout)

        //Beverages Click Method
    public void beveragesClick(View view){
        Intent in = new Intent(RoomServices.this, Beverages.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //Laundry Click method
    public void laundryClick(View view){
        Intent in = new Intent(RoomServices.this, Laundry.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //Cab Click method
    public void cabClick(View view){
        Intent in = new Intent(RoomServices.this, Cab.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Bed Tea Click method
    public void bedteaClick(View view){
        Intent in = new Intent(RoomServices.this, BedTea.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Bath Essentials Click method
    public void bathessentialsClick(View view){
        Intent in = new Intent(RoomServices.this, Bathroom.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Bell-Boy Click method
    public void bellboyClick(View view){
        Intent in = new Intent(RoomServices.this, BellBoy.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Room Clean Click method
    public void roomcleanClick(View view){
        Intent in = new Intent(RoomServices.this, RoomCleaning.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Wake-up Click method
    public void wakeupClick(View view){
        Intent in = new Intent(RoomServices.this, WakeUp.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
