package com.example.birthdayreminder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
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
	
	private Cursor getContactsBirthdays()
	{
		Uri uri = ContactsContract.Data.CONTENT_URI;
		
		String[] projection =  //null;
				new String[]{
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Event.CONTACT_ID,
				ContactsContract.CommonDataKinds.Event.START_DATE
		};

		String selection = ContactsContract.Data.MIMETYPE + " = ? AND " +
				ContactsContract.CommonDataKinds.Event.TYPE + " = " +
				ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;

		String[] selectionArgs = new String[]{
				ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
		};

		String sortOrder = null;
		
		return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
	}
	
	private void sendNotification(String msg)
	{
		Log.d(this.getClass().getSimpleName(), "Fetch Contact Info...");
		
		Cursor cursor = getContactsBirthdays();
		
		cursor.moveToFirst();
		
		while(cursor.moveToNext())
		{
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			String birthDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)); 

			Log.d(this.getClass().getSimpleName(), "name="+name+"; number="+number+"; Birth Date="+birthDate);
		}
		
		cursor.close();
		Log.d(this.getClass().getSimpleName(), "Fetch Contact Info... Done");
		
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
