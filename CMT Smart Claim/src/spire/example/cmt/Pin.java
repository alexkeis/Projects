package spire.example.cmt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Pin extends Activity {
	static String pin;
	static String[] name_pin = { "", "", "", "" };
	static String key = "";
	static int id = -1;
	ImageView im1;
	ImageView im2;
	ImageView im3;
	ImageView im4;
	final int DIALOG_EXIT = 1;

//	private void SavePreferences(String key, String value) {
//		SharedPreferences sharedPreferences = getSharedPreferences("MY_DATA",
//				MODE_PRIVATE);
//		SharedPreferences.Editor editor = sharedPreferences.edit();
//		editor.putString(key, value);
//		editor.commit();
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pin);
		p();
//		SharedPreferences sharedData = getSharedPreferences("MY_DATA",
//				MODE_PRIVATE);
//		String strdata = sharedData.getString("data", "");
//
//		long currentTs = System.currentTimeMillis() / 1000;
//		
//		if (strdata.equals("")) {
//
//			SavePreferences("data", String.valueOf(currentTs));
//			//p();
//
//		} else {
//			long savedTs = Long.parseLong(strdata);
//
//			if( ((currentTs - savedTs) >= 60 * 60 * 24 * 5) ){
//				
//				finish();
//			}
//
//		}

	}

	void p() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"MY_SHARED_PREF", MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("MEM1", "");

		if (strSavedMem1.equals("")) {
			finish();
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent1);
		} else
			pin = strSavedMem1;
		im1 = (ImageView) findViewById(R.id.im1);
		im2 = (ImageView) findViewById(R.id.im2);
		im3 = (ImageView) findViewById(R.id.im3);
		im4 = (ImageView) findViewById(R.id.im4);
	}

	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_EXIT) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle("PIN-Code");
			adb.setMessage("Wrong PIN-code");
			adb.setIcon(android.R.drawable.ic_dialog_info);
			adb.setNeutralButton("Ok", myClickListener);
			return adb.create();
		}
		return super.onCreateDialog(id);
	}

	OnClickListener myClickListener = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case Dialog.BUTTON_POSITIVE:
				finish();
				break;
			case Dialog.BUTTON_NEGATIVE:
				finish();
				break;
			case Dialog.BUTTON_NEUTRAL:
				break;
			}
		}
	};

	public void delete() {
		if (id - 1 > -2) {

			if (id == 0) {
				im1.setVisibility(View.INVISIBLE);
			} else if (id == 1) {
				im2.setVisibility(View.INVISIBLE);
			} else if (id == 2) {
				im3.setVisibility(View.INVISIBLE);
			} else if (id == 3) {
				im4.setVisibility(View.INVISIBLE);
			}
			id--;
		}

	}

	public void image() {
		if (id == 0) {
			im1.setVisibility(View.VISIBLE);
		} else if (id == 1) {
			im2.setVisibility(View.VISIBLE);
		} else if (id == 2) {
			im3.setVisibility(View.VISIBLE);
		} else if (id == 3) {
			im4.setVisibility(View.VISIBLE);
		}
	}

	public void check() {
		image();

		if (id <= 3) {

			name_pin[id] = key;
			if (id == 3) {
				if (pin.equals(name_pin[0] + name_pin[1] + name_pin[2]
						+ name_pin[3])) {
					finish();
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(),
							MainActivity.class);
					startActivity(intent1);
					id = -1;
				} else {
					im1.setVisibility(View.INVISIBLE);
					im2.setVisibility(View.INVISIBLE);
					im3.setVisibility(View.INVISIBLE);
					im4.setVisibility(View.INVISIBLE);
					showDialog(DIALOG_EXIT);
					id = -1;
				}
			}
		} else {
			id = -1;
		}
	}

	public void click1(View view) {
		id++;
		key = "1";
		check();
	}

	public void click2(View view) {
		id++;
		key = "2";
		check();
	}

	public void click3(View view) {
		id++;
		key = "3";
		check();
	}

	public void click4(View view) {
		id++;
		key = "4";
		check();
	}

	public void click5(View view) {
		id++;
		key = "5";
		check();
	}

	public void click6(View view) {
		id++;
		key = "6";
		check();
	}

	public void click7(View view) {
		id++;
		key = "7";
		check();
	}

	public void click8(View view) {
		id++;
		key = "8";
		check();
	}

	public void click9(View view) {
		id++;
		key = "9";
		check();
	}

	public void click0(View view) {
		id++;
		key = "0";
		check();
	}

	public void click_del(View view) {
		delete();
	}
}