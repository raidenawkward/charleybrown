/**
 * @Title: CBTagsSet.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.List;

/**
 * @author raiden
 *
 * @Description: tags set list
 */
public class CBTagsSet {
	private List<String> mTagsList;

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
	 * @Description: combine with set
	 * @param @param set - set to combine
	 * @return int - number of tags in common
	 */
	int combine(CBTagsSet set) {
		if (set == null)
			return 0;

		int res = 0;
		for (int i = 0; i < set.count(); ++i) {
			String s = set.getTagsList().get(i);
			if (!contains(s)) {
				add(s);
			} else {
				++res;
			}
		}

		return res;
	}

	boolean remove(String tag) {
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

	boolean remove(int index) {
		if (index < 0 || index > mTagsList.size())
			return false;

		mTagsList.remove(index);
		return true;
	}

	/**
	 * @Description: get count of intersection tags with set
	 * @param @param set
	 * @return int
	 */
	int getIntersection(CBTagsSet set) {
		if (set == null)
			return 0;

		int res = 0;
		List<String> targetList = set.getTagsList();
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

	public List<String> getTagsList() {
		return mTagsList;
	}

	public void setTagsList(List<String> tagsList) {
		this.mTagsList = tagsList;
	}
}
