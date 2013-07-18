package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Dialog1 extends DialogFragment implements OnClickListener {
	public DatePicker dpResult;
	public TimePicker dpTime;
	public TextView txt;
	Button yyy;
	 int year;
	 int month;
	 int day;
	 int hour;
	 int minute;
	 int d=0;
	 public static int id_tab=0;
	public static String date_time="";
  final String LOG_TAG = "myLogs";

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    getDialog().setTitle("Accident Time:");
    View v = inflater.inflate(R.layout.dialog1, null);
    v.findViewById(R.id.btnYes).setOnClickListener(this);
    v.findViewById(R.id.btnNo).setOnClickListener(this);
    dpResult = (DatePicker) v.findViewById(R.id.datePicker1);
    dpTime = (TimePicker) v.findViewById(R.id.timePicker1);
    txt = (TextView) v .findViewById(R.id.proba123);
    return v;

  }
  public void date(){
	  year=dpResult.getYear();
	  month=dpResult.getMonth()+1;
	  day=dpResult.getDayOfMonth();
	  hour=dpTime.getCurrentHour();
	  minute=dpTime.getCurrentMinute();
	  
	  String hoursString = String.format("%02d", hour);
	  String minutesString = String.format("%02d", minute);
	  
	  txt.setText(year + "." + month +"." + day + " " + hoursString + ":" + minutesString);
	 date_time=txt.getText().toString();
	 Lodge_claim lg = new Lodge_claim();
	 lg.names_znach[0]=date_time;

	    File sdFile = new File(lg.path_d, "Date_time.txt");
	    try {
	      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile)); 
	      
	      bw.write(year + "." + month +"." + day + " " + hoursString + ":" + minutesString);
	      
	      bw.close();
	      Log.d(LOG_TAG, "Файл записан на SD: " + lg.path_d.toString());
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
  }

  public void onClick(View v) {
	  switch (v.getId()) {
	case R.id.btnYes:
	    date();
	    dismiss();
		break;
		case R.id.btnNo:
		{
			dismiss();
		}
		break;

	default:
		break;
	}

  }

  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
	Intent intent1 = new Intent();
//	intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    intent1.setClass(getActivity(), Lodge_claim.class);
    startActivity(intent1);
    Lodge_claim lg = new Lodge_claim();
    if(id_tab==0)
    lg.id=0;
    if(id_tab==1)
    lg.id=1;
    if(id_tab==2)
    lg.id=2;
    lg.rez_time=false;
  }

  
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
  }
}