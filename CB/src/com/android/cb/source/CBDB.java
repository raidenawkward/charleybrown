/**
 * @Title: CBDB.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.source;

import android.database.Cursor;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBTagsSet;

/**
 * @author raiden
 *
 * @Description database center of project
 */
public class CBDB extends CBDBBase implements CBIFDishDB {

	protected final static String DBTABLE_DISHES_NAME = "dishes";
	protected final static String DISHES_TAGS_SPLITOR = ",";

	public CBDB(String DBPath) {
		super(DBPath);
		open();
		initDB();
	}

	private void initDB() {
		createDishDBTable();
	}

	public void dropDishDBTable() {
		dropTable(DBTABLE_DISHES_NAME);
		flush();
	}

	public void createDishDBTable() {
		execSql("CREATE TABLE IF NOT EXISTS " + DBTABLE_DISHES_NAME + " ( " +
				"id VARCHAR PRIMARY KEY NOT NULL," +
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

	private CBTagsSet splitTagString(String str, String splitor) {
		CBTagsSet res = new CBTagsSet();

		String[] tagList = str.split(",");
		for (int i = 0; i < tagList.length; ++i) {
			res.add(tagList[i]);
		}

		return res;
	}

	public CBMenuItemsSet loadMenuItemsSet() {
		Cursor cursor = select("SELECT * from " + DBTABLE_DISHES_NAME);

		CBMenuItemsSet res = new CBMenuItemsSet();
		for (int i = 0; i < cursor.getCount(); ++i) {
			int j = 0;
			CBDish dish = new CBDish();

			CBId id = new CBId(cursor.getString(j++));
			dish.setId(id);
			dish.setName(cursor.getString(j++));
			dish.setPrice(cursor.getFloat(j++));

			CBTagsSet tagSet = splitTagString(cursor.getString(j++), DISHES_TAGS_SPLITOR);
			dish.setTags(tagSet);

			dish.setScore(cursor.getFloat(j++));
			dish.setSummarize(cursor.getString(j++));
			dish.setDetail(cursor.getString(j++));
			dish.setThumb(cursor.getString(j++));
			dish.setPicture(cursor.getString(j++));

			CBMenuItem menuItem = new CBMenuItem();
			menuItem.setDish(dish);
			menuItem.setIndex(i);
			res.add(menuItem);

			if (!cursor.moveToNext())
				break;
		}

		return res;
	}

	public boolean addDishToDB(CBDish dish) {
		if (dish == null)
			return false;

		String sql = "INSERT INTO " + DBTABLE_DISHES_NAME + " VALUES(";
		sql += "\'" + dish.getId().toString() + "\',";
		sql += "\'" + dish.getName() + "\',";
		sql += dish.getPrice() + ",";
		sql += "\'" + dish.getTagsSet().toString(DISHES_TAGS_SPLITOR) + "\',";
		sql += dish.getScore() + ",";
		sql += "\'" + dish.getSummarize() + "\',";
		sql += "\'" + dish.getDetail() + "\',";
		sql += "\'" + dish.getThumb() + "\',";
		sql += "\'" + dish.getPicture() + "\'";
		sql += ")";

		execSql(sql);
		return true;
	}

	public boolean addMenuItemsToDB(CBMenuItemsSet set) {
		if (set == null)
			return false;

		for (int i = 0; i < set.count(); ++i) {
			if (addDishToDB(set.get(i).getDish()) == false) {
				return false;
			}
		}

		return true;
	}

	public boolean removeDishFromDB(CBDish dish) {
		execSql("DELETE from " + DBTABLE_DISHES_NAME + " where id=\'"
				+ dish.getId().toString() + "\'");
		return true;
	}

	public boolean dishExists(CBDish dish) {
		Cursor cursor = select("SELECT * from " + DBTABLE_DISHES_NAME
				+ " WHERE id=\'" + dish.getId().toString() + "\'");
		return (cursor.getCount() > 0);
	}

}
