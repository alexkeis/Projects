package spire.cmt;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import spire.cmt.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Your_vehicle extends Activity {
	DialogFragment dialog2;
	String tit2;
	public static int pos2;
	public static String title2 = "Title";
	int pp2;
	public static String[] names = { "Make ", "Model ", "*Rego " };
	public static String[] names_info = { "", "", "" };
	public String[] temp_names_info;
	public static String[] names_title = { "Make " + names_info[0],
			"Model " + names_info[1], "*Rego " + names_info[2] };
	public Menu_state_keeper menustate;
	public int count = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_vechicle);
		dialog2 = new Dialog_vehicle();
		list();
		
		
//		menustate = new Menu_state_keeper(savedInstanceState);
//		if(menustate.is_first_time_called()){
//			menustate.set_init_values(names_info);
//			menustate.menu_called_again();
//		}
		
		
	}

	public void list() {
		ListView lv = (ListView) findViewById(R.id.listView_your_vechicle);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.list_item, names_title);
		View footer = getLayoutInflater().inflate(R.layout.footer_your_vehicle,
				null);
		lv.addFooterView(footer);
		lv.setDividerHeight(0);
		lv.setAdapter(adapt);
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pp2 = position;
				tit2 = names_title[position];
				dialog();
			}
		});
	}

	public void button_use_my_proile_vehicle(View view) {

	}

	public void ok_footer_your_vehicle(View view) {
		if (names_info[2].length() < 3) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Please enter a valid *Rego:", Toast.LENGTH_SHORT);
			toast.show();
		} else {
//
//			menustate.set_final_values(names_info);
//			if (menustate.make_comparison()) { 
//				menustate.notify("true", MODE_PRIVATE);
//			}
//			menustate.set_first_time();

			writeFileSD();
			finish();
		}
	}

	void writeFileSD() {
		File path = new File(getFilesDir(), "/Your_details");
		path.mkdirs();
		File sdFile = new File(path, "My_profile_vehicle.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(sdFile), "utf8"), 8192);
			// BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			int i = 0;
			for (i = 0; i < names.length - 1; i++) {

				bw.write(names_info[i] + "\n");
			}
			bw.write(names_info[i]);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dialog() {
		pos2 = pp2;
		title2 = tit2;
		dialog2.show(getFragmentManager(), "");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}