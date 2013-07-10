package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class Dialog2 extends DialogFragment implements OnClickListener {
   Integer rez=0;
   public static int id_tab=0;
   public static String location="";
  final String LOG_TAG = "myLogs";
  GoogleMapsActivity gg = new GoogleMapsActivity();
  public static EditText edit;
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    getDialog().setTitle("Accident Location:");
    View v = inflater.inflate(R.layout.dialog2, null);
    v.findViewById(R.id.btnYes).setOnClickListener(this);
    v.findViewById(R.id.btnNo).setOnClickListener(this);
    edit = (EditText) v.findViewById(R.id.editText_maps);
    
    return v;
  }
  public void onClick(View v) {
	  switch (v.getId()) {
	case R.id.btnYes:
	{
		Lodge_claim lg = new Lodge_claim();
		if(edit.getText().toString().length()<1)
		{
			edit.setText(" ");
		}
		gg.local_n=edit.getText().toString();
		
	    File sdFile = new File(lg.path_d,"local2.txt");
	    try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			bw.write(edit.getText().toString());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		Intent intent1 = new Intent();
	    intent1.setClass(getActivity(), Lodge_claim.class);
	    startActivity(intent1);
	    lg.rez_time=true;

	}
		break;
	case R.id.btnNo:
		
		break;

	default:
		break;
	}
 		

    dismiss();
  }

  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Log.d(LOG_TAG, "Dialog 1: onDismiss");
    Lodge_claim lg = new Lodge_claim();
    if(id_tab==0)
    lg.id=0;
    if(id_tab==1)
    lg.id=1;
    if(id_tab==2)
    lg.id=2;   
    lg.rez_time=true;
    
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
  }
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		edit.setText(location);
	}
}