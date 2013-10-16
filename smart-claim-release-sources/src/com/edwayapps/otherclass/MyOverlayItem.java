package com.edwayapps.otherclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.edwayapps.cmt.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyOverlayItem extends OverlayItem {

	protected String mTitle, mSnippet;
	protected GeoPoint mPoint;
	protected Drawable mMarker;
	private Context context;
	
	public MyOverlayItem(GeoPoint _point, String _title, String _snippet, Context _context) {
		super(_point, _title, _snippet);
		mTitle = _title;
		mSnippet = _snippet;
		mPoint = _point;
		context = _context;
	}
	
	public Drawable getMarker(int stateBitset) {
		return super.getMarker(stateBitset);

	}

	public GeoPoint getPoint() {
		return mPoint;
	}
	
	public String getSnippet() {
		return mSnippet;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String routableAddress() {
		return mPoint.getLatitudeE6() + "," + mPoint.getLongitudeE6();
	}
	
	public void setMarker(Drawable marker) {
		mMarker = marker;
	}
	
	public void draw(Canvas canvas, MapView mapView) {
		Point screen = mapView.getProjection().toPixels(mPoint, null); //????????? GeoPoint ? ???????
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.map_marker_red);
		canvas.drawBitmap(bitmap, screen.x - (bitmap.getWidth()/2), screen.y - (bitmap.getHeight()/2), null); //????????? ??????
	}
	
}
