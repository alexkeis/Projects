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


public class Dialog_insurer_lodge_claim extends DialogFragment implements OnClickListener {
	
	EditText edit_driver;
	Button yyy;
	String sd;
	Lodge_claim_your_insurer yd = new Lodge_claim_your_insurer();
	DialogFragment ds;
	public static int click=0;

  final  String LOG_TAG = "myLogs";
  public static int rezzz=0;
  public static String title_n="";
  static int i=0;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

	  getDialog().setTitle(yd.names[yd.pos2+i]);
	 View v = inflater.inflate(R.layout.dialog_driver, null);
	 yyy=(Button)v.findViewById(R.id.btnYes_d);
    v.findViewById(R.id.btnYes_d).setOnClickListener(this);
    v.findViewById(R.id.btnNo_d).setOnClickListener(this);
    v.findViewById(R.id.btnYes_ok).setOnClickListener(this);
	  if(yd.pos2 + i == 0)
	  {
		 v.findViewById(R.id.btnYes_d).setEnabled(false);
		  
	  }
    edit_driver=(EditText)v.findViewById(R.id.editText_drivers);
    if(yd.pos2 + i==2)
    {
    	edit_driver.setInputType(InputType.TYPE_CLASS_PHONE);
    }
    else
    	edit_driver.setInputType(InputType.TYPE_CLASS_TEXT);
    edit_driver.setText(yd.names_info[yd.pos2+i]);
    ds = new Dialog_insurer_lodge_claim();
    return v;
  }
  
public void next()
{
	i++;
	if(yd.pos2 + i > 2)
	{
		click=0;
		onDismiss(getDialog());		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	else
		ds.show(getFragmentManager(), "sad");
}
public void prev(){

	i--;
	
	ds.show(getFragmentManager(), "sad");	
}
public void add_list()
{
	yd.names_info[yd.pos2 + i]=edit_driver.getText().toString();
	yd.names_title[yd.pos2 + i]=yd.names[yd.pos2 + i] + yd.names_info[yd.pos2 + i];	
}
  public void onClick(View v) {
    
	  switch (v.getId()) {
	case R.id.btnYes_d:
	{
		
			click=1;			
	}
		break;
	case R.id.btnNo_d:
	{	
		add_list();
		click=2;
	}
		break;
	case R.id.btnYes_ok:
	{	
		yd.names_info[yd.pos2 + i]=edit_driver.getText().toString();
		yd.names_title[yd.pos2 + i]=yd.names[yd.pos2 + i] + yd.names_info[yd.pos2 + i];	
		
		click=0;
	}
		break;
	default:
		break;
	  }
    dismiss();
  }

  public void onDismiss(DialogInterface dialog) {
	  if(click==0)
	  {
			Intent intent1 = new Intent();
		    intent1.setClass(getActivity(), Lodge_claim_your_insurer.class);
		    startActivity(intent1);
		    i=0;
	  }
	  if(click==1)
	  {
		  prev();
		  click=0;
	  }
	  if(click==2)
	  {
		  next();
		  click=0;
	  }
	  	
    Log.d(LOG_TAG, "Dialog 1: onDismiss");
    
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
  }
}