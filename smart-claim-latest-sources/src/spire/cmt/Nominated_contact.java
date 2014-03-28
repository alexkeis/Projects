package spire.cmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.*;
import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.ArrayList;

public class Nominated_contact extends Activity {
	DialogFragment dialog_nc;
	DialogFragment dialog_date;
	DialogFragment dialog_dropdown;
	String tit_nc;

	public static int pos_nc;
	int pp_nc;
	public static String title_nc = "Title";
	public static boolean click_state = false;
	public static boolean click_contact_type = false;
	public static boolean click_contact_mode = false;
	private String initial_key = null; 
	private Activity initial_activity;
	
	
	int radio_pos = -1;
	private Email_processor ep = new Email_processor();
	Long date = System.currentTimeMillis()/1000;

	
    //alexkeis
	String[]   country = new String[] {"Australia"};
	String[] state = new String[] { "ACT", "NSW", "NT", "QLD", "SA", "TAC",
			"VIC", "WA" };
	String[] contact_type = {"Smash Repair Center", "Personal Contact (e.g family)", "Other Contact"};
	String[] contact_via = {"Email", "Mobile phone"};
	
	
	public static String[] names = { "*Country: ", "State: ",
			"City: ", "Contact Type: ", "*Name: ",
			"Business Name\n (If applicable): ", "Contact Mode: ", "*Mobile phone number:", "Email: " };
	
	public static String[] names_info = { "", "", "", "", "", "", "", "", ""};
	
	public static String[] names_title = { "*Country: " + names_info[0],
			"State: " + names_info[1], "City: " + names_info[2],
			"Contact Type: " + names_info[3],
			"*Name: " + names_info[4], "Business Name (If applicable): " + names_info[5],
			"Contact mode: " + names_info[6],
			"*Mobile phone number: " + names_info[7], "Email: " + names_info[8]};

	
	public Nominated_contact(final String info[]){		
        this.runOnUiThread(new Runnable() {
            public void run() {
            	setNamesInfo(info);
            }
         });
	}
	
	public Nominated_contact(){
		
	}
	
	public void setNamesInfo(String[] info){
		this.names_info = info;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.nominated_contact);
		dialog_nc = new Dialog_nc();
		dialog_date = new Dialog_date_details();
		
		
		this.readFileInfo();
		
		list();
	}
	
	void readFileInfo() {
		String key =  getStoredNominatedContactKey();
		//String mode =  getStoredNominatedContactMode();
		
		if(key.equals("editing")){
		//if(mode.equals("editing")){
		      return;
		}
		else if (key.equals("firsttime")){		
			for(int i=0; i<names_info.length; i++){
				names_info[i] = "";
			}
			
			for(int i=0; i<names_info.length; i++){
				names_title[i] = names[i] + names_info[i];
			}
		}
		else if (!TextUtils.isEmpty(key)) {
			this.initial_key = key;
			//this.initial_activity = (Activity)this.getCallingActivity();
			
			Application_files_explorer ap = new Application_files_explorer(getFilesDir(), key);
			names_info = ap.getNamesInfoNc();
		
			for(int i=0; i<names_info.length; i++){
				names_title[i] = names[i] + names_info[i];
			}
		}
		
		  SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("key", "editing");
	      editor.putString("originalkey", key);
	      // Commit the edits!
	      editor.commit();
			
		
		
//		String keytext = "";
//		if (!key.equals(null)) {
//			keytext = "_"+key;
//		}
		
//		String str = "";
//		File path = new File(getFilesDir(), "/Your_details");
//		File sdFile = new File(path, "Nominated_contact"+keytext+".txt");
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(sdFile));
//			int qw = 0;
//		
//			while ((str = br.readLine()) != null)
//			{
//				names_info[qw] = str;
//				names_title[qw] = names[qw] + names_info[qw];
//				qw++;
//
//			}
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private String getStoredNominatedContactOriginalKey(){
		SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
	    return settings.getString("originalkey", null);
	}
	
	
	private String getStoredNominatedContactKey(){
		SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
		return settings.getString("key", null);
	}
	
	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_nominated_contact);
		
		names_title[0] = "Country: Australia";
		names_info[0] = "Australia";
		
		// Make the mobile phone the default contact mode
		if(TextUtils.isEmpty(names_info[6])){
			names_info[6] = "Mobile phone";
			names_title[6] = "Contact Mode: Mobile phone";
		}
		
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(R.layout.footer_nominated_contact,
				null);
		lv.addFooterView(footer);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pp_nc = position;
				tit_nc = names_title[position];
				
				if(position == 0){
						Toast toast = Toast.makeText(getApplicationContext(),
								"Currently available in Australia only", Toast.LENGTH_SHORT);
						toast.show();
					
				}
				else if(position == 1){
					for (int i = 0; i < state.length; i++) {
						if (state[i].equals(names_info[1])) {
							radio_pos = i;
						}
					}
					showDialog(2);
				}

				else if(position == 3){
					for (int i = 0; i < contact_type.length; i++) {
						if (contact_type[i].equals(names_info[3])) {
							radio_pos = i;
						}
					}
					showDialog(1);
				}
				else if(position == 6){
					for (int i = 0; i < contact_via.length; i++) {
						if (contact_via[i].equals(names_info[6])) {
							radio_pos = i;
						}
					}
					showDialog(3);
				}
