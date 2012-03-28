/**
 * @Title: CBDB.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.source;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBMenuItemsSet;

/**
 * @author raiden
 *
 * @Description database center of project
 */
public class CBDB extends CBDBBase implements CBIFDishDB {

	protected final static String DBTABLE_DISHES_NAME = "dishes";

	public CBDB(String DBPath) {
		super(DBPath);
		open();
		initDB();
	}

	private void initDB() {
		createDishDBTable();
	}

	public void dropDishDBTable() {
		this.dropTable(DBTABLE_DISHES_NAME);
	}

	public void createDishDBTable() {
		execSql("CREATE TABLE IF NOT EXISTS " + DBTABLE_DISHES_NAME + " ( " +
				"id VARCHAR PRIMARY KEY," +
				"name VARCHAR NOT NULL," +
				"price REAL DEFAULT 0," +
				"tags VARCHAR," +
				"score REAL DEFAULT 0," +
				"summarize VARCHAR," +
				"detail VARCHAR," +
				"thumb VARCHAR," +
				"picture VARCHAR" +
				" ) ");
	}

	public CBMenuItemsSet loadMenuItemsSet() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addDishToDB(CBDish dish) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeDishFromDB(CBDish dish) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dishExists(CBDish dish) {
		// TODO Auto-generated method stub
		return false;
	}

}
