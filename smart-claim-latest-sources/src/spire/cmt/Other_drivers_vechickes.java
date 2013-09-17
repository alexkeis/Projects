package spire.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import spire.cmt.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Other_drivers_vechickes extends Activity {
	DialogFragment dlg2;
	Lodge_claim lg = new Lodge_claim();
	public static String[] names_123 = { "", "", "", "", "", "", "", "", "",
			"", "", "", "" };
	public static String[] names_2 = { "", "", "", "", "", "", "", "", "", "",
			"", "", "" };
	public static String[] mas_name_file;
	public static int pos_mas_name = 0;
	Button btn_add_driver;
	public static int colvo = 0;
	Button btn_other_drivers_nach;
	final String LOG_TAG = "myLogs";

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_other_drivers);

		mas_name_file = new String[lg.colvo];
		readFile_names();
		dlg2 = new Dialog2();
		btn_add_driver = (Button) findViewById(R.id.button_add_drivers);

		list();

	}

	void readFile_names() {
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		File sdFile = new File(path, "1123.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			while ((str = br.readLine()) != null)

			{
				mas_name_file[qw] = str.toString();
				qw++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void readFile_info() {
		String str = "";
		Lodge_claim lg = new Lodge_claim();
		String vr_str = "";
		File path = new File(getFilesDir(), "/CMT/" + lg.nameFolder);
		File sdFile = new File(path, mas_name_file[pos_mas_name].replace(" ",
				"") + ".txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			int qw = 0;
			Other_drivers od = new Other_drivers();
			while ((str = br.readLine()) != null)

			{
				od.names_n[qw] = str;
				od.names[qw] = od.names_k[qw] + od.names_n[qw];
				qw++;
				Log.d(LOG_TAG, str);

			}

			int r = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_other_drivers_n);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, mas_name_file);
		View header = getLayoutInflater().inflate(R.layout.header, null);
		lv.addHeaderView(header);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pos_mas_name = position - 1;
				readFile_info();
				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(), Other_drivers.class);
				startActivity(intent1);

			}
		});
	}

	public void click(View view) {
		Other_drivers od = new Other_drivers();
		for (int i = 0; i < od.names.length; i++) {
			od.names_n[i] = "";
			od.names[i] = od.names_k[i] + od.names_n[i];
		}
		Intent intent1 = new Intent();
		intent1.setClass(this, Other_drivers.class);
		startActivity(intent1);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		finish();
		Intent intent1 = new Intent();
		intent1.setClass(this, Other_drivers_vechickes.class);
		startActivity(intent1);
	}
}