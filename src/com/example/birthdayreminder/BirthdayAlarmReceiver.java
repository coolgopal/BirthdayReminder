package com.example.birthdayreminder;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class BirthdayAlarmReceiver extends WakefulBroadcastReceiver {

	private static AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Intent service = new Intent(context, BirthdaySchedulingService.class);
		
		startWakefulService(context, service);
	}
	
	@SuppressWarnings("deprecation")
	public void setAlarm(Context context)
	{
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, BirthdayAlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// Set the alarm trigger time
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 00);
		
		Log.d(this.getClass().getSimpleName(), "Setting Alarm at "+calendar.getTime().toLocaleString());
		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 300000, alarmIntent);
	}
	
	public void cancelAlarm(Context context)
	{
		Log.d(this.getClass().getSimpleName(), "Cancel Alarm!!");

		if(alarmMgr != null)
		{
			alarmMgr.cancel(alarmIntent);
			Log.d(this.getClass().getSimpleName(), "Alarm Cancelled!!");
			alarmMgr = null;
		}
	}

}
