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
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class View_modify extends Activity{
	CheckedTextView checktext;
	 ActionMode actionMode;
	 final String LOG_TAG = "myLogs";
	 public static File path_d;
		 static String[] names;
		 static String[] names_or;
		 SparseBooleanArray sbArray;
		 ListView lv;
		public static int key;
	 @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_modify);

        checktext = (CheckedTextView) findViewById(R.id.text1);
        start();
    }
	 public void start()
	 {

		 File sdPath = new File(getFilesDir(),"/CMT");
	        File[] file = sdPath.listFiles();
	        try {
	             names = new String[file.length];
	             names_or = new String[names.length];
	            for (int i = 0; i < file.length; i++) {
	            	if(file[i].toString().contains("Your_details")==false){            		
	    			names[i]=file[i].toString();
	            	names[i]=names[i].substring(names[i].lastIndexOf("/")+1);
	            	names_or[i]=names[i];
	            	}
	    		}            
	            for(int b = 0; b<names.length; b++)
	            {
	            	File sdPath2 = new File(getFilesDir(),"/CMT"+"/"+names[b]);
	                  	File sdFile=new File(sdPath2,"local2.txt");
	                	    try {
	                	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	                	      names[b]="Unfinished claim "+names[b]+"\n"+br.readLine();
	                	    } catch (FileNotFoundException e) {
	                	    	names[b]="Unfinished claim "+names[b]+"\n"+"unknown";
	                	    	e.printStackTrace();
	                	    } catch (IOException e) {
	                	    	names[b]="Unfinished claim "+names[b]+"\n"+"unknown";
	                	      e.printStackTrace();
	                	    }
	                	  }
	            
	             lv = (ListView)findViewById(R.id.listView_view_modify);
	            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	R.layout.list_item, names);//dima names
	            adapter.notifyDataSetChanged();
	            lv.setAdapter(adapter);
	            list_click();		
			} catch (Exception e) {
				// TODO: handle exception
			}
	 }
	 public void startdel()
	 {
		 File sdPath = new File(getFilesDir(),"/CMT");
	        File[] file = sdPath.listFiles();
	        try {
	        	
	             names = new String[file.length];
	             names_or = new String[names.length];
	            for (int i = 0; i < file.length; i++) {
	            	if(file[i].toString().contains("Your_details")==false){            		
	    			names[i]=file[i].toString();
	            	names[i]=names[i].substring(names[i].lastIndexOf("/")+1);
	            	names_or[i]=names[i];
	            	}
	    		} 
	            for(int b = 0; b<names.length; b++)
	            {
	            	File sdPath2 = new File(getFilesDir(),"/CMT"+"/"+names[b]);
	                  	File sdFile=new File(sdPath2,"local2.txt");
	                	    try {
	                	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	                	      names[b]="Unfinished claim "+names[b]+"\n"+br.readLine();
	                	    } catch (FileNotFoundException e) {
	                	    	names[b]="Unfinished claim "+names[b]+"\n"+"unknown";
	                	    	e.printStackTrace();
	                	    } catch (IOException e) {
	                	    	names[b]="Unfinished claim "+names[b]+"\n"+"unknown";
	                	      e.printStackTrace();
	                	    }
	                	  }
	            ListView lv = (ListView)findViewById(R.id.listView_view_modify);
	            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	R.layout.viev_modify_new, names);
	            adapter.notifyDataSetChanged();
	            lv.setAdapter(adapter);
			} catch (Exception e) {
				// TODO: handle exception
			}
	 }
	 public void list_click()
	 {
		 lv.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
             	int st = position;
             	path_d=new File(getFilesDir(),"/CMT/"+names_or[st].substring(names_or[st].lastIndexOf("/")+1));
             	Lodge_claim lg = new Lodge_claim();
             	lg.path_d=path_d;
             	lg.nameFolder=names_or[st].substring(names_or[st].lastIndexOf("/")+1);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
            	String strSavedMem1 = sharedPreferences.getString(lg.path_d.toString(), "");
            	Lodge_claim.id = Integer.valueOf(strSavedMem1);
         		Intent intent1 = new Intent();
         	    intent1.setClass(getApplicationContext(), Lodge_claim.class);
         	    startActivity(intent1);
             }
           }); 
	 }
	 public void list_noclick()
	 {
		 lv.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
             }
           }); 
	 }
	 void new_delete()
	 {
		 Lodge_claim lg = new Lodge_claim();
		 sbArray = lv.getCheckedItemPositions();
		 for(int i = 0; i<sbArray.size(); i ++)
		 {
			 key = sbArray.keyAt(i);
			 if (sbArray.get(key))
		      {
				 File sdPath = new File(getFilesDir(),"/CMT/"+names_or[key]);
				 File[] f= sdPath.listFiles();
				 for(int b = 0; b<f.length; b++)
				 {
					 File sdf = new File(f[b].toString());
					 sdf.delete();					 
				 }
				 sdPath.delete();
		      }
		 }
		 startdel();
	 }
	 public void delete()
	 {

	     sbArray = lv.getCheckedItemPositions();
	    for (int i = 0; i < sbArray.size(); i++) {
	       key = sbArray.keyAt(i);
	      if (sbArray.get(key))
	      {
		      if (!Environment.getExternalStorageState().equals(
				        Environment.MEDIA_MOUNTED)) {
		    	  return;
				    }
		      File sdPath = new File(getFilesDir(),"/CMT");
				    try {			    
					    File sdFile = new File(sdPath, names_or[key]);					    
					   sdFile.delete();
				    	}
				    catch (Exception e) {
					}
	    }
	      }
	    startdel(); 
	 }
	 public void list_a()
	 {
         ListView lv = (ListView)findViewById(R.id.listView_view_modify);
         lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	R.layout.list_item, names);
         adapter.notifyDataSetChanged();
         lv.setAdapter(adapter);
	 }
	public boolean onCreateOptionsMenu(Menu menu)
	  {
	    getMenuInflater().inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	  }
		 @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		      // TODO Auto-generated method stub
		      if (actionMode == null)
		          actionMode = startActionMode(callback);
		        else
		          actionMode.finish();
		      list_noclick();
	            ListView lv = (ListView)findViewById(R.id.listView_view_modify);
	            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	R.layout.viev_modify_new, names);
	            adapter.notifyDataSetChanged();
	            lv.setAdapter(adapter);
		      return super.onOptionsItemSelected(item);
		    }
		  private ActionMode.Callback callback = new ActionMode.Callback() {

			    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			      mode.getMenuInflater().inflate(R.menu.context, menu);
			      return true;
			    }
			    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			      return false;
			    }
			    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			      new_delete();
			            return false;
			    }
			  
			    @Override
			    public void onDestroyActionMode(ActionMode mode) {
			      Log.d(LOG_TAG, "destroy");
			      	list_a();
			      	list_click();
			      actionMode = null;
			    }

			  };
				@Override
				protected void onResume() {
					// TODO Auto-generated method stub
					start();
					super.onResume();
				}
}