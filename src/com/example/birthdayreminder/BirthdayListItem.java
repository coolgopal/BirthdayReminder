package com.example.birthdayreminder;

public class BirthdayListItem
{
	String mName;
	String mNumber;
	String mBirthDate;
	
	public BirthdayListItem(String pName, String pNumber, String pBirthDate)
	{
		mName = pName;
		mNumber = pNumber;
		mBirthDate = pBirthDate;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public String getNumber()
	{
		return mNumber;
	}
	
	public String getBirthDate()
	{
		return mBirthDate;
	}
	
	public String toString()
	{
		return mName;
	}
}