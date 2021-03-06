package spire.example.cmt;

import spire.example.cmt.R;
import android.app.DialogFragment;
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
import android.widget.Button;
import android.widget.Toast;

public class Dialog_details_lodge_claim extends DialogFragment implements
		OnClickListener {

	EditText edit_driver;
	Button yyy;
	String sd;
	Lodge_claim_your_details yd = new Lodge_claim_your_details();
	DialogFragment ds;
	DialogFragment dialog_date;
	public static int click = 0;

	final String LOG_TAG = "myLogs";
	public static int rezzz = 0;
	public static String title_n = "";
	static int i = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getDialog().setTitle(yd.names[yd.pos + i]);
		View v = inflater.inflate(R.layout.dialog_driver, null);
		yyy = (Button) v.findViewById(R.id.btnYes_d);
		v.findViewById(R.id.btnYes_d).setOnClickListener(this);
		v.findViewById(R.id.btnNo_d).setOnClickListener(this);
		v.findViewById(R.id.btnYes_ok).setOnClickListener(this);
		edit_driver = (EditText) v.findViewById(R.id.editText_drivers);
		edit_driver.setText(yd.names_info[yd.pos + i]);
		if (yd.pos + i == 0) {
			v.findViewById(R.id.btnYes_d).setEnabled(false);
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		}
		else if (yd.pos + i == 1 ) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		} 
		else if (yd.pos + i == 5 || yd.pos + i == 9) {
			edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
		}
		else if (yd.pos + i == 6 ) {
			edit_driver.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		} else
			edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);
		ds = new Dialog_details_lodge_claim();
		dialog_date = new Dialog_date_lodge_claim_details();
		return v;
	}

	public void next() {
		i++;
		if (yd.pos + i > 9) {
			Lodge_claim_your_details.click_state_lodge_claim = true;
			click = 0;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");
	}

	public void prev() {
		add_list();
		i--;

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
		default:
			break;
		}
		dismiss();
	}

	public void onDismiss(DialogInterface dialog) {
		if (click == 0) {
			Intent intent1 = new Intent();
			intent1.setClass(getActivity(), Lodge_claim_your_details.class);
			startActivity(intent1);
			i = 0;
		}
		if (click == 1) {
			int re = yd.pos + i;
			Log.d(LOG_TAG, "positionpositionpositionposition = " + re);
			if (yd.pos + i == 3) {
				Dialog_date_lodge_claim_details ddd = new Dialog_date_lodge_claim_details();
//				ddd.i = yd.pos - 4;
				yd.pos=yd.pos+i-1;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else if (yd.pos + i == 5) {
				Dialog_date_lodge_claim_details ddd = new Dialog_date_lodge_claim_details();
				yd.pos=yd.pos+i-1;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else {
				prev();
			}
			click = 0;
		}
		if (click == 2) {
			if (yd.pos + i == 1) {
				Dialog_date_lodge_claim_details ddd = new Dialog_date_lodge_claim_details();
				ddd.i = yd.pos + i + i;
//				yd.pos=yd.pos+i-1;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else if (yd.pos + i == 3) {
				Dialog_date_lodge_claim_details ddd = new Dialog_date_lodge_claim_details();
				ddd.i = yd.pos - 2;
//				yd.pos=yd.pos+i-1;
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
		Log.d(LOG_TAG, "Dialog 1: onCancel");
	}
}