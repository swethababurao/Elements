package com.example.elements;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapterActivity extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private TextView name;
	private TextView screen_name;
	private TextView time;
	private TextView text;
	
	
	
	ArrayList<Dummy> aList = new ArrayList<Dummy>();

	
	public ListViewAdapterActivity(Context context, ArrayList<Dummy> gList) {
		mContext = context;
		aList = gList;
		mLayoutInflater = LayoutInflater.from(this.mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return aList.size();
	}
	@Override
	public Dummy getItem(int arg0) {
		// TODO Auto-generated method stub
		return aList.get(arg0);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if( v == null)
		{
			v = mLayoutInflater.inflate(R.layout.list_view_each_row, parent, false);
			name = (TextView) v.findViewById(R.id.name);
			screen_name = (TextView) v.findViewById(R.id.screen_name);
			time = (TextView) v.findViewById(R.id.time);
			text = (TextView) v.findViewById(R.id.text);
		}
		
		Dummy item = getItem(position);
		name.setText(item.getName());
		screen_name.setText(item.getScreenName());
		text.setText(item.getText());
		time.setText(item.getTime());
		return v;
	}

}
