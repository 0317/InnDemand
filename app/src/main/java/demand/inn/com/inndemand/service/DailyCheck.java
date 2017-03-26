package demand.inn.com.inndemand.service;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DailyCheck extends IntentService{

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "Daily Update";

    public DailyCheck(){
        super(DailyCheck.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started");


    }
}
