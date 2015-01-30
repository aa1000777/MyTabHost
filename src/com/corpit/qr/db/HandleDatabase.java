package com.corpit.qr.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandleDatabase {
	static String filePath = "data/data/com.corpit.corpitiscan/databases/CorpitApp.db";
	static String pathStr = "data/data/com.corpit.corpitiscan/databases";

	public static SQLiteDatabase openDatabase(Context context) {
		File jhPath = new File(filePath);
		if (jhPath.exists()) {
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(jhPath, null);
			return db;
		} else {
			File path = new File(pathStr);
			if (path.mkdir()) {
				System.out.println("�����ɹ�");
			} else {
				System.out.println("����ʧ��");
			}
			;
			try {
				AssetManager am = context.getAssets();
				InputStream is = am.open("CorpitApp.db");
				FileOutputStream fos = new FileOutputStream(jhPath);
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return openDatabase(context);
		}
	}

	public synchronized static void closeCusorAndDB(Cursor cursor, SQLiteDatabase db) {
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (db != null && db.isOpen()) {
			db.close();
			db = null;
		}
	}
}
