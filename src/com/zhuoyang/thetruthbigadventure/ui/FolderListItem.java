package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.R;
import com.zhuoyang.thetruthbigadventure.utils.Utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FolderListItem extends RelativeLayout{
	private ImageView mTypeImageView;
	private TextView mTitle;
	private TextView mDate;
	private TextView mSnippet;
	
	private FolderItem mFolderItem;

	public FolderListItem(Context context) {
		super(context);
	}

	public FolderListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
//		mTypeImageView = findViewById(R.id.)
		mTitle = (TextView) findViewById(R.id.title);
		mDate = (TextView) findViewById(R.id.date);
		mSnippet = (TextView) findViewById(R.id.snippet);
	}

	public void bindView(FolderItem item){
		mFolderItem = item;
		bindCommonMessage(item);
	}
	
	private void bindCommonMessage(FolderItem folderItem){
		mTitle.setText(folderItem.getTitle());
		mDate.setText(Utils.formatTimeStampString(getContext(), folderItem.getDate(), false));
		mSnippet.setText(folderItem.getSnippet());
	}
	public final void unbind(){
		//do nothing
	}
}
