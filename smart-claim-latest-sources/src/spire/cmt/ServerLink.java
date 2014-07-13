package spire.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.*;

import javax.json.*;

import org.apache.commons.codec.binary.Base64;

import spire.cmt.My_profile.MyTask_show;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.os.Bundle;

import java.util.Arrays;

public class ServerLink extends Activity{

	public ProgressDialog progressDialog;
	private String data = null;
	public String result = null;
	public Context context;
	public Bundle bundle;
	public View view;
	private Application_files_explorer ap;

	public ServerLink(View v,  Bundle savedInstanceState) {
		this.view = v;
		this.context = v.getContext();
		this.bundle = savedInstanceState;
		ap = new Application_files_explorer(new File(context.getFilesDir(), "/Your_details"));
	}

	public void getClinetObejct(String data, ConnectivityManager connMgr) {
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			{

				Task myTask = new Task(data, context);
				myTask.execute();

			}
			// ///////////////

		} else {
			// display error
			Toast.makeText(context, "Invalid connection", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public String getData() {
		return this.data;
	}
	
	public String getClientId(){
		return this.data.split(",")[0];
	}

	public void savePreferences(String key, String value) {
		try{
		SharedPreferences sharedPreferences = context.getSharedPreferences("MY_CLIENT",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
		}
		catch(Exception e){
			return; 
		}
	}
	
	// //////////////////////
	public class Task extends AsyncTask<Void, Void, Integer> {

		private String data;
		private Context context;
		private ProgressDialog progressDialog;

		Task(String data, Context context) {
			this.data = data;
			this.context = context;
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(this.context);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setIndeterminate(true);
			progressDialog.setMessage("Wait");
			this.progressDialog.show();
			Log.d("Logn", "Begin_registration");
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Integer res = new Integer(0);
			Log.d("Log", "doInBackground---");
			try {
				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 5000;
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						timeoutConnection);
				int timeoutSocket = 5000;
				HttpConnectionParams
						.setSoTimeout(httpParameters, timeoutSocket);
				DefaultHttpClient httpclient = new DefaultHttpClient(
						httpParameters);

				try {
					byte[] encodedbytes = Base64.encodeBase64(data.getBytes());
					String encodedString = new String(encodedbytes);

					HttpGet httpget = new HttpGet(
							"http://test.service.cmt.net.au/ClaimsDataService.svc/GetClientObject?data="
									+ encodedString);
					httpget.setHeader("Content-Type", "application/json");
					httpget.setHeader("Accept", "application/json");

					HttpResponse response = httpclient.execute(httpget);
					InputStream is = response.getEntity().getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "windows-1251"));
					JsonReader rdr = Json.createReader(is);
					JsonObject obj = rdr.readObject();
					ap.parseJsontoFiles(obj);
					
					result = obj.toString();

					Log.d("Log", "result " + result);
					//
				} catch (Exception e) {
					e.printStackTrace();
					res = new Integer(1);
				}

			} finally {
				return res;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {	
			String title;
			String buttext;
			super.onPostExecute(result);
			if (result.equals(0)){
			
				
				final AlertDialog.Builder builder = new AlertDialog.Builder(context);

				
				builder.setTitle("Your profile has been sychronized");
				builder.setMessage("Your Client id is " + data.split(",")[0]);
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setNeutralButton("OK", null);
		
				
				
				builder.setCancelable(false);
				final AlertDialog dialog = builder.create();
				dialog.show();	
				
				String clientid = data.split(",")[0];
				savePreferences("ID", clientid);
				savePreferences("ID2", clientid);
				ap.backup_your_details();
				try{
					//profile.showDetails(view);
				}
				catch (Exception e) {
					e.getStackTrace();
				}
			}
			
			else{
				
						final AlertDialog.Builder builder = new AlertDialog.Builder(context);

						
						builder.setTitle("Profile has not been synchronized");
						builder.setMessage("The Client ID or PIN is incorrect, please verify your details and try again.");
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setNeutralButton("OK", null);
				
						builder.setCancelable(false);
						final AlertDialog dialog = builder.create();
						dialog.show();	
			}
			
			
			Log.d("Log", "End");
			progressDialog.dismiss();

		}
	}
	

	

}
