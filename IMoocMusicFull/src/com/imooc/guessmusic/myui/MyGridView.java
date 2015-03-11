package com.imooc.guessmusic.myui;

import java.util.ArrayList;

import com.imooc.guessmusic.R;
import com.imooc.guessmusic.model.IWordButtonClickListener;
import com.imooc.guessmusic.model.WordButton;
import com.imooc.guessmusic.util.Util;




import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class MyGridView extends GridView{
	// 设置一个常量来代表生成的字数
	public final static int COUNTS_WORDS = 16;
	// 容器
	private ArrayList<WordButton> mArrayList = new ArrayList<WordButton>();
	private MyGridAdapter mAdapter;
	private Context mContext;
	
	// 定义一个动画来进行文字待选框
	private Animation mScaleAnimmation;
	// 定义一个WordButton的接口的监听器的对象
	private IWordButtonClickListener mWordButtonListener;

	public MyGridView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// TODO Auto-generated constructor stub
		// context的初始化
		mContext = context;
		// 初始化
		mAdapter = new MyGridAdapter();
		// 关联
		this.setAdapter(mAdapter);
	}
	
	//填充数据
	public void updateData(ArrayList<WordButton> list) {
		mArrayList = list;		//mArrayList是真正的容器，其他的是形参list
		
		// 刷新，重新设置数据源
		setAdapter(mAdapter);
	}
	
	class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mArrayList.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return mArrayList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public View getView(int pos, View v, ViewGroup p) {
			// TODO Auto-generated method stub
			final WordButton holder;
			// 此处对v进行判断
			if(v==null){
				v = Util.getView(mContext, R.layout.self_ui_gridview_item);
				
				holder = mArrayList.get(pos);
				
				// 加载动画
				mScaleAnimmation = AnimationUtils.loadAnimation(mContext, R.anim.scale);
				mScaleAnimmation.setStartOffset(pos * 100);
				
				holder.mIndex = pos;
				holder.mViewButton = (Button)v.findViewById(R.id.item_btn);
				
				holder.mViewButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mWordButtonListener.onWordButtonClick(holder);
					}
				});
				
				v.setTag(holder);
			} else {
				holder = (WordButton)v.getTag();
			}
			
			holder.mViewButton.setText(holder.mWordString);
			
			// 动画播放
			v.startAnimation(mScaleAnimmation);
			return v;
		}
		
	}
	/**
	 * 注册监听接口
	 * 括号()里面指的是谁注册的这个接口
	 * @param listener
	 */
	//监听器实现的方法
	public void registOnWordButtonClick(IWordButtonClickListener listener) {
		mWordButtonListener = listener;
	}
	
}
