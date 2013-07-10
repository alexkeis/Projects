package spire.example.cmt;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

public class Lodge_claim extends Activity implements OnClickListener {
	DialogFragment dlg2;
	DialogFragment dlg1;
	TabHost tabs;
	public static String street;
	public static final String[] local_names = { "", "", "", "" };
	Button qwe;
	Button btn_other_drivers_vechickes;
	Button qwe1;
	public static String acident_description;
	Button qwe2;
	int DIALOG_DATE = 1;
	int DIALOG_TIME = 1;
	double sh;
	double dl;
	public static String[] names_info = { "", "", "" };
	public static String[] info_vechicle = { "", "", "" };
	String strSavedMem1;
	String new_str = "";
	public static String[] names_znach = new String[] { "", "", "", "", "", "",
			"", "", "", "" };
	public static String[] lc = { "", "" };
	public static String str_l = "";
	public static int id;
	public static int colvo = 0;
	public static String nameFolder = "";
	public static boolean rez_time = false;
	public static File path_d;
	public static boolean rez_new_folder = false;
	GoogleMapsActivity ggg = new GoogleMapsActivity();
	String id_client = "";
	String str_request = new String("");
	String str_request_image = new String("");
	File sdPath2;
	byte[] b;
	File[] file;
	int id_image = 0;
	public ByteArrayOutputStream bos;
	public Bitmap bm_n;
	public byte[] bitmapdata;
	JSONArray array;
	ByteArrayOutputStream baos;
	Bitmap bmp;
	FileInputStream in;
	int id_req;
	String result;
	BufferedInputStream buf;
	Bitmap bMap;
	public static String[] details = { "", "", "", "", "", "", "", "", "", "",
			"", "" };
	public static String[] details_vehicle = { "", "", "" };
	MyTask_send_image myTask_send_image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lodge_claim);
		// readFile_info();
		readFile_info2();
		array = new JSONArray();

		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("ID", "");

		if (strSavedMem1.equals("")) {
			id_client = "";
		} else
			id_client = strSavedMem1;

		GoogleMapsActivity ggg = new GoogleMapsActivity();
		if (rez_new_folder == true) {
			final Calendar c = Calendar.getInstance();
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinute = c.get(Calendar.MINUTE);
			int mSecunde = c.get(Calendar.SECOND);
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH) + 1;
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			nameFolder = String.valueOf(mDay) + "." + String.valueOf(mMonth)
					+ "." + String.valueOf(mYear) + " " + String.valueOf(mHour)
					+ ":" + String.valueOf(mMinute) + ":"
					+ String.valueOf(mSecunde);
			path_d = new File(getFilesDir(), "/CMT/" + nameFolder); // dima izm
			File path = new File(getFilesDir(), "/CMT/" + nameFolder);
			path.mkdirs();
			rez_new_folder = false;
			names_znach[0] = "";
			str_l = "";
			// //////////////

			Lodge_claim_smash_repair repair = new Lodge_claim_smash_repair();
			for (int i = 0; i < repair.names_info.length; i++) {
				repair.names_info[i] = "";
				repair.names_title[i] = repair.names[i];
			}
			// ////////////
			Lodge_claim_your_details details = new Lodge_claim_your_details();
			for (int i = 0; i < details.names_info.length; i++) {
				details.names_info[i] = "";
				details.names_title[i] = details.names[i];
			}
			// ////////////
			Lodge_claim_your_vehicle vehicle = new Lodge_claim_your_vehicle();
			for (int i = 0; i < vehicle.names_info.length; i++) {
				vehicle.names_info[i] = "";
				vehicle.names_title[i] = vehicle.names[i];
			}
			// ////////////
			Lodge_claim_your_insurer insurer = new Lodge_claim_your_insurer();
			for (int i = 0; i < insurer.names_info.length; i++) {
				insurer.names_info[i] = "";
				insurer.names_title[i] = insurer.names[i];
			}
			// ////////////
		} else {
			str_l = ggg.local_n;
			// ///////////
			readFile_DATE_TIME();
			readFile_names();
			local_position();
		}

		dlg2 = new Dialog2();
		dlg1 = new Dialog1();
		ListView lv = (ListView) findViewById(R.id.listView_lodge_claim);
		ListView lv2 = (ListView) findViewById(R.id.listView_lodge_claim2);
		ListView lv3 = (ListView) findViewById(R.id.listView_lodge_claim3);
		// Dialog1 dd1 = new Dialog1();

		String[] names_title = new String[] {
				"Accident Time: " + names_znach[0],
				"Accident Location: " + str_l, "Other Drivers & Vechicles",
				"Who Admitted the liability", "Your Details", "Vechicle",
				"Your Insurer", "Smash Repair", "Attache Images" };
		String[] names_title2 = new String[] {
				"Accident Time: " + names_znach[0],
				"Accident Location: " + str_l, "Your Details", "Vechicle",
				"Your Insurer", "Smash Repair", "Attache Images" };
		String[] names_title3 = new String[] {
				"Accident Time: " + names_znach[0],
				"Accident Location: " + str_l, "Accident Description: ",
				"Your Details", "Vechicle", "Your Insurer", "Smash Repair",
				"Attache Images" };

		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		ArrayAdapter<String> adapt2 = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title2);
		ArrayAdapter<String> adapt3 = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title3);
		View footer = getLayoutInflater().inflate(R.layout.footer, null);
		lv.addFooterView(footer);
		lv.setDividerHeight(0);
		lv2.addFooterView(footer);
		lv2.setDividerHeight(0);
		lv3.addFooterView(footer);
		lv3.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv2.setAdapter(adapt2);
		lv3.setAdapter(adapt3);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Lodge_claim lg = new Lodge_claim();
				lg.id = 0;
				Dialog1 dialog_time = new Dialog1();
				dialog_time.id_tab = 0;
				MultipleItemsList.id_tab = 0;
				if (position == 0) {
					dlg1.show(getFragmentManager(), "dlg1");

				}
				if (position == 1) {
					Dialog2 d2 = new Dialog2();
					d2.id_tab = 0;
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							GoogleMapsActivity.class);
					startActivity(intent1);

				}
				if (position == 2) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Other_drivers_vechickes.class);
					startActivity(intent1);
				}
				if (position == 3) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Other_drivers_insurance.class);
					startActivity(intent1);
				}
				if (position == 4) {
					Lodge_claim_your_details details = new Lodge_claim_your_details();
					details.update = true;
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_details.class);
					startActivity(intent1);
				}
				if (position == 5) {
					Lodge_claim_your_vehicle vehicle = new Lodge_claim_your_vehicle();
					vehicle.update = true;
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_vehicle.class);
					startActivity(intent1);
				}
				if (position == 6) {
					Lodge_claim_your_insurer insurer = new Lodge_claim_your_insurer();
					insurer.update = true;
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_insurer.class);
					startActivity(intent1);
				}
				if (position == 7) {
					Lodge_claim_smash_repair repair = new Lodge_claim_smash_repair();
					repair.update = true;
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_smash_repair.class);
					startActivity(intent1);
				}
				if (position == 8) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(), Photo.class);
					startActivity(intent1);
				}

			}
		});
		// ///////
		lv2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Lodge_claim lg = new Lodge_claim();
				lg.id = 1;
				MultipleItemsList.id_tab = 1;
				Dialog1 dialog_time = new Dialog1();
				dialog_time.id_tab = 1;
				Dialog2 d2 = new Dialog2();
				d2.id_tab = 1;
				if (position == 0) {
					id = 1;
					dlg1.show(getFragmentManager(), "dlg1");

				}
				if (position == 1) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							GoogleMapsActivity.class);
					startActivity(intent1);

				}
				if (position == 2) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_details.class);
					startActivity(intent1);
				}
				if (position == 3) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_vehicle.class);
					startActivity(intent1);
				}
				if (position == 4) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_insurer.class);
					startActivity(intent1);
				}
				if (position == 5) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_smash_repair.class);
					startActivity(intent1);
				}
				if (position == 6) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(), Photo.class);
					startActivity(intent1);
				}
			}
		});
		// ///////
		lv3.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				id = 2;
				Dialog1 dialog_time = new Dialog1();
				dialog_time.id_tab = 2;
				Dialog2 d2 = new Dialog2();
				d2.id_tab = 2;
				if (position == 0) {
					dlg1.show(getFragmentManager(), "dlg1");

				}
				if (position == 1) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							GoogleMapsActivity.class);
					startActivity(intent1);

				}
				if (position == 2)// ///////////////////////
				{
					readFile_acciden_description();
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Accident_description.class);
					startActivity(intent1);
				}
				if (position == 3) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_details.class);
					startActivity(intent1);
				}
				if (position == 4) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_vehicle.class);
					startActivity(intent1);
				}
				if (position == 5) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_your_insurer.class);
					startActivity(intent1);
				}
				if (position == 6) {
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							Lodge_claim_smash_repair.class);
					startActivity(intent1);
				}
				if (position == 7) {
					Intent intent1 = new Intent();
					MultipleItemsList.id_tab = 2;
					intent1.setClass(getApplicationContext(), Photo.class);
					startActivity(intent1);
				}

			}
		});
		// ////////

		tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Not My Fault");
		tabs.addTab(spec);
		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("My Fault");
		tabs.addTab(spec);
		spec = tabs.newTabSpec("tag3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Other");
		tabs.addTab(spec);
		tabs.setCurrentTab(id);

	}

	// //////////////////
	// //////////////
	public class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin_registration");
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
					obj.put("VehicleMake", info_vechicle[0]);
					obj.put("VehicleCategoryId", null);
					obj.put("VehicleModelUser", info_vechicle[1]);
					obj.put("VehicleModelData", null);
					obj.put("VehicleRego", info_vechicle[2]);
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
					in_obj.put("FirstName", details[0]);
					in_obj.put("Surname", details[1]);
					in_obj.put("Email", details[6]);
					in_obj.put("Phone", details[5]);
					in_obj.put("Phone2", null);
					in_obj.put("Company", null);
					in_obj.put("AbnNumber", null);
					in_obj.put("Country", null);
					in_obj.put("Address1", details[7]);
					in_obj.put("Address2", null);
					in_obj.put("City", "");
					in_obj.put("StateCode", details[10]);///izm
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
				SavePreferences2("ID", result);
				id_client = result;
				strSavedMem1 = result;
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					{
						
							MyTask_send myTask_send = new MyTask_send();
							myTask_send.execute();
						
					}
					// ///////////////
				} else {
					// display error
					Toast.makeText(getApplicationContext(),
							"Invalid connection", Toast.LENGTH_SHORT).show();
				}

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

					SavePreferences2("ID", result);
					Log.d("!!!!!", "Shared OOOOOOOOOOOOK");

				} else if (id_request == false) {
					Log.d("!!!!!!!", "333333333");
					id_request = true;
				}

			}
			return null;
		}
	}

	// ////////////////
	// ////////////
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

	void readFile_info_details() {
		Log.d("!!!!!!!!!!!!!!", "STARTSTARTSTART");
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "Lodge_claim_my_profile_details.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			for (int i = 0; i < details.length - 1; i++) {
				details[i] = br.readLine();
//				Log.d("!!!!!!!!!!!!!!!!!!", details[i]);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ////////////////////
	void readFile_info_vechicle() {
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		String vr_str = "";
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		File sdFile = new File(path, "My_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null) {
				info_vechicle[qw] = str;

				Log.d("!!!!!!!!", info_vechicle[qw]);
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// /////////////
	public byte[] getBytesFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
	}

	public void send_not_my_foult(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		strSavedMem1 = sharedPreferences.getString("ID", "");

		if (strSavedMem1.equals("")) {
			readFile_info_details();
			readFile_info_vechicle();
			String enter = "Please enter: ";
			{
				if (details[0].length() < 1) {
					enter = enter + "*First Name" + "\n";
				}
				if (details[1].length() < 1) {
					enter = enter + "*Last Name" + "\n";
				}
				if (details[5].length() < 1) {
					enter = enter + "*Phone" + "\n";
				}
				if (info_vechicle[2].length() < 1) {
					enter = enter + "*Rego" + "\n";
				}
			}
			if(enter.length()>15){
				Toast.makeText(getApplicationContext(), enter, Toast.LENGTH_SHORT).show();
			}
			else{
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					{
							MyTask myTask = new MyTask();
							myTask.execute();

						
					}
					// ///////////////
				} else {
					// display error
					Toast.makeText(getApplicationContext(), "Invalid connection",
							Toast.LENGTH_SHORT).show();
				}
				
			}

		} else {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				{
					if (id_client.equals("")) {
					} else {
						MyTask_send myTask_send = new MyTask_send();
						myTask_send.execute();
					}
				}
				// ///////////////
			} else {
				// display error
				Toast.makeText(getApplicationContext(), "Invalid connection",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class MyTask_send extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin_send");
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/InsertAccidentClaim");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");
				try {

					Map obj = new LinkedHashMap();
					obj.put("Id", new Integer(0));
					obj.put("LodgementType", new Integer(1));
					obj.put("ProviderData", null);

					Map myData = new LinkedHashMap();
					myData.put("Id", new Integer(0));
					myData.put("AccidentLodgementDataId", new Integer(0));
					myData.put("ClientId",
							new Integer(Integer.parseInt(strSavedMem1)));//izm
					myData.put("IsOtherParty", false);
					myData.put("DriverFirstName", details[0]);
					myData.put("DriverSurname", details[1]);
					myData.put("DateOfBirth", null);
					myData.put("OwnerFirstName", "");
					myData.put("OwnerSurname", "");
					myData.put("Address", "");
					myData.put("Postcode", "");
					myData.put("MobilePhone", details[7]);
					myData.put("HomeBusinessPhone", "");
					myData.put("EmailAddress", "hz@gmail.com");
					myData.put("OwnerInsuranceCompany", "");
					myData.put("PolicyNumber", null);
					myData.put("ClaimNumber", null);
					myData.put("VehicleMake", null);
					myData.put("VehicleModel", null);
					myData.put("VehicleYear", null);
					myData.put("VehicleRegistrationNumber", null);
					myData.put("DriverLicenseNo", "12345678");
					String s = "/Date(" + System.currentTimeMillis() / 1000
							+ ")/";
					myData.put("LicenseExpiryDate", s);

					obj.put("MyData", myData);

					Map otherData = new LinkedHashMap();
					otherData.put("Id", new Integer(0));
					otherData.put("AccidentLodgementDataId", new Integer(0));
					otherData.put("ClientId", null);
					otherData.put("IsOtherParty", true);
					otherData.put("DriverFirstName", "DriverFirst");
					otherData.put("DriverSurname", "DriverLast");
					otherData.put("DateOfBirth", null);
					otherData.put("OwnerFirstName", "OwnerFirst");
					otherData.put("OwnerSurname", "OwnerLast");
					otherData.put("Address", "");
					otherData.put("Postcode", "");
					otherData.put("MobilePhone", "051502151");
					otherData.put("HomeBusinessPhone", "");
					otherData.put("EmailAddress", "");
					otherData.put("OwnerInsuranceCompany", "");
					otherData.put("PolicyNumber", null);
					otherData.put("ClaimNumber", null);
					otherData.put("VehicleMake", null);
					otherData.put("VehicleModel", null);
					otherData.put("VehicleYear", null);
					otherData.put("VehicleRegistrationNumber", null);
					otherData.put("DriverLicenseNo", "45645343");
					otherData.put("LicenseExpiryDate", s);

					obj.put("OtherData", otherData);

					Map accidentData = new LinkedHashMap();
					accidentData.put("Id", new Integer(0));
					accidentData.put("AccidentLodgementDataId", new Integer(0));
					accidentData.put("Date", s);
					accidentData.put("Time", "14:44:44");
					accidentData.put("Suburb", "Belanglo State Forest");
					accidentData.put("Location", "her znset gde");
					accidentData.put("Description", null);
					accidentData.put("WitnessNameAddress", null);
					accidentData.put("NumberOfVehilcesInvolved", null);
					accidentData.put("AddmitedLiability", null);

					obj.put("AccidentData", accidentData);

					obj.put("PoliceReportData", null);
					obj.put("SmashRepairData", null);
					obj.put("ReplacementVehicleData", null);

					Map declarationData = new LinkedHashMap();
					declarationData.put("Name", "Web Service Test");
					declarationData.put("Date", s);

					obj.put("DeclarationData", declarationData);

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

					str_request = sb.toString();

				}

				catch (org.apache.http.client.ClientProtocolException e) {

				} catch (IOException e) {

				} catch (Exception e) {

				}

			} finally {
				Log.d("!!!", "!!!!!!!ww!");

				str_request = str_request
						.substring(0, str_request.length() - 1);
				Log.d("DLINNA", String.valueOf(str_request.length()) + "/"
						+ str_request + "/");
				sdPath2 = new File(path_d.toString());
				file = sdPath2.listFiles();
				{
					if (file.length > 0) {

						myTask_send_image = new MyTask_send_image();
						myTask_send_image.execute();

					}
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			Toast.makeText(getApplicationContext(), "Okkkkkk",
					Toast.LENGTH_SHORT).show();
		//	delete();
		//	finish();

		}
	}

	// /////////////
	public void delete() {

		try {
			File sdPath = new File(path_d.toString());
			File[] f = sdPath.listFiles();
			for (int b = 0; b < f.length; b++) {
				File sdf = new File(f[b].toString());
				sdf.delete();
			}
			sdPath.delete();

		} catch (Exception e) {
		}

	}

	// /////////////////////
	class MyTask_send_image extends AsyncTask<Void, Void, Void> {

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
						"http://test.service.cmt.net.au/ClaimsDataService.svc/UploadAccidentClaimImage");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");
				try {
					// /////////
					// /////////
					sdPath2 = new File(path_d.toString());
					file = sdPath2.listFiles();
					for (int c = 0; c < file.length; c++) {
						baos = new ByteArrayOutputStream();
						bmp = BitmapFactory.decodeFile(file[c].toString());
						bmp.compress(Bitmap.CompressFormat.PNG, 50, baos);
						try {

							baos.flush();
							baos.close();
							bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

							byte[] bMapArray;
							in = new FileInputStream(file[c]);
							buf = new BufferedInputStream(in, 1070);

							bMapArray = new byte[buf.available()];
							buf.read(bMapArray);
							bMap = BitmapFactory.decodeByteArray(bMapArray, 0,
									bMapArray.length);

							// array = null;
							array = new JSONArray();
							for (int i = 0; i < bMapArray.length; i++) {
								array.put(((byte) bMapArray[i]) & 0xFF);
							}
							Map obj = new LinkedHashMap();
							obj.put("FileByteStream", array);
							obj.put("AccidentLodgementDataId",
									new Integer(str_request = str_request
											.substring(0,
													str_request.length() - 1)));
							obj.put("FileName", "Image.jpg");

							StringWriter out = new StringWriter();
							JSONValue.writeJSONString(obj, out);
							String jsonText = out.toString();

							Log.d("WWW", jsonText);

							httppost.setEntity(new StringEntity(jsonText));
							HttpResponse response = httpclient
									.execute(httppost);

							Log.d("444", response.toString());
							// ////////////
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(response.getEntity()
											.getContent(), "windows-1251"));
							StringBuilder sb = new StringBuilder();
							String line = null;

							while ((line = reader.readLine()) != null) {
								sb.append(line
										+ System.getProperty("line.separator"));
							}

							str_request_image = sb.toString();
							str_request_image = str_request_image.substring(0,
									str_request_image.length() - 1);
							Log.d("DLINNA",
									String.valueOf(str_request_image.length())
											+ "/" + str_request_image + "/");
							if (in != null) {
								in.close();
							}
							if (buf != null) {
								buf.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// ////////
					// //////
					// Map obj = new LinkedHashMap();
					// obj.put("FileByteStream", array);
					// obj.put("AccidentLodgementDataId", new
					// Integer(id_client));
					// obj.put("FileName", "Image.jpg");
					//
					// StringWriter out = new StringWriter();
					// JSONValue.writeJSONString(obj, out);
					// String jsonText = out.toString();
					//
					// Log.d("WWW", jsonText);
					//
					// httppost.setEntity(new StringEntity(jsonText));

					// HttpResponse response = httpclient.execute(httppost);
					//
					// Log.d("444", response.toString());
					// // ////////////
					// BufferedReader reader = new BufferedReader(
					// new InputStreamReader(response.getEntity()
					// .getContent(), "windows-1251"));
					// StringBuilder sb = new StringBuilder();
					// String line = null;
					//
					// while ((line = reader.readLine()) != null) {
					// sb.append(line + System.getProperty("line.separator"));
					// }
					//
					// str_request_image = sb.toString();
					//
				}

				catch (Exception e) {
					Log.d("!!!!!!", e.toString());

				}

			} finally {
				Log.d("!!!", "!!!!!!!ww!");

				// str_request_image = str_request_image.substring(0,
				// str_request_image.length() - 1);
				// Log.d("DLINNA", String.valueOf(str_request_image.length())
				// + "/" + str_request_image + "/");

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

	// //////////////////////
	// ///////////////////////

	public void save_d() {
		Dialog1 d = new Dialog1();
		File path = new File(getFilesDir(), "/CMT/" + nameFolder);
		File sdFile = new File(path, "Date_time.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			bw.write(d.year + " " + d.month + " " + d.day + " " + d.hour + " "
					+ d.minute);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void view_summary_1(View view) {
		MultipleItemsList.id_tab = tabs.getCurrentTab();
		id = tabs.getCurrentTab();
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), MultipleItemsList.class);
		startActivity(intent1);
	}

	void readFile_names() {
		String str = "";
		File path = new File(getFilesDir(), "/CMT/" + nameFolder);
		File sdFile = new File(path, "1123.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null)

			{
				qw++;
				Log.d("My log", str);

			}
			colvo = qw;

		} catch (FileNotFoundException e) {
			colvo = 0;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void readFile_DATE_TIME() {
		File path = new File(getFilesDir(), "/CMT/" + nameFolder);
		File sdFile = new File(path, "Date_time.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			names_znach[0] = br.readLine();

		} catch (FileNotFoundException e) {
			names_znach[0] = "";
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void readFile_acciden_description() {
		String str = "";
		String str2 = "";

		File path = new File(getFilesDir(), "/CMT/" + nameFolder);
		File sdFile = new File(path, "Accident_description.txt");
		try {
			// открываем поток для чтения
			BufferedReader br = new BufferedReader(new FileReader(sdFile));

			// читаем содержимое
			while ((str = br.readLine()) != null) {
				str2 = str2.concat(str + "\n");
			}
			Accident_description acd = new Accident_description();
			acd.accident_text = str2;
			Log.d("Log", str2);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.d("Log", "333333");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void local_position() {

		File path = new File(getFilesDir(), "/CMT/" + nameFolder);
		File sdFile = new File(path, "local2.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			str_l = br.readLine();
		} catch (FileNotFoundException e) {
			str_l = "";
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SavePreferences(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"MY_SHARED_PREF", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	private void SavePreferences2(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		finish();
		Intent intent1 = new Intent();
		intent1.setClass(this, Lodge_claim.class);
		startActivity(intent1);
		// Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onStop() {
		SavePreferences(path_d.toString(), String.valueOf(tabs.getCurrentTab()));

		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
		//
		// if(rez_time==false){
		// finish();
		// }

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (rez_time == true) {
			rez_time = false;
			finish();
		}
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

}
