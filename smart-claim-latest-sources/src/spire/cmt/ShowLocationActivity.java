package spire.cmt;

import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocationActivity extends Activity implements LocationListener {
	  private TextView latituteField;
	  private TextView longitudeField;
	  private LocationManager locationManager;
	  private String provider;
	  private double lat;
	  private double lng;
	  private Location location;
	  private Geocoder geocoder;
	  
	/** Called when the activity is first created. */

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_470);
	    
	    
	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    location = locationManager.getLastKnownLocation(provider);
	    
	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	      Toast.makeText(this,"Location Not found",Toast.LENGTH_LONG).show(); 	
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }
	  }

	  /* Request updates at startup */
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
	    this.lat = location.getLatitude();
	    this.lng = location.getLongitude();
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	  }
	  
	  public String getLatitude(){
		  return String.valueOf(this.lat);
	  }
	  
	  public String getLongatude(){
		  return String.valueOf(this.lng);
	  }
	  
	  public String getProvider(){
		  return this.provider;
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
	} 