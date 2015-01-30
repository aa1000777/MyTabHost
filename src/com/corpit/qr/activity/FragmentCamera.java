package com.corpit.qr.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.db.GetDataFromSQL;
import com.corpit.qr.tools.CommonFunction;
import com.corpit.qr.tools.Global;
import com.corpit.qr.tools.Security;
import com.zbar.lib.CaptureActivity;

public class FragmentCamera extends CaptureActivity {
	private Button content_menu_btn;
	private Intent intent;
	private TextView textView;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				textView.setVisibility(View.INVISIBLE);
				restartPreview();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_qr_scan, container, false);
		setWidthHeight(v);
		TextView text = (TextView) v.findViewById(R.id.qr_top_mask);
		text.setText(Global.title + "\n" + Global.time);
		textView = (TextView) v.findViewById(R.id.qr_text2);
		final RelativeLayout camera = (RelativeLayout) v.findViewById(R.id.qr_camera);
		camera.setVisibility(View.GONE);
		final RelativeLayout foreground = (RelativeLayout) v.findViewById(R.id.foreground);
		Button btn = (Button) foreground.findViewById(R.id.scanBtn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				camera.setVisibility(View.VISIBLE);
				foreground.setVisibility(View.GONE);
			}
		});
		content_menu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoActivitySessionList();
			}
		});
		return v;
	}

	private void setWidthHeight(View v) {
		// TODO Auto-generated method stub
		ImageView company_logo = (ImageView) v.findViewById(R.id.qr_company_logo);
		LayoutParams menu_iv_ps = (LayoutParams) company_logo.getLayoutParams();
		menu_iv_ps.height = CommonFunction.getImageHeight(120);
		menu_iv_ps.width = CommonFunction.getImageWidth(540);
		company_logo.setLayoutParams(menu_iv_ps);

		content_menu_btn = (Button) v.findViewById(R.id.qr_content_menu_btn);
		LayoutParams menu_btn_ps = (LayoutParams) content_menu_btn.getLayoutParams();
		menu_btn_ps.height = CommonFunction.getImageHeight(55);
		menu_btn_ps.width = CommonFunction.getImageWidth(55);
		content_menu_btn.setLayoutParams(menu_btn_ps);

		TextView top_mask = (TextView) v.findViewById(R.id.qr_top_mask);
		LayoutParams tv_ps = (LayoutParams) top_mask.getLayoutParams();
		tv_ps.height = CommonFunction.getImageHeight(122);
		tv_ps.width = CommonFunction.getImageWidth(540);
		top_mask.setPadding(CommonFunction.getImageWidth(30), 0, 0, 0);
		top_mask.setLayoutParams(tv_ps);

		ImageView left_mask = (ImageView) v.findViewById(R.id.qr_left_mask);
		LayoutParams iv_left_ps = (LayoutParams) left_mask.getLayoutParams();
		iv_left_ps.height = CommonFunction.getImageHeight(300);
		iv_left_ps.width = CommonFunction.getImageWidth(120);
		left_mask.setLayoutParams(iv_left_ps);

		ImageView right_mask = (ImageView) v.findViewById(R.id.qr_right_mask);
		LayoutParams iv_right_ps = (LayoutParams) right_mask.getLayoutParams();
		iv_right_ps.height = CommonFunction.getImageHeight(300);
		iv_right_ps.width = CommonFunction.getImageWidth(120);
		right_mask.setLayoutParams(iv_right_ps);

		RelativeLayout capture_crop_layout = (RelativeLayout) v.findViewById(R.id.capture_crop_layout);
		LayoutParams iv_crop_ps = (LayoutParams) capture_crop_layout.getLayoutParams();
		iv_crop_ps.height = CommonFunction.getImageHeight(300);
		iv_crop_ps.width = CommonFunction.getImageWidth(300);
		capture_crop_layout.setLayoutParams(iv_crop_ps);

		TextView text3 = (TextView) v.findViewById(R.id.text3);
		LayoutParams tv3_ps = (LayoutParams) text3.getLayoutParams();
		tv3_ps.height = CommonFunction.getImageHeight(110);
		tv3_ps.width = CommonFunction.getImageWidth(540);
		text3.setLayoutParams(tv3_ps);

		TextView text2 = (TextView) v.findViewById(R.id.qr_text2);
		LayoutParams tv2_ps = (LayoutParams) text2.getLayoutParams();
		tv2_ps.height = CommonFunction.getImageHeight(200);
		tv2_ps.width = CommonFunction.getImageWidth(540);
		text2.setLayoutParams(tv2_ps);

	}

	protected void gotoActivitySessionList() {
		// TODO Auto-generated method stub
		Intent mainIntent = new Intent(context, ActivitySessionList.class);
		startActivity(mainIntent);
		this.getActivity().finish();
	}

	@Override
	protected void handleDecodeInternally(String result) {
		if (result != null && !"".endsWith(result)) {
			String code = result;
			String decodedCode = Security.decrypt(code, 10);
			gotoMain(decodedCode);
		}
	}

	private void gotoMain(String decodedCode) {
		if (intent == null) {
			intent = new Intent(getActivity(), ActivityTabBar.class);
		}

		String type = decodedCode.substring(0, 1);
		String decodedData = decodedCode.substring(1);
		// decodedData:suny1hu^sunny@corpit.com.sg^CORPIT1^SINGAPORE^manager^0111011001
		System.out.println("decodedData: " + decodedData);
		String[] data = decodedData.split("\\^");
		if (data.length != 6) {
			showInvalidQRCodeMessage();
			return;
		} else {
			String sessionId = String.valueOf(Global.id);
			if ("1".equals(String.valueOf(data[5].charAt(Global.id)))) {
				textView.setText("REGISTERED");
				textView.setTextColor(Color.parseColor("#00B050"));
				textView.setVisibility(View.VISIBLE);
				System.out.println("REGISTERED");
				if (!GetDataFromSQL.isUser(context, data[0], data[1], sessionId)) {
					ContentValues values1 = new ContentValues();
					Global.SessionCont = Global.SessionCont + 1;
					values1.put("SessionCont", Global.SessionCont);
					GetDataFromSQL.updateSession(context, values1, sessionId);
					// 扫描通过存入数据库
					ContentValues values = new ContentValues();
					values.put("Name", data[0]);
					values.put("Email", data[1]);
					values.put("Company", data[2]);
					values.put("Country", data[3]);
					values.put("Position", data[4]);
					values.put("Remark", data[5]);
					values.put("SessionId", sessionId);
					values.put("SessionName", Global.title);
					GetDataFromSQL.insertUser(context, values);
				}
			} else {
				textView.setText("NOT\nREGISTERED");
				textView.setTextColor(Color.parseColor("#FF0000"));
				textView.setVisibility(View.VISIBLE);
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000 * 2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(1);
				}
			}).start();
		}
	}

	private void showInvalidQRCodeMessage() {
		new AlertDialog.Builder(getActivity()).setTitle("Invalid QR Code").setMessage("Invalid QR Code!")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continue with delete
						dialog.cancel();
						restartPreview();
					}
				}).show();
	}
}
