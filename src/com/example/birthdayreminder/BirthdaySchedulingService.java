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
    public static final String ACTION_CHK_BIRTHDAY="com.example.CHK_BIRTHDAY";

	public BirthdaySchedulingService() {
		super("SchedulingService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("BirthdaySchedulingService", "intent:"+intent.getAction());
		if (BirthdaySchedulingService.ACTION_CHK_BIRTHDAY.equals(intent
				.getAction())) {
		sendNotification();
		}
	}
	
	private void sendNotification()
	{
		BirthdayData data = BirthdayData.getInstance(getApplicationContext());
		data.initBirthdayList();
		String msg = String.valueOf(data.getCount()) + " people's birthday today!!";
		
		mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
		NotificationCompat.Builder mBuilder = 
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(getString(R.string.app_name))
		.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
		.setContentText(msg);
		
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
