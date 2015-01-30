package com.corpit.qr.adapter;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.entity.Session;

/**
 * 
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-23 上午9:44:52</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
 */
public class HistoryAdapter extends BaseAdapter {
	private List<Session> historyData;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	private ContentValues values;

	
	
	public HistoryAdapter(Context context, List<Session> historyData) {
		this.historyData = historyData;
		inflater = LayoutInflater.from(context);

	}

	public void updateListView(List<Session> list) {
		this.historyData = list;
		notifyDataSetInvalidated();
	}

	@Override
	public int getCount() {
		return historyData == null ? 0 : historyData.size();
	}

	@Override
	public Object getItem(int position) {
		return historyData == null ? null : historyData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		TextView history_title;
		TextView history_content;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.history_fragment_item, null);
			holder.history_title = (TextView) convertView.findViewById(R.id.history_title);
			holder.history_content = (TextView) convertView.findViewById(R.id.history_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.history_title.setText(historyData.get(position).SessionTitle);
		holder.history_content.setText(historyData.get(position).SessionCont+" records");
		return convertView;
	}

}
