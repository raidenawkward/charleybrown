package com.android.cb;

import com.android.cb.source.CBPathWalker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class CBActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

		// for testing
		testPathWalker();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
    }

    private class TestPathWalker implements CBPathWalker.Callback {

		public boolean onDirDetected(String dir, int depth) {
			Log.d("dir found", dir + ", depth: " + depth);
			return true;
		}

		public boolean onFileDetected(String file, int depth) {
			Log.d("file found", file + ", depth: " + depth);
			return true;
		}

    }

	private void testPathWalker() {
		CBPathWalker walker = new CBPathWalker(new TestPathWalker());
		walker.setRoot("/sdcard");
		walker.go();
	}

}