package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.utils.ColumnTruthItem;
import com.zhuoyang.thetruthbigadventure.utils.ImageUtils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TruthItem {
	private int _id;
	private int thread_id;
	private long date;
	private String title;
	private String content;
	private int desire;
	private int negtive;
	private Context mContext;
	
	private Bitmap mBitmap;
	
	public TruthItem(Context context,ColumnTruthItem columnMap,Cursor cursor){
		mContext = context;
		
		_id = cursor.getInt(columnMap._ID_INDEX);
		thread_id = cursor.getInt(columnMap.THREAD_ID_INDEX);
		date = cursor.getInt(columnMap.CREATE_TIME_INDEX);
		title = cursor.getString(columnMap.TITLE_INDEX);
		content = cursor.getString(columnMap.CONTENT_INDEX);
		desire = cursor.getInt(columnMap.DESIRE_INDEX);
		negtive = cursor.getInt(columnMap.NEGTIVE_INDEX);
	}

	public void initBitmapForTruthItem(Context context,int width,int number,int resId){
		mBitmap = ImageUtils.loadBitmap(context, width, number, resId);
	}
	
	public int get_id() {
		return _id;
	}

	public int getThread_id() {
		return thread_id;
	}

	public long getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getDesire() {
		return desire;
	}

	public int getNegtive() {
		return negtive;
	}

	public Context getmContext() {
		return mContext;
	}

	public Bitmap getmBitmap() {
		return mBitmap;
	}
	
}
