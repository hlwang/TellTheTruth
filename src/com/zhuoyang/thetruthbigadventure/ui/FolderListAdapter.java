package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;
import com.zhuoyang.thetruthbigadventure.utils.ColumnFolder;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;

public class FolderListAdapter extends CursorAdapter implements RecyclerListener{
	private static final String TAG = "FolderListAdapter";
	private static final boolean DBG = true;
	
	private LayoutInflater mInflater;
	private OnContentChangedListener mOnContentChangedListener;
	
	public FolderListAdapter(Context context, Cursor c) {
		super(context, c);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if(!(view instanceof FolderListItem)){
			if(DBG)Log.d(TAG,"Unexcepted bound view:"+view);
			return;
		}
		FolderListItem folderListItem = (FolderListItem) view;
		FolderItem item = new FolderItem(context,new ColumnFolder(cursor), cursor);
		folderListItem.bindView(item);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		FolderListItem folderView = (FolderListItem) mInflater.inflate(R.layout.folder_list_item, null);
		return folderView;
	}

	public interface OnContentChangedListener{
		void onContentChanged(FolderListAdapter adapter);
	}
	
	public void setOnContentChangedListener(OnContentChangedListener l){
		mOnContentChangedListener = l;
	}

	@Override
	public void onMovedToScrapHeap(View view) {
		FolderListItem folderListItem = (FolderListItem) view;
		folderListItem.unbind();
	}

	@Override
	protected void onContentChanged() {
		if(mCursor != null && mCursor.isClosed()){
			if(mOnContentChangedListener != null){
				mOnContentChangedListener.onContentChanged(this);
			}
		}
	}
	
	
}
