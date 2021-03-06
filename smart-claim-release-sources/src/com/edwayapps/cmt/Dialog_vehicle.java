package com.edwayapps.cmt;

//
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


public class Dialog_vehicle extends DialogFragment implements OnClickListener {
	
	EditText edit_driver;
	Button yyy;
	String sd;
	Your_vehicle your_v = new Your_vehicle();
	DialogFragment ds;
	 static int click=0;

  final  String LOG_TAG = "myLogs";
  public static int rezzz=0;
  public static String title_n="";
  static int i=0;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

	  getDialog().setTitle(your_v.names[your_v.pos2+i]);
	 View v = inflater.inflate(R.layout.dialog_vehicle, null);
	 yyy=(Button)v.findViewById(R.id.btnYes_d2);
    v.findViewById(R.id.btnYes_d2).setOnClickListener(this);
    v.findViewById(R.id.btnNo_d2).setOnClickListener(this);
    v.findViewById(R.id.buttonYes_d2).setOnClickListener(this);
	  if(your_v.pos2 + i == 0)
	  {
		 v.findViewById(R.id.btnYes_d2).setEnabled(false);
		  
	  }
    edit_driver=(EditText)v.findViewById(R.id.editText_drivers2);
    edit_driver.setText(your_v.names_info[your_v.pos2+i]);
    if(your_v.pos2 + i == 2)
    	edit_driver.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    ds = new Dialog_vehicle();
    return v;
  }
  
public void next()
{
	i++;
	if(your_v.pos2 + i > 2)
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
	your_v.names_info[your_v.pos2 + i]=edit_driver.getText().toString();
	your_v.names_title[your_v.pos2 + i]=your_v.names[your_v.pos2 + i] + your_v.names_info[your_v.pos2 + i];	
}
  public void onClick(View v) {
    
	  switch (v.getId()) {
	case R.id.btnYes_d2:
	{
			click=1;			
	}
		break;
	case R.id.btnNo_d2:
	{	
		add_list();
		click=2;
	}
		break;
	case R.id.buttonYes_d2:
	{		
		your_v.names_info[your_v.pos2 + i]=edit_driver.getText().toString();
		your_v.names_title[your_v.pos2 + i]=your_v.names[your_v.pos2 + i] + your_v.names_info[your_v.pos2 + i];	
	
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
		    intent1.setClass(getActivity(), Your_vehicle.class);
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
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
  }}
