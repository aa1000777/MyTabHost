package com.corpit.qr.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.entity.User;

/**
 ***************************************************** 
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-30 上午9:57:44</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * 
 * @author aa1000777 - Email:aa1000777@qq.com
 ***************************************************** 
 */

public class MoreAdapter extends BaseAdapter {
	private List<User> moreData;
	private LayoutInflater inflater;
	private Context context;
	private ViewHolder holder = null;
	private ContentValues values;

	public MoreAdapter(Context context, List<User> moreData) {
		this.moreData = moreData;
		this.context = context;
		inflater = LayoutInflater.from(context);

	}

	public void updateListView(List<User> list) {
		this.moreData = list;
		notifyDataSetInvalidated();
	}

	@Override
	public int getCount() {
		return moreData == null ? 0 : moreData.size();
	}

	@Override
	public Object getItem(int position) {
		return moreData == null ? null : moreData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		TextView more_Text;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.more_fragment_item, null);
			holder.more_Text = (TextView) convertView.findViewById(R.id.more_Text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.more_Text.setText("Name: " + moreData.get(position).Name);
		return convertView;
	}

}
