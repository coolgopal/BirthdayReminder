package com.example.birthdayreminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;

public class BirthdayData {

	Context mContext;
	ArrayList<BirthdayListItem> mBirthdayList;
	int mBirthdayCount;
	static BirthdayData mBirthdayData;
	private BirthdayData(Context ctx){
		mContext = ctx;
		
	}
	public static BirthdayData getInstance(Context ctx){
		if(mBirthdayData == null){
			mBirthdayData = new BirthdayData(ctx);
		}
		return mBirthdayData;
	}
	private  Cursor getContactsBirthdays()
	{
		Uri uri = ContactsContract.Data.CONTENT_URI;
		
		String[] projection =  //null;
				new String[]{
				ContactsContract.Contacts.DISPLAY_NAME,
				//ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
				ContactsContract.CommonDataKinds.Event.CONTACT_ID,
				ContactsContract.CommonDataKinds.Event.START_DATE
		};

		String selection = ContactsContract.Data.MIMETYPE + " = ? AND " +
				ContactsContract.CommonDataKinds.Event.TYPE + " = " +
				ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND "+
				ContactsContract.CommonDataKinds.Event.START_DATE+"=?";
       DateFormat formate = new DateFormat();
       String todayDate = formate.format("yyyy-MM-dd", System.currentTimeMillis()).toString();
       Log.d("Birthday","today date:"+todayDate);
		String[] selectionArgs = new String[]{
				ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,todayDate
		};

		String sortOrder = null;
		
		return mContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
	}

	public  void initBirthdayList()
	{
		Log.d(mContext.getClass().getSimpleName(), "Fetch Contact Info...");
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		Log.d(mContext.getClass().getSimpleName(), "Today is " + year + "-" + month + "-" + day);
		
		mBirthdayList = new ArrayList<BirthdayListItem>();
		Cursor cursor = getContactsBirthdays();

		mBirthdayCount = cursor.getCount();
		
		while(cursor.moveToNext())
		{
			String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.CONTACT_ID));
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String number = "";
			if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))) > 0)
			{
				Cursor numbers = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", 
						new String[]{id}, null);

				while(numbers.moveToNext())
				{
					number = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					number = number + ", ";
				}

				numbers.close();
			}

			//int bday = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.));
			String birthDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = formatter.parse(birthDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mBirthdayList.add(new BirthdayListItem(name, number, birthDate));

			Log.d(mContext.getClass().getSimpleName(), "name="+name+"; number="+number+"; Birth Date="+birthDate);
		}			

		cursor.close();
		Log.d(mContext.getClass().getSimpleName(), "Fetch Contact Info... Done");
	}
	
	public  int getCount()
	{
		return mBirthdayCount;
	}
	
	public  ArrayList<BirthdayListItem> getBirthdayList()
	{
		return mBirthdayList;
	}
}
