package com.zhuoyang.thetruthbigadventure.utils;

import com.zhuoyang.thetruthbigadventure.data.Truths.TruthItemColumns;

import android.database.Cursor;

public class ColumnTruthItem {
	public int _ID_INDEX;
	public int THREAD_ID_INDEX;
	public int CREATE_TIME_INDEX;
	public int TITLE_INDEX;
	public int CONTENT_INDEX;
	public int DESIRE_INDEX;
	public int NEGTIVE_INDEX;
	
	public ColumnTruthItem(){
		_ID_INDEX = Utils.TRUTH_ITEM_ID;
		THREAD_ID_INDEX = Utils.TRUTH_ITEM_THREAD_ID;
		CREATE_TIME_INDEX = Utils.TRUTH_ITEM_CREATE_TIME;
		TITLE_INDEX = Utils.TRUTH_ITEM_TITLE;
		CONTENT_INDEX = Utils.TRUTH_ITEM_CONTENT;
		DESIRE_INDEX = Utils.TRUTH_ITEM_DESIRE;
		NEGTIVE_INDEX = Utils.TRUTH_ITEM_NEGTIVE;
	}
	
	public ColumnTruthItem(Cursor c){
		_ID_INDEX = c.getColumnIndexOrThrow(TruthItemColumns._ID);
		THREAD_ID_INDEX = c.getColumnIndexOrThrow(TruthItemColumns.THREAD_ID);
		CREATE_TIME_INDEX = c.getColumnIndexOrThrow(TruthItemColumns.CREATE_DATE);
		TITLE_INDEX = c.getColumnIndexOrThrow(TruthItemColumns.TITLE);
		CONTENT_INDEX = c.getColumnIndexOrThrow(TruthItemColumns.CONTENT);
		DESIRE_INDEX = c.getColumnIndexOrThrow(TruthItemColumns.DESIRE);
		NEGTIVE_INDEX = c.getColumnIndex(TruthItemColumns.NEGTIVE);
	}
}
