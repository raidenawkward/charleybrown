/**
 * @Title: CBXmlParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

/**
 * @author raiden
 *
 * @Description base class of xml parser
 */
public abstract class CBXmlParser {

	private String mFile;

	public CBXmlParser() {
		mFile = new String();
	}

	public CBXmlParser(String filePath) {
		mFile = filePath;
	}

	public String getFile() {
		return mFile;
	}

	public void setFile(String file) {
		mFile = file;
	}

	/**
	 * @Description do parse action
	 * @return boolean
	 */
	public abstract boolean parse();

}
