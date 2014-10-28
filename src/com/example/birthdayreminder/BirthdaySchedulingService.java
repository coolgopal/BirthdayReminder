package com.example.birthdayreminder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BirthdaySchedulingService extends IntentService {
	
	private NotificationManager mNotificationManager;
	// An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;

	public BirthdaySchedulingService() {
		super("SchedulingService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		sendNotification("Alarm Expired!!!");
	}
	
	private void sendNotification(String msg)
	{
		Log.d(this.getClass().getSimpleName(), "Display Notification!!! Fetch Contact Info");
		mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
		NotificationCompat.Builder mBuilder = 
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(getString(R.string.app_name))
		.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
		.setContentText(msg);
		
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
