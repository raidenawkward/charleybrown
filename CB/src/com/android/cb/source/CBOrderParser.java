/**
 * @Title: CBOrderParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import com.android.cb.support.CBOrder;

/**
 * @author raiden
 *
 * @Description get order object according to '.xml' file
 */
public class CBOrderParser extends CBXmlParser {

	public CBOrderParser() {
		// TODO Auto-generated constructor stub
	}

	public CBOrderParser(String filePath) {
		super(filePath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean parse() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @Description get order object as result of parse
	 * @return CBOrder but returns null if parse failed
	 */
	public CBOrder getOrder() {
		return null;
	}
}
