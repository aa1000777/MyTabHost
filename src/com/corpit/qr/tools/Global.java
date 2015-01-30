package com.corpit.qr.tools;

import android.graphics.Point;

import com.corpit.qr.views.ReclickableTabHost;

public class Global {
	public static String PersonnelNo = "";
	public static String actionAnswer = "";
	public static String productAnswer = "";
	public static ReclickableTabHost mTabHost;
	public static Point point;// 取得当前屏幕的宽高
	public static String title;
	public static String time;
	public static int id;// login 用于扫描sessionId
	public static int SessionCont;

	public static String sessionId = null;// 用于查询的sessionId
}
