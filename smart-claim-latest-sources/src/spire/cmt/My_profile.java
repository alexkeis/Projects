package spire.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.*;
import org.json.JSONObject;
import org.json.simple.*;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class My_profile extends Activity {
	public static String[] mas_prof = { "Your Details", "Your Vehicle" };
	Button im_del, sendDetails;
	SharedPreferences sPref;
	final String SAVED_TEXT = "pin";
	int id_req;
	String result = null;
	String id_client = "";
	JSONArray array;
	String strSavedMem1;
	private ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile);
		 Log.d("Log", "---My_profile---");
		
		array = new JSONArray();
		sendDetails = (Button) findViewById(R.id.sendDetails);

		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("ID", "");
		if (strSavedMem1.equals("")) {
			sendDetails.setVisibility(View.VISIBLE);
		} else
			sendDetails.setVisibility(View.GONE);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage("Wait");

		load();
		list();
		readFile_info();
		readFile_info2();
	}

	public void showDetails(View view) {
		Your_details yr = new Your_details();
		Your_vehicle your_vehicle = new Your_vehicle();
		if (yr.names_info[0].equals("") || yr.names_info[1].equals("")
				|| yr.names_info[5].equals("")
				|| your_vehicle.names_info[2].equals("")) {
			Toast.makeText(getApplicationContext(),
					"Please fill all required fields in your profile",
					Toast.LENGTH_LONG).show();
		} else {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				{
					MyTask_show myTask = new MyTask_show();
					myTask.execute();

				}
				// ///////////////
			} else {
				// display error
				Toast.makeText(getApplicationContext(), "Invalid connection",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void SavePreferences2(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	// ///////////////////////
	Your_vehicle info_vechicle = new Your_vehicle();
	Your_details details = new Your_details();

	// //////////////////

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {

		case 0:

		{
			builder.setTitle("Registration Succeeded");
			builder.setMessage("Your ClientID is " + id_client);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setNeutralButton("Cancel", null);

			builder.setCancelable(false);
			return builder.create();
		}
		case 1:

		{

			builder.setTitle("Registration Failed");
			builder.setMessage("Please try again later");
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setNeutralButton("Cancel", null);
			builder.setCancelable(false);
			return builder.create();
		}
		default:
			return null;
		}
	}

	// //////////////////////
	public class MyTask_show extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
			Log.d("Logn", "Begin_registration");
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer res = new Integer(0);
			 Log.d("Log", "doInBackground---");
			try {
				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 5000;
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						timeoutConnection);
				int timeoutSocket = 5000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
				DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost(
						"http://service.cmt.net.au/ClaimsDataService.svc/SaveClientObject");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");
				try {
					Map obj = new LinkedHashMap();
					obj.put("Id", new Integer(0));
					obj.put("DealerId", null);
					obj.put("DealerVehicleMake", null);
					obj.put("VehicleMake", info_vechicle.names_info[0]);
					obj.put("VehicleCategoryId", null);
					obj.put("VehicleModelUser", info_vechicle.names_info[1]);
					obj.put("VehicleModelData", null);
					obj.put("VehicleRego", info_vechicle.names_info[2]);
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
					in_obj.put("FirstName", details.names_info[0]);
					in_obj.put("Surname", details.names_info[1]);
					in_obj.put("Email", details.names_info[6]);
					in_obj.put("Phone", details.names_info[5]);
					in_obj.put("Phone2", null);
					in_obj.put("Company", null);
					in_obj.put("AbnNumber", null);
					in_obj.put("Country", null);
					in_obj.put("Address1", details.names_info[7]);
					in_obj.put("Address2", null);
					in_obj.put("City", "");
					in_obj.put("StateCode", details.names_info[10]);// /izm
																	// details[10]
					in_obj.put("Postcode", details.names_info[9]);// details[9]

					obj.put("PersonalData", "--");
					//
					//StringWriter out2 = new StringWriter();					
					// JSONValue.writeJSONString(obj, out2);
					
					JSONObject json =  new JSONObject(in_obj);
					JSONObject json1 =  new JSONObject(obj);
					//String jsonText = out2.toString();
					String jsonText = json.toString(); 
					String jsonText1 = json1.toString();
					jsonText1 = jsonText1.replace("\"--\"", jsonText);
					httppost.setEntity(new StringEntity(jsonText1, "UTF-8"));

					HttpResponse response = httpclient.execute(httppost);
                     // if (response == null)
					
					  // ////////////
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "windows-1251"));
					
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + System.getProperty("line.separator"));
					}

					result = sb.toString();
					Log.d("Log", "result "+result);
					//
				} catch (Exception e) {
					e.printStackTrace();
					res = new Integer(1);
				}

			} finally {
				Log.d("Log", "finally ");
				if (result == null){
					res = new Integer(1);
				}else{
				  result = result.substring(0, result.length() - 1);
				  try {

					Integer.parseInt(result);
					Log.d("Log", "Norm");
					res = new Integer(0);
				  } catch (Exception e) {
					Log.d("Log", "Fail");
					res = new Integer(1);
					// TODO: handle exce;ption
				  }

				 Log.d("DLINNA", String.valueOf(result.length()) + "/" + result+ "/");
				  id_client = result;
				}

			}
			return res;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			progressDialog.dismiss();
			
			if (result.intValue() == 1)

				// Toast.makeText(
				// getApplicationContext(),
				// "Registration Failed. Please try again later.",
				// Toast.LENGTH_SHORT).show();
				showDialog(1);

			else {
				Log.d("Log", "VSE OKEY");
				showDialog(0);

				// Toast.makeText(getApplicationContext(),
				// "Registration Succeeded. Your ClientID is " + id_client,
				// Toast.LENGTH_SHORT)
				// .show();

				SavePreferences2("ID", id_client);
				sendDetails.setVisibility(View.GONE);
			}

		}
	}

	// /////////////////////
	// ////////////////////

	public void load() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"MY_SHARED_PREF", MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("MEM1", "");
		if (strSavedMem1.equals("")) {
			im_del = (Button) findViewById(R.id.del_pin);
			im_del.setVisibility(View.GONE);
		} else {
			im_del = (Button) findViewById(R.id.del_pin);
			im_del.setVisibility(View.VISIBLE);
		}
	}

	public void click_pin(View view) {
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), New_pin.class);
		startActivity(intent1);
	}

	public void click_del_pin(View view) {
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Del_pin.class);
		startActivity(intent1);
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_my_profile);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, mas_prof);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Your_details.class);
					startActivity(intent1);
				}
				if (position == 1) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Your_vehicle.class);
					startActivity(intent1);
				}
			}
		});
	}

	void readFile_info() {
		String str = "";

		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_details.txt");

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(sdFile), "utf8"), 8192);
			// BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			Your_details yr = new Your_details();
			while ((str = br.readLine()) != null) {
				yr.names_info[qw] = str;
				yr.names_title[qw] = yr.names[qw] + yr.names_info[qw];
				qw++;
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void readFile_info2() {
		String str = "";
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			Your_vehicle yv = new Your_vehicle();
			while ((str = br.readLine()) != null)

			{
				yv.names_info[qw] = str;
				yv.names_title[qw] = yv.names[qw] + yv.names_info[qw];
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		load();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		load();
	}
}