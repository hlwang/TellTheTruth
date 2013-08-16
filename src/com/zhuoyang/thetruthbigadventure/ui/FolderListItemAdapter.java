package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.data.Truths.TruthFolder;
import com.zhuoyang.thetruthbigadventure.utils.ColumnFolder;
import com.zhuoyang.thetruthbigadventure.utils.ColumnTruthItem;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.CursorAdapter;

public class FolderListItemAdapter extends CursorAdapter implements RecyclerListener{
	private static final String TAG = "FolderListAdapter";
	private static final boolean DBG = true;
	
	private LayoutInflater mInflater;
	private OnContentChangedListener mOnContentChangedListener;
	private int mWidth,mNumber,mResid;
	
	public FolderListItemAdapter(Context context, Cursor c) {
		super(context, c);
		mInflater = LayoutInflater.from(context);
	}
	
	public void init(int width,int number,int resId){
		mWidth = width;
		mNumber = number;
		mResid  = resId;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if(!(view instanceof TruthListItem)){
			if(DBG)Log.d(TAG,"Unexcepted bound view:"+view);
			return;
		}
		TruthListItem folderListItem = (TruthListItem) view;
		TruthItem item = new TruthItem(context,new ColumnTruthItem(cursor), cursor);
		item.initBitmapForTruthItem(context, mWidth, mNumber, mResid);
		folderListItem.bindView(item);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		TruthListItem folderView = (TruthListItem) mInflater.inflate(R.layout.truth_list_item, null);
		return folderView;
	}

	public interface OnContentChangedListener{
		void onContentChanged(FolderListItemAdapter adapter);
	}
	
	public void setOnContentChangedListener(OnContentChangedListener l){
		mOnContentChangedListener = l;
	}

	@Override
	public void onMovedToScrapHeap(View view) {
		TruthListItem truthListItem = (TruthListItem) view;
		truthListItem.unbind();
	}

	@Override
	protected void onContentChanged() {
		Cursor mCursor = getCursor();
		if(mCursor != null && mCursor.isClosed()){
			if(mOnContentChangedListener != null){
				mOnContentChangedListener.onContentChanged(this);
			}
		}
	}
	
	
}
