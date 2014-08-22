package spire.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Integer;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.app.Notification;
import android.app.Service;

public class Id_synch  extends Activity {
	
	private boolean timetoexit=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		try{
		check_client_id(savedInstanceState);
		}
		catch (Exception e){
			return;
		}
	}
	
	@Override
	protected void onResume() {
		
//		SharedPreferences sharedData = getSharedPreferences("MY_DATA",
//				MODE_PRIVATE);
//		String strdata = sharedData.getString("synchdialogdismissed", "");
//		
//		if(strdata.equals("true")){
//			SharedPreferences.Editor editor = sharedData.edit();
//			editor.putString("synchdialogdismissed", "false");
//			editor.commit();
//			this.finish();
//		}
		super.onResume();
		return;
	}
	
	public  void check_client_id(final Bundle bundle) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		// alert.setTitle("Identify user");
		alert.setMessage("Synchronize your CMT App");
		LayoutInflater inflater = getLayoutInflater();
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		View idview = inflater.inflate(R.layout.dialog_clientid, null);
		alert.setView(idview);
		final EditText idbox = (EditText) idview.findViewById(R.id.idBox);
		final EditText pinbox = (EditText) idview.findViewById(R.id.pinBox);

		alert.setPositiveButton("Submit ID",
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

//		alert.setNegativeButton("I don't have and ID",
//				new DialogInterface.OnClickListener() {
//					
//			        @Override
//					public void onClick(DialogInterface dialog, int whichButton) {
//
//					}
//				});

		final AlertDialog dialog = alert.create();
		dialog.show();

		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						Boolean wantToCloseDialog = false;
						// Do stuff, possibly set wantToCloseDialog to true
						// then...
						if (wantToCloseDialog)
							dialog.dismiss();
						else{
							// else dialog stays open. Make sure you have an obvious
							// way to close the dialog especially if you set
							// cancellable to false.
							String idstring = idbox.getText().toString().trim();
							String pinstring = pinbox.getText().toString().trim();
							if (valid_clientdata(idstring, pinstring)) {
								wantToCloseDialog = true;
								ServerLink link = new ServerLink(v, bundle);
								ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
								String idpin = idstring+","+pinstring;
								link.setDialog(dialog);
								link.getClinetObejct(idpin, connMgr); //idstring+pinstring);	
								//dialog.dismiss();
//								try{
//									java.lang.Thread.sleep(2000);
//								}
//								catch (Exception e) {}
//								
							}
							
						}
					}
		
				
				});


		
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialog) {
	        	backtoprofile();
	        	//finish();
	        }
	        });
		
		

		
	
//		dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						dialog.cancel();
//
//						Application_files_explorer ap = new Application_files_explorer(
//								new File(getFilesDir(), "/Your_details"));
//						if (!ap.hasNominatedContacts()) {
//							check_nominated_contact();
//						}
//					}
//				});
		
		
//		Intent intent1 = new Intent();
//		intent1.setClass(this, My_profile.class);
//		startActivity(intent1);

	}

	
	public void backtoprofile() {
		Intent intent1 = new Intent();
		intent1.setClass(this, My_profile.class);
		startActivity(intent1);
   }
	
	public boolean valid_clientdata(String clientid, String clientpin) {
		boolean errs = false;
		String errmsg = "";
		if (clientid.length() < 6) {
			errs = true;
			errmsg = "client id";
		}

		if (clientpin.length() < 4) {
			errmsg = "pin";
			if (errs) {
				errmsg = "client id and pin";
			}
			errs = true;
		}

		if (errs) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Please enter a valid " + errmsg, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		} else
			return true;
	}
	
	public void check_nominated_contact() {

		// Application_files_explorer app_files = new
		// Application_files_explorer();
		// app_files.set_path_string(new File(getFilesDir(), "/Your_details"));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Nominate accident contact");
		builder.setMessage("Would you like someone to contact you if you have an accident?");

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				readFile_info3();

				dialog.dismiss();
				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(),
						Nominated_contacts.class);
				startActivity(intent1);
			}

		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}
	
	void readFile_info3() {
		String str = "";
		File path = new File(getFilesDir(), "/Your_details");
		File sdFile = new File(path, "Nominated_contact.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			Nominated_contact contact = new Nominated_contact();
			while ((str = br.readLine()) != null)

			{
				contact.names_info[qw] = str;
				contact.names_title[qw] = contact.names[qw]
						+ contact.names_info[qw];
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SavePreferences(String key, String value) {

		SharedPreferences sharedPreferences = getSharedPreferences("MY_DATA",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
