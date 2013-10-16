package com.edwayapps.otherclass;

import java.sql.Time;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {

	public void onLocationChanged(Location location) {

		Log.i("MyTag", "onLocationChanged -: " + location.getLatitude() + " | : " + location.getLongitude() + " | speed: " + location.getSpeed()
				+ " | provider: " + location.getProvider() + " | time: " + new Time(location.getTime()).getSeconds() + "s");
	}

	public void onProviderDisabled(String provider) {
	
		Log.i("MyTag", "Provider disabled. GPS is off");
	}

	public void onProviderEnabled(String provider) {
		
		Log.i("MyTag", "Provider enabled. GPS is on");
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

		Log.i("MyTag", "Provider " + provider + " status " + status + " changed");
	}

}
