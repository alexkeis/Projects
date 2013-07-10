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


public class Dialog_existing_client extends DialogFragment implements OnClickListener {
	
	EditText edit_driver;
	Button yyy;
	String sd;
	Call_me_back yd = new Call_me_back();
	DialogFragment ds;
  final  String LOG_TAG = "myLogs";
  public static int rezzz=0;
  public static String title_n="";
  static int i=0;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

	  getDialog().setTitle(yd.names2[yd.pos+i]);
	 View v = inflater.inflate(R.layout.dialog_existing_client, null);
	 yyy=(Button)v.findViewById(R.id.btnYes_client);
    v.findViewById(R.id.btnYes_client).setOnClickListener(this);

	  if(yd.pos + i == 0)

    edit_driver=(EditText)v.findViewById(R.id.editText_drivers);
    edit_driver.setText(yd.names_info2[yd.pos+i]);

    	edit_driver.setInputType(InputType.TYPE_CLASS_NUMBER);

    ds = new Dialog_existing_client();
    return v;
  }
  


  public void onClick(View v) {  
	  switch (v.getId()) {

	case R.id.btnYes_client:
	{
		yd.names_info2[yd.pos + i]=edit_driver.getText().toString();
		yd.names_title2[yd.pos + i]=yd.names2[yd.pos + i] + yd.names_info2[yd.pos + i];	

	
	}
	break;
	default:
		break;
	  }
    dismiss();
  }

  public void onDismiss(DialogInterface dialog) {
	  	
			Intent intent1 = new Intent();
		    intent1.setClass(getActivity(), Call_me_back.class);
		    intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    startActivity(intent1);
		    yd.id=1;

	  	
    Log.d(LOG_TAG, "Dialog 1: onDismiss");
    
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);

  }
}