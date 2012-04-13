/**
 * @Title: CBXmlWritter.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-6
 */
package com.android.cb.source;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


/**
 * @author raiden
 *
 * @Description base class of xml writer
 */
public class CBXmlWriter {

	public static final String XML_START_TAG_START = "<";
	public static final String XML_END_TAG_START = "</";
	public static final String XML_TAG_END = ">";
	public static final String XML_ONELINE_TAG_END = "/>";
	public static final String XML_SPACE = "\t";
	public static final String XML_RETURN = "\n";

	private File mFile = null;
	private FileOutputStream mOutStream = null;
	private OutputStreamWriter mStreamWriter = null;
	private String mEncoding;

	public CBXmlWriter() {
	}

	public CBXmlWriter(String path, String encoding) {
		mFile = new File(path);
		if (mFile.exists() == false) {
			try {
				if (mFile.createNewFile() == false)
					return;
				if (mFile.canWrite() == false)
					return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mEncoding = encoding;
	}

	public void setEncoding(String encoding) {
		mEncoding = encoding;
	}

	public String getEncoding() {
		return mEncoding;
	}

	public boolean open() {
		return open(mFile);
	}

	public boolean open(File file) {
		if (file == null)
			return false;

		if (file.exists() == false) {
			try {
				if (file.createNewFile() == false)
					return false;
				if (file.canWrite() == false)
					return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			mOutStream = new FileOutputStream(file, false);
			mStreamWriter = new OutputStreamWriter(mOutStream, mEncoding);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void close() {
		if (mStreamWriter != null) {
			try {
				mStreamWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (mOutStream != null) {
			try {
				mOutStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mOutStream = null;
	}

	protected boolean writeStringToFile(String str, int level, boolean newLine) {
		if (mOutStream == null || level < 0)
			return false;

		String header = newLine ? XML_RETURN : "";
		for (int i = 0; i < level; ++i)
			header += XML_SPACE;

		try {
			mStreamWriter.write(new String(header + str));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean writeXmlHeader() {
		String header = "<?xml version=\"1.0\" encoding=\"" + mEncoding + "\"?>";
		return writeStringToFile(header, 0, false);
	}

	public boolean writeStartTag(String tag, int level) {
		String str = new String(tag);

		if (str.startsWith(XML_START_TAG_START) == false)
			str = XML_START_TAG_START + str;

		if (str.endsWith(XML_TAG_END) == false)
			str += XML_TAG_END;

		return writeStringToFile(str, level, true);
	}

	public boolean writeEndTag(String tag, int level) {
		String str = new String(tag);
		if (str.startsWith(XML_END_TAG_START) == false)
			str = XML_END_TAG_START + str;

		if (str.endsWith(XML_TAG_END) == false)
			str += XML_TAG_END;

		return writeStringToFile(str, level, true);
	}

	public boolean writeTagContent(String content, int level) {
		return writeStringToFile(content, level, true);
	}

	public boolean writeOneLine(String line, int level) {
		String str = line;
		if (str.startsWith(XML_START_TAG_START) == false)
			str = XML_START_TAG_START + str;

		if (str.endsWith(XML_ONELINE_TAG_END) == false)
			str += XML_ONELINE_TAG_END;

		return writeStringToFile(str, level, true);
	}

	public boolean writeOneLineTags(String rawTag, String tagAttrs, String content, int level) {
		String line = "";
		String startTag = rawTag + (tagAttrs == null ? "" : " " + tagAttrs);
		if (startTag.startsWith(XML_START_TAG_START) == false)
			startTag = XML_START_TAG_START + startTag;

		if (startTag.endsWith(XML_TAG_END) == false)
			startTag += XML_TAG_END;

		String endTag = rawTag;
		if (endTag.startsWith(XML_END_TAG_START) == false)
			endTag = XML_END_TAG_START + endTag;

		if (endTag.endsWith(XML_TAG_END) == false)
			endTag += XML_TAG_END;

		line = startTag + content + endTag;

		return writeStringToFile(line, level, true);
	}

}
