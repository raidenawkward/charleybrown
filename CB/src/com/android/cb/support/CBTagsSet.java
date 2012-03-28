/**
 * @Title: CBTagsSet.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.ArrayList;

/**
 * @author raiden
 *
 * @Description tags set list
 */
public class CBTagsSet implements CBIFSetHandler<String> {
	private ArrayList<String> mTagsList = new ArrayList<String>();

	public CBTagsSet() {
		
	}

	public CBTagsSet(CBTagsSet set) {
		mTagsList = set.getTagsList();
	}

	public CBTagsSet(String[] list) {
		mTagsList.clear();
		for (int i = 0; i < list.length; ++i) {
			mTagsList.add(list[i]);
		}
	}

	public CBTagsSet(String string, String separator) {
		String[] list = string.split(separator);
		for (int i = 0; i < list.length; ++i) {
			mTagsList.add(list[i]);
		}
	}

	public boolean isEmpty() {
		return mTagsList.isEmpty();
	}

	public int count() {
		return mTagsList.size();
	}

	public boolean contains(String tag) {
		if (tag == null)
			return false;

		for (int i = 0; i < mTagsList.size(); ++i) {
			if (mTagsList.get(i) == tag)
				return true;
		}

		return false;
	}

	public boolean add(String tag) {
		if (tag == null)
			return false;

		String tagToInsert = tag.trim();
		if (contains(tagToInsert))
			return false;

		mTagsList.add(tagToInsert);
		return true;
	}

	/**
	 * @Description combine with set
	 * @param @param set - set to combine
	 * @return int - number of tags in common
	 */
	int combine(CBTagsSet set) {
		if (set == null)
			return 0;

		int res = 0;
		for (int i = 0; i < set.count(); ++i) {
			String s = set.getTagsList().get(i);
			if (!add(s)) {
				++res;
			}
		}

		return res;
	}

	public boolean remove(String tag) {
		if (tag == null)
			return false;

		for (int i = 0; i < mTagsList.size(); ++i) {
			String s = mTagsList.get(i);
			if (s == tag) {
				mTagsList.remove(i);
				return true;
			}
		}

		return false;
	}

	public boolean remove(int index) {
		if (index < 0 || index >= mTagsList.size())
			return false;

		mTagsList.remove(index);
		return true;
	}

	/**
	 * @Description get count of intersection tags with set
	 * @param @param set
	 * @return int
	 */
	int getIntersection(CBTagsSet set) {
		if (set == null)
			return 0;

		int res = 0;
		ArrayList<String> targetList = set.getTagsList();
		for (int i = 0; i < mTagsList.size(); ++i) {
			String src = mTagsList.get(i);
			for (int j = 0; j < targetList.size(); ++i) {
				if (src == targetList.get(j)) {
					res ++;
				}
			}
		}
		return res;
	}

	public ArrayList<String> getTagsList() {
		return mTagsList;
	}

	public void setTagsList(ArrayList<String> tagsList) {
		this.mTagsList = tagsList;
	}

	public String get(int index) {
		if (index < 0 || index >= mTagsList.size())
			return null;

		return mTagsList.get(index);
	}

	public String get(String item) {
		if (item == null)
			return null;

		for (int i = 0; i < mTagsList.size(); ++i) {
			String s = mTagsList.get(i);
			if (s.equals(item))
				return s;
		}

		return null;
	}

	public boolean update(String item) {
		return false;
	}

	public boolean update(int index, String item) {
		return false;
	}

	public int getIndexOf(String item) {
		if (item == null)
			return -1;

		for (int i = 0; i < mTagsList.size(); ++i) {
			if (mTagsList.get(i).equals(item))
				return i;
		}

		return -1;
	}

	public String toString(String splitor) {
		String res = new String();
		for (int i = 0; i < mTagsList.size(); ++i) {
			String tag = mTagsList.get(i);
			if (i > 0 && i != mTagsList.size() - 1)
				res += splitor;
			res += tag;
		}

		return res;
	}
}
