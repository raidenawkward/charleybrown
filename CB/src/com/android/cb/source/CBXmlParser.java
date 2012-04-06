/**
 * @Title: CBXmlParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import java.io.FileInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * @author raiden
 *
 * @Description base class of xml parser
 */
public abstract class CBXmlParser {

	public interface Callback {
		public void onStartDocument(final XmlPullParser parser);
		public void onTagWithValueDetected(String tag, String value, final XmlPullParser parser);
		public void onEndDocument(final XmlPullParser parser);
	}

	private Callback mCallback = null;
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
	public boolean parse() {
		XmlPullParser parser = Xml.newPullParser();
		CBXmlParser.Callback callback = getCallback();

		try {
			InputStream inStream = new FileInputStream(getFile());
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					if (callback != null) {
						callback.onStartDocument(parser);
					}
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();

					eventType = parser.next();
					while(eventType == XmlPullParser.IGNORABLE_WHITESPACE) {
						eventType = parser.next();
					}

					if (eventType == XmlPullParser.TEXT) {
						if (callback != null) {
							String value  = parser.getText();
							callback.onTagWithValueDetected(name, value, parser);
						}
					} else
						continue;

					break;
				case XmlPullParser.END_TAG:
					if (callback != null) {
						callback.onEndDocument(parser);
					}
					break;
				}

				eventType = parser.next();
			} // while

			inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
		}

		return true;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

	public Callback getCallback() {
		return mCallback;
	}

}
