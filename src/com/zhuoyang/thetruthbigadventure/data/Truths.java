package com.zhuoyang.thetruthbigadventure.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Truths {
	public static final String AUTHORITY = "zhuoyang_truth";
	public static final String TRUTH_FOLDER = "truth_folder";
	public static final String TRUTH_ITEM = "truth_content";
			
	public static final String TAG = "Truth";
	public static final int TYPE_FOLDER = 0;
	public static final int TYPE_TRUTH_ITEM = 1;
	
	public static final int TYPE_DEFAULT_END = 1;//type default ,not allowed trash
	public static final int FOLDER_IS_SHOW = 1;//0 NOT SHOW ,1 SHOW
	
	public static final Uri CONTENT_FOLDER_URI = Uri.parse("content://"+AUTHORITY +"/"+TRUTH_FOLDER);
	public static final Uri CONTENT_TRUTH_ITEM_URI = Uri.parse("content://"+AUTHORITY +"/"+TRUTH_ITEM);
	
	public static final Uri getTruthUridWithThreadId (long threadId){
		return ContentUris.withAppendedId(CONTENT_TRUTH_ITEM_URI, threadId);
	}
	
	public static final Uri getFolderUriWithThreadId (long threadId){
		return ContentUris.withAppendedId(CONTENT_FOLDER_URI, threadId);
	}
	
	public interface TruthItemColumns{
		public static final String _ID = "_id";
		public static final String THREAD_ID = "thread_id";
		public static final String CREATE_DATE = "date";
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
		public static final String DESIRE = "desire";//�Ƿ��Ϸ�
		public static final String NEGTIVE = "negtive";//�Ƿ��Ϸ�
	}
	
	public interface TruthFolder extends BaseColumns{
		public static final String TITLE = "title";
		public static final String CREATE_DATE = "date";
		//public static final String MESSAGE_COUNT = "item_count";
		public static final String SNIPPET = "snippet";
		public static final String DEFAULT = "isDefault";//�Ƿ�ΪĬ�����
		public static final String IS_SHOW ="is_show";//�Ƿ���ʾ��Ĭ����𲻿�ɾ��
	}
}
