/**
 * @Title: CBDishParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import com.android.cb.support.CBDish;

/**
 * @author raiden
 *
 * @Description get dish object according to '.xml' file
 */
public class CBDishParser extends CBXmlParser {

	public CBDishParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CBDishParser(String filePath) {
		super(filePath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean parse() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @Description get dish object as result of parse
	 * @return CBDish but returns null if parse failed
	 */
	public CBDish getDish() {
		return null;
	}
}
