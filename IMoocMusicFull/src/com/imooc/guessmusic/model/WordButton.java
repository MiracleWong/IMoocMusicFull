package com.imooc.guessmusic.model;

import android.widget.Button;

/**
 * ÎÄ×Ö°´Å¥
 * @author MiracleWong
 *
 */
public class WordButton {
	public int mIndex;
	public boolean mIsVisiable;
	public String mWordString;
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisiable = true;
		mWordString = "";
	}
}
