package spire.example.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import spire.example.otherclass.MyCurrentLocation;
import spire.example.otherclass.MyItemizedOverlay;
import spire.example.otherclass.MyLocationListener;
import spire.example.otherclass.MyOverlayItem;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;


public class GoogleMapsActivity extends MapActivity {
	private boolean isPotentialLongPress;
	DialogFragment dlg2;
	Button btn_maps_yes;
	Button btn_yes_maps;
	Button btn_input_maps;
	public static double getLatitude;
	public static double getLongitude;
	public static String local_n="";
	
	

	private MapView mapView;
	private LocationManager lManager;
	private MyLocationListener myLocationListener;
	private MyCurrentLocation myLocationOverlay;
	private boolean isAdd = true;

	Integer rez=0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        btn_maps_yes = (Button) findViewById(R.id.btnYes);
        btn_yes_maps=(Button)findViewById(R.id.btn_yes_maps);
        btn_input_maps=(Button)findViewById(R.id.btn_input_maps);        
        OnClickListener oclBtnOk = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	geo();
        		finish();
        		
            }
          };
          btn_yes_maps.setOnClickListener(oclBtnOk);
          OnClickListener inpBtn = new OnClickListener() {
              @Override
              public void onClick(View v) {
            	  dlg2.show(getFragmentManager(), "dlg2");
            	  
              }
            };

            btn_input_maps.setOnClickListener(inpBtn);
        dlg2 = new Dialog2();
        init();       
    }


	protected boolean isRouteDisplayed() {

		return false;
	}
	private void handleLongPress(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
 
            new Thread(new Runnable() {
                public void run() {
                    Looper.prepare();
                    if (isLongPressDetected()) {
                    	if(isAdd == true)
                    	{
        				GeoPoint point = mapView.getProjection().fromPixels((int)event.getX(), (int)event.getY());
        				local_n=geolok(point.getLatitudeE6()/1e6,point.getLongitudeE6()/1e6);
        				Dialog2.location=geolok(point.getLatitudeE6()/1e6,point.getLongitudeE6()/1e6);
        				
        				dlg2.show(getFragmentManager(),"dialog");
        				
        				isAdd = true;
                    	}
                    	
                    }
                }
            }).start();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getHistorySize() < 1)
                return;
 
            float diffX = event.getX()
                    - event.getHistoricalX(event.getHistorySize() - 1);
            float diffY = event.getY()
                    - event.getHistoricalY(event.getHistorySize() - 1);
 
            if (Math.abs(diffX) > 0.5f || Math.abs(diffY) > 0.5f) {
                isPotentialLongPress = false;
            }
        } else {
            isPotentialLongPress = false;
        }
    }

    public boolean isLongPressDetected() {
        isPotentialLongPress = true;
        try {
            for (int i = 0; i < (50); i++) {
                Thread.sleep(10);
                if (!isPotentialLongPress) {
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            return false;
        } finally {
            isPotentialLongPress = false;
        }
    }
    
    
	String geolok(Double Latitude, Double Longitude)
	{
		List<Address> addresses1;
    	String str2="";    	
    	String rez="";
    	Geocoder gc = new Geocoder(this, Locale.getDefault());
    	try {
    	addresses1 = gc.getFromLocation(Latitude, Longitude , 1);
    	str2=addresses1.toString();
    	int n1 = str2.indexOf("\"");
    	int n2 = str2.indexOf("\"", n1+1);
    	int n3 = str2.indexOf("\"", n2+1);
    	int n4 = str2.indexOf("\"", n3+1);
    	int n5 = str2.indexOf("\"", n4+1);
    	int n6 = str2.indexOf("\"", n5+1);
    	int n7 = str2.indexOf("\"", n6+1);
    	int n8 = str2.indexOf("\"", n7+1);
    	String str1 = str2.substring(n1+1, n2); 
    	String str222 = str2.substring(n3+1, n4); 
    	String str3 = str2.substring(n5+1, n6); 
    	String str4 = str2.substring(n7+1, n8); 
    	rez=str1+" "+str222+" "+str3+ " "+str4;    	   
    	} catch (IOException e) {
    		rez="";
    	}
    	Log.d("Logd",rez);
    	
		return rez;  
	}
	
    public void geo()
    { 
  	  String[] lc={"",""};
  	  Double sh;
  	  Double dl;
	    Lodge_claim lg = new Lodge_claim();
	    File sdFile = new File(lg.path_d,"local.txt");
	    try {
	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	      	for(int i = 0; i<2; i++)
	      	{
	      		lc[i]=br.readLine();
	      		
	      	}  
	         sh =  Double.parseDouble(lc[0]);
	         dl =  Double.parseDouble(lc[1]);
	         Log.d("Logdolgota", lc[0]);
	 	  	List<Address> addresses1;
	    	String str2="";    	
	    	Geocoder gc = new Geocoder(this, Locale.getDefault());
	    	try {
	    	addresses1 = gc.getFromLocation(sh, dl , 1);
	    	str2=addresses1.toString();
	    	int n1 = str2.indexOf("\"");
	    	int n2 = str2.indexOf("\"", n1+1);
	    	int n3 = str2.indexOf("\"", n2+1);
	    	int n4 = str2.indexOf("\"", n3+1);
	    	int n5 = str2.indexOf("\"", n4+1);
	    	int n6 = str2.indexOf("\"", n5+1);
	    	int n7 = str2.indexOf("\"", n6+1);
	    	int n8 = str2.indexOf("\"", n7+1);
	    	String str1 = str2.substring(n1+1, n2); 
	    	String str222 = str2.substring(n3+1, n4); 
	    	String str3 = str2.substring(n5+1, n6); 
	    	String str4 = str2.substring(n7+1, n8); 
	    	local_n=str1+" "+str222+" "+str3+ " "+str4;
	    	    File sdFile2 = new File(Lodge_claim.path_d, "local2.txt");
	    	    sdFile2.createNewFile();
	    	    try {
	    	      BufferedWriter bw2 = new BufferedWriter(new FileWriter(sdFile2));	    	    	 
	    	      bw2.write(local_n);	    	      
	    	      bw2.close();

	    	    } catch (IOException e) {
	    	      e.printStackTrace();
	    	    }
	    	} catch (IOException e) {
		    	local_n="";
	    	}
	    } catch (FileNotFoundException e) {

	    	local_n="";

	    	e.printStackTrace();
	    } catch (IOException e) {
	    //	Lodge_claim lg = new Lodge_claim();
	    	local_n="";
	      e.printStackTrace();
	    }
    }
	private void init() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		lManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		myLocationListener = new MyLocationListener();
		lManager.requestLocationUpdates(lManager.getBestProvider(new Criteria(), true), 1, 1000, myLocationListener);
		myLocationOverlay = new MyCurrentLocation(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
				mapView.getController().setZoom(18);
			}
		});
		mapView.setReticleDrawMode(MapView.ReticleDrawMode.DRAW_RETICLE_UNDER);
	}
	
	private void addOverlay(GeoPoint point, String title, String snippet) {
		Drawable drawable = this.getResources().getDrawable(R.drawable.map_marker_purple); 
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this); 

		MyOverlayItem overlayitem = new MyOverlayItem(point, title, snippet, this);
		itemizedoverlay.addOverlay(overlayitem);
		mapView.getOverlays().add(itemizedoverlay);
		isAdd = false;
	}
	

	public boolean dispatchTouchEvent(MotionEvent event) {
		handleLongPress(event);
		return super.dispatchTouchEvent(event);
	}
	
	protected void onPause() {
		super.onPause();
		lManager.removeUpdates(myLocationListener);
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();	
		finish();
		}
	
	protected void onResume() {
		super.onResume();
		lManager.requestLocationUpdates(lManager.getBestProvider(new Criteria(), true), 1, 1000, myLocationListener);
		myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();
	}
	

    
    @Override
  	protected void onDestroy() {
  		// TODO Auto-generated method stub
  		super.onDestroy();
  	}


  	@Override
  	protected void onRestart() {
  		// TODO Auto-generated method stub
  		super.onRestart();
  	}

 

  	@Override
  	protected void onStart() {
  		// TODO Auto-generated method stub
  		super.onStart();
  	}
  	@Override
  	protected void onStop() {
  		// TODO Auto-generated method stub
  		super.onStop();
  		
  	}
    

	
}
	


	

	
