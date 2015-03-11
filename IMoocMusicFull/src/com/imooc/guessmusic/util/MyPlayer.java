package com.imooc.guessmusic.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

/**
 * 音乐播放类
 * @author MiracleWong
 *
 */
// 设置成为一个单例的模式，不管你怎么调用，返回的始终是一个结果
public class MyPlayer {
	// 索引
	public final static int INDEX_STONE_ENTER = 0;
	public final static int INDEX_STONE_CANCEL = 1;
	public final static int INDEX_STONE_COIN = 2;
	
	// 音效的文件名称
	private final static String[] SONG_NAMES = 
		{"enter.mp3","cancel.mp3","coin.mp3"};
	
	// 音效
	private static MediaPlayer[] mToneMediaPlayer = new MediaPlayer[SONG_NAMES.length];
	/**
	 * 播放音效，按钮点击的声音，无须反复加载
	 * @param context
	 * @param index
	 */
	public static void playTone(Context context, int index) {
		// 加载声音文件
		AssetManager assetManager = context.getAssets();
		if (mToneMediaPlayer[index]==null) {
			mToneMediaPlayer[index] = new MediaPlayer();
			try {
				// 设置数据源
				AssetFileDescriptor fileDescriptor = 
						assetManager.openFd(SONG_NAMES[index]);
				mToneMediaPlayer[index].setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
				// 使音乐播放器进入到可以播放的状态
				mToneMediaPlayer[index].prepare();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		// 开始播放
		mToneMediaPlayer[index].start();		
	}
	
	
	// 歌曲播放
	private static MediaPlayer mMusicMediaPlayer;
	/**
	 * 播放歌曲
	 * @param context
	 * @param fileName
	 */
	public static void playSong(Context context, String fileName) {
		// 该对象始终只被创建了一次
		if (mMusicMediaPlayer == null) {
			mMusicMediaPlayer = new MediaPlayer();
		}
		
		// 强制重置，针对非第一次播放的时候
		mMusicMediaPlayer.reset();
		
		// 加载声音文件
		AssetManager assetManager = context.getAssets();
		try {
			// 设置数据源
			AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
			mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			
			mMusicMediaPlayer.prepare();
			// 声音播放
			mMusicMediaPlayer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void stopTheSong(Context context) {
		if (mMusicMediaPlayer != null) {
			mMusicMediaPlayer.stop();
		}
	}
}
