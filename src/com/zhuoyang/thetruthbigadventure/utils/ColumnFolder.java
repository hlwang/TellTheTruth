package com.zhuoyang.thetruthbigadventure.utils;

import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;

import android.database.Cursor;

public class ColumnFolder {
	public int _ID_INDEX;
	public int TITLE_INDEX;
	public int CREATE_TIME_INDEX;
	public int SNIPPET_INDEX;
	public int DEFAULT_INDEX;
	public int IS_SHOW_INDEX;
	
	public ColumnFolder(){
		_ID_INDEX = Utils.FOLDER_ID;
		TITLE_INDEX  = Utils.FOLDER_TITLE;
		CREATE_TIME_INDEX = Utils.FOLDER_CREATE_TIME;
		SNIPPET_INDEX = Utils.FOLDER_SNIPPET;
		DEFAULT_INDEX = Utils.FOLDER_DEFAULT;
		IS_SHOW_INDEX = Utils.FOLDER_IS_SHOW;
	}
	
	public ColumnFolder(Cursor c){
		_ID_INDEX = c.getColumnIndexOrThrow(TruthFolder._ID);
		TITLE_INDEX  = c.getColumnIndexOrThrow(TruthFolder.TITLE);
		CREATE_TIME_INDEX = c.getColumnIndexOrThrow(TruthFolder.CREATE_DATE);
		SNIPPET_INDEX = c.getColumnIndexOrThrow(TruthFolder.SNIPPET);
		DEFAULT_INDEX = c.getColumnIndexOrThrow(TruthFolder.DEFAULT);
		IS_SHOW_INDEX = c.getColumnIndexOrThrow(TruthFolder.IS_SHOW);
	}
}
