/**
 * @Title: CBDB.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.source;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * @author raiden
 *
 * @Description base database class
 */
public abstract class CBDBBase {
	protected SQLiteDatabase mDB;
	private String mDBPath;

	public CBDBBase(String DBPath) {
		mDBPath = DBPath;
	}

	public SQLiteDatabase getDB() {
		return mDB;
	}

	public void execSQL(boolean ignoreErrors, String... sqls) {
		for ( String sql : sqls ) {
			try {
				mDB.execSQL(sql);
			} catch ( SQLException e ) {
				if (!ignoreErrors) {
					throw e;
				}
			}
		} // for
	}

	public void execSql(String... sqls) {
		execSQL(true,sqls);
	}

	synchronized public Cursor select(String sql) {
		Cursor res = null;

		try {
			res = mDB.rawQuery(sql, null);
		} catch ( SQLException e ) {
			if (res != null)
				res.close();
		} finally {
			if (res != null)
				res.moveToFirst();
		}

		return res;
	}
	/**
	 * @Description reset database file path, will close old database if path changed
	 * @param DBPath
	 * @return void
	 */
	public void setDBPath(String DBPath) {
		if (mDBPath.equals(DBPath))
			return;

		close();

		this.mDBPath = DBPath;
	}

	public String getDBPath() {
		return mDBPath;
	}

	public boolean open() {
		if (mDB.isOpen())
			return true;

		if (mDBPath == null)
			return false;

		mDB = SQLiteDatabase.openOrCreateDatabase(mDBPath, null);
		if (mDB == null)
			return false;

		return mDB.isOpen();
	}

	public boolean open(String path) {
		setDBPath(path);
		return open();
	}

	public void close() {
		if (mDB == null)
			return;

		if (mDB.isOpen()) {
			mDB.close();
			mDB = null;
		}
	}

	public void dropTable(String table) {
		mDB.execSQL("DROP TABLE IF EXISTS " + table);
	}
}
