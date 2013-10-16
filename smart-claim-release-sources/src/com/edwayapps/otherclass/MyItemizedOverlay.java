package com.edwayapps.otherclass;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<MyOverlayItem> {

	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
	private Context mContext;
	
	public MyItemizedOverlay(Drawable defaultMarker, Context context) {

		super(boundCenter(defaultMarker));
		mContext = context;

	}

	public void addOverlay(MyOverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	protected MyOverlayItem createItem(int idx) {
		return mOverlays.get(idx);
	}

	public int size() {

		return mOverlays.size();
	}
	
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
		populate();
		if(!shadow) {
			MyOverlayItem item;
			for(int i=0; i<mOverlays.size(); i++) {
				item = mOverlays.get(i);
				item.draw(canvas, mapView);
			}
		}
		
	}
	
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
	
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		GeoPoint point = mapView.getProjection().fromPixels((int)event.getX(), (int)event.getY());

		return false;
	}

}
