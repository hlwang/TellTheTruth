package com.zhuoyang.thetruthbigadventure.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageUtils {
	private static final String TAG = "ImageUtils";
	private static final boolean DBG = true;
	public static Bitmap loadBitmap(Context context,int width,int number,int resId){
		float denty = context.getResources().getDisplayMetrics().density;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap temp = BitmapFactory.decodeResource(context.getResources(), resId, opts);
		int radio = (int) Math.ceil(opts.outWidth/(width*denty/number - 30));
		if(DBG)Log.d(TAG,"ImageUtils width is:"+opts.outWidth+"width"+width+"density"+denty+"number"+number+"radio"+radio);
		opts.inSampleSize = radio;
		if(null != temp){
			temp.recycle();
		}
		opts.inJustDecodeBounds=false;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId,opts);
		return bitmap;
	}

}
