package com.corpit.qr.tools;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class CommonFunction {
	public static final float WIDTH = 540;// with指定的宽
	public static final float HEIGHT = 960;// height指定的高

	public static int getImageWidth(float swidth, int ewidth) {
		float scale = swidth / Global.point.x;// 求出比例
		float imageWidth = ewidth / scale;
		return (int) imageWidth;
	}

	public static int getImageHeight(float swidth, int height) {
		float scale = swidth / Global.point.y;// 求出比例
		float imageHeight = height / scale;
		return (int) imageHeight;
	}

	public static int getImageWidth(int width) {
		float scale = WIDTH / Global.point.x;// 求出比例
		float imageWidth = width / scale;
		return (int) imageWidth;
	}

	public static int getImageHeight(int height) {
		float scale = HEIGHT / Global.point.y;// 求出比例
		float imageHeight = height / scale;
		return (int) imageHeight;
	}

	/**
	 ***************************************************** 
	 * 方法简介: 获取屏幕大小，单位px
	 * 
	 * @param activity
	 * @return Point 屏幕大小对象
	 ***************************************************** 
	 */
	public static Point getScreenSize(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		size.set(display.getWidth(), display.getHeight());
		return size;
	}
}
