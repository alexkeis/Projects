package spire.example.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lodge_claim_your_details extends Activity{
	DialogFragment dialog;
	DialogFragment dialog_date;
	 String tit;
	 public static int pos;
	 public static String title="Title";
	 int pp;
	 public static boolean update;
	  public static String[] names = {
		  "*First Name: ","*Last Name: ","Date of Birth: ","Driver's License: ",
		  "Xpire Date: ","Phone: ","Email: ","Street Adress: ","Suburb: ",
		  "Postcode: ", "State: "};
	  public static String[] names_info = {"","","","","","","","","","",""};
	  public static String[] names_title = {
		  "*First Name: " + names_info[0],"*Last Name: "+ names_info[1],"Date of Birth: "+ names_info[2],"Driver's License: "+ names_info[3],
		  "Xpire Date: "+ names_info[4],"Phone: "+ names_info[5],"Email: "+ names_info[6],"Street Adress: "+ names_info[7],"Suburb: "+ names_info[8],
		  "Postcode: "+ names_info[9],"State: "+ names_info[10]};  

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.your_details);
	    dialog = new Dialog_details_lodge_claim();
	    dialog_date = new Dialog_date_lodge_claim_details();
	    if(update==true){

	    readFile_info();
	    update=false;
	    }
	    list();
	}
	
	  public void list()
	  {
		    ListView lv = (ListView)findViewById(R.id.listView_your_details);
		    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.list_item, names_title);
		    View footer = getLayoutInflater().inflate(R.layout.footer_lodge_claim_details, null); 
		    View header = getLayoutInflater().inflate(R.layout.header_your_details, null); 
		    lv.addFooterView(footer);
		    lv.addHeaderView(header);
		    lv.setDividerHeight(0);
		    lv.setAdapter(adapt);
		    lv.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {

		        	pp=position-1;
	        		tit=names_title[position-1];
	        		if(position-1==2 || position-1 == 4)
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
	  public void use_header_lidge_claim_your_details(View view)
	  {

		  readFile_info_details();
		  finish();
			Intent intent1 = new Intent();
		    intent1.setClass(getApplicationContext(), Lodge_claim_your_details.class);
		    startActivity(intent1);
		  
	  }
	    void readFile_info_details() {
	    	File path=new File(getFilesDir(),"/Your_details");
	    	File sdFile=new File(path,"My_profile_details.txt");
	    	    try {
	    	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	    	      for(int i = 0 ; i<11 ; i++)
	    	      {
	    	    	  names_info[i]=br.readLine();
	    	      names_title[i]=names[i]+names_info[i];
	    	      }

	    	    } catch (FileNotFoundException e) {
	    	    	e.printStackTrace();
	    	    } catch (IOException e) {
	    	      e.printStackTrace();
	    	    }

	    	  }

		  void readFile_info() {
			  Log.d("!!!!!!!!!!!!!!", "STARTSTARTSTART");
			  String str = "";
			    Lodge_claim lg = new Lodge_claim();
			    String vr_str="";
				File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
				path.mkdirs();
			  	File sdFile=new File(path,"Lodge_claim_my_profile_details.txt");
			    try {
			      BufferedReader br = new BufferedReader(new FileReader(sdFile));
			      int qw = 0;
			    	 // while ((str = br.readLine()) != null) 
			      for(int i=0; i<names_info.length-1; i++)
			      {		
			    		  names_info[qw]=br.readLine();
			    		  names_title[qw]=names[qw]+names_info[qw];
			    		  qw++;
			    		  Log.d("!!!!!!!!!!!!!!!!!!", i+names_info[qw]);
			      }		    	  
			    } catch (FileNotFoundException e) {

			    	e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }

			  }
public void ok_footer_lidge_claim_your_details(View view)
{
	writeFileSD();
	finish();
}
void writeFileSD() {
    Lodge_claim lg = new Lodge_claim();
	File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
	path.mkdirs();
  	File sdFile=new File(path,"Lodge_claim_my_profile_details.txt");
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
      int i=0;
      for(i=0; i<names.length-1; i++){
    	 
      bw.write( names_info[i]+"\n");
      }
      bw.write( names_info[i]);
      bw.close();
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