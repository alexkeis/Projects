package spire.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import spire.cmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Nominated_contact extends Activity {
	DialogFragment dialog;
	DialogFragment dialog_date;
	String tit;

	public static int pos;
	int pp;
	public static String title = "Title";
	public static boolean click_state = false;
	int radio_pos = -1;

	
    //alexkeis
	String[] country = new String[] {"Australia"};
	String[] state = new String[] { "ACT", "NSW", "NT", "QLD", "SA", "TAC",
			"VIC", "WA" };
	
	
	public static String[] names = { "*Country: ", "State: ",
			"City: ", "Contact Type: ", "Name: ",
			"Business Name (If applicable): ", "Mobile phone number:", "Email: " };
	
	public static String[] names_info = { "Australia", "", "", "", "", "", "", ""};
	
	public static String[] names_title = { "*Country: " + names_info[0],
			"State: " + names_info[1], "City: " + names_info[2],
			"Contact Type: " + names_info[3],
			"Name: " + names_info[4], "Business Name (If applicable): " + names_info[5],
			"Mobile phone number: " + names_info[6], "Email: " + names_info[7]};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.your_details);
		//dialog = new Dialog_details();
		//dialog_date = new Dialog_date_details();
		list();
	}

	
	
	
	
	
	
	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_nominated_contact);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(R.layout.footer_nominated_contact,
				null);
		lv.addFooterView(footer);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pp = position;
				tit = names_title[position];
//				if (position == 2) {
//					dialog_date();
//				} else if (position == 4) {
//					dialog_date();
//				} else if (position == 10) {
//					for (int i = 0; i < state.length - 1; i++) {
//						if (state[i].equals(names_info[10])) {
//							radio_pos = i;
//						}
//					}
//					showDialog(1);
//				} else {
//					dialog();
//				}
			}
		});
	}

	public void ok_footer_nominated_contact(View view) {
		chek();
	}

	public void chek() {
		if (names_info[0].length() < 1) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Please enter a valid *First name:", Toast.LENGTH_SHORT);
			toast.show();
		}
		{
			writeFileSD();
			finish();
		}
	}

	void writeFileSD() {
		File path = new File(getFilesDir(), "/Nominated_contact");
		path.mkdirs();
		File sdFile = new File(path, "Nominated_contacts.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
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

		if (click_state == true) {
			for (int i = 0; i < state.length - 1; i++) {
				if (state[i].equals(names_info[10])) {
					radio_pos = i;
				}
			}
			showDialog(1);
			click_state = false;
		}
		super.onResume();

	}

}