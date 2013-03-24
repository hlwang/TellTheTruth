package com.zhuoyang.thetruthbigadventure.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.zhuoyang.thetruthbigadventure.data.Truths;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthItemColumns;

public class Utils {
	static final int FOLDER_ID =0;
	static final int FOLDER_TITLE =1;
	static final int FOLDER_CREATE_TIME =2;
	static final int FOLDER_SNIPPET =3;
	static final int FOLDER_DEFAULT =4;
	static final int FOLDER_IS_SHOW =5;
	
	static final String[] FOLDER_PROJECTION = new String[]{
		TruthFolder._ID,
		TruthFolder.TITLE,
		TruthFolder.CREATE_DATE,
		TruthFolder.SNIPPET,
		TruthFolder.DEFAULT,
		TruthFolder.IS_SHOW
	};
	
	static final int TRUTH_ITEM_ID =0;
	static final int TRUTH_ITEM_THREAD_ID =1;
	static final int TRUTH_ITEM_CREATE_TIME =2;
	static final int TRUTH_ITEM_TITLE =3;
	static final int TRUTH_ITEM_CONTENT =4;
	static final int TRUTH_ITEM_DESIRE =5;
	static final int TRUTH_ITEM_NEGTIVE = 6;
	
	static final String[] TRUTH_ITEM_PROJECTION = new String[]{
		TruthItemColumns._ID,
		TruthItemColumns.THREAD_ID,
		TruthItemColumns.CREATE_DATE,
		TruthItemColumns.TITLE,
		TruthItemColumns.CONTENT,
		TruthItemColumns.DESIRE,
		TruthItemColumns.NEGTIVE
	};
	
	public static String formatTimeStampString(Context context,long when,boolean isDetails){
		Time then = new Time();
		then.set(when);
		Time now = new Time();
		now.setToNow();
		
		int format_flags = DateUtils.FORMAT_NO_MIDNIGHT|DateUtils.FORMAT_ABBREV_ALL|DateUtils.FORMAT_CAP_AMPM;
		
		if(then.year != now.year){
			format_flags |= DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE;
		}else if(then.yearDay != now.yearDay){
			format_flags |= DateUtils.FORMAT_SHOW_DATE;
		}else{
			format_flags |= DateUtils.FORMAT_SHOW_TIME;
		}
		
		if(isDetails){
			format_flags |= (DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME);
		}
		return DateUtils.formatDateTime(context, when, format_flags);
	}
}
