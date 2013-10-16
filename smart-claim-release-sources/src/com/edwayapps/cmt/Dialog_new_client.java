package com.edwayapps.cmt;

//
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

public class Dialog_new_client extends DialogFragment implements
		OnClickListener {

	EditText edit_driver;
	Button yyy;
	String sd;
	Call_me_back yd = new Call_me_back();
	DialogFragment ds;
	public static int click = 0;
	Call_me_back cmd = new Call_me_back();
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
		if (yd.pos + i == 0) {
			v.findViewById(R.id.btnYes_d).setVisibility(View.INVISIBLE);

		}
		edit_driver = (EditText) v.findViewById(R.id.editText_drivers);
		edit_driver.setText(yd.names_info[yd.pos + i]);
		if (yd.pos + i == 3) {
			edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
		} else if (yd.pos + i == 0) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		} else if (yd.pos + i == 1 || yd.pos + i == 2) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		} else
			edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);
		ds = new Dialog_new_client();
		return v;
	}

	public void next() {
		i++;
		if (yd.pos + i > 3) {
			click = 0;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "");
	}

	public void prev() {

		i--;

		ds.show(getFragmentManager(), "");
	}

	public void add_list() {
		yd.names_info[yd.pos + i] = edit_driver.getText().toString();
		yd.names_title[yd.pos + i] = yd.names[yd.pos + i]
				+ yd.names_info[yd.pos + i];
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes_d: {
			add_list();
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
			intent1.setClass(getActivity(), Call_me_back.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent1);
			cmd.id = 0;
			i = 0;
		}
		if (click == 1) {
			prev();
			click = 0;
		}
		if (click == 2) {
			next();
			click = 0;
		}

	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);

	}
}