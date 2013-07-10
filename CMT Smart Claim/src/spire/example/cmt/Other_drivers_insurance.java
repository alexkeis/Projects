package spire.example.cmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Other_drivers_insurance extends Activity {
	public static String[] mas_name_file;
	public static String[] mas_name = { "111111" };
	public static int rez;
	TextView txt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		txt = (TextView) findViewById(R.id.textView_insurer);
		String text = "Select Party at who admitted the liability. If you are not sure please select \"Not Sure\"";
		setContentView(R.layout.other_drivers_insurance);
		Lodge_claim lg = new Lodge_claim();
		if (lg.colvo > 0) {
			rez = lg.colvo + 1;
			mas_name_file = new String[rez];
			mas_name_file[rez - 1] = "Not Sure";
			readFile_names();
		} else {
			mas_name_file = new String[0];
		}
		list();
		

	}

	private void SavePreferences(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"MY_SHARED_Admitted", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_insurance);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		final ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_insurance, mas_name_file);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SavePreferences(Lodge_claim.nameFolder, adapt.getItem(position));

			}
		});
		
		SharedPreferences sharedData = getSharedPreferences("MY_SHARED_Admitted",
				MODE_PRIVATE);
		String strdata = sharedData.getString(Lodge_claim.nameFolder, "");
		if (strdata.equals("")) {			

		} else {
			for (int i = 0; i < mas_name_file.length; i++) {
				if(mas_name_file[i].equals(strdata)){
					lv.setItemChecked(i, true);					
				}
				
			}
		}

		

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
}