/**
 * @Title: CBDishesScanner.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-4
 */
package com.android.cb.source;

import com.android.cb.support.CBComparator;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;

/**
 * @author raiden
 *
 * @Description TODO
 */
public class CBDishesScanner implements CBPathWalker.Callback {

	private final static String STRING_XML_EXT = "xml";
	private final static String STRING_BACKLASH = "/";

	private CBPathWalker mPathWalker = new CBPathWalker(this);
	private String mPath = null;
	private CBMenuItemsSet mMenuItemsSet = new CBMenuItemsSet();

	public CBDishesScanner() {

	}

	public CBDishesScanner(String dishesDir) {
		mPath = dishesDir;
	}

	/**
	 * @Description scan path for CBMenuItemsSet
	 * @return CBMenuItemsSet scan result
	 */
	public CBMenuItemsSet scan() {
		return scan(mPath);
	}

	/**
	 * @Description  scan specified dir for CBMenuItemsSet
	 * @param dishesDir path to scan
	 * @return CBMenuItemsSet scan result
	 */
	public CBMenuItemsSet scan(String dishesDir) {
		if (dishesDir == null)
			return mMenuItemsSet;

		clear();

		mPathWalker.setRoot(dishesDir);
		mPathWalker.go();

		return mMenuItemsSet;
	}

	/**
	 * @Description clear scan results
	 */
	public void clear() {
		mMenuItemsSet.clear();
	}

	public boolean onDirDetected(String dir, int depth) {
		if (depth < 0)
			return false;

		return true;
	}

	private static String getFileExt(String path) {
		if (path == null)
			return null;

		int indexOfDot = path.lastIndexOf(".");
		return indexOfDot >= 0 ? path.substring(indexOfDot + 1) : path;
	}

	private static String getFileDirFromPath(String path) {
		if (path == null)
			return null;

		int indexOfBackash = path.lastIndexOf(STRING_BACKLASH);
		return indexOfBackash > 0 ? path.substring(0,indexOfBackash + 1) : path;
	}

	private static boolean fileExists(String path) {
//		File file = new File(path);
//		return file.exists();
		return true;
	}

	public boolean onFileDetected(String file, int depth) {
		String ext = getFileExt(file);
		if (!ext.equals(STRING_XML_EXT))
			return false;

		CBDishParser parser = new CBDishParser(file);
		if (!parser.parse())
			return false;

		CBDish dish = parser.getDish();
		if (dish == null)
			return false;

		if (!fileExists(dish.getThumb()))
			return false;

		if (!fileExists(dish.getPicture()))
			return false;

		dish.setPicture(getFileDirFromPath(file) + "" + dish.getPicture());
		dish.setThumb(getFileDirFromPath(file) + dish.getThumb());

		CBMenuItem item = new CBMenuItem(dish);

		// compare and insert
		mMenuItemsSet.add(item, new CBDishComparator());

		return true;
	}

	protected class CBDishComparator extends CBComparator<CBMenuItem> {

		@Override
		public int compare(CBMenuItem arg0, CBMenuItem arg1) {
			try {
				long id0 = Long.valueOf(arg0.getDish().getId().toString());
				long id1 = Long.valueOf(arg1.getDish().getId().toString());

				return (int) (id0 - id1);

			} catch (Exception e) {
				return 0;
			}
		}
	}

}
