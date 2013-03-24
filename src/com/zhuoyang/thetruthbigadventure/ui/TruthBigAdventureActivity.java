package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.data.Truths;
import com.zhuoyang.thetruthbigadventure.ui.FolderListAdapter.OnContentChangedListener;
import com.zhuoyang.thetruthbigadventure.utils.ColumnFolder;
import com.zhuoyang.thetruthbigadventure.utils.ColumnTruthItem;
import com.zhuoyang.thetruthbigadventure.utils.DbOperation;

import android.app.Activity;
import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;

public class TruthBigAdventureActivity extends ListActivity{
	private static final String TAG = "TruthBigAdventureActivity";
	private static final boolean DBG = true;
	
	private static final int FOLDER_LIST_QUERY_TOKEN = 0;
	private static final int FOLDER_LIST_DELETE_TOKEN = 1;
	
	private static final int MENU_ADD = 0;
	private static final int MENU_DELETE_ITEM = 1;
	private static final int MENU_DELETE_ALL = 2;
	
	private static final int CONTEXT_MENU_MODIFY = 0;
	private static final int CONTEXT_MENU_DELETE = 1;
	
	private ThreadListQueryHandler mQueryHandler;
	private FolderListAdapter mListAdapter;
	private CharSequence mTitle;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(DBG)Log.d(TAG,"onCreate");
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.truth_big_adventure_activity);
		
		mQueryHandler = new ThreadListQueryHandler(getContentResolver());
		ListView listView = getListView();
		listView.setOnCreateContextMenuListener(mFolderListItemOnContextMenuListener);
//		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		View emptyView = findViewById(R.id.empty);
		listView.setEmptyView(emptyView);
		
		initListAdapter();
		
//		setupActionBar();
		
		mTitle = getString(R.string.folder_title);
		setTitle(mTitle);
	}
	
	private void initListAdapter(){
		mListAdapter = new FolderListAdapter(this, null);
		mListAdapter.setOnContentChangedListener(mContentChangedListener);
		setListAdapter(mListAdapter);
		getListView().setRecyclerListener(mListAdapter);
	}
	private final FolderListAdapter.OnContentChangedListener mContentChangedListener =
			new OnContentChangedListener() {
				
				@Override
				public void onContentChanged(FolderListAdapter adapter) {
					startAsyncQuery();
				}
			};
	
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
		if(DBG)Log.d(TAG,"onStart");
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
			
			DbOperation.startQueryFolder(mQueryHandler,Truths.CONTENT_FOLDER_URI, FOLDER_LIST_QUERY_TOKEN, null);
		}catch(SQLiteException e){
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		FolderItem item = new FolderItem(TruthBigAdventureActivity.this, new ColumnFolder(cursor), cursor);
		openFolder(item);
	}
	
	private void openFolder(FolderItem item){
		Intent intent = TruthBigAdventureList.createIntent(TruthBigAdventureActivity.this,item.getThread_id());
		startActivity(intent);
	}
	
	private final OnCreateContextMenuListener mFolderListItemOnContextMenuListener = 
			new OnCreateContextMenuListener() {
				
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					Cursor cursor = mListAdapter.getCursor();
					if(cursor == null || cursor.getPosition() < 0){
						return ;
					}
					FolderItem item = new FolderItem(TruthBigAdventureActivity.this, new ColumnFolder(), cursor);
					menu.setHeaderTitle(item.getTitle());
					menu.add(0,CONTEXT_MENU_MODIFY,0,R.string.menu_modify_folder);
					menu.add(0,CONTEXT_MENU_DELETE,0,R.string.menu_delete_folder);
				}
			};
	
	private final class ThreadListQueryHandler extends AsyncQueryHandler{

		public ThreadListQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onDeleteComplete(int token, Object cookie, int result) {
			switch(token){
			case FOLDER_LIST_DELETE_TOKEN:
				startAsyncQuery();
				break;
				default:
					super.onDeleteComplete(token, cookie, result);
			}
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			switch(token){
			case FOLDER_LIST_QUERY_TOKEN:
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
