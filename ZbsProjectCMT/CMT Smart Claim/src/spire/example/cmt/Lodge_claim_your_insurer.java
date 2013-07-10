package spire.example.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Lodge_claim_your_insurer extends Activity{
	DialogFragment dialog2;
	 String tit2;
	 public static int pos2;
	 public static String title2="Title";
	 int pp2;
	  public static String[] names = {
		  "Insurer: ","Policy Number: ", "Claim Number: "};
	  public static String[] names_info = {"","",""};
	  public static String[] names_title = {
		  "Insurer: " + names_info[0],"Policy Number: "+ names_info[1],"Claim Number: "+ names_info[2]};  
	  public static boolean update;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.your_insurer);
	    dialog2 = new Dialog_insurer_lodge_claim();
	    if(update==true){
	    readFile_info();
	    update=false;
	    }
	    list();
	}
	
	  public void list()
	  {
		    ListView lv = (ListView)findViewById(R.id.listView_your_insurer);
		    ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.list_item, names_title);
		    View footer = getLayoutInflater().inflate(R.layout.footer_your_insurer, null); 
		   lv.addFooterView(footer);
		    lv.setDividerHeight(0);
		    lv.setAdapter(adapt);
		    lv.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {

		        	pp2=position;
	        		tit2=names_title[position];
	        		dialog();
   		    
		       	 
		        }
		      });
	  }
public void ok_footer_your_insurer(View view)
{
	writeFileSD();
	finish();
}
void readFile_info() {
	  String str = "";
	    Lodge_claim lg = new Lodge_claim();
	    String vr_str="";
		File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
	  	File sdFile=new File(path,"My_profile_insurer.txt");
	    try {
	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	      int qw = 0;
	    	  while ((str = br.readLine()) != null) 
	    		
	      {		
	    		  names_info[qw]=str;
	    		  names_title[qw]=names[qw]+names_info[qw];
	    		  qw++;

	    
	      }		    	  
	    } catch (FileNotFoundException e) {

	    	e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	  }
void writeFileSD() {
   if (!Environment.getExternalStorageState().equals(
       Environment.MEDIA_MOUNTED)) {
     return;
   }
   Lodge_claim lg = new Lodge_claim();
	File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
	path.mkdirs();
 	File sdFile=new File(path,"My_profile_insurer.txt");
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
	  	pos2=pp2;
	  	title2=tit2;
	  	dialog2.show(getFragmentManager(), "");
	  }

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			finish();
		}
}