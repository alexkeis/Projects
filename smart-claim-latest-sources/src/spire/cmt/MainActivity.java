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

public class MainActivity extends Activity implements OnClickListener {
	RelativeLayout.LayoutParams lParams1;
	RelativeLayout.LayoutParams lParams2;
	RelativeLayout.LayoutParams lParams4;
	LinearLayout.LayoutParams lParams3;
	private final int IDD_BUTTONS = 0;
	final int DIALOG_EXIT = 1;
	ImageView more_services;
	RelativeLayout relat_anim;
	RelativeLayout relat_vis;
	Button btn_lets_talk;
	Button btn_center;
	Button btn_right;
	ScrollView scroll;
	Button btn_left;
	TextView tvOut;
	ImageView imageView_circle;
	ImageView imageView_lodge_claim;
	ImageView imageView_banner;
	ImageView imageView_terms;
	ImageView imageView_header1;
	final int DIALOG_ITEMS = 1;
	String data[] = { "Yes", "No" };
	int i = 1;
	int i2 = 1;
	DisplayMetrics metricsB = new DisplayMetrics();
	Context context;

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

	private void begin_service() {
		// alexkeis, app as service stuff
		// Intent main_intent = new Intent(this, CMTIntentService.class);
		// startService(main_intent);

		Thread t = new Thread() {
			public void run() {
				Intent i = new Intent();
				i.setClassName("spire.cmt",
				// "spire.cmt.CMTIntentService" );
						"spire.cmt.CMT_service");
				// bindService( i, null, Context.BIND_AUTO_CREATE);
				startService(i);
			}
		};
		t.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// begin_service();

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// captcha
		SharedPreferences sharedData = getSharedPreferences("MY_DATA",
				MODE_PRIVATE);
		String strdata = sharedData.getString("data", "");

		long currentTs = System.currentTimeMillis() / 1000;

		if (strdata.equals("")) {

			SavePreferences("data", String.valueOf(currentTs));
			// p();

		} else {
			long savedTs = Long.parseLong(strdata);

			if (((currentTs - savedTs) >= 60 * 60 * 24 * 5)) {
				finish();
			}

		}

		Display display = getWindowManager().getDefaultDisplay();
		Log.d("Resolution", "resolution: " + display.getWidth() + " x "
				+ display.getHeight());
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.d("Resolution", "resolution: " + metrics.widthPixels + " x "
				+ metrics.heightPixels);
		if (metrics.widthPixels < 700) {
			setContentView(R.layout.main_470);
		}
		if (metrics.widthPixels > 700 && metrics.widthPixels < 1188) {
			setContentView(R.layout.main_710n);
		}
		if (metrics.widthPixels > 1189) {
			setContentView(R.layout.main_1190);
		}
		Log.d("Proverka", "kto perviyyyyyyyyyyyyyy: ");
		imageView_header1 = (ImageView) findViewById(R.id.imageView_header1);

		Application_files_explorer app_files = new Application_files_explorer();
		app_files.set_path_string(new File(getFilesDir(), "/Your_details"));
		String nc_email_string = app_files.get_nc_email();
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		if (nc_email_string != null && !(app_files.get_nc_email().equals(""))) {
			editor.putString("NC_EMAIL", "yes");
			editor.commit();
		} else {
			editor.putString("NC_EMAIL", "no");
			editor.commit();
		}

		// alexkeis, only prompt for nominated contact if contact email is not
		// set
		String strSavedMem1 = sharedPreferences.getString("NC_EMAIL", "");

		// if (strSavedMem1.equals("no")) {
		// alexkeis, ask whether a user wants to nominated a contact
		if (!is_clientid_present()) {
			check_client_id();
		}

		// if(!ap.hasNominatedContacts()) {
		// check_nominated_contact();
		// }

		btn_center = (Button) findViewById(R.id.button_center);
		btn_center.setOnClickListener(this);
		btn_right = (Button) findViewById(R.id.button_right);
		btn_right.setOnClickListener(this);
		btn_left = (Button) findViewById(R.id.button_left);
		btn_left.setOnClickListener(this);
		btn_lets_talk = (Button) findViewById(R.id.button_top_470);
		btn_lets_talk.setOnClickListener(this);
		relat_anim = (RelativeLayout) findViewById(R.id.trtrtrt);
		relat_anim.setOnClickListener(this);
		relat_vis = (RelativeLayout) findViewById(R.id.relativ_anim);

		// imageView_lodge_claim = (ImageView)
		// findViewById(R.id.imageView_lodge_claim);
		// imageView_lodge_claim.setOnClickListener(this);

		imageView_banner = (ImageView) findViewById(R.id.imageView_banner);
		imageView_banner.setOnClickListener(this);
		imageView_terms = (ImageView) findViewById(R.id.imageView_claim_terms);
		imageView_terms.setOnClickListener(this);
		scroll = (ScrollView) findViewById(R.id.scroll);
		more_services = (ImageView) findViewById(R.id.imageView_more_services2);
		more_services.setOnClickListener(this);
		// lParams1 =(RelativeLayout.LayoutParams)
		// imageView_header1.getLayoutParams();
		// lParams1.height = (metrics.heightPixels*16)/100;
		// lParams1 =(RelativeLayout.LayoutParams)
		// imageView_banner.getLayoutParams();
		// lParams1.height = (metrics.heightPixels*11)/100;
		// lParams3=(LinearLayout.LayoutParams) more_services.getLayoutParams();
		// lParams3.height = (metrics.heightPixels*4)/100;
		// lParams4 =(RelativeLayout.LayoutParams) scroll.getLayoutParams();
		// lParams4.height=(((metrics.heightPixels*91)/100)-((metrics.heightPixels*16)/100)-((metrics.heightPixels*11)/100)-((metrics.heightPixels*4)/100));

		File path = new File(getFilesDir(), "/CMT");
		path.mkdirs();

	}

