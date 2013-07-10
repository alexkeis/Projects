package spire.example.otherclass;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyOverlay extends Overlay {

	private GeoPoint pointX, pointY;
	
	public MyOverlay(GeoPoint _pointX, GeoPoint _pointY) {
		pointX = _pointX;
		pointY = _pointY;
	}
	
	public void drawAt(Canvas canvas, MapView mapv, boolean shadow) {
		if(!shadow) {
			super.draw(canvas, mapv, shadow);
			Paint   mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(2);
			Point p1 = new Point();
			Point p2 = new Point();
			Path path = new Path();
			Projection projection = mapv.getProjection();
			projection.toPixels(pointX, p1);
			projection.toPixels(pointY, p2);
			canvas.drawPath(path, mPaint);
			//canvas.drawLine((float)pointX.getLatitudeE6(), (float)pointY.getLatitudeE6(), (float)pointX.getLongitudeE6(), (float)pointY.getLongitudeE6(), mPaint);
			
		}
	}
	
}
