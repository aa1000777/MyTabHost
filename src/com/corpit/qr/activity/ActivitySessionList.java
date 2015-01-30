package com.corpit.qr.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ListView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.adapter.SessionAdapter;
import com.corpit.qr.db.GetDataFromSQL;
import com.corpit.qr.entity.Session;
import com.corpit.qr.tools.CommonFunction;
import com.corpit.qr.tools.Global;

/**
 ***************************************************** 
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-30 上午9:39:16</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>Session列表页面</dd>
 * 
 * @author aa1000777 - Email:aa1000777@qq.com
 ***************************************************** 
 */

public class ActivitySessionList extends Activity {
	private List<Session> data;
	private SessionAdapter adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Global.point == null) {
			Global.point = CommonFunction.getScreenSize(this);
		}
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.list_main);
		ImageView company_logo = (ImageView) findViewById(R.id.company_logo);
		LayoutParams menu_iv_ps = (LayoutParams) company_logo.getLayoutParams();
		menu_iv_ps.height = CommonFunction.getImageHeight(120);
		menu_iv_ps.width = CommonFunction.getImageWidth(540);
		company_logo.setLayoutParams(menu_iv_ps);
	}

	private void initData() {
		// TODO Auto-generated method stub
		data = GetDataFromSQL.getSession(this);
		adapter = new SessionAdapter(this, data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				resetClicked(data.get(position));
			}
		});
	}

	private void resetClicked(Session session) {
		// TODO Auto-generated method stub
		Intent mainIntent = new Intent(this, ActivityTabBar.class);
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(mainIntent);
		Global.title = session.SessionTitle;
		Global.time = session.SessionTime;
		Global.id = session.SessionId;
		Global.SessionCont = session.SessionCont;
		Global.sessionId = String.valueOf(session.SessionId);
		this.finish();
	}

}
