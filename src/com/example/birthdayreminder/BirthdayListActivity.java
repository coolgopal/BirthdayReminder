package com.example.birthdayreminder;

import android.app.ListActivity;
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
		
		showDialog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ArrayAdapter<BirthdayListItem> adapter = new ArrayAdapter<BirthdayListItem>(this,
							android.R.layout.simple_list_item_1, BirthdayList.mbirthdayList);
		
		setListAdapter(adapter);
	}
	
	void showDialog()
	{
		
	}

}
