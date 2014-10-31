package com.example.birthdayreminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class BirthdayList {

	static Context mContext;
	static ArrayList<BirthdayListItem> mBirthdayList;
	static int mBirthdayCount;
	
	private static Cursor getContactsBirthdays()
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
				ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;

		String[] selectionArgs = new String[]{
				ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
		};

		String sortOrder = null;
		
		return mContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
	}

	public static void initBirthdayList(Context context)
	{
		mContext = context;
		Log.d(mContext.getClass().getSimpleName(), "Fetch Contact Info...");
		
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = formatter.parse();
		int date = cal.get(Calendar.DATE);
		Log.d(mContext.getClass().getSimpleName(), "Today is " + date);
		
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
			int bday = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
			String birthDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)); 
			mBirthdayList.add(new BirthdayListItem(name, number, birthDate));

			Log.d(mContext.getClass().getSimpleName(), "name="+name+"; number="+number+"; Birth Date="+bday);
		}			

		cursor.close();
		Log.d(mContext.getClass().getSimpleName(), "Fetch Contact Info... Done");
	}
	
	public static int getCount()
	{
		return mBirthdayCount;
	}
	
	public static ArrayList<BirthdayListItem> getBirthdayList()
	{
		return mBirthdayList;
	}
}
