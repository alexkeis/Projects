package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.xml.sax.Parser;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Dialog_date_details2 extends DialogFragment implements OnClickListener {
	public DatePicker dpResult;
	public TextView txt;
	DialogFragment dialog_n;
	Button btn_dialog_yes;	
	Button btn_dialog_ok;
	Button btn_dialog_no;
	 int year;
	 int month;
	 int day;
	 int rez=0;
	 int d=0;
	 public static int id_tab=0;
	public static String date_time="";
	final String LOG_TAG = "myLogs";
	Other_drivers ood = new Other_drivers();
	public  static int i=0;
	public String pol;
 
 
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
	  dialog_n = new Dialog_drivers();
	  pol=ood.names[ood.pos+i];
    getDialog().setTitle(pol);
    View v = inflater.inflate(R.layout.dialog_date_drivers, null);
    v.findViewById(R.id.btnYes_date).setOnClickListener(this);
    v.findViewById(R.id.btnNo_date).setOnClickListener(this);
    v.findViewById(R.id.btnOk_date).setOnClickListener(this);
    dpResult = (DatePicker) v.findViewById(R.id.datePicker_date);
    btn_dialog_yes = (Button)  v.findViewById(R.id.btnYes_date);
    btn_dialog_yes.setOnClickListener(this);
    btn_dialog_no = (Button)  v.findViewById(R.id.btnNo_date);
    btn_dialog_no.setOnClickListener(this);
    btn_dialog_ok = (Button)  v.findViewById(R.id.btnOk_date);
    btn_dialog_ok.setOnClickListener(this);
    txt = (TextView) v .findViewById(R.id.proba123);
    return v;
  }
  public void date(){
	  year=dpResult.getYear();
	  month=dpResult.getMonth()+1;
	  day=dpResult.getDayOfMonth();	 
  }
  public void add_list()
  {
	  year=dpResult.getYear();
	  month=dpResult.getMonth()+1;
	  day=dpResult.getDayOfMonth();	 
  	ood.names_n[ood.pos + i]=String.valueOf(year)+ " " +String.valueOf(month)+ " " +String.valueOf(day);
  	ood.names[ood.pos + i]=ood.names_k[ood.pos + i] + ood.names_n[ood.pos + i];	
  }
  public void onClick(View v) {
	  switch (v.getId()) {
	case R.id.btnYes_date:
		rez=1;
		add_list();
		dismiss();
		break;
		case R.id.btnNo_date:
		{
			add_list();
			rez=2;
			dismiss();
		
		}
		break;
		case R.id.btnOk_date:
		{
			  year=dpResult.getYear();
			  month=dpResult.getMonth()+1;
			  day=dpResult.getDayOfMonth();	 
			  	ood.names_n[ood.pos + i]=String.valueOf(year)+ " " +String.valueOf(month)+ " " +String.valueOf(day);
			  	ood.names[ood.pos + i]=ood.names_k[ood.pos + i] + ood.names_n[ood.pos + i];		
			rez=0;
			dismiss();
		}
		break;
		

	default:
		break;
	}

  }

  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    if(rez == 0)
    {
    	Intent intent1 = new Intent();
      intent1.setClass(getActivity(), Other_drivers.class);
      startActivity(intent1);
    	i=0;
    }
    else if(rez==1)
    {
        dialog_n.show(getFragmentManager(), "");
        ood.pos=ood.pos+i-1;
        i=0;
    }
    else if (rez==2){
    dialog_n.show(getFragmentManager(), "");
    ood.pos=ood.pos+i+1;
    i=0;
    }

  }

  
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    Log.d(LOG_TAG, "Dialog 1: onCancel");
  }
}