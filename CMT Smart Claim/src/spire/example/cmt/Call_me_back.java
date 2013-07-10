package spire.example.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_me_back);
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

	public void forgout_id(View view) {

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

	public class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin");
		}

		@Override
		protected Void doInBackground(Void... params) {

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
					in_obj.put("Id", new Integer(1));
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

					obj.put("PersonalData", in_obj);

					StringWriter out = new StringWriter();
					JSONValue.writeJSONString(obj, out);
					String jsonText = out.toString();

					Log.d("WWW", jsonText);

					httppost.setEntity(new StringEntity(jsonText));

					HttpResponse response = httpclient.execute(httppost);

					Log.d("444", response.toString());
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

				}

				catch (org.apache.http.client.ClientProtocolException e) {

				} catch (IOException e) {

				} catch (Exception e) {

				}

			} finally {
				Log.d("!!!", result);
				boolean id_request;
				result = result.substring(0, result.length() - 1);
				Log.d("DLINNA", String.valueOf(result.length()) + "/" + result
						+ "/");
				try {
					Log.d("!1!", result);
					Integer.parseInt(result.toString());
					id_req = Integer.parseInt(result.toString());
					id_request = true;
				} catch (Exception e) {
					Log.d("!!!!!!!", e.toString());
					id_request = false;

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
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
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
				String str_utf8 = new String(br.readLine().getBytes(
						"ISO-8859-1"), "UTF-8");
				details[i] = str_utf8;
				Log.d("FILE", details[i]);

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// //////////
	// ////////
	// ////////
	class MyTask2 extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin");
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/InsertEnquiryEntry");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");
				try {

					Map obj = new LinkedHashMap();
					obj.put("Id", new Integer(0));
					obj.put("ClientId", Integer.parseInt(names_info2[0]));

					String s = "/Date(" + System.currentTimeMillis() / 1000
							+ ")/";
					obj.put("EnquiryT 	ime", s);
					obj.put("ResponseTime", s);

					obj.put("ResponseText", null);
					obj.put("EnquiryText", "Callback Request");

					obj.put("PersonalData", null);

					StringWriter out = new StringWriter();
					JSONValue.writeJSONString(obj, out);
					String jsonText = out.toString();

					Log.d("WWW", jsonText);

					httppost.setEntity(new StringEntity(jsonText));

					HttpResponse response = httpclient.execute(httppost);

					Log.d("444", response.toString());
					// ////////////
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "windows-1251"));
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + System.getProperty("line.separator"));
					}

					result_enquiry = sb.toString();

				}

				catch (org.apache.http.client.ClientProtocolException e) {

				} catch (IOException e) {

				} catch (Exception e) {

				}

			} finally {
				Log.d("!!!", "!!!!!!!ww!");

				result_enquiry = result_enquiry.substring(0,
						result_enquiry.length() - 1);
				Log.d("DLINNA", String.valueOf(result_enquiry.length()) + "/"
						+ result_enquiry + "/");

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT)
					.show();

		}
	}

	// ///////
	// //////
	// //////

	public void dialog() {
		pos = pp;
		title = tit;
		dialog.show(getFragmentManager(), "");
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
