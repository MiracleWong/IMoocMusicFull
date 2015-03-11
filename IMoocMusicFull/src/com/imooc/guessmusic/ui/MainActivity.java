package com.imooc.guessmusic.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import com.imooc.guessmusic.R;
import com.imooc.guessmusic.data.Const;
import com.imooc.guessmusic.model.IWordButtonClickListener;
import com.imooc.guessmusic.model.Song;
import com.imooc.guessmusic.model.WordButton;
import com.imooc.guessmusic.myui.MyGridView;
import com.imooc.guessmusic.util.MyLog;
import com.imooc.guessmusic.util.Util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity implements IWordButtonClickListener {
	
	// 设置常量
	private static final String TAG = "MainActivity";
	
	// 唱片相关的动画
	private Animation mPanAnim;
	private LinearInterpolator mPanLin;			//线性的速度

	// 拨杆进入的动画
	private Animation mBarInAnim;
	private LinearInterpolator mBarInLin;		//线性的速度

	// 拨杆划出的动画
	private Animation mBarOutAnim;
	private LinearInterpolator mBarOutLin;		//线性的速度
	
	//
	private ImageView mViewPan;
	private ImageView mViewPanBar;
	
	// Play 按键事件
	private ImageButton mBtnPlayStart;
	
	private boolean mIsRunning = false;
	
	// 文字框的容器
	private ArrayList<WordButton> mAllWords;
	
	// 定义容器
	private ArrayList<WordButton> mBtnSelectWords;
	
	// 定义一个自定义控件的对象
	private MyGridView mMyGridView;
	
	// 已选择的文字框的UI容器
	private LinearLayout mViewWordsContainer;
	
	// 当前的歌曲
	private Song mCurrentSong;
	
	// 当前关的索引
	private int mCurrentStageIndex = 2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mViewPan = (ImageView)findViewById(R.id.imageView1);
		mViewPanBar = (ImageView)findViewById(R.id.imageView2);
		
		// 初始化自定义控件
		mMyGridView = (MyGridView)findViewById(R.id.gridview);
		
		mViewWordsContainer = (LinearLayout)findViewById(R.id.word_select_container);
		
		// 
		mMyGridView.registOnWordButtonClick(this);
		
		// 初始化动画
		mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		mPanLin = new LinearInterpolator();
		mPanAnim.setInterpolator(mPanLin);	
		mPanAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				mViewPanBar.startAnimation(mBarOutAnim);
			}
		});

		mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
		mBarInLin = new LinearInterpolator();
		mBarInAnim.setFillAfter(true); 			//动画保持停止的状态
		mBarInAnim.setInterpolator(mBarInLin);		
		mBarInAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				mViewPan.startAnimation(mPanAnim);	
			}
		});
		
		mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
		mBarOutLin = new LinearInterpolator();
		mBarOutAnim.setFillAfter(true); 			//动画保持停止的状态
		mBarOutAnim.setInterpolator(mBarOutLin);	

		mBarOutAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				mIsRunning = false;
				mBtnPlayStart.setVisibility(View.VISIBLE);
			}
		});
		
		mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
		mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "HELLO WORLD", Toast.LENGTH_SHORT).show();
				handlePlayButton();
			}
		});
		
		// 初始化游戏数据
		initCurrentStageData();
		
	}
	
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		// TODO Auto-generated method stub
//		Toast.makeText(MainActivity.this, wordButton.mIndex, Toast.LENGTH_SHORT).show();
		setSelectWord(wordButton);
	}
	
	//清除答案的方法
	private void clearTheAnswer(WordButton wordButton) {
		// 设置已选框的文字变得不可见
		wordButton.mViewButton.setText("");
		wordButton.mWordString = "";
		wordButton.mIsVisiable = false;
		
		// 设置待选框
		setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
	}
	/**
	 * 设置答案
	 * @param wordButton
	 */
	private void setSelectWord(WordButton wordButton) {
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			if (mBtnSelectWords.get(i).mWordString.length() == 0) {
				// 设置答案文字框的内容和可见性
				mBtnSelectWords.get(i).mViewButton.setText(wordButton.mWordString);
				mBtnSelectWords.get(i).mIsVisiable = true;
				mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
				// 记录索引
				mBtnSelectWords.get(i).mIndex = wordButton.mIndex;
				
				// Log ......
//				MyLog.d(TAG, mBtnSelectWords.get(i).mIndex+"");
				// 设置待选框的可见性
				setButtonVisiable(wordButton, View.INVISIBLE);
				break;
			}
		}
	}
	
	/**
	 * 设置文字框是否可见
	 * @param button
	 * @param visibility
	 */
	private void setButtonVisiable(WordButton button, int visibility) {
		button.mViewButton.setVisibility(visibility);
		button.mIsVisiable = (visibility == View.VISIBLE)? true : false;
		
		// Log
		MyLog.d(TAG, button.mIsVisiable+"");
	}
    /**
     * 处理圆盘中间的播放按钮，就是开始播放音乐
     */
	private void handlePlayButton() {
		if (mViewPanBar != null){
			if(!mIsRunning){
				mIsRunning = true;
				// 开始拨杆进入动画
				mViewPanBar.startAnimation(mBarInAnim);	
				mBtnPlayStart.setVisibility(View.INVISIBLE);
			}			
		}						
	}

	/**
	 * 根据索引读取当前关的歌曲信息，返回查询到的歌曲
	 * @param stageIndex
	 * @return
	 */
	private Song loadStageSongInfo(int stageIndex) {
		Song song = new Song();
		
		String[] stage = Const.SONG_INFO[stageIndex];
		song.setSongFileName(stage[Const.INDEX_FILE_NAME]);		//获得歌曲文件的名字
		song.setSongName(stage[Const.INDEX_SONG_NAME]);				//获得歌曲名
		return song;
	}
	protected void initCurrentStageData() {
		// 读取当前关的歌曲信息
		mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);
		// 初始化已选择框
		mBtnSelectWords = initWordSelect();
		
		LayoutParams params =  new LayoutParams(40, 40);
		
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton, params);
		}
		// 获得数据
		mAllWords = initAllWord();
		// 更新数据 —— MyGridView
		mMyGridView.updateData(mAllWords);	
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mViewPan.clearAnimation();
		super.onPause();
	}
	
	/**
	 * 初始化待选择的文字框
	 * @return
	 */
	private ArrayList<WordButton> initAllWord() {
		// 初始化
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		// 获得所有的待选文字
		String[] words = generateWords();
		// 获得所有的待选文字，这里会有一个相应的方法
		// 硬编码的24
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			WordButton button = new WordButton();
			button.mWordString = words[i];
			data.add(button);
		}
		return data;
	}

	/**
	 * 初始化已选择的文字框
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		for(int i = 0; i < mCurrentSong.getNameLength(); i++) {
			View view = Util.getView(MainActivity.this, R.layout.self_ui_gridview_item);
			
			final WordButton holder = new WordButton();
			holder.mViewButton = (Button)view.findViewById(R.id.item_btn);
			holder.mViewButton.setTextColor(Color.WHITE);
			holder.mViewButton.setText("");
			holder.mIsVisiable = false;
			holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
			holder.mViewButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					clearTheAnswer(holder);
					
				}
			});
			
			data.add(holder);
		}
		return data;
	}

	
	/**
	 * 生成所有的待选文字——包含歌曲名和其他的随机文字
	 * 返回的是一个字符串里面包含了24个字
	 * @return
	 */
	private String[] generateWords(){
		Random random = new Random();
		
		String[] words = new String[MyGridView.COUNTS_WORDS];
		
		//存入歌名
		for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
			//加入空的字符串会进行自动的字符转换
			//getNameCharacters返回的是一个数组，需要下标来进行一个一个的返回和加入
			words[i] = mCurrentSong.getNameCharacters()[i]+"";
		}
		
		//存入随机文字并存入数组
		for (int i = mCurrentSong.getNameLength(); i < MyGridView.COUNTS_WORDS; i++) {
			// char类型转换为字符串
			words[i] = getRandomChar()+"";			
		}
		// 打乱文字的顺序：首先从所有元素中随机选取一个与第一个元素进行交换
		// 然后在第二个之后选择一个元素与第二个交换，直到最后一个元素。
		for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);		//确保出现的数据在0~24中间
			
			String buf = words[index];
			words[index] = words[i];
			words[i] = buf;
		}
		return words;
	}
	
	/**
	 * 随机生成汉字
	 * @return
	 */
	private char getRandomChar() {
		String str = "";
		int hightPos;		//高位
		int lowPos;			//低位
		
		Random random = new Random();			//引入随机
		
		hightPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));
		
		//一个汉字由两个字节组合起来，
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();
		
		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str.charAt(0);
	}
}
