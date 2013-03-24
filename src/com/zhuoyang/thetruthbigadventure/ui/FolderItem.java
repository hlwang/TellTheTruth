package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.utils.ColumnFolder;

import android.content.Context;
import android.database.Cursor;

public class FolderItem {
	private int thread_id;
	private String title;
	private long date;
	private String snippet;
	private boolean isDefault;
	private boolean is_show = true;
	private Context mContext;
	
	public FolderItem(Context context,ColumnFolder columnMap,Cursor cursor){
		mContext = context;
		
		thread_id = cursor.getInt(columnMap._ID_INDEX);
		title = cursor.getString(columnMap.TITLE_INDEX);
		date = cursor.getLong(columnMap.CREATE_TIME_INDEX);
		snippet = cursor.getString(columnMap.SNIPPET_INDEX);
		isDefault = cursor.getInt(columnMap.DEFAULT_INDEX) == 1;
		is_show = cursor.getInt(columnMap.IS_SHOW_INDEX) == 1;
	}

	public int getThread_id() {
		return thread_id;
	}

	public String getTitle() {
		return title;
	}

	public long getDate() {
		return date;
	}

	public String getSnippet() {
		return snippet;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public boolean isIs_show() {
		return is_show;
	}

	public Context getmContext() {
		return mContext;
	}
	
}
