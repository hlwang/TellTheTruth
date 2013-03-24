package com.zhuoyang.thetruthbigadventure.ui;

import com.zhuoyang.thetruthbigadventure.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TruthListItem extends LinearLayout{
	private ImageView mForeBitmap;

	public TruthListItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TruthListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mForeBitmap = (ImageView) findViewById(R.id.truth_img);
	}
	
	public void bindView(TruthItem item){
//		mForeBitmap.setImageBitmap(bitmap);
		mForeBitmap.setImageBitmap(item.getmBitmap());
	}
	
	public void unbind(){
		//nothind to do
	}
	
}
