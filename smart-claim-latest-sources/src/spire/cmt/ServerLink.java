package spire.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONObject;

import spire.cmt.My_profile.MyTask_show;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

public class ServerLink extends Activity {
	
	private ProgressDialog progressDialog;
	private String data = null;
	public String result = null;
	
	public void getClinetObejct(String data){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			{
				Task myTask = new Task(data);
				myTask.execute();

			}
			// ///////////////
		
		} else {
			// display error
			Toast.makeText(getApplicationContext(), "Invalid connection",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public String getData(){
		return this.data;
	}

	// //////////////////////
	public class Task extends AsyncTask<Void, Void, Integer> {

		private String data;

		Task(String data){
			this.data = data;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
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
					Map obj = new LinkedHashMap();

					obj.put("Data", this.data);
					JSONObject json = new JSONObject(obj);
					String jsonText = json.toString();
			
					HttpGet httpget = new HttpGet("http://test.service.cmt.net.au/ClaimsDataService.svc/GetClientObject?"+jsonText);
					httpget.setHeader("Content-Type", "application/json");
					httpget.setHeader("Accept", "application/json");

					HttpResponse response = httpclient.execute(httpget);
			
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "windows-1251"));

					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + System.getProperty("line.separator"));
					}

					result = sb.toString();
					Log.d("Log", "result " + result);
					//
				} catch (Exception e) {
					e.printStackTrace();
					res = new Integer(1);
				}

			} finally {
				return 0;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("Log", "End");
			progressDialog.dismiss();

		}
	}

}
