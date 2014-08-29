package com.example.elements;

import java.util.ArrayList;


import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterActivityHolder extends BaseAdapter{
	
	private static class ViewHolder{
		TextView name;
		TextView screen_name;
		TextView time;
		TextView text;
		ImageView image;
	}
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	
	ArrayList<Dummy> aList = new ArrayList<Dummy>();

	
	public ListViewAdapterActivityHolder(Context context, ArrayList<Dummy> gList) {
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
		ViewHolder holder = null;
		if( v == null)
		{
			v = mLayoutInflater.inflate(R.layout.list_view_each_row, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) v.findViewById(R.id.name);
			holder.screen_name = (TextView) v.findViewById(R.id.screen_name);
			holder.time = (TextView) v.findViewById(R.id.time);
			holder.text = (TextView) v.findViewById(R.id.text);
			holder.image = (ImageView) v.findViewById(R.id.funny);
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder)v.getTag();
		}
		
		Dummy item = getItem(position);
		//ImageView imageView = new ImageView(mContext);
		
		//ViewHolder item = getItem(position);
		//Uri myUri = Uri.parse(item.getImageUrl());
		holder.name.setText(item.getName());
		holder.screen_name.setText(item.getScreenName());
		holder.text.setText(item.getText());
		holder.time.setText(item.getTime());
		try {
			Picasso.with(mContext).load(item.getImageUrl()).fit().into(holder.image);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return v;
	}

}
