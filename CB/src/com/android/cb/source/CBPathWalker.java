/**
 * @Title: CBPathWalker.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-22
 */
package com.android.cb.source;

import java.io.File;

/**
 * @author raiden
 *
 * @Description walk through the path recursively, with callback methods called
 */
public class CBPathWalker {

	/**
	 * @author raiden
	 *
	 * @Description will be implemented by data collector who wants to use CBPathWalker
	 */
	public abstract static interface Callback {
		/**
		 * @Description will be called when directory detected while walking, and directory counter++
		 * @param dir directory absolute path detected
		 * @param depth depth of current directory
		 * @return boolean returns false if this directory need not be walked recursively any more,
		 * and counter wouldn't ++
		 */
		boolean onDirDetected(String dir, int depth);
		/**
		 * @Description will be called when file detected while walking, and file counter ++
		 * @param file file absolute path detected
		 * @param depth depth of current file
		 * @return boolean returns false if this directory need not be walked recursively any more,
		 * and counter wouldn't ++
		 */
		boolean onFileDetected(String file, int depth);
	}

	private Callback mCallback;
	private String mRoot = new String();
	private int mFileDetectedCount = 0;
	private int mDirDetectedCount = 0;

	public CBPathWalker(Callback callback) {
		mCallback = callback;
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
	}

	public void setRoot(String root) {
		mRoot = root;
	}

	public String getRoot() {
		return mRoot;
	}

	public int getFileDetectedCount() {
		if (mCallback == null)
			return 0;
		return mFileDetectedCount;
	}

	public int getDirDetectedCount() {
		if (mCallback == null)
			return 0;
		return mDirDetectedCount;
	}

	/**
	 * @Description start go walk through path
	 */
	public void go() {
		mFileDetectedCount = 0;
		mDirDetectedCount = 0;

		if (mCallback == null)
			return;

		traverse(mRoot, 0);
	}

	protected void traverse(String node, int depth) {
		if (node == null)
			return;

		File root = new File(node);
		if (!root.exists()) {
			return;
		}

		if (root.isHidden()) {
			return;
		}

		if (root.isDirectory()) {
			if (!mCallback.onDirDetected(root.getAbsolutePath(), depth)) {
				return;
			}

			++mDirDetectedCount;

			File childs[] = root.listFiles();
			if (childs != null) {
				for (int i = 0; i < childs.length; ++i) {
					traverse(childs[i].getAbsolutePath(), depth + 1);
				}
			}

			return;
		}

		if (root.isFile()) {
			if (mCallback.onFileDetected(root.getAbsolutePath(), depth))
				++mFileDetectedCount;
		}
	}

}
