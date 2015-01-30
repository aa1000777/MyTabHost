package com.corpit.qr.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.tools.Global;

/**
 * 
 ***************************************************** 
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-22 下午6:44:27</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>启动页面</dd>
 * 
 * @author aa1000777 - Email:aa1000777@qq.com
 ***************************************************** 
 */
public class ActivityAppStart extends Activity {
	private ImageView imageView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.app_start, null);
		setContentView(view);
		imageView = (ImageView) view.findViewById(R.id.start_iv);
		ViewTreeObserver vto = imageView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int height = imageView.getHeight();
				int width = imageView.getWidth();
				Global.point = new Point(width, height);
				System.out.println("3height:" + height);
				System.out.println("3width:" + width);
			}
		});
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});

	}

	// 跳转到Session列表页面
	private void redirectTo() {
		Intent intent = new Intent(this, ActivitySessionList.class);
		startActivity(intent);
		finish();
	}
}