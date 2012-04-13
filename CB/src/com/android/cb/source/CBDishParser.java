/**
 * @Title: CBDishParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;

/**
 * @author raiden
 *
 * @Description get dish object according to '.xml' file
 */
public class CBDishParser extends CBXmlParser implements CBXmlParser.Callback {

	private CBDish mDish = null;

	public CBDishParser() {
		super();
		setCallback(this);
	}

	public CBDishParser(String filePath) {
		super(filePath);
		setCallback(this);
	}

	@Override
	public boolean parse() {
		return super.parse();
	}

	/**
	 * @Description get dish object as result of parse
	 * @return CBDish but returns null if parse failed
	 */
	public CBDish getDish() {
		return mDish;
	}

	/**
	 * @Description parse element of dish
	 * @return void
	 */
	public void onTagWithValueDetected(String name, String value, final HashMap<String, String> attrs) {
		if ( mDish != null) {
			if (name.equalsIgnoreCase("id")) {
				CBId id = new CBId(value);
				mDish.setId(id);
			}
			else if (name.equalsIgnoreCase("name")) {
				mDish.setName(value);
			}
			else if (name.equalsIgnoreCase("price")) {
				mDish.setPrice(new Float(value));
			}
			else if (name.equalsIgnoreCase("tag")) {
				mDish.addTag(value);
			}
			else if (name.equalsIgnoreCase("score")) {
				mDish.setScore(new Float(value));
			}
			else if (name.equalsIgnoreCase("summary")) {
				mDish.setSummarize(value);
			}
			else if (name.equalsIgnoreCase("detail")) {
				mDish.setDetail(value);
			}
			else if (name.equalsIgnoreCase("thumb")) {
				mDish.setThumb(value);
			}
			else if (name.equalsIgnoreCase("picture")) {
				mDish.setPicture(value);
			}
		} // if
	}

	public void onStartDocument(final XmlPullParser parser) {
		mDish = new CBDish();
	}

	public void onEndDocument(final XmlPullParser parser) {

	}

}