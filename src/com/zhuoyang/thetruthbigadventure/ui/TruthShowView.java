package com.zhuoyang.thetruthbigadventure.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TruthShowView extends ImageView{
	private static final String TAG = "TruthShowView";
	private static final boolean DBG = true;
	
	private int width ,height;
	Bitmap temp_bitmap;
	Canvas canvasTemp;
	Paint paint;
	Path path;
	
	int startX;
	int startY;
	
	Matrix matrix;
	float mDensity;
	
	public TruthShowView(Context context) {
		super(context);
		init();
	}
	
	public TruthShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		mDensity = getContext().getResources().getDisplayMetrics().density;
		temp_bitmap = ((BitmapDrawable)getDrawable()).getBitmap();
		canvasTemp = new Canvas(temp_bitmap);
		canvasTemp.drawColor(Color.TRANSPARENT);
		
		matrix = new Matrix();
		matrix.setScale(width*mDensity/temp_bitmap.getWidth(),height*mDensity/temp_bitmap.getHeight());
		canvasTemp.drawBitmap(temp_bitmap, matrix, paint);
		
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(20);
		BlurMaskFilter bmf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);
		paint.setMaskFilter(bmf);
		paint.setStyle(Paint.Style.STROKE);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.SQUARE);
		
		path = new Path();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		if(DBG)Log.d(TAG,"onDraw  width"+getWidth()+"height"+getHeight());
		Matrix matrix = new Matrix();
		matrix.setScale(width*mDensity/temp_bitmap.getWidth(),height*mDensity/temp_bitmap.getHeight());
		canvas.drawBitmap(temp_bitmap, matrix, null);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(DBG)Log.d(TAG,"onMeasure  width"+getWidth()+"height"+getHeight());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(DBG)Log.d(TAG,"onFinishInflate  width"+getWidth()+"height"+getHeight());
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(DBG)Log.d(TAG,"left"+left+"top"+top+"right"+right+"bottom"+bottom+"width"+getWidth()+"height"+getHeight());
		width = getWidth();
		height = getHeight();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		int endX = 0;
		int endY = 0;
		
		int action = event.getAction();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			startX = x;
			startY = y;
			endX = x;
			endY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			endX = x;
			endY = y;
			break;
		case MotionEvent.ACTION_UP:
			endX = x;
			endY = y;
			break;
		}
		
		path.moveTo(startX, startY);
		path.lineTo(endX, endY);
		canvasTemp.drawPath(path,paint);
		postInvalidate();
		startX = endX;
		startY = endY;
		return true;
	}
	
	public void recyleBitmap(){
		if(temp_bitmap!=null){
			temp_bitmap.recycle();
		}
	}
}
