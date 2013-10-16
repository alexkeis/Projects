package com.edwayapps.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

//

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Lodge_claim_your_vehicle extends Activity {
	DialogFragment dialog2;
	String tit2;
	public static int pos2;
	public static String title2 = "Title";
	int pp2;
	public static String[] names = { "Make ", "Model ", "*Rego " };
	public static String[] names_info = { "", "", "" };
	public static String[] names_title = { "Make " + names_info[0],
			"Model " + names_info[1], "*Rego " + names_info[2] };
	public static boolean update;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_vechicle);
		dialog2 = new Dialog_vehicle_lodge_claim();
		if (update == true) {
			readFile_info();
			update = false;
		}
		list();
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_your_vechicle);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(R.layout.footer_your_vehicle,
				null);
		View header = getLayoutInflater()
				.inflate(R.layout.header_vehicle, null);
		lv.addHeaderView(header);
		lv.addFooterView(footer);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp2 = position - 1;
				tit2 = names_title[position - 1];
				dialog();
			}
		});
	}

	public void button_use_my_proile_vehicle(View view) {
		readFile_info_details();
		finish();
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(),
				Lodge_claim_your_vehicle.class);
		startActivity(intent1);
	}

	void readFile_info_details() {
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			for (int i = 0; i < names.length; i++) {
				names_info[i] = br.readLine();
				names_title[i] = names[i] + names_info[i];
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Lodge_claim lg = new Lodge_claim();
	// File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
	// path.mkdirs();
	// File sdFile=new File(path,"Lodge_claim_my_profile_details.txt");
	void readFile_info() {
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "My_vehicle.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null) {
				names_info[qw] = str;
				names_title[qw] = names[qw] + names_info[qw];

				Log.d("!!!!!!!!", names_info[qw]);
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void ok_footer_your_vehicle(View view) {
		if (names_info[2].length() < 3) {
			Toast.makeText(getApplicationContext(), "Input: *Rego",
					Toast.LENGTH_SHORT).show();
		} else {
			writeFileSD();
			finish();
		}
	}

	void writeFileSD() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "My_vehicle.txt");
		Log.d("!!!!!!!!!!!!", sdFile.toString());
		try {
			// BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(sdFile), "utf8"), 8192);
			int i = 0;
			for (i = 0; i < names.length - 1; i++) {

				bw.write(names_info[i] + "\n");
			}
			bw.write(names_info[i]);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dialog() {
		pos2 = pp2;
		title2 = tit2;
		dialog2.show(getFragmentManager(), "");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}
