package demand.inn.com.inndemand.gcm;

import java.io.IOException;
import java.util.Random;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import demand.inn.com.inndemand.R;


public class GcmIntentService extends IntentService {
	private static final String TAG = "RegIntentService";
	private static final String[] TOPICS = {"global"};
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	String title;
	int counter = 0;
	String token;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		/*InstanceID instanceID = InstanceID.getInstance(this);
		try {
			token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " + extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				String msg = "";
				try {
					msg = extras.getString("Message");
					title = extras.getString("Title");



				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendNotification(msg);
				//AIzaSyC4StSzOJ8D9LdcZ6jmTL2mtaiU28dzNtE
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	
	private void sendNotification(String msg) {

		Random random = new Random();
		int no = random.nextInt(1000000000);
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent resultIntent  = new Intent(this, GCMNotifications.class);;

		PendingIntent contentIntent = PendingIntent.getActivity(this, no, resultIntent, PendingIntent.FLAG_ONE_SHOT);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)

		.setContentTitle(title);
		if(counter == 0){
			mBuilder.setContentText(msg)

		.setSmallIcon(R.mipmap.ic_logo)
		.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
		.setPriority(NotificationCompat.PRIORITY_HIGH)
		.setSound(notification)
		.setAutoCancel(true);
			counter++;
			counter++;
		}
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(1, mBuilder.build());

//		android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(this);
//		builder.setSmallIcon(R.mipmap.ic_launcher);
//		Intent intent = new Intent(this, GCMNotifications.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//		builder.setContentIntent(pendingIntent);
//		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//		builder.setContentTitle(title);
//		builder.setContentText(msg);
//		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		notificationManager.notify(1, builder.build());
	}
}
