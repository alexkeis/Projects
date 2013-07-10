package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Other_drivers extends Activity {
	final String FILENAME = "file";
	public static final String IMAGES_PATH = "/CMT Cache/images";
	public static final String SIGNS_PATH = "/CMT Cache/signs";
	final String DIR_SD = "MyFiles";
	final String FILENAME_SD = "fileSD.txt";
	Lodge_claim lg = new Lodge_claim();
	public static String title = "Title";
	DialogFragment dialog;
	DialogFragment dialog_date;
	Button btn_other_drivers_clear;
	Button btn_other_drivers_ok;
	final String LOG_TAG = "myLogs";
	public static int pos;
	public static int rezult = 0;
	String tit;
	int pp;
	Other_drivers_vechickes odv = new Other_drivers_vechickes();
	public static boolean rez = false;
	public static String[] mas_name_file = new String[25];
	public static String[] names_k = { "*First Name: ", "*Last Name: ",
			"Date of Birth: ", "Driver's License: ", "Expire Date: ", "Phone: ",
			"Email: ", "Street Adress: ", "Suburb: ", "Postcode: ", "State: ",
			"Make: ", "Model: ", "*Rego: " };
	public static boolean click_state_dialog = false;
	public static String[] names_n = { "", "", "", "", "", "", "", "", "", "",
			"", "", "", "" };
	public static String[] names_123 = { "", "", "", "", "", "", "", "", "",
			"", "", "", "" };
	public static String[] n = new String[12];
	public static String[] names = { "*First Name: " + names_n[0],
			"*Last Name: " + names_n[1], "Date of Birth: " + names_n[2],
			"Driver's License: " + names_n[3], "Expire Date: " + names_n[4],
			"Phone: " + names_n[5], "Email: " + names_n[6],
			"Street Adress: " + names_n[7], "Suburb: " + names_n[8],
			"Postcode: " + names_n[9], "State: " + names_n[10],
			"Make: " + names_n[11], "Model: " + names_n[12],
			"*Rego: " + names_n[13] };
	int radio_pos = -1;

	String[] state = new String[] { "ACT", "NSW", "NT", "QLD", "SA", "TAC",
			"VIC", "WA" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_drivers);
		dialog = new Dialog_drivers();
		dialog_date = new Dialog_date_details2();

		ListView lvMain = (ListView) findViewById(R.id.lvMain);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, names);
		View footer = getLayoutInflater().inflate(
				R.layout.footer_other_drivers, null);
		lvMain.addFooterView(footer);
		lvMain.setDividerHeight(0);
		lvMain.setAdapter(adapter);

		lvMain.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp = position;
				tit = names[position];
				if (position == 2 || position == 4) {
					dialog_date();
				} 
				else if(position == 10){
					for (int i = 0; i < state.length - 1; i++) {
						if (state[i].equals(names_n[10])) {
							radio_pos = i;
						}
					}
					showDialog(1);
				}
				else {
					dialog();
				}
				Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
						+ id);
			}
		});

	}

	public void ok_footer_other(View view) {

		proverka();

	}
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select state:");
			builder.setSingleChoiceItems(state, radio_pos, myClickListener);
			builder.setPositiveButton("Next", myClickListener);
			builder.setNegativeButton("Prev", myClickListener);
			builder.setNeutralButton("Done", myClickListener);
		//	builder.setCancelable(false);
			return builder.create();
		default:
			return null;
		}
	}

	android.content.DialogInterface.OnClickListener myClickListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			ListView lv = ((AlertDialog) dialog).getListView();
			if( which ==Dialog.BUTTON_NEUTRAL){
				try {
					names_n[10] = state[lv.getCheckedItemPosition()];
					names[10] = names_k[10] + names_n[10];
					ListView lv2 = (ListView) findViewById(R.id.lvMain);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names);
					lv2.setAdapter(adapt);
				} catch (Exception e) {
					names_n[10] = "";
					names[10] = names_k[10] + names_n[10];
					ListView lv2 = (ListView) findViewById(R.id.lvMain);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}

			}
			else if (which == Dialog.BUTTON_POSITIVE) {
				try {
					
				
				pp=11;
				pos = pp;
				title=names[pos];
				names_n[10] = state[lv.getCheckedItemPosition()];
				names[10] = names_k[10] + names_n[10];
				ListView lv2 = (ListView) findViewById(R.id.lvMain);
				ArrayAdapter<String> adapt = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.list_item,
						names);
				lv2.setAdapter(adapt);
				dialog();
				Log.d("!!!!!!!!!", "pos = " + lv.getCheckedItemPosition());
				} catch (Exception e) {
					names_n[10] = "";
					names[10] = names_k[10] + names_n[10];
					ListView lv2 = (ListView) findViewById(R.id.lvMain);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}
			}
			else if (which == Dialog.BUTTON_NEGATIVE) {
				try {
					
				
				pp=9;
				title=names[pos];
				names_n[10] = state[lv.getCheckedItemPosition()];
				names[10] = names_k[10] + names_n[10];
				ListView lv2 = (ListView) findViewById(R.id.lvMain);
				ArrayAdapter<String> adapt = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.list_item,
						names);
				lv2.setAdapter(adapt);
				dialog();
				Log.d("!!!!!!!!!", "pos = " + lv.getCheckedItemPosition());
				} catch (Exception e) {
					names_n[10] = "";
					names[10] = names_k[10] + names_n[10];
					ListView lv2 = (ListView) findViewById(R.id.lvMain);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}
			}
		}
	};
	public void clear_footer_other(View view) {
		clear();
		delete_file();
		finish();
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Other_drivers.class);
		startActivity(intent1);

	}

	public void proverka() {
		if (names_n[0] == "") {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Input: *First name", Toast.LENGTH_SHORT);
			toast.show();
		}
		if (names_n[1] == "") {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Input: *Last name", Toast.LENGTH_SHORT);
			toast.show();
		} else {
			writeFile_names();
			writeFileSD();
			clear();
			finish();

		}

	}

	public void clear() {
		for (int i = 0; i < names_n.length; i++) {
			names_n[i] = "";
			names[i] = names_k[i];
		}

	}

	void delete_file() {
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		try {

			File sdFile = new File(path,
					odv.mas_name_file[odv.pos_mas_name].replace(" ", "")
							+ ".txt");
			odv.mas_name_file[odv.pos_mas_name] = "";
			sdFile.delete();
			File sdFile2 = new File(path, "1123.txt");
			try {
				int index = 0;
				BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile2));
				for (int i = 0; i < odv.mas_name_file.length - 1; i++) {
					if (odv.mas_name_file[i].equals("")) {
						index = i;
					}
				}

				for (int i = 0; i < index; i++) {
					bw.write(odv.mas_name_file[i] + "\n");
				}
				for (int b = index; b < odv.mas_name_file.length - 1; b++) {
					odv.mas_name_file[b] = odv.mas_name_file[b + 1];
					bw.write(odv.mas_name_file[b] + "\n");
				}
				lg.colvo = lg.colvo - 1;

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	void writeFileSD() {
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		Log.d("SmartClaim", path.toString());
		path.mkdirs();
		File sdFile = new File(path, names_n[0] + names_n[1] + ".txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			for (int i = 0; i < names.length; i++) {

				bw.write(names_n[i] + "\n");
			}
			bw.close();
			Log.d(LOG_TAG,
					"Р¤Р°Р№Р» Р·Р°РїРёСЃР°РЅ РЅР° SD: "
							+ sdFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void writeFile_names() {
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "1123.txt");
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));

			boolean rez = true;

			for (int i = 0; i < odv.mas_name_file.length; i++) {
				if (odv.mas_name_file[i].equals(names_n[0] + " " + names_n[1])) {
					rez = false;
				}
			}

			if (rez == true) {

				for (int i = 0; i < odv.mas_name_file.length; i++) {
					bw.write(odv.mas_name_file[i] + "\n");
				}
				lg.colvo = odv.mas_name_file.length + 1;
				bw.write(names_n[0] + " " + names_n[1]);
			} else if (rez == false) {
				for (int i = 0; i < odv.mas_name_file.length; i++) {
					bw.write(odv.mas_name_file[i] + "\n");
				}

			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void writeFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					openFileOutput(FILENAME, MODE_PRIVATE)));
			bw.write("РЎРѕРґРµСЂР¶РёРјРѕРµ С„Р°Р№Р»Р°");
			bw.close();
			Log.d(LOG_TAG, "Р¤Р°Р№Р» Р·Р°РїРёСЃР°РЅ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dialog() {
		pos = pp;
		title = tit;
		dialog.show(getFragmentManager(), "");
	}

	public void dialog_date() {
		pos = pp;
		title = tit;
		dialog_date.show(getFragmentManager(), "");
	}

	public void perehod() {

		Intent intent1 = new Intent();
		intent1.setClass(this, Other_drivers.class);
		startActivity(intent1);
	}

	public void other_drivers_vechickes() {
		writeFile();
		writeFileSD();

	}

	public void perehod2() {

		Intent intent1 = new Intent();
		intent1.setClass(this, Other_drivers_vechickes.class);
		startActivity(intent1);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		if (click_state_dialog == true) {
			for (int i = 0; i < state.length - 1; i++) {
				if (state[i].equals(names_n[10])) {
					radio_pos = i;
				}
			}
			showDialog(1);
		}
		click_state_dialog = false;
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}