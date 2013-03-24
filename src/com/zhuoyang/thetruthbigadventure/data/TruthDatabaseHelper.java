package com.zhuoyang.thetruthbigadventure.data;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthItemColumns;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class TruthDatabaseHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "truth.db";
	private static final int DB_VERSION = 8;
	
	public interface TABLE{
		public static final String FOLDER = Truths.TRUTH_FOLDER;
		public static final String TRUTH_ITEM = Truths.TRUTH_ITEM;
	}
	private static final String TAG = "TruthDatabaseHelper";
	private static TruthDatabaseHelper mInstance;
	private Context mContext;
	
	private static final String CREATE_FOLDER_TABLE_SQL = 
			"CREATE TABLE " + TABLE.FOLDER + "(" + 
					TruthFolder._ID + " INTEGER PRIMARY KEY," +
					TruthFolder.TITLE + " TEXT NOT NULL DEFAULT '',"+
					TruthFolder.CREATE_DATE + " INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000)," +
					//TruthFolder.MESSAGE_COUNT + "INTEGER NOT NULL DEFAULT 0,"+
					TruthFolder.SNIPPET + " TEXT NOT NULL DEFAULT '',"+
					TruthFolder.DEFAULT + " INTEGER NOT NULL DEFAULT 0,"+
					TruthFolder.IS_SHOW + " INTEGER NOT NULL DEFAULT 1"+
					")";
	private static final String CREATE_TRUTH_ITEM_TABLE_SQL = 
			"CREATE TABLE " +TABLE.TRUTH_ITEM + "(" +
					TruthItemColumns._ID + " INTEGER PRIMARY KEY," + 
					TruthItemColumns.THREAD_ID + " INTEGER NOT NULL DEFAULT 0," +
					TruthItemColumns.CREATE_DATE + " INTEGER NOT NULL DEFAULT (strftime('%s','now')*1000),"+
					TruthItemColumns.TITLE + " TEXT NOT NULL DEFAULT '',"+
					TruthItemColumns.CONTENT + " TEXT NOT NULL DEFAULT '',"+
					TruthItemColumns.DESIRE + " INTEGER NOT NULL DEFAULT 0,"+
					TruthItemColumns.NEGTIVE + " INTEGER NOT NULL DEFAULT 0"+
					")";
	
	public TruthDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG,"TruthDatabaseHelper onCreate");
		db.execSQL(CREATE_TRUTH_ITEM_TABLE_SQL);
		db.execSQL(CREATE_FOLDER_TABLE_SQL);
		loadDeafult(mContext,db);
		loadDefaultForlder(mContext,db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG,"TruthDatabaseHelper onUpdate");
		switch(oldVersion){
			default:
				dropAll(db);
				onCreate(db);
		}
		
	}
	private void dropAll(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS "+TABLE.FOLDER);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE.TRUTH_ITEM);
	}
	static synchronized TruthDatabaseHelper getInstance(Context context){
		if(mInstance == null){
			mInstance = new TruthDatabaseHelper(context);
		}
		return mInstance;
	}
	
	private void loadDeafult(Context context,SQLiteDatabase db){
		String[] defaultTruth = context.getResources().getStringArray(R.array.folder_type);
		String title = context.getResources().getString(R.string.default_name);
		for(String value:defaultTruth){
			ContentValues values = new ContentValues();
			values.put(TruthItemColumns.THREAD_ID, 1);
			values.put(TruthItemColumns.TITLE, title);
			values.put(TruthItemColumns.CONTENT, value);
			insertDefault(TABLE.TRUTH_ITEM, values,db);
		}
	}
	private void loadDefaultForlder(Context context,SQLiteDatabase db){
		String title = context.getResources().getString(R.string.default_name);
		String snippet = context.getResources().getString(R.string.default_snippet);
		ContentValues values = new ContentValues();
		values.put(TruthFolder.TITLE, title);
		values.put(TruthFolder.SNIPPET, snippet);
//		context.getContentResolver().insert(Truths.CONTENT_FOLDER_URI, values);
		insertDefault(TABLE.FOLDER,values,db);
	}
	private void insertDefault(Context context,Uri uri,ContentValues values){
		context.getContentResolver().insert(uri, values);
	}
	private void insertDefault(String table,ContentValues values,SQLiteDatabase db){
		db.insert(table, null, values);
	}
}
