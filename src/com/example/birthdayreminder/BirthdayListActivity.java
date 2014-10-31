package com.example.birthdayreminder;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BirthdayListActivity extends ListActivity {

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		//super.onListItemClick(arg0, arg1, arg2, arg3);
		BirthdayListItem birthdayListItem = (BirthdayListItem) l.getItemAtPosition(position);
		
		showDialog(birthdayListItem.getName(), birthdayListItem.getNumber());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ArrayAdapter<BirthdayListItem> adapter = new ArrayAdapter<BirthdayListItem>(this,
							android.R.layout.simple_list_item_1, BirthdayList.mBirthdayList);
		
		setListAdapter(adapter);
	}
	
	void showDialog(String name, final String number)
	{
		new AlertDialog.Builder(this)
			.setTitle(name)
			.setMessage("How would you like to wish?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// Send SMS to number
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
					intent.putExtra("sms_body", "Happy Birthday!!");
					startActivity(intent);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNeutralButton("Call", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// make GSM call to number
					Intent intent = new Intent(Intent.ACTION_CALL);
					
					intent.setData(Uri.parse("tel:" + number));
					startActivity(intent);
				}
			})
			.show();
	}

}
