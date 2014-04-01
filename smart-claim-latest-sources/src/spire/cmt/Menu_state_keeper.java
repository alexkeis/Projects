package spire.cmt;

import android.content.SharedPreferences;
import android.app.Activity;

public class Menu_state_keeper{
	
	private boolean menu_called_firsttime;
	private Object init_values;
	private Object final_values;
	
	
	public Menu_state_keeper(){
		//super.onCreate(null);
		set_first_time();
		//this.notify("false", MODE_PRIVATE);
	}
	
	public void set_first_time(){
		this.menu_called_firsttime = true;
	}
	
	public void menu_called_again(){
		this.menu_called_firsttime = false;
	}

	public  void set_init_values(Object o){
		this.init_values = o;
	}
	
	public  void set_final_values(Object o){
		this.final_values = o;
	}

	public boolean make_comparison(){
		return this.init_values.equals(this.final_values);
	}
	
	public boolean is_first_time_called(){
		return this.menu_called_firsttime;
	}
	
	public void notify(String s, int mode){
		//SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", mode);
		//SharedPreferences.Editor editor = sharedPreferences.edit();
		//editor.putString("Contact_edited", s);
		//editor.commit();
	}

}
