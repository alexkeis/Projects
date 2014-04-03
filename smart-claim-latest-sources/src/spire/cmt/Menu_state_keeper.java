package spire.cmt;

import android.content.SharedPreferences;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import android.app.Activity;
import android.os.Bundle;

public class Menu_state_keeper  extends Activity{
	
	private boolean menu_called_firsttime;
	private Object init_values;
	private Object final_values;
	
	
	public Menu_state_keeper(Bundle bundle){
		super.onCreate(bundle);
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("callnum", "firstcall");
		
		if(strSavedMem1.equals("firstcall")){
			set_first_time();
		}
		else{
		    set_non_first_time();
		}
		
	}
	
	public void set_non_first_time(){
		this.menu_called_firsttime = false;
	}
	
	public void set_first_time(){
		this.menu_called_firsttime = true;
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("callnum", "firstcall");
		editor.commit();
	}
	
	public void menu_called_again(){
		this.menu_called_firsttime = false;
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("callnum", "nexttime");
		editor.commit();
	}

	public  void set_init_values(Object o){
		this.init_values = o;
		
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		Set s = new HashSet<Object>(Arrays.asList(o));
		editor.putStringSet("initset", s);
		editor.commit();
		
	}
	
	public  void set_final_values(Object o){
		this.final_values = o;
		
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		Set s = new HashSet<Object>(Arrays.asList(o));
		editor.putStringSet("finalset", s);
		editor.commit();
	}

	public boolean make_comparison(){
		//return this.init_values.equals(this.final_values);
		String[] empty = {""};
		Set emtyset = new HashSet<Object>(Arrays.asList(empty));
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",MODE_PRIVATE);
		Set<String> strSavedMem1 = sharedPreferences.getStringSet("initset", emtyset);
		Set<String> strSavedMem2 = sharedPreferences.getStringSet("final", emtyset);
		return strSavedMem1.equals(strSavedMem2);
	}
	
	public boolean is_first_time_called(){
		return this.menu_called_firsttime;
	}
	
	public void notify(String s, int mode){
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", mode);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("Contact_edited", s);
		editor.commit();
	}

}
