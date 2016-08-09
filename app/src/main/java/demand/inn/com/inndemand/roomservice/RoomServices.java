package demand.inn.com.inndemand.roomservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*import com.google.android.gms.games.multiplayer.realtime.Room;*/

import org.w3c.dom.Text;

import demand.inn.com.inndemand.R;
import demand.inn.com.inndemand.login.HotelDetails;
import demand.inn.com.inndemand.utility.AppPreferences;
import demand.inn.com.inndemand.utility.NetworkUtility;
import demand.inn.com.inndemand.utility.Serve;
import demand.inn.com.inndemand.volleycall.AppController;

/**
 * Created by akash
 */

public class RoomServices extends AppCompatActivity {

    //Utility Call Area
    NetworkUtility nu;
    AppPreferences prefs;


    //UI Call Area for the screen
    Button beverages_click, laundry_click, cab_click, bed_tea_click, bath_essentials_click, bell_boy_click,
            room_clean_click, wake_up_click;
    Toolbar toolbar;
    TextView services_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomservices);
//        Utility Class Initialisation
        nu = new NetworkUtility(RoomServices.this);
        prefs = new AppPreferences(RoomServices.this);


        //UI elements call Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.room_services);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });

//        Testing a service for the App
//        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent m_intent = new Intent(this, Serve.class);
//        PendingIntent pi = PendingIntent.getService(RoomServices.this, 2, m_intent, 0);
//        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 60 * 30 , pi);


//      UI Class call for different services (Initialisation)
        beverages_click = (Button) findViewById(R.id.beverages_click);
        laundry_click = (Button) findViewById(R.id.laundry_click);
        cab_click = (Button) findViewById(R.id.cab_click);
        bed_tea_click = (Button) findViewById(R.id.bed_tea_click);
        bath_essentials_click = (Button) findViewById(R.id.bath_essentials_click);
        bell_boy_click = (Button) findViewById(R.id.bell_boy_click);
        room_clean_click = (Button) findViewById(R.id.roomclean_click);
        wake_up_click = (Button) findViewById(R.id.wake_up_click);
        services_text = (TextView) findViewById(R.id.services_text);

//      Conditions to check If Room services matches the timings  of the Hotel to provide room services
//        to user or not
        if(prefs.getFm_service() == true){
            services_text.setText(R.string.roomservicesnotavailable);
            beverages_click.setClickable(false);
            beverages_click.setClickable(false);
            laundry_click.setClickable(false);
            cab_click.setClickable(false);
            bed_tea_click.setClickable(false);
            bath_essentials_click.setClickable(false);
            bell_boy_click.setClickable(false);
            room_clean_click.setClickable(false);
            wake_up_click.setClickable(false);
        }else{
            services_text.setText("");
            services_text.setVisibility(View.GONE);
        }


        if(prefs.getBeverage() == false)
            beverages_click.setVisibility(View.GONE);
        else
            beverages_click.setVisibility(View.VISIBLE);

        if(prefs.getLaundry() == false)
            laundry_click.setVisibility(View.GONE);
        else
            laundry_click.setVisibility(View.VISIBLE);

        if(prefs.getCab()== false)
            cab_click.setVisibility(View.GONE);
        else
            cab_click.setVisibility(View.VISIBLE);

        if(prefs.getBed_tea() == false)
            bed_tea_click.setVisibility(View.GONE);
        else
            bed_tea_click.setVisibility(View.VISIBLE);

        if(prefs.getBathroom()== false)
            bath_essentials_click.setVisibility(View.GONE);
        else
            bath_essentials_click.setVisibility(View.VISIBLE);

        if(prefs.getBell_boy()== false)
            bell_boy_click.setVisibility(View.GONE);
        else
            bell_boy_click.setVisibility(View.VISIBLE);

        if(prefs.getRoom_clean()== false)
            room_clean_click.setVisibility(View.GONE);
        else
            room_clean_click.setVisibility(View.VISIBLE);

        if(prefs.getWake_up() == false)
            wake_up_click.setVisibility(View.GONE);
        else
            wake_up_click.setVisibility(View.VISIBLE);

    }

//    onClick Method Call for different services

//        To launch Beverages Class
    public void beverages_click(View view){
        Intent in = new Intent(RoomServices.this, Beverages.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

//    To launch Laundry Class
    public void laundryClick(View view){
        Intent in = new Intent(RoomServices.this, Laundry.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

//    To launch Ca Class
    public void cabClbick(View view){
        Intent in = new Intent(RoomServices.this, Cab.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

//    To launch Bed tea/Coffe Class
    public void bedteaClick(View view){
        Intent in = new Intent(RoomServices.this, BedTea.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

//    To launch Bathroom Essentials Class
    public void bathessentialsClick(View view){
        Intent in = new Intent(RoomServices.this, Bathroom.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

//    To launch Bell-boy Class
    public void bellboyClick(View view){
        Intent in = new Intent(RoomServices.this, BellBoy.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

//    To launch Room Clean Class
    public void roomcleanClick(View view){
        Intent in = new Intent(RoomServices.this, RoomCleaning.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

//    To launch Wake-up Class
    public void wakeupClick(View view){
        Intent in = new Intent(RoomServices.this, WakeUp.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