//				else if (position == 10) {
//					for (int i = 0; i < state.length - 1; i++) {
//						if (state[i].equals(names_info[10])) {
//							radio_pos = i;
//						}
//					}
//					showDialog(1);
//				}
				
				else
				{
					dialog();
				}
//				if (position == 2) {
//					dialog_date();
//				} else if (position == 4) {
//					dialog_date();
//				} else if (position == 10) {
//					for (int i = 0; i < state.length - 1; i++) {
//						if (state[i].equals(names_info[10])) {
//							radio_pos = i;
//						}
//					}
//					showDialog(1);
//				} else {
//					dialog();
//				}
			}
		});
	}

	public void ok_footer_nominated_contact(View view) {

		boolean mistakes = chek();
		
		
		if (!mistakes) {
			deleteInitialFile();
			String x = "done";
			
			
			Intent resultIntent = new Intent(getApplicationContext(), Nominated_contact.class);
			resultIntent.putExtra(x,
					"Clicked Screen 2");
					//this.getKey());
			resultIntent.addFlags(resultIntent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			this.setResult(Activity.RESULT_OK, resultIntent);
			//this.onResume();
			
		      SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_SCREEN_VALUES", MODE_PRIVATE);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("done", getKey());

		      // Commit the edits!
		      editor.commit();
			
			
			
			finish();
		}

	}

	

	public void deleteInitialFile(){
		this.initial_key = this.getStoredNominatedContactOriginalKey();
		this.initial_key = this.getStoredNominatedContactOriginalKey();
		if(this.initial_key != null && !this.initial_key.equals(getKey())){
			  SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("deletekey", initial_key);
		      editor.commit();
		}
	}
	
	public String getKey(){
		String key = "";
		
		if (!this.getName().equals("") && !this.getName().equals(null)){
			key += this.getName();
		}
		
		if (!this.getPhone().equals("") && !this.getPhone().equals(null)){
			key += "  |  "+ this.getPhone();
		}
		
		if (!this.getEmail().equals("") && !this.getEmail().equals(null)){
			key += "  |  "+ this.getEmail();
		}
	
		return key;
	}
	
	
	public String getDate(){
		String s = "/Date(" + this.date
				+ ")/";
		return s;
	}
	
	
	public String getType(){
		if(names_info[3].equals("Personal Contact (e.g family)")){
			return "1";
		}
		else if(names_info[3].equals("Smash Repair Center")){
			return "2";
		} 
		else{
			return "3";
		} 
	}
	
	public String getContactVia(){
		
		// decide what to return based on which values are available
		if(names_info[6].equals("Email")){
			return "2";
		}
		else{
			return "1";
		}
			
	}
	
	public String getName(){
		return names_info[4];
		
	}
	
	public String getSurname(){
		return null;
		
	}
	
	public String getEmail(){
		return names_info[8];
		
	}
	
	public String getPhone(){
		return names_info[7];	
	}
	
	public String getCountry(){
		return names_info[0];
		
	}
	
	public String getAddress(){
		return null;
		
	}
	public String getCity(){
		return names_info[2];
		
	}
	public String getState(){
		return names_info[1];
	}
	
	public String getCompany(){
		return names_info[5];
		
	}
	
	
	public ArrayList getCompolsoryFields(){
		//int cf[] = new int[50];
		ArrayList cf = new ArrayList();
		
		//private List<Integer> x = new ArrayList<Integer>();
		int i;
				
		for(i=0; i<names_title.length; i++){
			
			if(names_title[i].startsWith("*")){
				cf.add(i);
			}
		
		}
		return cf;
	}
	
	public boolean chek() {
		boolean mistakes = false;
		ArrayList compulsory_fields = getCompolsoryFields();
		int i;
		int j;
		
		for(i=0; i<compulsory_fields.size(); i++){
			j = (Integer) compulsory_fields.get(i);
			
			if (names_info[j].length() < 1) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Please enter a valid "+names[j],  Toast.LENGTH_SHORT);
				toast.show();
				mistakes = true;
			}
		}
		
//		if (names_info[4].length() < 1) {
//			Toast toast = Toast.makeText(getApplicationContext(),
//					"Please enter a valid contact *name:", Toast.LENGTH_SHORT);
//			toast.show();
//			mistakes = true;
//		}
//		if (names_info[6].length() < 1) {
//			Toast toast = Toast.makeText(getApplicationContext(),
//					"Please enter a valid contact *mobile phone number:", Toast.LENGTH_SHORT);
//			toast.show();
//			mistakes = true;
//		}
		
		if(!mistakes)
		{
			writeFileSD();
		}
		
		return mistakes;
	}

	void writeFileSD() {
		File path = new File(getFilesDir(), "/Your_details");
		path.mkdirs();
		String key = getKey();
		
		File sdFile = new File(path, "Nominated_contact_"+key+".txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			int i = 0;
			for (i = 0; i < names.length - 1; i++) {

				bw.write(names_info[i] + "\n");

			}
			bw.write(names_info[i]+"\n");
			bw.write(this.date+"\n");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		switch (id) {
		case 1:
			
			builder.setTitle("Contact type:");
			builder.setSingleChoiceItems(contact_type, radio_pos, myClickListener);
			//builder.setPositiveButton("Next", myClickListener);
			//builder.setNegativeButton("Prev", myClickListener);
			builder.setNeutralButton("Done", myClickListener);
			// builder.setCancelable(false);
			return builder.create();
		case 2:
			builder.setTitle("State:");
			builder.setSingleChoiceItems(state, radio_pos, myClickListener);
			//builder.setPositiveButton("Next", myClickListener);
			//builder.setNegativeButton("Prev", myClickListener);
			builder.setNeutralButton("Done", myClickListener);
			// builder.setCancelable(false);
			return builder.create();
		case 3:
			builder.setTitle("Contact Mode:");
			builder.setSingleChoiceItems(contact_via, radio_pos, myClickListener);
			//builder.setPositiveButton("Next", myClickListener);
			//builder.setNegativeButton("Prev", myClickListener);
			builder.setNeutralButton("Done", myClickListener);
			// builder.setCancelable(false);
			return builder.create();
			
		default:
			return null;
		}
	}

	android.content.DialogInterface.OnClickListener myClickListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			ListView lv = ((AlertDialog) dialog).getListView();
			if (which == Dialog.BUTTON_POSITIVE
					|| which == Dialog.BUTTON_NEUTRAL) {
				try {
					if(pp_nc == 1){
						names_info[1] = state[lv.getCheckedItemPosition()];
						names_title[1] = names[1] + names_info[1];
					}
					else if(pp_nc == 6){
						int temppos = lv.getCheckedItemPosition();
						names_info[6] = contact_via[temppos];
						
						if(names_info[6].equals("Email")){
							 make_compulsory(8);
							 make_uncompolsory(7);
						}
						else if (names_info[6].equals("Mobile phone")){
							make_compulsory(7);
							make_uncompolsory(8);
							
						}

						names_title[6] = names[6] + names_info[6];
					}
					else 
					{
						names_info[3] = contact_type[lv.getCheckedItemPosition()];
						names_title[3] = names[3] + names_info[3];
					}
					ListView lv2 = (ListView) findViewById(R.id.listView_nominated_contact);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
				} catch (Exception e) {
					names_info[3] = "";
					names_title[3] = names[3] + names_info[3];
					ListView lv2 = (ListView) findViewById(R.id.listView_nominated_contact);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}

			}

			else if (which == Dialog.BUTTON_NEGATIVE) {
				try {

					pp_nc = 2;
					title_nc = names_title[pos_nc];
					names_info[3] = state[lv.getCheckedItemPosition()];
					names_title[3] = names[3] + names_info[3];
					ListView lv2 = (ListView) findViewById(R.id.listView_nominated_contact);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					dialog();

				} catch (Exception e) {
					names_info[3] = "";
					names_title[3] = names[3] + names_info[3];
					ListView lv2 = (ListView) findViewById(R.id.listView_nominated_contact);
					ArrayAdapter<String> adapt = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.list_item,
							names_title);
					lv2.setAdapter(adapt);
					// TODO: handle exception
				}
			}
		}
	};

	public void make_compulsory(int index){
		if(this.names[index].startsWith("*")){	
		}
		else
			this.names[index] = "*"+this.names[index];
		
		this.names_title[index] =  this.names[index] + this.names_info[index];
	}
	
	public void make_uncompolsory(int index){
		if(this.names[index].startsWith("*"))
		{
			this.names[index] = this.names[index].substring(1); 
		}
		this.names_title[index] =  this.names[index] + this.names_info[index];
	}
	
	
	public void dialog() {
		pos_nc = pp_nc;
		title_nc = tit_nc;
		dialog_nc.show(getFragmentManager(), "");
	}

	public void dialog_date() {
		pos_nc = pp_nc;
		title_nc = tit_nc;
		dialog_date.show(getFragmentManager(), "");
	}
	
	public void dialog_dropdown(){
		pos_nc = pp_nc;
		title_nc = tit_nc;
		dialog_dropdown.show(getFragmentManager(), "");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		if (click_contact_type == true) {
			for (int i = 0; i < contact_type.length - 1; i++) {
				if (contact_type[i].equals(names_info[3])) {
					radio_pos = i;
				}
			}
			showDialog(1);
			click_contact_type = false;
		}
		
		if (click_contact_mode == true) {
			for (int i = 0; i < contact_via.length - 1; i++) {
				if (contact_via[i].equals(names_info[6])) {
					radio_pos = i;
				}
			}
			showDialog(3);
			click_contact_mode = false;
		}
		
		if (click_state == true) {
			for (int i = 0; i < state.length - 1; i++) {
				if (state[i].equals(names_info[1])) {
					radio_pos = i;
				}
			}
			showDialog(2);
			click_state = false;
		}
		super.onResume();

	}

}