package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Your_details extends Activity{
	DialogFragment dialog;
	DialogFragment dialog_date;
	 String tit;
	 public static int pos;
	 int pp;
	 public static String title="Title";
	  public static String[] names = {
		  "*First Name: ","*Last Name: ","Date of Birth: ","Driver's License: ",
		  "Xpire Date: ","*Phone: ","Email: ","Street Adress: ","Suburb: ",
		  "Postcode: ","State: "};
	  public static String[] names_info = {"","","","","","","","","","",""};
	  public static String[] names_title = {
		  "*First Name: " + names_info[0],"*Last Name: "+ names_info[1],"Date of Birth: "+ names_info[2],"Driver's License: "+ names_info[3],
		  "Xpire Date: "+ names_info[4],"*Phone: "+ names_info[5],"Email: "+ names_info[6],"Street Adress: "+ names_info[7],"Suburb: "+ names_info[8],
		  "Postcode: "+ names_info[9],"State: "+ names_info[10]};  

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.your_details);
	    dialog = new Dialog_details();
	    dialog_date = new Dialog_date_details();
	    list();
	}
	  public void list()
	  {
		    ListView lv = (ListView)findViewById(R.id.listView_your_details);
		    ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.list_item, names_title);
		    View footer = getLayoutInflater().inflate(R.layout.footer_your_details, null); 
		    lv.addFooterView(footer);
		    lv.setDividerHeight(0);
		    lv.setAdapter(adapt);
		    lv.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		        	pp=position;
	        		tit=names_title[position];
	        		if(position==2 || position == 4)
	        		{
	        			dialog_date();
	        		}
	        		else
	        		{
	        		dialog();   
	        		}
		        }
		      });
	  }
public void ok_footer_your_details(View view)
{
	chek();
}
public void chek()
{
	if(names_info[0].length()<2)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid *First name:", Toast.LENGTH_SHORT);
		toast.show();
	}
	 if(names_info[1].length()<2)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid *Last name:", Toast.LENGTH_SHORT);
		toast.show();
	}
	 if(names_info[7].length()<2)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid *Phone:", Toast.LENGTH_SHORT);
		toast.show();
	}
	else
	{
		writeFileSD();
		finish();
	}
}
void writeFileSD() {
	File path=new File(getFilesDir(),"/Your_details");
	path.mkdirs();
	File sdFile=new File(path,"My_profile_details.txt");
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
		int i=0;
		for(i=0; i<names.length-1; i++){
			 
		bw.write( names_info[i]+"\n");
		Log.d("ssss", i+names_info[i]);
		}
		bw.write( names_info[i]);
		Log.d("ssss", i+names_info[i]);
		bw.close();
		Log.d("ssss", "Р¤Р°Р№Р» Р·Р°РїРёСЃР°РЅ РЅР° SD: " + sdFile.getAbsolutePath());
		} catch (IOException e) {
		e.printStackTrace();
		}
  }
	  public void dialog()
	  {
	  	pos=pp;
	  	title=tit;
	  	dialog.show(getFragmentManager(), "");
	  }
	  public void dialog_date()
	  {
	  	pos=pp;
	  	title=tit;
	  	dialog_date.show(getFragmentManager(), "");
	  }

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			finish();
		}
}