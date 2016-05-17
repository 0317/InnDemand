package demand.inn.com.inndemand.roomservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.google.android.gms.games.multiplayer.realtime.Room;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomservices);
        nu = new NetworkUtility(RoomServices.this);
        prefs = new AppPreferences(RoomServices.this);

        getSupportActionBar().hide();

        //UI elements call

        //Linearlayout click call for different services
        beverages_click = (LinearLayout) findViewById(R.id.beverages_click);
        laundry_click = (LinearLayout) findViewById(R.id.laundry_click);
        cab_click = (LinearLayout) findViewById(R.id.cab_click);
        bed_tea_click = (LinearLayout) findViewById(R.id.bed_tea_click);
        bath_essentials_click = (LinearLayout) findViewById(R.id.bath_essentials_click);
        bell_boy_click = (LinearLayout) findViewById(R.id.bell_boy_click);
        room_clean_click = (LinearLayout) findViewById(R.id.roomclean_click);
        wake_up_click = (LinearLayout) findViewById(R.id.wake_up_click);
        backPress = (LinearLayout) findViewById(R.id.backpress);
    }

    //onClick method for back-press or cancel
    public void backPress(){
        onBackPressed();
    }

        //onClick Method Call for different services (Linearlayout)

        //Beverages Click Method
    public void beveragesClick(){
        Intent in = new Intent(RoomServices.this, Beverages.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Laundry Click method
    public void laundryClick(){
        Intent in = new Intent(RoomServices.this, Laundry.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Cab Click method
    public void cabClick(){
        Intent in = new Intent(RoomServices.this, Cab.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Bed Tea Click method
    public void bedteaClick(){
        Intent in = new Intent(RoomServices.this, BedTea.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Bath Essentials Click method
    public void bathessentialsClick(){
        Intent in = new Intent(RoomServices.this, Bathroom.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Bell-Boy Click method
    public void bellboyClick(){
        Intent in = new Intent(RoomServices.this, BellBoy.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Room Clean Click method
    public void roomcleanClick(){
        Intent in = new Intent(RoomServices.this, RoomCleaning.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    //Wake-up Click method
    public void wakeupClick(){
        Intent in = new Intent(RoomServices.this, WakeUp.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
