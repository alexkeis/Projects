package spire.cmt;

import spire.cmt.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class Dialog_nc extends DialogFragment implements OnClickListener {

	EditText edit_driver;
	Button yyy;
	String sd;
	Nominated_contact your_nc = new Nominated_contact();
	DialogFragment ds;
	static int click = 0;
	
	
	final String LOG_TAG = "myLogs";
	public static int rezzz = 0;
	public static String title_n = "";
	static int i = 0;
	private Context thiscontext;


	

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		thiscontext = getActivity().getApplicationContext();
		getDialog().setTitle(your_nc.names[your_nc.pos_nc + i]);
		View v = inflater.inflate(R.layout.dialog_nc, null);
//		yyy = (Button) v.findViewById(R.id.btnYes_d2);
//		v.findViewById(R.id.btnYes_d2).setOnClickListener(this);
//		v.findViewById(R.id.btnNo_d2).setOnClickListener(this);
		v.findViewById(R.id.buttonYes_d2).setOnClickListener(this);
//		if (your_nc.pos_nc + i == 0) {
//			v.findViewById(R.id.btnYes_d2).setEnabled(false);
//
//		}
		edit_driver = (EditText) v.findViewById(R.id.editText_drivers2);
		edit_driver.setText(your_nc.names_info[your_nc.pos_nc + i]);
		if (your_nc.pos_nc + i == 5 || your_nc.pos_nc + i == 4) {
			edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		} else if (your_nc.pos_nc + i == 7) {
			edit_driver
					.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
		} else if (your_nc.pos_nc + i == 6 ) {
			edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
		} else
			edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);

		
		
		// if(your_v.pos2 + i == 2)
		// edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		ds = new Dialog_nc();
		return v;
	}

	public void next() {
		i++;
		if (your_nc.pos_nc == 2) {
			click = 0;
			Nominated_contact.click_contact_type = true;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");
	}

	public void prev() {

		i--;
		//click = 1;
		
		if (your_nc.pos_nc == 4) {
			click = 0;
			Nominated_contact.click_contact_type = true;
			onDismiss(getDialog());
		} else
			ds.show(getFragmentManager(), "sad");;
	}

	public void add_list() {
		 your_nc.names_info[your_nc.pos_nc + i]=edit_driver.getText().toString();
		// your_v.names_title[your_v.pos2 + i]=your_v.names[your_v.pos2 + i] +
		// your_v.names_info[your_v.pos2 + i];

		your_nc.names_title[your_nc.pos_nc + i] = your_nc.names[your_nc.pos_nc
				+ i]
				+ your_nc.names_info[your_nc.pos_nc + i];
	}

	public void onClick(View v) {

		switch (v.getId()) {
//		case R.id.btnYes_d2: {
//			click = 1;
//		}
//			break;
//		case R.id.btnNo_d2: {
//			add_list();
//			click = 2;
//		}
	//		break;
		case R.id.buttonYes_d2: {
			
			
			your_nc.names_info[your_nc.pos_nc + i] = edit_driver.getText()
					.toString();
			
			if (your_nc.pos_nc + i == 8) {
				Email_processor ep = new Email_processor();
				String email = your_nc.names_info[your_nc.pos_nc + i];
				
				if(!email.equals("") && email != null  && !ep.isValidEmailAddress(email)){
					Toast toast = Toast.makeText(thiscontext,
					//Toast toast = Toast.makeText(getActivity().getApplicationContext(),
							"Please enter a valid email", Toast.LENGTH_SHORT);
					toast.show();
					return;
					
				}
			}
			//else
			
			your_nc.names_title[your_nc.pos_nc + i] = your_nc.names[your_nc.pos_nc
					+ i]
					+ your_nc.names_info[your_nc.pos_nc + i];

			click = 0;
			
//			
//			String y = "done";
//			Intent resultIntent = new Intent();
//			resultIntent.putExtra(y, "Clicked Screen 3...");
//			 //finish();
//			//getActivity().moveTaskToBack(false);
//			getActivity().setResult(Activity.RESULT_OK, resultIntent);
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
			intent1.setClass(getActivity(), Nominated_contact.class);
			startActivity(intent1);
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
