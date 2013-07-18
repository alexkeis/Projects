package spire.example.cmt;

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

public class Dialog_drivers extends DialogFragment implements OnClickListener {

	EditText edit_driver;
	Button yyy;
	String sd;
	Other_drivers ood = new Other_drivers();
	DialogFragment ds;
	public static int click = 0;
	DialogFragment dialog_date;
	final String LOG_TAG = "myLogs";
	public static int rezzz = 0;
	public static String title_n = "";
	static int i = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dialog_date = new Dialog_date_details2();

		if (ood.pos == 1) {

		}
		getDialog().setTitle(ood.names_k[ood.pos + i]);
		View v = inflater.inflate(R.layout.dialog_driver, null);
		yyy = (Button) v.findViewById(R.id.btnYes_d);
		v.findViewById(R.id.btnYes_d).setOnClickListener(this);
		v.findViewById(R.id.btnYes_ok).setOnClickListener(this);
		v.findViewById(R.id.btnNo_d).setOnClickListener(this);
		edit_driver = (EditText) v.findViewById(R.id.editText_drivers);
		edit_driver.setText(ood.names_n[ood.pos + i]);
		if (ood.pos + i == 0) {
			v.findViewById(R.id.btnYes_d).setEnabled(false);
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

		} else if (ood.pos + i == 1) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		} else if (ood.pos + i == 6) {
			edit_driver
					.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		} else if (ood.pos + i == 5 || ood.pos + i == 9) {
			edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
		}
		else if (ood.pos + i == 13) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		}else
			edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);

		ds = new Dialog_drivers();

		return v;

	}

	public void next() {
		i++;
		if (ood.pos + i == 10) {
			Other_drivers.click_state_dialog = true;
			click = 0;
			onDismiss(getDialog());
		}

		else if (ood.pos + i > 13) {
			click = 0;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");
	}

	public void prev() {

		i--;
		Log.d("!!!!!!!!!!!!!!!!!!!!!!!!!", String.valueOf(ood.pos));
		if (ood.pos + i == 10) {
			Other_drivers.click_state_dialog = true;
			click = 0;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");

	}

	public void add_list() {
		ood.names_n[ood.pos + i] = edit_driver.getText().toString();
		ood.names[ood.pos + i] = ood.names_k[ood.pos + i]
				+ ood.names_n[ood.pos + i];
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
			ood.names_n[ood.pos + i] = edit_driver.getText().toString();
			ood.names[ood.pos + i] = ood.names_k[ood.pos + i]
					+ ood.names_n[ood.pos + i];
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
			intent1.setClass(getActivity(), Other_drivers.class);
			startActivity(intent1);
			i = 0;
		}
		if (click == 1) {
			int re = ood.pos + i;
			Log.d(LOG_TAG, "positionpositionpositionposition = " + re);
			if (ood.pos + i == 3) {
				Dialog_date_details2 ddd = new Dialog_date_details2();
				ood.pos = ood.pos + i - 1;
				// ddd.i = ood.pos - 4;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else if (ood.pos + i == 5) {
				Dialog_date_details2 ddd = new Dialog_date_details2();
				ood.pos = ood.pos + i - 1;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else {
				prev();
			}
			click = 0;
		}
		if (click == 2) {
			if (ood.pos + i == 1) {
				Dialog_date_details2 ddd = new Dialog_date_details2();
				ddd.i = ood.pos + i + i;
				dialog_date.show(getFragmentManager(), "");
				i = 0;
				click = 0;
			} else if (ood.pos + i == 3) {
				Dialog_date_details2 ddd = new Dialog_date_details2();
				ddd.i = ood.pos - 2;
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