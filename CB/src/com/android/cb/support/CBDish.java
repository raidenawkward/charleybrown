/**
 * @Title: CBDish.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.ArrayList;

/**
 * @author raiden
 *
 * @Description: basic dish define
 */
public class CBDish {

	private CBId mId = new CBId();
	private String mName = "";
	private float mPrice = 0.0f;
	private CBTagsSet mTags = new CBTagsSet();
	private float mScore = 0.0f;
	private String mSummarize = "";
	private String mDetail = "";
	private String mThumb = "";
	private String mPicture = "";

	public CBDish() {
	}

	public CBDish(CBDish dish) {
		if (dish == null)
			return;

		mId = new CBId(dish.getId());
		mName = dish.getName();
		mPrice = dish.getPrice();
		mTags.setTagsList(dish.getTags());
		mScore = dish.getScore();
		mSummarize = dish.getSummarize();
		mDetail = dish.getDetail();
		mThumb = dish.getThumb();
		mPicture = dish.getPicture();
	}

	public boolean equals(CBDish dish) {
		return mId.equals(dish.getId());
	}

	public boolean isTagContained(String tag) {
		return mTags.contains(tag);
	}

	public boolean isTagsSetContained(CBTagsSet tags) {
		return (mTags.getIntersection(tags) != 0);
	}

	public void addTag(String tag) {
		mTags.add(tag);
	}

	public void addTagSet(CBTagsSet set) {
		mTags.combine(set);
	}

	public CBId getId() {
		return mId;
	}

	public void setId(CBId id) {
		this.mId.setId(id);
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public float getPrice() {
		return mPrice;
	}

	public void setPrice(float price) {
		this.mPrice = price;
	}

	public ArrayList<String> getTags() {
		return mTags.getTagsList();
	}

	public CBTagsSet getTagsSet() {
		return mTags;
	}
	public void setTags(ArrayList<String> tagsList) {
		this.mTags.setTagsList(tagsList);
	}

	public void setTags(CBTagsSet set) {
		if (set == null)
			return;
		this.mTags.setTagsList(set.getTagsList());
	}

	public float getScore() {
		return mScore;
	}

	public void setScore(float score) {
		this.mScore = score;
	}

	public String getSummarize() {
		return mSummarize;
	}

	public void setSummarize(String summarize) {
		this.mSummarize = summarize;
	}

	public String getDetail() {
		return mDetail;
	}

	public void setDetail(String detail) {
		this.mDetail = detail;
	}

	public String getThumb() {
		return mThumb;
	}

	public void setThumb(String thumb) {
		this.mThumb = thumb;
	}

	public String getPicture() {
		return mPicture;
	}

	public void setPicture(String picture) {
		this.mPicture = picture;
	}


	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	public static final String TAG_PRICE = "price";
	public static final String TAG_TAGS = "tags";
	public static final String TAG_TAGS_SPLITER = ",";
	public static final String TAG_SCORE = "score";
	public static final String TAG_SUMMARIZE = "summary";
	public static final String TAG_DETAIL = "detail";
	public static final String TAG_THUMB = "thumb";
	public static final String TAG_PICTURE = "picture";

	public static boolean setDishAttr(CBDish dish, final String attr, final String value) {
		if (dish == null || attr == null || value == null)
			return false;

		if (attr.equalsIgnoreCase(TAG_ID)) {
			dish.setId(new CBId(value));

		} else if (attr.equalsIgnoreCase(TAG_NAME)) {
			dish.setName(value);

		} else if (attr.equalsIgnoreCase(TAG_PRICE)) {
			float price = Float.valueOf(value);
			dish.setPrice(price);

		} else if (attr.equalsIgnoreCase(TAG_TAGS)) {
			CBTagsSet set = new CBTagsSet();
			String[] tags = value.split(TAG_TAGS_SPLITER);
			if (tags != null) {
				for (int i = 0; i < tags.length; ++i)
					set.add(tags[i]);
			}
			dish.setTags(set);

		} else if (attr.equalsIgnoreCase(TAG_SCORE)) {
			float score = Float.valueOf(value);
			dish.setScore(score);

		} else if (attr.equalsIgnoreCase(TAG_SUMMARIZE)) {
			dish.setSummarize(value);

		} else if (attr.equalsIgnoreCase(TAG_DETAIL)) {
			dish.setDetail(value);

		} else if (attr.equalsIgnoreCase(TAG_THUMB)) {
			dish.setThumb(value);

		} else if (attr.equalsIgnoreCase(TAG_PICTURE)) {
			dish.setPicture(value);

		} else {
			return false;
		}

		return true;
	}

}
