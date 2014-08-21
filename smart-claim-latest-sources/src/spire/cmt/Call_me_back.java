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
import java.util.LinkedHashMap;
import java.util.Map;

import android.view.Display;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.simple.JSONValue;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.os.Vibrator;

/**
 * @author alex
 *
 */
/**
 * @author alex
 * 
 */
public class Call_me_back extends Activity implements OnClickListener {
	public static int id = 0;
	int id_req;
	TabHost tabs;
	Button btn_call;
	EditText edit_last_name;
	EditText edit_first_name;
	EditText edit_number;
	Button btn_send;
	DialogFragment dialog;
	DialogFragment dialog2;
	String title = "";
	String tit;
	public static int pos;
	String result = new String("");
	String result_enquiry = new String("");
	int pp;
	ListView lv2;
	String utf8;
	String temp;
	public static String[] details_vehicle = { "", "", "" };
	ListView lv;
	public static String[] names = { "*Rego: ", "*Firs Name: ", "*Last Name: ",
			"*Contact Number: " };
	public static String[] names_info = { "", "", "", "" };
	public static String[] names_title = { names[0] + names_info[0],
			names[1] + names_info[1], names[2] + names_info[2],
			names[3] + names_info[3] };

	public static String[] names2 = { "Client ID: " };
	public static String[] names_info2 = { "" };
	public static String[] names_title2 = { names2[0] + names_info2[0] };
	MyTask mt;
	MyTask2 mt2;
	public static String[] details = { "", "", "", "", "", "", "", "", "", "",
			"", "" };
	private ProgressDialog progressDialog;
	private Application_files_explorer app_files = new Application_files_explorer();
	private String latitude;
	private String longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_me_back);

		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage("Wait");

		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("ID", "");
		if (strSavedMem1.equals("")) {
			names_info2[0] = "";
		} else
			names_info2[0] = strSavedMem1;
		Log.d("!!!!!!!!!!", strSavedMem1);
		names_title2[0] = names2[0] + names_info2[0];
		lv = (ListView) findViewById(R.id.listView_new_client);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(R.layout.footer_new_client,
				null);
		lv.addFooterView(footer);
		lv.setAdapter(adapt);
		lv2 = (ListView) findViewById(R.id.listView_existing_client);
		ArrayAdapter<String> adapt2 = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title2);
		View footer2 = getLayoutInflater().inflate(
				R.layout.footer_existing_client, null);
		lv2.addFooterView(footer2);

		// New code added 2/13/2014 by alexkeis
		// -----------------------------------
		View footer3;
		DisplayMetrics metrics = new DisplayMetrics();
		
		if (metrics.widthPixels > 1189) {
			footer3 = getLayoutInflater().inflate(R.layout.gap_big, null);	
		}
		else
			footer3 = getLayoutInflater().inflate(R.layout.gap_small, null);
		
		lv2.addFooterView(footer3);
		// -----------------------------------------------------------------------------

		lv2.setAdapter(adapt2);

		dialog = new Dialog_new_client();
		dialog2 = new Dialog_existing_client();
		readFile_info();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp = position;
				tit = names_title[position];

				dialog();

			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp = position;
				tit = names_title[position];

				dialog2();

			}
		});

		
		
		
		tabs = (TabHost) findViewById(android.R.id.tabhost);

		tabs.setup();


		TabHost.TabSpec spec = tabs.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("New Client");
		tabs.addTab(spec);

		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Existing Client");
		tabs.addTab(spec);

		tabs.setCurrentTab(id);

	}

	public void ok_footer_new_client(View view) {
		int s = 0;
		String pr = "Please enter: ";
		for (int i = 0; i < names_info.length; i++) {
			if (names_info[i].length() < 1) {
				pr = pr.concat(names_title[i] + "\n");
				s++;
			}
		}
		if (s != 0) {
			Toast.makeText(getApplicationContext(), pr, Toast.LENGTH_SHORT)
					.show();

		} else {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				// fetch data
				mt = new MyTask();
				mt.execute();
			} else {
				// display error
				Toast.makeText(getApplicationContext(), "Invalid connection",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	public void forgot_id(View view) {

		id = 0;
		tabs.setCurrentTab(id);

	}

	private void SavePreferences(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void ok_footer_existing_client(View view) {
		if (names_info2[0].equals("")) {
			Toast.makeText(
					getApplicationContext(),
					"ClientId is required field. Please use \"New Client\" if you do not know your ClientId",
					Toast.LENGTH_LONG).show();
			id = 0;
			tabs.setCurrentTab(id);
		} else {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				// fetch data
				mt2 = new MyTask2();
				mt2.execute();
			} else {
				// display error
				Toast.makeText(getApplicationContext(), "Invalid connection",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				this.latitude = data.getStringExtra("resultlatitude");
				this.longitude = data.getStringExtra("resultlongitude");
//				SharedPreferences locationpref = getApplication()
//						.getSharedPreferences("location", MODE_WORLD_READABLE);
//				String lng = locationpref.getString("Longitude", null);
//				String lat = locationpref.getString("Latitude", null);
//				Toast.makeText(getApplicationContext(), "Lat - "+lat+" Long - "+lng,
//						 Toast.LENGTH_SHORT).show();
				
				
				//Toast.makeText(getApplicationContext(), this.latitude+" "+this.longitude ,
				//		 Toast.LENGTH_SHORT).show();
				
				
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					// fetch data
					MyTask3 mt3 = new MyTask3();
					mt3.lat = this.latitude;
					mt3.lng = this.longitude;
					mt3.execute();
				} else {
					// display error
					Toast.makeText(getApplicationContext(), "Invalid connection",
							Toast.LENGTH_SHORT).show();
				}
				
			}
			else
				Toast.makeText(getApplicationContext(), "Unable to obtain location coordinates. \n"
						+ "Please make sure location services are available.",
						 Toast.LENGTH_SHORT).show();
		}

	}

	public void ok_gap(View view) {

		try {
			
		
			MyLocation ml = new MyLocation();
			// ShowLocationActivity location_activity = new ShowLocationActivity();
	
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), LocationFinder.class);
			startActivityForResult(intent1, 1);
			
			//Toast.makeText(getApplicationContext(), "NO EXCEPTIONS!!!",
			//		 Toast.LENGTH_SHORT).show();
		
		}
		catch (Exception e){
			Toast.makeText(getApplicationContext(), "The following exception has occured: \n"
					+ e.getMessage(),
					 Toast.LENGTH_SHORT).show();
		}

		// String lat = location_activity.getLatitude();
		// String lng = location_activity.getLongatude();

		// Toast.makeText(getApplicationContext(), "Lat - "+lat+" Long - "+lng,
		// Toast.LENGTH_SHORT).show();

		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Accident");
		// builder.setMessage("We’ve detected that you’ve been in a car accident. Would you like a notification sent to your Nominated Contact?");
		// builder.setIcon(android.R.drawable.ic_dialog_info);
		//
		//
		// builder.setPositiveButton("YES", new
		// DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog, int which) {
		// send_email();
		// Toast.makeText(getApplicationContext(), "Sending Email",
		// Toast.LENGTH_SHORT).show();
		// dialog.dismiss();
		// }
		//
		// });
		//
		// builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // Do nothing
		// dialog.dismiss();
		// Toast.makeText(getApplicationContext(), "Email Canceled.",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// builder.setInverseBackgroundForced(true);
		// AlertDialog alert = builder.create();
		// Vibrator v = (Vibrator)
		// getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
		// // Vibrate for 500 milliseconds
		// v.vibrate(2500);
		// alert.show();
		//
		//
		// if (names_info2[0].equals("")) {
		// Toast.makeText(
		// getApplicationContext(),
		// "ClientId is required field. Please use \"New Client\" if you do not know your ClientId",
		// Toast.LENGTH_LONG).show();
		// id = 0;
		// tabs.setCurrentTab(id);
		// } else {
		//
		// }

	}

	void send_email() {

		app_files.set_path_string(new File(getFilesDir(), "/Your_details"));
		String email = app_files.get_nc_email();
		String profile_name = app_files.get_profile_name();
		String profile_phone = app_files.get_profile_phone();

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
		i.putExtra(Intent.EXTRA_SUBJECT, profile_name
				+ " has been in a car accident\n");
		i.putExtra(
				Intent.EXTRA_TEXT,
				profile_name
						+ " has been in a car accident. You’ve been chosen as their nominated contact in the event of a car crash. \n"
						+

						"Their current location is _________, and they can be contacted on "
						+ profile_phone + "\n\n\n" +

						"If you have any questions, please contact CMT on 1300 887 712. ");

		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getApplicationContext(),
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	void readFile_info2() {
		String str = "";
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null)

			{
				String str_utf8 = new String(str.getBytes("ISO-8859-1"),
						"UTF-8");
				details_vehicle[qw] = str_utf8;
				Log.d("FILE2", details_vehicle[qw]);
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class MyTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer ret = new Integer(0);

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/SaveClientObject");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");
				try {

					Map obj = new LinkedHashMap();
					obj.put("Id", new Integer(0));
					obj.put("DealerId", null);
					obj.put("DealerVehicleMake", null);
					obj.put("VehicleMake", details_vehicle[0]);
					obj.put("VehicleCategoryId", null);
					obj.put("VehicleModelUser", details_vehicle[1]);
					obj.put("VehicleModelData", null);
					obj.put("VehicleRego", names_info[0]);
					obj.put("VehicleYear", "2013");
					obj.put("VehicleVin", null);
					obj.put("PurchaseDate", null);
					obj.put("Notes", null);
					String s = "/Date(" + System.currentTimeMillis() / 1000
							+ ")/";
					obj.put("CreatedDate", s);

					obj.put("IsBusiness", false);
					obj.put("PersonalDataId", new Integer(0));

					Map in_obj = new LinkedHashMap();
					in_obj.put("Id", new Integer(0));
					in_obj.put("Title", null);
					in_obj.put("FirstName", names_info[1]);
					in_obj.put("Surname", names_info[2]);
					in_obj.put("Email", details[6]);
					in_obj.put("Phone", names_info[3]);
					in_obj.put("Phone2", null);
					in_obj.put("Company", null);
					in_obj.put("AbnNumber", null);
					in_obj.put("Country", null);
					in_obj.put("Address1", details[7]);
					in_obj.put("Address2", null);
					in_obj.put("City", "");
					in_obj.put("StateCode", details[10]);
					in_obj.put("Postcode", details[9]);

					obj.put("PersonalData", "--");

					// StringWriter out = new StringWriter();
					// JSONValue.writeJSONString(obj, out);
					// String jsonText = out.toString();
					JSONObject json = new JSONObject(in_obj);
					JSONObject json1 = new JSONObject(obj);
					// String jsonText = out2.toString();
					String jsonText = json.toString();
					String jsonText1 = json1.toString();
					jsonText1 = jsonText1.replace("\"--\"", jsonText);
					Log.d("WWW", jsonText1);

					httppost.setEntity(new StringEntity(jsonText1, "UTF-8"));

					HttpResponse response = httpclient.execute(httppost);

					Log.d("444", response.toString());
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "windows-1251"));
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + System.getProperty("line.separator"));
					}

					result = sb.toString();

				} catch (Exception e) {
					ret = new Integer(1);
				}

			} finally {
				Log.d("!!!", result);
				boolean id_request;
				if (result == null) {
					ret = new Integer(1);
				} else {
					result = result.substring(0, result.length() - 1);
					Log.d("DLINNA", String.valueOf(result.length()) + "/"
							+ result + "/");
					try {
						Log.d("!1!", result);
						Integer.parseInt(result.toString());
						id_req = Integer.parseInt(result.toString());
						id_request = true;

						ret = new Integer(0);

					} catch (Exception e) {
						Log.d("!!!!!!!", e.toString());
						id_request = false;

						ret = new Integer(1);
					}
					if (id_request == true) {
						Log.d("!!!!!!!", "22222222");
						names_info2[0] = String.valueOf(result);
						names_title2[0] = names2[0] + names_info2[0];
						ArrayAdapter<String> adapt2 = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.list_item,
								names_title2);
						lv2.setAdapter(adapt2);

						SavePreferences("ID", names_info2[0]);
						Log.d("!!!!!", "Shared OOOOOOOOOOOOK");

					} else if (id_request == false) {
						Log.d("!!!!!!!", "333333333");
						id_request = true;
					}
				}
			}
			return ret;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result.equals(0))
				Toast.makeText(getApplicationContext(),
						"Client registration succeeded", Toast.LENGTH_SHORT)
						.show();
			
			else
				Toast.makeText(
						getApplicationContext(),
						"Some error occured while sending data to server. Please try again later",
						Toast.LENGTH_SHORT).show();

			Log.d("Log", "End");
			id = 1;
			tabs.setCurrentTab(id);
			mt2 = new MyTask2();
			mt2.execute();

		}
	}

	void readFile_info() {
		String str = "";

		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_details.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));

			for (int i = 0; i < details.length - 1; i++) {
				details[i] = br.readLine();
				// Log.d("FILE", details[i]);

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// alexkeis read the nominated contact stuff
	void readFile_info3() {

	}

	class MyTask2 extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer ret = new Integer(0);

			try {
				String currentTimeStamp = "/Date(" + System.currentTimeMillis()
						/ 1000 + ")/";

				HttpPost request = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/InsertEnquiryEntry");
				request.setHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");

				JSONStringer json = new JSONStringer().object().key("Id")
						.value(0).key("ClientId")
						.value(Integer.parseInt(names_info2[0]))
						.key("EnquiryTime").value(currentTimeStamp)
						.key("ResponseTime").value(currentTimeStamp)
						.key("ResponseText").value(null).key("PersonalData")
						.value(null).key("EnquiryText")
						.value("Callback Request").endObject();
				Log.d("SmartClaim", json.toString());
				StringEntity entity = new StringEntity(json.toString(), "UTF-8");
				request.setEntity(entity);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(request);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + System.getProperty("line.separator"));
				}
				Log.d("SmartClaim", "req 3 " + sb.toString());

			} catch (Exception e) {
				ret = new Integer(1);
			}

			return ret;
		}

		
		
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			progressDialog.dismiss();
			if (result.intValue() == 0)
				Toast.makeText(getApplicationContext(),
						"Callback request successfully sent",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(
						getApplicationContext(),
						"Some error occured while sending Callback request to server. Please try again later",
						Toast.LENGTH_SHORT).show();
		}
	}
	
	
	class MyTask3 extends AsyncTask<Void, Void, Integer> {

		public String lng = "";
		public String lat = "";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer ret = new Integer(0);
//
			if(names_info2[0].isEmpty()){
				return  new Integer(2);
		    }
			
			try {
				String currentTimeStamp = "/Date(" + System.currentTimeMillis()
						/ 1000 + ")/";

				HttpPost request = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/InsertEnquiryEntry");
				request.setHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");

				JSONStringer json = new JSONStringer().object().key("Id")    
						.value(0).key("ClientId")
						.value(Integer.parseInt(names_info2[0]))
						.key("EnquiryTime").value(currentTimeStamp)
						.key("ResponseTime").value(currentTimeStamp)
						.key("ResponseText").value(null).key("PersonalData")
						.value(null).key("EnquiryText")
						.value("GAP Request: latitude="+lat+"; longitude="+lng+";").endObject();
				Log.d("SmartClaim", json.toString());
				StringEntity entity = new StringEntity(json.toString(), "UTF-8");
				request.setEntity(entity);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				
			
				
				
				HttpResponse response = httpClient.execute(request);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + System.getProperty("line.separator"));
				}
				Log.d("SmartClaim", "req 3 " + sb.toString());

			} catch (Exception e) {
				ret = new Integer(1);
			}

			return ret;
		}

		
		
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			progressDialog.dismiss();
			if (result.intValue() == 0)
				Toast.makeText(getApplicationContext(),
						"GAP request successfully sent",
						Toast.LENGTH_SHORT).show();
			else if(result.intValue() ==2){
				Toast.makeText(
						getApplicationContext(),
						"Please specify a valid Client ID",
						Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(
						getApplicationContext(),
						"Some error occured while sending Callback request to server. Please try again later",
						Toast.LENGTH_SHORT).show();
		}
	}

	public void dialog() {
		pos = pp;
		title = tit;
		dialog.show(getFragmentManager(), "");
		dialog.setCancelable(false);
	}

	public void dialog2() {
		pos = pp;
		title = tit;
		dialog2.show(getFragmentManager(), "");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ArrayAdapter<String> adapt2 = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title2);
		lv2.setAdapter(adapt2);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		lv.setAdapter(adapt);

	}

	@Override
	protected void onPause() {
		SavePreferences("ID", names_info2[0]);
		super.onPause();
	}

}
