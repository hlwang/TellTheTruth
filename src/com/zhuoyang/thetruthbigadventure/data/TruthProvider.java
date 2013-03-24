package com.zhuoyang.thetruthbigadventure.data;

import com.zhuoyang.thetruthbigadventure.data.TruthDatabaseHelper.TABLE;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthItemColumns;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.text.TextUtils;
import android.util.Log;

public class TruthProvider extends ContentProvider{
	private static final UriMatcher mMatcher;
	private TruthDatabaseHelper mHelper;
	
	private static final String TAG = "TruthContentProvider";
	private static final boolean DEBUG = true;
	
	private static final int URI_FOLDER = 1;
	private static final int URI_FOLDER_ITEM = 2;
	private static final int URI_TRUTH = 3;
	private static final int URI_TRUTH_ITEM = 4;
	
//	private static final int URI_SEARCH = 5;
//	private static final int URI_SEARCH_SUGGEST = 6;
	static {
		mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mMatcher.addURI(Truths.AUTHORITY, Truths.TRUTH_FOLDER, URI_FOLDER);
		mMatcher.addURI(Truths.AUTHORITY, Truths.TRUTH_FOLDER+"/#", URI_FOLDER_ITEM);
		
		mMatcher.addURI(Truths.AUTHORITY, Truths.TRUTH_ITEM, URI_TRUTH);
		mMatcher.addURI(Truths.AUTHORITY, Truths.TRUTH_ITEM+"/#", URI_TRUTH_ITEM);
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		String id = null;
		if(DEBUG)Log.d(TAG,"delete uri is:"+uri+"selection "+selection+"selectionArgs:"+selectionArgs);
		SQLiteDatabase db = mHelper.getWritableDatabase();
		switch(mMatcher.match(uri)){
		case URI_FOLDER:
			selection = "(" +selection +")"+" AND " + TruthFolder._ID + ">0 ";
			count = db.delete(TABLE.FOLDER, selection, selectionArgs);
			break;
		case URI_FOLDER_ITEM:
			id = uri.getPathSegments().get(1);
			long folderId = Long.valueOf(id);
			if(folderId <Truths.TYPE_DEFAULT_END){//id that smaller than 100,is default folder ,not allowed to trash
				break;
			}
			count = db.delete(TABLE.FOLDER, TruthFolder._ID +"=" +id + " " +parseSelection(selection), selectionArgs);
			break;
		case URI_TRUTH:
			count = db.delete(TABLE.TRUTH_ITEM, selection, selectionArgs);
			break;
		case URI_TRUTH_ITEM:
			id = uri.getPathSegments().get(1);
			count = db.delete(TABLE.TRUTH_ITEM, TruthItemColumns._ID + "="+id+" "+parseSelection(selection), selectionArgs);
			break;
			default:
				throw new IllegalArgumentException("Unkown URI "+uri);
		}
		if(count > 0 ){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}
	
	private String parseSelection(String selection){
		return (!TextUtils.isEmpty(selection)? " AND (" + selection + ")":"");
	}
	
	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if(DEBUG)Log.d(TAG,"insrt uri is:"+uri+"values :"+values);
		SQLiteDatabase db = mHelper.getWritableDatabase();
		long folderId = 0,truthId = 0,insertId = 0;
		switch(mMatcher.match(uri)){
		case URI_FOLDER:
			insertId = folderId = db.insert(TABLE.FOLDER, null, values);
			break;
		case URI_TRUTH:
			if(values.containsKey(TruthFolder._ID)){
				folderId = values.getAsInteger(TruthFolder._ID);
			}else{
				Log.d(TAG,"Wrong data format without folder_id"+values.toString());
			}
			insertId = truthId = db.insert(TABLE.TRUTH_ITEM, null, values);
			break;
			default:
				throw new IllegalArgumentException("Unkonw URI "+uri);
		}
		if(folderId > 0){
			getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(Truths.CONTENT_FOLDER_URI, folderId), null);
		}
		if(DEBUG)Log.d(TAG,"insert into db count is:"+folderId);
		if(truthId > 0){
			getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(Truths.CONTENT_TRUTH_ITEM_URI, truthId), null);
		}
		return ContentUris.withAppendedId(uri, insertId);
	}

	@Override
	public boolean onCreate() {
		if(DEBUG)Log.d(TAG,"onCreate TruthProvider");
		mHelper = TruthDatabaseHelper.getInstance(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Cursor c = null;
		if(DEBUG)Log.d(TAG,"query uri "+uri);
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String id = null;
		switch(mMatcher.match(uri)){
		case URI_FOLDER:
			c = db.query(TABLE.FOLDER, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case URI_FOLDER_ITEM:
			id = uri.getPathSegments().get(1);
			if(DEBUG)Log.d(TAG,"query item value uri.getPathSegments().get(0),get(1)"+uri.getPathSegments().get(0)+"--"+id);
			c = db.query(TABLE.FOLDER, projection, TruthFolder._ID +" = "+id+ " " +parseSelection(selection), selectionArgs, null, null, sortOrder);
			break;
		case URI_TRUTH:
			c = db.query(TABLE.TRUTH_ITEM, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case URI_TRUTH_ITEM:
			id = uri.getPathSegments().get(1);
			if(DEBUG)Log.d(TAG,"query item value uri.getPathSegments().get(0),get(1)"+uri.getPathSegments().get(0)+"--"+id);
			c = db.query(TABLE.TRUTH_ITEM, projection, TruthItemColumns.THREAD_ID  +" = " + id+ " " +parseSelection(selection), selectionArgs, null, null, sortOrder);
			break;
			default:
				throw new IllegalArgumentException("UnKonwn URI "+uri);
		}
		if(c != null){
			if(DEBUG)Log.d(TAG,"query count is:"+c.getCount());
			c.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		String id = null;
		if(DEBUG)Log.d(TAG,"update uri is:"+uri +"values :"+values+"selection :"+selection+"selectionArgs:"+selectionArgs);
		SQLiteDatabase db = mHelper.getWritableDatabase();
		switch(mMatcher.match(uri)){
		case URI_FOLDER:
			count = db.update(TABLE.FOLDER, values, selection, selectionArgs);
			break;
		case URI_FOLDER_ITEM:
			id = uri.getPathSegments().get(1);
			count = db.update(TABLE.FOLDER, values, TruthFolder._ID +"=" + id+ " " +parseSelection(selection), selectionArgs);
			break;
		case URI_TRUTH:
			count = db.update(TABLE.TRUTH_ITEM, values, selection, selectionArgs);
			break;
		case URI_TRUTH_ITEM:
			id = uri.getPathSegments().get(1);
			count = db.update(TABLE.TRUTH_ITEM, values, TruthItemColumns._ID + "=" +id + " " +parseSelection(selection), selectionArgs);
			break;
		}
		if(count > 0){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

}
