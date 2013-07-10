package spire.example.otherclass;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;

import spire.example.cmt.GoogleMapsActivity;
import spire.example.cmt.Lodge_claim;
import spire.example.cmt.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyCurrentLocation extends MyLocationOverlay {
	public static double sch;
	public static double dol;
	private Context context;
	private MapView mapView;
	
	public MyCurrentLocation(Context _context, MapView _mapView) {
		super(_context, _mapView);
		mapView = _mapView;
		context = _context;
	}
	
	
	@Override
	protected void drawMyLocation(Canvas _canvas, MapView _mapView, Location _lastFix, GeoPoint _myLocation, long _when) {
		Point screen = mapView.getProjection().toPixels(_myLocation, null); //????????? GeoPoint ? ???????
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.map_marker_red);
		_canvas.drawBitmap(bitmap, screen.x - (bitmap.getWidth()/2), screen.y - (bitmap.getHeight()/2), null); //????????? ??????
		Lodge_claim lg = new Lodge_claim();

		
		// File sdPath = Environment.getExternalStorageDirectory();
		///    sdPath = new File(sdPath.getAbsolutePath() + "/" + "CMT" + "/" + lg.nameFolder);
		 //   sdPath.mkdirs();
		
		Log.d("Log", Double.toString(_lastFix.getLatitude()) + ".:." + Double.toString(_lastFix.getLongitude()));
		    File sdFile = new File(lg.path_d, "local.txt");
		    try {
		      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
		      for(int i=0; i<2; i++){
		    	 
		      bw.write(_lastFix.getLatitude() + "\n");
		      bw.write(_lastFix.getLongitude() + "\n");
		      }
		      bw.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		
		
		Log.i("MyTag", "??????: " + _lastFix.getLatitude() + " | ???????: " + _lastFix.getLongitude() + " | speed: " + _lastFix.getSpeed()
				+ " | provider: " + _lastFix.getProvider() + " | time: " + new Time(_lastFix.getTime()).getSeconds() + "ssssssssssssssssssssssssssssssssssssssssssssssssss");
	 }


}
