/**
 * @Title: CBDirWalker.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-22
 */
package com.android.cb.source;

/**
 * @author raiden
 *
 * @Description walk through directory recursively, with callback methods called
 */
public class CBDirWalker {

	public abstract static interface Callback {
		boolean onDirDetected(String dir);
		boolean onFileDetected(String file);
	}

	private Callback mCallback;
	private String mRoot = new String();

	public CBDirWalker(Callback callback) {
		mCallback = callback;
	}

	public void setRoot(String root) {
		mRoot = root;
	}

	public String getRoot() {
		return mRoot;
	}

	public void go() {
		traverse(mRoot);
	}

	protected void traverse(String node) {
		
	}
}
