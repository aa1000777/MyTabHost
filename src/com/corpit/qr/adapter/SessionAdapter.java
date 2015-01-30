package com.corpit.qr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.entity.Session;

public class SessionAdapter extends BaseAdapter {
	private Context context;
	private List<Session> data;
	private LayoutInflater inflater;

	public SessionAdapter(Context context, List<Session> data) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
	}

	public void updateListView(List<Session> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_qr_detail, null);
			holder.textView = (TextView) convertView.findViewById(R.id.angry_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(data.get(position).SessionTitle);
		return convertView;
	}

	class ViewHolder {
		TextView textView;
	}
}
