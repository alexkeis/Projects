package spire.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

import android.content.Intent;
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
import android.content.SharedPreferences;

public class LocationFinder extends Activity {

	int increment = 4;
	MyLocation myLocation = new MyLocation();
	public LocationResult locationResult = new LocationResult();

	// private ProgressDialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_470);
		boolean r = myLocation.getLocation(getApplicationContext(),
				this.locationResult);

		try{
			// SharedPreferences locationpref = getApplication()
			// .getSharedPreferences("location", MODE_WORLD_READABLE);
			// SharedPreferences.Editor prefsEditor = locationpref.edit();
			// prefsEditor.putString("Longitude", this.getLongitude());
			// prefsEditor.putString("Latitude", this.getLatitude());
			// prefsEditor.commit();
			//

			Intent returnIntent = new Intent();
			returnIntent.putExtra("resultlatitude", this.getLatitude());
			returnIntent.putExtra("resultlongitude", this.getLongitude());
			setResult(RESULT_OK, returnIntent);
			finish();
		
		}
		catch(NullPointerException e){
			Intent returnIntent = new Intent();
			setResult(RESULT_CANCELED, returnIntent);
			finish();
		}
	}

	
	public String getLongitude() {

		if (locationResult==null){ return "0";}
			return locationResult.getLongitude();
	}

	public String getLatitude() {
		if (locationResult==null){ return "0";}
			return locationResult.getLatitude();
	}

}
