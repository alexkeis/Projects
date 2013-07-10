package spire.example.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class My_profile extends Activity{
	public static String[] mas_prof = {"Your Details","Your Vehicle"};
	Button im_del;
	 SharedPreferences sPref;
	 final String SAVED_TEXT = "pin";
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_profile); 
	    load();
	    
	    list();
	    readFile_info();
	    readFile_info2();
	}
	public void load()
	{
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
    	String strSavedMem1 = sharedPreferences.getString("MEM1", "");
	    if(strSavedMem1.equals(""))
	    {
		    im_del = (Button) findViewById(R.id.del_pin);    
			im_del.setVisibility(View.GONE);
	    }
	    else
	    {
			im_del = (Button) findViewById(R.id.del_pin);    
			im_del.setVisibility(View.VISIBLE);
	    }
	}

	public void click_pin(View view)
	{
		Intent intent1 = new Intent();
	    intent1.setClass(getApplicationContext(), New_pin.class);
	    startActivity(intent1);
	}
	public void click_del_pin(View view)
	{
		Intent intent1 = new Intent();
	    intent1.setClass(getApplicationContext(), Del_pin.class);
	    startActivity(intent1);
	}
	  public void list()
	  {
		    ListView lv = (ListView)findViewById(R.id.listView_my_profile);
		    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.list_item, mas_prof);
		    lv.setDividerHeight(0);
		    lv.setAdapter(adapt);
		    lv.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		        	if(position==0)
		        	{
						Intent intent1 = new Intent();
					    intent1.setClass(getApplicationContext(), Your_details.class);
					    startActivity(intent1);
		        	}
		        	if(position==1)
		        	{
						Intent intent1 = new Intent();
					    intent1.setClass(getApplicationContext(), Your_vehicle.class);
					    startActivity(intent1);
		        	}
		        }
		      });
	  }
	  
	  void readFile_info() {
		  String str = "";

	    	File path=new File(getFilesDir(),"/Your_details");
	    	File sdFile=new File(path,"My_profile_details.txt");

		    try {
		      BufferedReader br = new BufferedReader(new FileReader(sdFile));
		      int qw = 0;
		      Your_details yr = new Your_details();
		    	  while ((str = br.readLine()) != null) 
		      {		
		    		  yr.names_info[qw]=str;
		    		  yr.names_title[qw]=yr.names[qw]+yr.names_info[qw];
		    		  qw++;
		      }		    	  
		    } catch (FileNotFoundException e) {

		    	e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		  }
	  void readFile_info2() {
		  String str = "";
	    	File path=new File(getFilesDir(),"/Your_details");
	    	File sdFile=new File(path,"My_profile_vehicle.txt");
		    try {
		      BufferedReader br = new BufferedReader(new FileReader(sdFile));
		      int qw = 0;
		      Your_vehicle yv = new Your_vehicle();
		    	  while ((str = br.readLine()) != null) 
		    		
		      {		
		    		  yv.names_info[qw]=str;
		    		  yv.names_title[qw]=yv.names[qw]+yv.names_info[qw];
		    		  qw++;

		    
		      }		    	  
		    } catch (FileNotFoundException e) {

		    	e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		  }
		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			super.onRestart();
			load();
		}

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			load();
		}
}