	public boolean is_clientid_present() {
		try {

			SharedPreferences sharedPreferences = getSharedPreferences(
					"MY_CLIENT", MODE_PRIVATE);
			String strSavedMem1 = sharedPreferences.getString("ID2", "0");
			int tempid = new Integer(strSavedMem1);

			if (tempid != 0)
				return true;
			else
				return false;
			// int id = new Integer(id_client);
		} catch (Exception e) {

		}
		return false;
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

	public void check_client_id() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		// alert.setTitle("Identify user");
		alert.setMessage("Synchronize your CMT app");
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

		alert.setNegativeButton("I don't have and ID",
				new DialogInterface.OnClickListener() {
					
			        @Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

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
								ServerLink link = new ServerLink(v.getContext());
								ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
								String idpin = idstring+","+pinstring;
								link.getClinetObejct(idpin, connMgr); //idstring+pinstring);
								dialog.dismiss();
								
								//My_profile profile = new My_profile();
								//profile.showDetails(v);
							}
							
						}
					}
				});

		dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();

						Application_files_explorer ap = new Application_files_explorer(
								new File(getFilesDir(), "/Your_details"));
						if (!ap.hasNominatedContacts()) {
							check_nominated_contact();
						}
					}
				});

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

	public void cmt_insurance(View view) {
		Web web = new Web();
		web.uri = "http://www.cmt.net.au/Home.aspx";
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Web.class);
		startActivity(intent1);
	}

	public void cmt_bmw(View view) {
		Web web = new Web();
		web.uri = "http://www.bmwcarrental.com.au/index.php";
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Web.class);
		startActivity(intent1);
	}

	public void cmt_mercedes(View view) {
		Web web = new Web();
		web.uri = "http://www.mercedes-benzrentals.com/index.php";
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Web.class);
		startActivity(intent1);
	}

	public void city_car(View view) {
		Web web = new Web();
		web.uri = "http://www.citycarrentals.com.au/";
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Web.class);
		startActivity(intent1);
	}

	public void last_baner(View view) {
		Web web = new Web();
		web.uri = "http://www.bmwondemand.com.au/";
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(), Web.class);
		startActivity(intent1);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case IDD_BUTTONS: {
			builder.setMessage(
					"Would you like to call CMT \n Dial 1300 663 470?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent intent;
									intent = new Intent(Intent.ACTION_DIAL);
									intent.setData(Uri.parse("tel:1300663470"));
									startActivity(intent);
									dialog.cancel();
								}
							})

					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			return builder.create();
		}

		case 1:

		{

			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), My_profile.class);
			startActivity(intent);
			// SharedPreferences sharedPreferences = getSharedPreferences(
			// "MY_CLIENT", MODE_PRIVATE);
			// String strSavedMem1 = sharedPreferences.getString("ID", "");
			// if (strSavedMem1.equals("")) {

			// //
			// builder.setTitle("Profile incomplete");
			// builder.setMessage("Please fill all required fields in your profile");
			// builder.setIcon(android.R.drawable.ic_dialog_info);
			// builder.setNeutralButton("Fill My Profile",
			// new DialogInterface.OnClickListener() {/

			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent();
			// intent.setClass(getApplicationContext(),
			// My_profile.class);
			// startActivity(intent);
			// }
			// });
			// //

			// } else {
			// Call_me_back cmb = new Call_me_back();
			// cmb.id = 1;
			// Intent intent1 = new Intent();
			// intent1.setClass(getApplicationContext(), Call_me_back.class);
			// startActivity(intent1);
			// }

			// //
			// builder.setCancelable(false);
			// return builder.create();
			// //
		}

		case 2:

		{
			// SharedPreferences sharedPreferences = getSharedPreferences(
			// "MY_CLIENT", MODE_PRIVATE);
			// String strSavedMem1 = sharedPreferences.getString("ID", "");
			// if (strSavedMem1.equals("")) {

			// builder.setTitle("Profile incomplete");
			// builder.setMessage("Please fill all required fields in your profile");
			// builder.setIcon(android.R.drawable.ic_dialog_info);
			// builder.setNeutralButton("Fill My Profile",
			// new DialogInterface.OnClickListener() {

			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

			Call_me_back cmb = new Call_me_back();
			cmb.id = 0;
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), Call_me_back.class);
			startActivity(intent1);

			// }
			// });
			// } else {
			// Call_me_back cmb = new Call_me_back();
			// cmb.id = 1;
			// Intent intent1 = new Intent();
			// intent1.setClass(getApplicationContext(), Lodge_claim.class);
			// startActivity(intent1);
			// }
			// builder.setCancelable(false);
			// return builder.create();
		}
		default:
			return null;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView_claim_terms: {
			Intent intent1 = new Intent();
			intent1.setClass(this, Term.class);
			startActivity(intent1);
		}
			break;
		case R.id.imageView_more_services2: {
			switch (i) {
			case 1: {
				relat_vis.setVisibility(View.VISIBLE);

				i = 2;
			}
				break;
			case 2: {
				relat_vis.setVisibility(View.GONE);
				i = 1;
			}
				break;
			default:
				break;
			}
		}
			break;
		case R.id.button_top_470:
			showDialog(0);
			Log.d("Proba", "proba: ");
			break;

		case R.id.imageView_lodge_claim: {
			Lodge_claim lg = new Lodge_claim();
			lg.rez_new_folder = true;

			// showDialog(2);
			SharedPreferences sharedPreferences = getSharedPreferences(
					"MY_CLIENT", MODE_PRIVATE);
			String strSavedMem1 = sharedPreferences.getString("ID", "");
			if (strSavedMem1.equals("")) {
				showDialog(2);
			} else {

				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(), Lodge_claim.class);
				startActivity(intent1);
			}

		}
			break;

		case R.id.button_right: {
			Log.d("Log", "---button_right---");
			Intent intent1 = new Intent();
			intent1.setClass(this, My_profile.class);
			startActivity(intent1);
		}
			break;
		case R.id.button_left: {

			Toast toast = Toast.makeText(getApplicationContext(),
					"Currently unavailable", Toast.LENGTH_SHORT);
			toast.show();

			// Intent intent1 = new Intent();
			// intent1.setClass(this, View_modify.class);
			// startActivity(intent1);
		}
			break;
		case R.id.imageView_banner: {
			showDialog(0);
		}
			break;
		case R.id.button_center: {
			SharedPreferences sharedPreferences = getSharedPreferences(
					"MY_CLIENT", MODE_PRIVATE);
			String strSavedMem1 = sharedPreferences.getString("ID", "");
			if (strSavedMem1.equals("")) {
				showDialog(1);
			} else {
				Call_me_back cmb = new Call_me_back();
				cmb.id = 1;
				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(), Call_me_back.class);
				startActivity(intent1);
			}

		}
			break;

		}
	}

}