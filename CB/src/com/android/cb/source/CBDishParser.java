/**
 * @Title: CBDishParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import java.io.FileInputStream;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;

/**
 * @author raiden
 *
 * @Description get dish object according to '.xml' file
 */
public class CBDishParser extends CBXmlParser {

	//private static final CBDish  = null;

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
		XmlPullParser parser = Xml.newPullParser();

		try {
			InputStream inStream = new FileInputStream(getFile());
			parser.setInput(inStream, "UTF-8");

			int eventType = parser.getEventType();


			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {

				case XmlPullParser.START_DOCUMENT://init dish

					mDish = new CBDish();	
					break;

				case XmlPullParser.START_TAG://start element parse

					String name = parser.getName();
					String value  = parser.nextText();
					parseDish(name, value);
					
					break;

				case XmlPullParser.END_TAG://end element

					if (parser.getName().equalsIgnoreCase("dish") && mDish != null) {
						
					}

					break;

				}

				eventType = parser.next();

			}

			inStream.close();



			} catch (Exception e) {

				e.printStackTrace();

		}

		
		return false;
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
	private void  parseDish(String name, String value) {
		
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
			
			else if (name.equalsIgnoreCase("detail"))	{
				mDish.setDetail(value);
			}
			
			else if (name.equalsIgnoreCase("thumb")) {
				mDish.setThumb(value);
			}
			
			else if (name.equalsIgnoreCase("picture")) {
				mDish.setPicture(value);
			}			
		}	// if
	}
	private CBDish mDish;
}