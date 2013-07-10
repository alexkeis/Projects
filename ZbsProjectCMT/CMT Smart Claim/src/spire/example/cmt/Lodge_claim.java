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
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
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
import android.util.JsonWriter;
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
	public static String IMAGES_PATH;
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
	boolean deleteFiles = true;
	String[] details_other_data = new String[] { "", "", "", "", "", "", "",
			"", "", "", "", "", "", "" };
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
	public static File sdPath2;
	byte[] b;
	public static String pathToImage = "";
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
	public static String[] details_insurer = { "", "", "" };
	public static String[] details_vehicle = { "", "", "" };
	MyTask_send_image myTask_send_image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lodge_claim);
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
	void read_other_data() {
		if (colvo > 0) {
			String str = "";
			Lodge_claim lg = new Lodge_claim();
			File path22 = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
			File sdFile22 = new File(path22, "1123.txt");

			try {
				BufferedReader br = new BufferedReader(new FileReader(sdFile22));
				str = br.readLine();
				str = str.substring(0, str.indexOf(" "))
						+ str.substring(str.indexOf(" ") + 1, str.length());
				Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG)
						.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File readdFile = new File(path22, str + ".txt");
			try {
				BufferedReader br = new BufferedReader(
						new FileReader(readdFile));
				for (int i = 0; i < details_other_data.length; i++) {
					details_other_data[i] = br.readLine();
					Log.d("SmartClaim", details_other_data[i]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// //////////////
	public class MyTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin_registration");
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer res = new Integer(0);
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
					in_obj.put("StateCode", details[10]);// /izm details[10]
					in_obj.put("Postcode", details[9]);// details[9]

					obj.put("PersonalData", in_obj);

					StringWriter out = new StringWriter();
					JSONValue.writeJSONString(obj, out);
					String jsonText = out.toString();

					Log.d("WWW", jsonText);

					httppost.setEntity(new StringEntity(jsonText, "UTF-8"));

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

				} catch (Exception e) {
					res = new Integer(1);
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
						Log.d("SmartClaim", "Start Claim Send");
						MyTask_send myTask_send = new MyTask_send();
						myTask_send.execute();

					}
					// ///////////////
				} else {
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
			return res;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");

			if (result.intValue() == 0)
				Toast.makeText(
						getApplicationContext(),
						"Some error occured while sending data to server. Please try again later",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getApplicationContext(),
						"Client registration succeeded", Toast.LENGTH_SHORT)
						.show();
			// delete();
			// finish();

		}
	}

	// ////////////////
	// ////////////
	void readFile_info2() {
		String str = "";
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(sdFile), "utf8"), 8192);
			// BufferedReader br = new BufferedReader(new FileReader(sdFile));
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
			// BufferedReader br = new BufferedReader(new FileReader(sdFile));
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(sdFile), "utf8"), 8192);
			for (int i = 0; i < details.length - 1; i++) {
				details[i] = br.readLine();
				// Log.d("!!!!!!!!!!!!!!!!!!", details[i]);
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

	// /data/data/spire.example.cmt/files/CMT/5.6.2013 14:56:44/ао ап.txt
	// /data/data/spire.example.cmt/files/CMT/5.6.2013 14:56:44/ргпв.txt

	// /////////////
	public byte[] getBytesFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
	}

	public void send_not_my_foult(View view) {
		readFile_insurer();
		read_other_data();
		readFile_info_details();
		readFile_info_vechicle();
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
			if (enter.length() > 15) {
				Toast.makeText(getApplicationContext(), enter,
						Toast.LENGTH_SHORT).show();
			} else {
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
					Toast.makeText(getApplicationContext(),
							"Invalid connection", Toast.LENGTH_SHORT).show();
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

	void readFile_insurer() {
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		String vr_str = "";
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		File sdFile = new File(path, "My_profile_insurer.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null)

			{
				details_insurer[qw] = str;

				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class MyTask_send extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("Logn", "Begin_send");
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer res = new Integer(0);

			try {
				String currentTimeStamp = "/Date(" + System.currentTimeMillis()
						/ 1000 + ")/";
				String accidentTime = names_znach[0];
				if (!accidentTime.equals("")) {
					int index = accidentTime.indexOf(" ") + 1;
					accidentTime = accidentTime.substring(index);
					accidentTime = accidentTime + ":00";
				} else
					accidentTime = "00:00:00";

				String accidentLocation = str_l;
				if (accidentLocation.equals(""))
					accidentLocation = null;

				int clientId = Integer.parseInt(strSavedMem1);
				assert (0 != clientId);

				String myDriverFirstName = details[0];
				assert (null != myDriverFirstName);

				String myDriverSurname = details[1];
				assert (null != myDriverSurname);

				String myDriverAddres = details[7];
				if (myDriverAddres.equals(""))
					myDriverAddres = null;

				int myDriverPostcode = 0;
				if (!details[9].equals(""))
					myDriverPostcode = Integer.parseInt(details[9]);

				String myDriverPhone = details[5];
				if (myDriverPhone.equals("")) {
					myDriverPhone = null;
				}

				String myDriverEmailAddres = details[6];
				if (myDriverAddres.equals(""))
					myDriverAddres = null;

				String myDriverPoliceNumber = details_insurer[1];
				if (myDriverPoliceNumber.equals(""))
					myDriverPoliceNumber = null;

				String myDriverClaimNumber = details_insurer[2];
				if (myDriverClaimNumber.equals(""))
					myDriverClaimNumber = null;

				String myDriverVehicleMake = info_vechicle[0];
				if (myDriverVehicleMake.equals(""))
					myDriverVehicleMake = null;

				String myDriverVehicleModel = info_vechicle[1];
				if (myDriverVehicleModel.equals(""))
					myDriverVehicleModel = null;

				String myVehicleRegistrationNumber = info_vechicle[2];
				if (myVehicleRegistrationNumber.equals(""))
					myVehicleRegistrationNumber = null;

				String myDriverLicenseNo = details[3];
				if (myDriverLicenseNo.equals(""))
					myDriverLicenseNo = null;

				String myDriverInsurer = details_insurer[0];
				if (myDriverInsurer.equals(""))
					myDriverInsurer = null;

				String myDateOfBirth = details[2];
				if (!myDateOfBirth.equals("")) {
					int index = myDateOfBirth.indexOf(" ");
					String year = myDateOfBirth.substring(0, index);
					String month = myDateOfBirth.substring(index + 1,
							myDateOfBirth.lastIndexOf(" "));
					String day = myDateOfBirth.substring(
							myDateOfBirth.lastIndexOf(" ") + 1,
							myDateOfBirth.length());

					Calendar calendar = new GregorianCalendar(
							Integer.parseInt(year), Integer.parseInt(month),
							Integer.parseInt(day));
					long dateOfBirdh = Long.parseLong(String.valueOf(calendar
							.getTimeInMillis())) / 1000;
					myDateOfBirth = "/Date(" + String.valueOf(dateOfBirdh)
							+ ")/";
				} else
					myDateOfBirth = "/Date(" + System.currentTimeMillis()
							/ 1000 + ")/";

				String myLicenseExpirydate = details[4];
				if (!myDateOfBirth.equals("")) {
					int index = myLicenseExpirydate.indexOf(" ");

					String year = myLicenseExpirydate.substring(0, index);
					String month = myLicenseExpirydate.substring(index + 1,
							myLicenseExpirydate.lastIndexOf(" "));
					String day = myLicenseExpirydate.substring(
							myLicenseExpirydate.lastIndexOf(" ") + 1,
							myLicenseExpirydate.length());

					Calendar calendar = new GregorianCalendar(
							Integer.parseInt(year), Integer.parseInt(month),
							Integer.parseInt(day));
					long dateOfBirdh = Long.parseLong(String.valueOf(calendar
							.getTimeInMillis())) / 1000;
					myLicenseExpirydate = "/Date("
							+ String.valueOf(dateOfBirdh) + ")/";
				} else
					myLicenseExpirydate = "/Date(" + System.currentTimeMillis()
							/ 1000 + ")/";

				LinkedHashMap<String, Object> otherData = null;
				if (colvo > 0) {
					// OtherData
					String otherDriverFirstName = details_other_data[0];
					assert (null != otherDriverFirstName);

					String otherDriverSurname = details_other_data[1];
					assert (null != otherDriverSurname);

					String otherDriverAddres = details_other_data[7];
					if (otherDriverAddres.equals(""))
						otherDriverAddres = null;

					int otherDriverPostcode = 0;
					if (!details_other_data[9].equals(""))
						otherDriverPostcode = Integer
								.parseInt(details_other_data[9]);

					String otherDriverPhone = details_other_data[5];
					if (otherDriverPhone.equals("")) {
						otherDriverPhone = null;
					}

					String otherDriverEmailAddres = details_other_data[6];
					if (otherDriverAddres.equals(""))
						otherDriverAddres = null;

					String otherDriverPoliceNumber = null;

					String otherDriverClaimNumber = null;

					String otherDriverVehicleMake = details_other_data[11];
					if (otherDriverVehicleMake.equals(""))
						otherDriverVehicleMake = null;

					String otherDriverVehicleModel = details_other_data[12];
					if (otherDriverVehicleModel.equals(""))
						otherDriverVehicleModel = null;

					String otherVehicleRegistrationNumber = details_other_data[13];
					if (otherVehicleRegistrationNumber.equals(""))
						otherVehicleRegistrationNumber = null;

					String otherDriverLicenseNo = details_other_data[3];
					if (otherDriverLicenseNo.equals(""))
						otherDriverLicenseNo = null;

					String otherDateOfBirth = details_other_data[2];
					if (!otherDateOfBirth.equals("")) {
						int index = otherDateOfBirth.indexOf(" ");
						String year = otherDateOfBirth.substring(0, index);
						String month = otherDateOfBirth.substring(index + 1,
								otherDateOfBirth.lastIndexOf(" "));
						String day = otherDateOfBirth.substring(
								otherDateOfBirth.lastIndexOf(" ") + 1,
								otherDateOfBirth.length());

						Calendar calendar = new GregorianCalendar(
								Integer.parseInt(year),
								Integer.parseInt(month), Integer.parseInt(day));
						long dateOfBirdh = Long.parseLong(String
								.valueOf(calendar.getTimeInMillis())) / 1000;
						otherDateOfBirth = "/Date("
								+ String.valueOf(dateOfBirdh) + ")/";
					} else
						otherDateOfBirth = "/Date("
								+ System.currentTimeMillis() / 1000 + ")/";

					String otherLicenseExpirydate = details[4];
					if (!otherDateOfBirth.equals("")) {
						int index = otherLicenseExpirydate.indexOf(" ");
						String year = otherLicenseExpirydate
								.substring(0, index);
						String month = otherLicenseExpirydate.substring(
								index + 1,
								otherLicenseExpirydate.lastIndexOf(" "));
						String day = otherLicenseExpirydate.substring(
								otherLicenseExpirydate.lastIndexOf(" ") + 1,
								otherLicenseExpirydate.length());

						Calendar calendar = new GregorianCalendar(
								Integer.parseInt(year),
								Integer.parseInt(month), Integer.parseInt(day));
						long dateOfBirdh = Long.parseLong(String
								.valueOf(calendar.getTimeInMillis())) / 1000;
						otherLicenseExpirydate = "/Date("
								+ String.valueOf(dateOfBirdh) + ")/";
					} else
						otherLicenseExpirydate = "/Date("
								+ System.currentTimeMillis() / 1000 + ")/";

					otherData = new LinkedHashMap<String, Object>();
					otherData.put("Id", 0);
					otherData.put("AccidentLodgementDataId", 0);
					otherData.put("ClientId", 0);
					otherData.put("IsOtherParty", true);
					otherData.put("DriverFirstName", otherDriverFirstName);
					otherData.put("DriverSurname", otherDriverSurname);
					otherData.put("DateOfBirth", otherDateOfBirth);
					otherData.put("OwnerFirstName", null);
					otherData.put("OwnerSurname", null);
					otherData.put("Address", otherDriverAddres);
					otherData.put("Postcode", otherDriverPostcode);
					otherData.put("MobilePhone", otherDriverPhone);
					otherData.put("HomeBusinessPhone", null);
					otherData.put("EmailAddress", otherDriverEmailAddres);
					otherData.put("OwnerInsuranceCompany", null);
					otherData.put("PolicyNumber", otherDriverPoliceNumber);
					otherData.put("ClaimNumber", otherDriverClaimNumber);
					otherData.put("VehicleMake", otherDriverVehicleMake);
					otherData.put("VehicleModel", otherDriverVehicleModel);
					otherData.put("VehicleYear", 0);
					otherData.put("VehicleRegistrationNumber",
							otherVehicleRegistrationNumber);
					otherData.put("DriverLicenseNo", otherDriverLicenseNo);
					otherData.put("LicenseExpiryDate", otherLicenseExpirydate);
				}

				int faultTabs = MultipleItemsList.id_tab;
				if (faultTabs == 0)
					faultTabs = 3;
				else if (faultTabs == 1)
					faultTabs = 2;
				else
					faultTabs = 1;
				HttpPost request = new HttpPost(
						"http://test.service.cmt.net.au/ClaimsDataService.svc/InsertAccidentClaim");
				request.setHeader("Content-Type", "application/json");
				request.setHeader("Accept", "application/json");

				LinkedHashMap<String, Object> declarationData = new LinkedHashMap<String, Object>();
				declarationData.put("Name", myDriverFirstName + " "
						+ myDriverSurname);

				declarationData.put("Date", currentTimeStamp);

				SharedPreferences sharedData = getSharedPreferences(
						"MY_SHARED_Admitted", MODE_PRIVATE);
				String accidentAddmittedLiablity = sharedData.getString(
						Lodge_claim.nameFolder, "");
				if (accidentAddmittedLiablity.equals("")) {
					accidentAddmittedLiablity = "Not Sure";
				}

				LinkedHashMap<String, Object> myData = new LinkedHashMap<String, Object>();
				myData.put("Id", 0);
				myData.put("AccidentLodgementDataId", 0);
				myData.put("ClientId", clientId);
				myData.put("IsOtherParty", false);
				myData.put("DriverFirstName", myDriverFirstName);
				myData.put("DriverSurname", myDriverSurname);
				myData.put("DateOfBirth", myDateOfBirth);
				myData.put("OwnerFirstName", null);
				myData.put("OwnerSurname", null);
				myData.put("Address", myDriverAddres);
				myData.put("Postcode", myDriverPostcode);
				myData.put("MobilePhone", myDriverPhone);
				myData.put("HomeBusinessPhone", null);
				myData.put("EmailAddress", myDriverEmailAddres);
				myData.put("OwnerInsuranceCompany", myDriverInsurer);
				myData.put("PolicyNumber", myDriverPoliceNumber);
				myData.put("ClaimNumber", myDriverClaimNumber);
				myData.put("VehicleMake", myDriverVehicleMake);
				myData.put("VehicleModel", myDriverVehicleModel);
				myData.put("VehicleYear", 0);
				myData.put("VehicleRegistrationNumber",
						myVehicleRegistrationNumber);
				myData.put("DriverLicenseNo", myDriverLicenseNo);
				myData.put("LicenseExpiryDate", myLicenseExpirydate);

				LinkedHashMap<String, Object> accidentData = new LinkedHashMap<String, Object>();
				accidentData.put("Id", 0);
				accidentData.put("AccidentLodgementDataId", 0);
				accidentData.put("Date", currentTimeStamp);
				accidentData.put("Time", accidentTime);
				accidentData.put("Suburb", null);
				accidentData.put("Location", accidentLocation);
				accidentData.put("Description", null);
				accidentData.put("WitnessNameAddress", null);
				accidentData.put("NumberOfVehilcesInvolved", colvo);
				accidentData
						.put("AddmitedLiability", accidentAddmittedLiablity);

				JSONObject myDataObject = new JSONObject(myData);

				JSONObject otherDataObject = null;
				if (null != otherData)
					otherDataObject = new JSONObject(otherData);

				JSONObject accidentDataObject = new JSONObject(accidentData);
				JSONObject declarationDataObject = new JSONObject(
						declarationData);

				JSONStringer json = new JSONStringer()
						.object()
						.key("Id")
						.value(0)
						.key("LodgementType")
						.value(faultTabs)
						// Not My fault = 1, My Fault = 2, Not Sure = 0
						.key("ProviderData").value(null)
						.key("PoliceReportData").value(null)
						.key("SmashRepairData").value(null)
						.key("ReplacementVehicleData").value(null)
						.key("MyData").value(myDataObject).key("OtherData")
						.value(otherDataObject).key("AccidentData")
						.value(accidentDataObject).key("DeclarationData")
						.value(declarationDataObject).endObject();

				StringEntity entity = new StringEntity(json.toString(), "UTF-8");
				request.setEntity(entity);

				Log.d("SmartClaim", json.toString());

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
				str_request = sb.toString().substring(0,
						sb.toString().length() - 1);
				Log.d("SmartClaim", str_request);
			} catch (Exception e) {

				res = new Integer(1);

			} finally {

				Log.d("SmartClaim", "Claim Id : " + str_request);

				if (str_request.length() >= 10)
					res = new Integer(1);

				sdPath2 = new File(path_d.toString());
				pathToImage = sdPath2.toString();
				file = sdPath2.listFiles();
				{
					if (file.length > 0) {
						for (int i = 0; i < file.length; i++) {
							if (file[i].toString()
									.substring(file[i].toString().length() - 1)
									.equals("g")) {
								Log.d("SmartCliam", "Start cend i,age Task");
								myTask_send_image = new MyTask_send_image();
								myTask_send_image.execute();
								deleteFiles = false;
								break;

							}
						}
					}
				}

			}
			return res;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			if (deleteFiles == true) {
				delete();
				finish();	
			}

			if (result.intValue() == 0)
				Toast.makeText(getApplicationContext(),
						"Lodge Claim succeeded", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(
						getApplicationContext(),
						"Some error occured while sending data to server. Please try again later",
						Toast.LENGTH_SHORT).show();
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
					Log.d("SmartClaim", sdPath2.toString());
					File[] file_;
					File sdPath22 = new File(pathToImage);
					Log.d("Logn при запуске", sdPath22.toString());
					file_ = sdPath22.listFiles();

					for (int c = 0; c < file_.length; c++) {
						if ((file_[c].toString().substring(
								file_[c].toString().length() - 1).equals("g"))) {
							Log.d("SmartClaim", file_[c].toString());
							baos = new ByteArrayOutputStream();
							bmp = BitmapFactory.decodeFile(file_[c].toString());
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
								bMap = BitmapFactory.decodeByteArray(bMapArray,
										0, bMapArray.length);
								array = new JSONArray();
								for (int i = 0; i < bMapArray.length; i++) {
									array.put(((byte) bMapArray[i]) & 0xFF);
								}
								LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
								obj.put("FileByteStream", array);
								obj.put("AccidentLodgementDataId", new Integer(
										Integer.parseInt(str_request)));
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
										new InputStreamReader(response
												.getEntity().getContent(),
												"windows-1251"));
								StringBuilder sb = new StringBuilder();
								String line = null;

								while ((line = reader.readLine()) != null) {
									sb.append(line
											+ System.getProperty("line.separator"));
								}

								str_request_image = sb.toString();
								str_request_image = str_request_image
										.substring(0,
												str_request_image.length() - 1);
								Log.d("DLINNA",
										String.valueOf(str_request_image
												.length())
												+ "/"
												+ str_request_image + "/");
								if (in != null) {
									in.close();
								}
								if (buf != null) {
									buf.close();
								}

							} catch (IOException e) {
								// TODO Auto-generated catch block
								Log.d("SmartClaim", "DERMO");
								Log.e("SmartClaim", e.toString());
							}
						}
					}
				}

				catch (Exception e) {
					e.printStackTrace();
					Log.e("SmartClaim", e.toString());
				}

			} finally {

				Log.d("!!!", "!!!!!!!ww!");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			delete();
			finish();	

		}
	}

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
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
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
