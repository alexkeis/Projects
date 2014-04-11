package spire.cmt;

import android.content.SharedPreferences;
import android.location.Location;
import android.widget.Toast;
import android.app.Activity;

public class LocationResult extends Activity {

	Location mylocation;

	public void gotLocation(Location location) {
		// TODO Auto-generated method stub
		try {
			double Longitude = location.getLongitude();
			double Latitude = location.getLatitude();
			this.mylocation = location;
			SharedPreferences locationpref = getApplication()
					.getSharedPreferences("location", MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = locationpref.edit();
			prefsEditor.putString("Longitude", Longitude + "");
			prefsEditor.putString("Latitude", Latitude + "");
			prefsEditor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLongitude() {
		String lng;
		if (mylocation == null) {
			lng = "0";
		}
		else{
			lng = Double.toString(mylocation.getLongitude());
		}
		return lng;
	}

	public String getLatitude() {
		String lat;
		if (mylocation == null) {
			lat = "0";
		}
		else{
			lat = Double.toString(mylocation.getLatitude());
		}
		return lat;
	}

}
