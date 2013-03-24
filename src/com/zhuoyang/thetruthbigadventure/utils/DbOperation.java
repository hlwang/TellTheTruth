package com.zhuoyang.thetruthbigadventure.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.net.Uri;

import com.zhuoyang.thetruthbigadventure.data.Truths;

public class DbOperation {
	public static final String DEFAULT_SORT_ORDER = "date DESC";
	
	public static void startDeleteFolderItem(AsyncQueryHandler handler,int token,long id){
		Uri uri = ContentUris.withAppendedId(Truths.CONTENT_FOLDER_URI, id);
		handler.startDelete(token, null, uri, null, null);
	}
	
	public static void startDeleteFolderAll(AsyncQueryHandler handler, int token){
		handler.startDelete(token, null, Truths.CONTENT_FOLDER_URI, null, null);
	}
	
	public static void startDeleteTruthItem(AsyncQueryHandler handler,int token,long id){
		Uri uri = ContentUris.withAppendedId(Truths.CONTENT_TRUTH_ITEM_URI, id);
		handler.startDelete(token, null, uri, null, null);
	}
	
	public static void startDeleteTruthAll(AsyncQueryHandler handler, int token){
		handler.startDelete(token, null, Truths.CONTENT_TRUTH_ITEM_URI, null, null);
	}
	
	
	public static void startQueryFolder(AsyncQueryHandler handler,Uri uri,int token,String selection){
		handler.startQuery(token, null, uri, Utils.FOLDER_PROJECTION, selection, null, DEFAULT_SORT_ORDER);
	}
	
	public static void startQueryTruthItem(AsyncQueryHandler handler,Uri uri,int token,String selection){
		handler.startQuery(token, null, uri, Utils.TRUTH_ITEM_PROJECTION, selection, null, DEFAULT_SORT_ORDER);
	}
}
