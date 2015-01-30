package com.corpit.qr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.corpit.corpitiscan.R;
import com.corpit.qr.tools.BaseFragment;
import com.corpit.qr.tools.CommonFunction;

public class FragmentSessionDetail extends BaseFragment {
	private Button content_menu_btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_qr_detail, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		View parent = this.getView();
		setWidthHeight(parent);
		TextView tv_name = (TextView) parent.findViewById(R.id.tv_detail_name);
		TextView tv_email = (TextView) parent.findViewById(R.id.tv_detail_email);
		TextView tv_company = (TextView) parent.findViewById(R.id.tv_detail_company);
		TextView tv_country = (TextView) parent.findViewById(R.id.tv_detail_country);
		TextView tv_position = (TextView) parent.findViewById(R.id.tv_detail_position);
		TextView tv_detail_Welcome = (TextView) parent.findViewById(R.id.tv_detail_Welcome);
		tv_name.setText("Name: " + getArguments().getString("Name"));
		tv_email.setText("Email: " + getArguments().getString("Email"));
		tv_company.setText("Company: " + getArguments().getString("Company"));
		tv_country.setText("Country: " + getArguments().getString("Country"));
		tv_position.setText("Job Title: " + getArguments().getString("Position"));
		tv_detail_Welcome.setText("Registered for " + getArguments().getString("sessionName"));

		content_menu_btn = (Button) parent.findViewById(R.id.content_menu_btn);
		LayoutParams menu_btn_ps = (LayoutParams) content_menu_btn.getLayoutParams();
		menu_btn_ps.height = CommonFunction.getImageHeight(75);
		menu_btn_ps.width = CommonFunction.getImageWidth(75);
		content_menu_btn.setLayoutParams(menu_btn_ps);
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
