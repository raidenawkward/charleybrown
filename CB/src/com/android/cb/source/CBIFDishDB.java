/**
 * @Title: CBIFDishDB.java
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
 * @Description dish database operations
 */
public interface CBIFDishDB {

	void dropDishDBTable();
	void createDishDBTable();

	CBMenuItemsSet loadMenuItemsSet();
	boolean addDishToDB(CBDish dish);
	boolean removeDishFromDB(CBDish dish);
	boolean dishExists(CBDish dish);
}
