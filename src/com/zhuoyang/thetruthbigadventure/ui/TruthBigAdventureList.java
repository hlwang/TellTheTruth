package com.zhuoyang.thetruthbigadventure.ui;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.data.Truths;
import com.zhuoyang.thetruthbigadventure.ui.FolderListItemAdapter.OnContentChangedListener;
import com.zhuoyang.thetruthbigadventure.utils.ColumnFolder;
import com.zhuoyang.thetruthbigadventure.utils.ColumnTruthItem;
import com.zhuoyang.thetruthbigadventure.utils.DbOperation;

public class TruthBigAdventureList extends Activity{
	private static final String TAG = "TruthBigAdventureActivity";
	private static final boolean DBG = true;
	
	private static final int FOLDER_LIST_ITEM_QUERY_TOKEN = 0;
	private static final int FOLDER_LIST_ITEM_DELETE_TOKEN = 1;
	
	private static final int MENU_ADD = 0;
	private static final int MENU_DELETE_ITEM = 1;
	private static final int MENU_DELETE_ALL = 2;
	
	private static final int CONTEXT_MENU_MODIFY = 0;
	private static final int CONTEXT_MENU_DELETE = 1;
	
	private ThreadListQueryHandler mQueryHandler;
	private FolderListItemAdapter mListAdapter;
	private CharSequence mTitle;
	private GridView mGridView;
	private Uri mUri;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(DBG)Log.d(TAG,"onCreate");
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.truth_bit_list);
		
		mQueryHandler = new ThreadListQueryHandler(getContentResolver());
		mGridView = (GridView) findViewById(R.id.grid);
//		mGridView.setOnCreateContextMenuListener(mFolderListItemOnContextMenuListener);
//		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		View emptyView = findViewById(R.id.empty);
		mGridView.setEmptyView(emptyView);
		
		initListAdapter();
		
//		setupActionBar();
		
		mTitle = getString(R.string.folder_title);
		setTitle(mTitle);
	}
	
	private void initListAdapter(){
		float density = getResources().getDisplayMetrics().density;
		int width = (int) (getWindowManager().getDefaultDisplay().getWidth()/density);
		int number = 3;
		int resId = R.drawable.list_background;
		mListAdapter = new FolderListItemAdapter(this, null);
		mListAdapter.init(width, number, resId);
		mListAdapter.setOnContentChangedListener(mContentChangedListener);
		mGridView.setNumColumns(3);
		mGridView.setHorizontalSpacing(20);
		mGridView.setVerticalSpacing(40);
		mGridView.setAdapter(mListAdapter);
		mGridView.setRecyclerListener(mListAdapter);
		mGridView.setOnItemClickListener(mTruthBigAdventureListItemClick);
	}
	private final FolderListItemAdapter.OnContentChangedListener mContentChangedListener =
			new OnContentChangedListener() {
				
				@Override
				public void onContentChanged(FolderListItemAdapter adapter) {
					startAsyncQuery();
				}
			};
	
	public static Intent createIntent(Context context,long threadId){
		Intent intent = new Intent(context,TruthBigAdventureList.class);
//		intent.setData(Truths.getTruthUridWithThreadId(threadId));
		intent.putExtra("threadId", threadId);
		return intent;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		int threadId = intent.getIntExtra("threadId", 0);
		mUri = Truths.getTruthUridWithThreadId(threadId);
		if(DBG)Log.d(TAG,"onNewIntent"+threadId+"--"+mUri);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(DBG)Log.d(TAG,"onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(DBG)Log.d(TAG,"onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(DBG)Log.d(TAG,"onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		long threadId = getIntent().getLongExtra("threadId", 0);
		mUri = Truths.getTruthUridWithThreadId(threadId);
		if(DBG)Log.d(TAG,"onStart"+threadId+"---"+mUri);
		startAsyncQuery();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(DBG)Log.d(TAG,"onStop");
	}
	
	private void startAsyncQuery(){
		try{
			setTitle(getString(R.string.folder_refresh));
			setProgressBarIndeterminateVisibility(true);
			
			DbOperation.startQueryTruthItem(mQueryHandler,mUri, FOLDER_LIST_ITEM_QUERY_TOKEN, null);
		}catch(SQLiteException e){
			e.printStackTrace();
		}
	}
	
	private AdapterView.OnItemClickListener mTruthBigAdventureListItemClick = new  OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Cursor cursor = (Cursor) mGridView.getItemAtPosition(position);
			TruthItem item = new TruthItem(TruthBigAdventureList.this, new ColumnTruthItem(cursor), cursor);
			Intent intent =  new Intent(TruthBigAdventureList.this,TruthOperationActivity.class);
			intent.putExtra("title", item.getTitle());
			intent.putExtra("content", item.getContent());
			intent.putExtra("date", item.getDate());
			intent.putExtra("desire", item.getDesire());
			intent.putExtra("negtive", item.getNegtive());
			startActivity(intent);
		}
		
	};
	
	private final class ThreadListQueryHandler extends AsyncQueryHandler{

		public ThreadListQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onDeleteComplete(int token, Object cookie, int result) {
			switch(token){
			case FOLDER_LIST_ITEM_DELETE_TOKEN:
				startAsyncQuery();
				break;
				default:
					super.onDeleteComplete(token, cookie, result);
			}
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			switch(token){
			case FOLDER_LIST_ITEM_QUERY_TOKEN:
				mListAdapter.changeCursor(cursor);
				setTitle(mTitle);
				setProgressBarIndeterminate(false);
				break;
				default:
					super.onQueryComplete(token, cookie, cursor);
			}
		}
		
	}
}
