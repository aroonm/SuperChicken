package com.aroonsworld.gamedev.superchicken;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import com.aroonsworld.gamedev.superchicken.R;

public class ChickenAnim extends View{
	
	Bitmap chicken;
	
	public ChickenAnim(Context context) {
		super(context);
		
		chicken = BitmapFactory.decodeResource(getResources(),R.drawable.chicken1);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Rect ourRect = new Rect();
		ourRect.set(0, 410, canvas.getWidth(), canvas.getHeight());
		
		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		paint.setStyle(Paint.Style.FILL);
		
		canvas.drawRect(ourRect, paint);
		
		canvas.drawBitmap(chicken, 0, 0, null);
		invalidate();
	}
}
