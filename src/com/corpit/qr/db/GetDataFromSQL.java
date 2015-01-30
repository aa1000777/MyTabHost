package com.corpit.qr.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.corpit.qr.entity.Session;
import com.corpit.qr.entity.User;

public class GetDataFromSQL {
	private static final String USER = "User";
	private static final String SESSION = "Session";

	public synchronized static void updateSession(Context context, ContentValues values, String string) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.update(SESSION, values, "SessionId = '" + string + "'", null);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	public synchronized static boolean isUser(Context context, String name, String email, String SessinoSID) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		String sql = "select * from user where name='" + name + "' and email='" + email + "' and sessionid='" + SessinoSID + "';";
		Cursor cursor = null;
		cursor = db.rawQuery(sql, null);
		int cursorcount = 0;
		cursorcount = cursor.getCount();
		HandleDatabase.closeCusorAndDB(cursor, db);
		if (cursorcount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized static List<Session> getSession(Context context) {
		List<Session> user = new ArrayList<Session>();
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		Cursor cursor = db.rawQuery("Select * From Session", null);
		if (cursor.moveToFirst()) {
			int cursorcount = cursor.getCount();
			for (int i = 0; i < cursorcount; i++) {
				Session n = new Session();
				n.SessionId = (cursor.getInt(cursor.getColumnIndex("SessionId")));
				n.SessionCont = (cursor.getInt(cursor.getColumnIndex("SessionCont")));
				n.SessionContent = (cursor.getString(cursor.getColumnIndex("SessionContent")));
				n.SessionTitle = (cursor.getString(cursor.getColumnIndex("SessionTitle")));
				n.SessionTime = (cursor.getString(cursor.getColumnIndex("SessionTime")));
				user.add(n);
				cursor.moveToNext();
			}
		}
		HandleDatabase.closeCusorAndDB(cursor, db);
		return user;
	}

	public synchronized static void insertSession(Context context, ContentValues values) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.insert(SESSION, null, values);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	public synchronized static void deleteSession(Context context) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.delete(SESSION, null, null);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	public synchronized static void insertUser(Context context, ContentValues values) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.insert(USER, null, values);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	public synchronized static void deleteUser(Context context) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.delete(USER, null, null);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	public synchronized static List<User> getUserList(Context context) {
		List<User> user = new ArrayList<User>();
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		Cursor cursor = db.query(USER, new String[] { "_id", "Name", "Email", "Company", "Country", "Position", "SessionId", "SessionName", "Sent", "Remark" },
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			int cursorcount = cursor.getCount();
			for (int i = 0; i < cursorcount; i++) {
				User n = new User();
				n._id = (cursor.getInt(cursor.getColumnIndex("_id")));
				n.Name = (cursor.getString(cursor.getColumnIndex("Name")));
				n.Email = (cursor.getString(cursor.getColumnIndex("Email")));
				n.Company = (cursor.getString(cursor.getColumnIndex("Company")));
				n.Country = (cursor.getString(cursor.getColumnIndex("Country")));
				n.Position = (cursor.getString(cursor.getColumnIndex("Position")));
				n.SessionId = (cursor.getInt(cursor.getColumnIndex("SessionId")));
				n.SessionName = (cursor.getString(cursor.getColumnIndex("SessionName")));
				n.Sent = (cursor.getString(cursor.getColumnIndex("Sent")));
				n.Remark = (cursor.getString(cursor.getColumnIndex("Remark")));
				user.add(n);
				cursor.moveToNext();
			}
		}
		HandleDatabase.closeCusorAndDB(cursor, db);
		return user;
	}

	public synchronized static List<User> getUserList(Context context, String SessionId) {
		List<User> user = new ArrayList<User>();
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		Cursor cursor = db.rawQuery("Select * From user where sessionid='" + SessionId + "';", null);
		if (cursor.moveToFirst()) {
			int cursorcount = cursor.getCount();
			for (int i = 0; i < cursorcount; i++) {
				User n = new User();
				n._id = (cursor.getInt(cursor.getColumnIndex("_id")));
				n.Name = (cursor.getString(cursor.getColumnIndex("Name")));
				n.Email = (cursor.getString(cursor.getColumnIndex("Email")));
				n.Company = (cursor.getString(cursor.getColumnIndex("Company")));
				n.Country = (cursor.getString(cursor.getColumnIndex("Country")));
				n.Position = (cursor.getString(cursor.getColumnIndex("Position")));
				n.SessionId = (cursor.getInt(cursor.getColumnIndex("SessionId")));
				n.SessionName = (cursor.getString(cursor.getColumnIndex("SessionName")));
				n.Sent = (cursor.getString(cursor.getColumnIndex("Sent")));
				n.Remark = (cursor.getString(cursor.getColumnIndex("Remark")));
				user.add(n);
				cursor.moveToNext();
			}
		}
		HandleDatabase.closeCusorAndDB(cursor, db);
		return user;
	}

	public synchronized static User getUserNo(Context context, String UserNo) {
		User user = null;
		if (UserNo != null && !"".equals(UserNo)) {
			SQLiteDatabase db = HandleDatabase.openDatabase(context);
			Cursor cursor = db.rawQuery("select * from User Where Name = ?", new String[] { UserNo });
			if (cursor.moveToLast()) {
				user = new User();
				user._id = (cursor.getInt(cursor.getColumnIndex("_id")));
				user.Name = (cursor.getString(cursor.getColumnIndex("Name")));
				user.Email = (cursor.getString(cursor.getColumnIndex("Email")));
				user.Company = (cursor.getString(cursor.getColumnIndex("Company")));
				user.Country = (cursor.getString(cursor.getColumnIndex("Country")));
				user.Position = (cursor.getString(cursor.getColumnIndex("Position")));
				user.SessionId = (cursor.getInt(cursor.getColumnIndex("SessionId")));
				user.SessionName = (cursor.getString(cursor.getColumnIndex("SessionName")));
				user.Sent = (cursor.getString(cursor.getColumnIndex("Sent")));
				user.Remark = (cursor.getString(cursor.getColumnIndex("Remark")));
			}
			HandleDatabase.closeCusorAndDB(cursor, db);
		}
		return user;
	}
}
