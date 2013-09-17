package spire.cmt;

import spire.cmt.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Dialog_details extends DialogFragment implements OnClickListener {

	EditText edit_driver;
	Button yyy;
	String sd;
	Your_details yd = new Your_details();
	DialogFragment ds;
	DialogFragment dialog_date;
	public static int click = 0;
	Context context;
	final String LOG_TAG = "myLogs";
	public static int rezzz = 0;
	public static String title_n = "";
	static int i = 0;
	Activity mcontext;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().setTitle(yd.names[yd.pos + i]);
		View v = inflater.inflate(R.layout.dialog_driver, null);
		yyy = (Button) v.findViewById(R.id.btnYes_d);
		v.findViewById(R.id.btnYes_d).setOnClickListener(this);
		v.findViewById(R.id.btnNo_d).setOnClickListener(this);
		v.findViewById(R.id.btnYes_ok).setOnClickListener(this);
		if (yd.pos + i == 0) {
			v.findViewById(R.id.btnYes_d).setEnabled(false);

		}
		edit_driver = (EditText) v.findViewById(R.id.editText_drivers);
		edit_driver.setText(yd.names_info[yd.pos + i]);
		if (yd.pos + i == 0 || yd.pos + i == 1) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		} else if (yd.pos + i == 6) {
			edit_driver
					.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
		} else if (yd.pos + i == 5 || yd.pos + i == 9) {
			edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
		} else
			edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);
		dialog_date = new Dialog_date_details();
		ds = new Dialog_details();

		return v;
	}

	public void next() {
		i++;
		if (yd.pos + i > 9) {
			click = 0;
			Your_details.click_state = true;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");
	}

	public void prev() {

		i--;
		click = 1;
		ds.show(getFragmentManager(), "sad");
	}

	public void add_list() {
		yd.names_info[yd.pos + i] = edit_driver.getText().toString();
		yd.names_title[yd.pos + i] = yd.names[yd.pos + i]
				+ yd.names_info[yd.pos + i];
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnYes_d: {

			click = 1;
		}
			break;
		case R.id.btnNo_d: {
			add_list();
			click = 2;
		}
			break;
		case R.id.btnYes_ok: {
			yd.names_info[yd.pos + i] = edit_driver.getText().toString();
			yd.names_title[yd.pos + i] = yd.names[yd.pos + i]
					+ yd.names_info[yd.pos + i];
			click = 0;

		}
			break;
		default:
			break;
		}
		dismiss();
	}

	public void onDismiss(DialogInterface dialog) {

		if (click == 0) {
			Intent intent1 = new Intent();
			intent1.setClass(getActivity(), Your_details.class);
			startActivity(intent1);
			i = 0;
		}
		if (click == 1) {
			int re = yd.pos + i;
			Log.d(LOG_TAG, "positionpositionpositionposition = " + re);
			if (yd.pos + i == 3) {
				Dialog_date_details ddd = new Dialog_date_details();
				yd.pos = yd.pos + i - 1;
				// ddd.i =(-1)+(i*(-1));// yd.pos - 4;
				// Toast.makeText(getActivity(), String.valueOf(ddd.i),
				// Toast.LENGTH_SHORT).show();
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else if (yd.pos + i == 5) {
				Dialog_date_details ddd = new Dialog_date_details();
				yd.pos = yd.pos + i - 1;
				// ddd.i= (-1)+(i*(-1));
				dialog_date.show(getFragmentManager(), "");
				// Toast.makeText(getActivity(), String.valueOf(ddd.i),
				// Toast.LENGTH_SHORT).show();
				i = 0;
				click = 0;
			} else {
				prev();
			}
			click = 0;
		}
		if (click == 2) {
			if (yd.pos + i == 1) {
				Log.d("Logg", "Pos: " + yd.pos);
				Dialog_date_details ddd = new Dialog_date_details();
				 ddd.i = yd.pos + i + i;
//				yd.pos = yd.pos + i - 1;
				// Toast.makeText(getActivity(), String.valueOf(ddd.i),
				// Toast.LENGTH_SHORT).show();
				Log.d("Logg", "Pos: " + (yd.pos) + i + i);
				dialog_date.show(getFragmentManager(), "");

				i = 0;
				click = 0;
			} else if (yd.pos + i == 3) {
				Log.d("Logg", "Pos: " + yd.pos);
				Dialog_date_details ddd = new Dialog_date_details();
				 ddd.i = yd.pos - 2;
//				yd.pos = yd.pos + i - 1;
				// Toast.makeText(getActivity(), String.valueOf(ddd.i),
				// Toast.LENGTH_SHORT).show();
				Log.d("Logg", "Pos: " + (yd.pos - 2));
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else {
				next();
			}
			click = 0;
		}
		// }
		Log.d(LOG_TAG, "Dialog 1: onDismiss");

	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

}