package demand.inn.com.inndemand.fcm;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by akash on 22/6/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing firebase
        Firebase.setAndroidContext(getApplicationContext());
    }
}
