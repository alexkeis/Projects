package spire.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import spire.cmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

public class Lodge_claim_your_details extends Activity {
	DialogFragment dialog;
	DialogFragment dialog_date;
	String tit;
	public static int pos;
	public static String title = "Title";
	int pp;
	public static boolean click_state_lodge_claim = false;
	public static boolean update;
	public static String[] names = { "*First Name: ", "*Last Name: ",
			"Date of Birth: ", "Driver's License: ", "Expiry Date: ",
			"*Phone: ", "Email: ", "Street Adress: ", "Suburb: ", "Postcode: ",
			"State: " };
	public static String[] names_info = { "", "", "", "", "", "", "", "", "",
			"", "" };
	public static String[] names_title = { "*First Name: " + names_info[0],
			"*Last Name: " + names_info[1], "Date of Birth: " + names_info[2],
			"Driver's License: " + names_info[3],
			"Expiry Date: " + names_info[4], "*Phone: " + names_info[5],
			"Email: " + names_info[6], "Street Adress: " + names_info[7],
			"Suburb: " + names_info[8], "Postcode: " + names_info[9],
			"State: " + names_info[10] };
	int radio_pos = -1;
	String[] state = new String[] { "ACT", "NSW", "NT", "QLD", "SA", "TAC",
			"VIC", "WA" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_details);
		dialog = new Dialog_details_lodge_claim();
		dialog_date = new Dialog_date_lodge_claim_details();
		if (update == true) {

			readFile_info();
			update = false;
		}
		list();
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_your_details);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(
				R.layout.footer_lodge_claim_details, null);
		View header = getLayoutInflater().inflate(R.layout.header_your_details,
				null);
		lv.addFooterView(footer);
		lv.addHeaderView(header);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp = position - 1;
				tit = names_title[position - 1];
				if (position == 3) {
					dialog_date();
				} else if (position == 5) {
					dialog_date();
				} else if (position == 11) {
					for (int i = 0; i < state.length - 1; i++) {
						if (state[i].equals(names_info[10])) {
							radio_pos = i;
						}
					}
					showDialog(1);
				} else {
					dialog();
				}

			}
		});
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
			// builder.setCancelable(false);
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
			if (which == Dialog.BUTTON_POSITIVE
					|| which == Dialog.BUTTON_NEUTRAL) {
				try {
					names_info[10] = state[lv.getCheckedItemPosition()];
					names_title[10] = names[10] + names_info[10];
					ListView lv2 = (ListView) findViewById(R.id.listView_your_details);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
				} catch (Exception e) {
					names_info[10] = "";
					names_title[10] = names[10] + names_info[10];
					ListView lv2 = (ListView) findViewById(R.id.listView_your_details);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}

			}

			else if (which == Dialog.BUTTON_NEGATIVE) {
				try {

					pp = 9;
					title = names_title[pos];
					names_info[10] = state[lv.getCheckedItemPosition()];
					names_title[10] = names[10] + names_info[10];
					ListView lv2 = (ListView) findViewById(R.id.listView_your_details);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					dialog();
					Log.d("!!!!!!!!!", "pos = " + lv.getCheckedItemPosition());
				} catch (Exception e) {
					names_info[10] = "";
					names_title[10] = names[10] + names_info[10];
					ListView lv2 = (ListView) findViewById(R.id.listView_your_details);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}
			}
		}
	};

	public void use_header_lidge_claim_your_details(View view) {

		readFile_info_details();
		finish();
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(),
				Lodge_claim_your_details.class);
		startActivity(intent1);

	}

	void readFile_info_details() {
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "My_profile_details.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			for (int i = 0; i < names_info.length; i++) {
				names_info[i] = br.readLine();
				if (names_info[i] == null)
					names_info[i] = "";
				names_title[i] = names[i] + names_info[i];
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void readFile_info() {
		Log.d("!!!!!!!!!!!!!!", "STARTSTARTSTART");
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		String vr_str = "";
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "Lodge_claim_my_profile_details.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			// while ((str = br.readLine()) != null)
			for (int i = 0; i < names_info.length - 1; i++) {
				names_info[qw] = br.readLine();
				if (names_info[qw] == null)
					names_info[qw] = "";
				names_title[qw] = names[qw] + names_info[qw];
				qw++;
				Log.d("!!!!!!!!!!!!!!!!!!", i + names_info[qw]);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void ok_footer_lidge_claim_your_details(View view) {
		if (names_info[0].length() < 1) {
			Toast.makeText(getApplicationContext(), "Input: *First Name",
					Toast.LENGTH_SHORT).show();
		}
		if (names_info[1].length() < 1) {
			Toast.makeText(getApplicationContext(), "Input: *Last Name",
					Toast.LENGTH_SHORT).show();
		}
		if (names_info[5].length() < 3) {
			Toast.makeText(getApplicationContext(), "Input: *Phone",
					Toast.LENGTH_SHORT).show();
		} else {
			writeFileSD();
			finish();
		}
	}

	void writeFileSD() {
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		path.mkdirs();
		File sdFile = new File(path, "Lodge_claim_my_profile_details.txt");
		try {
			// BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(sdFile), "utf8"), 8192);
			int i = 0;
			for (i = 0; i < names_info.length - 1; i++) {

				bw.write(names_info[i] + "\n");
			}
			bw.write(names_info[i]);
			bw.close();
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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		if (click_state_lodge_claim == true) {
			for (int i = 0; i < state.length - 1; i++) {
				if (state[i].equals(names_info[10])) {
					radio_pos = i;
				}
			}
			showDialog(1);
			click_state_lodge_claim = false;
		}
		super.onResume();

	}
}