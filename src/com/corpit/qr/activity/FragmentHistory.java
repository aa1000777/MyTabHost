package com.corpit.qr.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ListView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.adapter.HistoryAdapter;
import com.corpit.qr.db.GetDataFromSQL;
import com.corpit.qr.entity.Session;
import com.corpit.qr.tools.BaseFragment;
import com.corpit.qr.tools.CommonFunction;
import com.corpit.qr.tools.Global;

/**
 * 
 ***************************************************** 
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-23 上午9:40:55</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * 
 * @author aa1000777 - Email:aa1000777@qq.com
 ***************************************************** 
 */
public class FragmentHistory extends BaseFragment {
	private List<Session> data;
	private HistoryAdapter adapter;
	private ListView historyList;
	private Button content_menu_btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.history_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		View view = this.getView();
		setWidthHeight(view);
		historyList = (ListView) view.findViewById(R.id.historyList);
		data = GetDataFromSQL.getSession(context);
		adapter = new HistoryAdapter(context, data);
		historyList.setAdapter(adapter);

		historyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Global.sessionId = String.valueOf(data.get(position).SessionId);
				ActivityTabBar.getInstance().selectTab("Statistics");
			}
		});
		content_menu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoActivitySessionList();
			}
		});
	}

	
	private void setWidthHeight(View v) {
		// TODO Auto-generated method stub
		ImageView company_logo = (ImageView) v.findViewById(R.id.company_logo);
		LayoutParams menu_iv_ps = (LayoutParams) company_logo.getLayoutParams();
		menu_iv_ps.height = CommonFunction.getImageHeight(120);
		menu_iv_ps.width = CommonFunction.getImageWidth(540);
		company_logo.setLayoutParams(menu_iv_ps);

		content_menu_btn = (Button) v.findViewById(R.id.content_menu_btn);
		LayoutParams menu_btn_ps = (LayoutParams) content_menu_btn.getLayoutParams();
		menu_btn_ps.height = CommonFunction.getImageHeight(55);
		menu_btn_ps.width = CommonFunction.getImageWidth(55);
		content_menu_btn.setLayoutParams(menu_btn_ps);
	}

	protected void gotoActivitySessionList() {
		// TODO Auto-generated method stub
		Intent mainIntent = new Intent(context, ActivitySessionList.class);
		startActivity(mainIntent);
		this.getActivity().finish();
	}
}
