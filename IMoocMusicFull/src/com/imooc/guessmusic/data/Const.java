package com.imooc.guessmusic.data;

public class Const {
	// 设置两个常量，来代表后面的名字，时候的选则
	public static final int INDEX_FILE_NAME = 0;
	public static final int INDEX_SONG_NAME = 1;
	// 初始的完整的金币的数量
	public static final int TOTAL_COINS = 20;
	//static 直接使用名字就可以使用其中的变量和方法，而不要new一个，常量要大写
	public static final String SONG_INFO[][] = {
		{"__00000.m4a","征服"},
		{"__00001.m4a","童话"},
		{"__00002.m4a","同桌的你"},
		{"__00003.m4a","七里香"},
		{"__00004.m4a","传奇"},
		{"__00005.m4a","大海"},
		{"__00006.m4a","后来"},
		{"__00007.m4a","你的背包"},
		{"__00008.m4a","再见"},
		{"__00009.m4a","老男孩"},
		{"__00010.m4a","龙的传人"},
	};
	public static final String FILE_NAME_SAVE_DATA = "data.dat";
	
	public static final int INDEX_LOAD_DATA_STAGE = 0;
	
	public static final int INDEX_LOAD_DATA_COINS = 1;
	
	
}
