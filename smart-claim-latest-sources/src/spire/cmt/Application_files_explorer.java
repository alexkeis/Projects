package spire.cmt;

import java.io.*;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

public class Application_files_explorer extends Activity {

	public String str = "";
	public static String[] nc_details= {"","","","","","","","",""};
	public static String[] vehicle_details = {"","",""};
	public static String[] profile_details = {"", "", "", "", "", "", "", "", "", ""}; 
			
			
	Context context;
	File path;// = new File(getFilesDir(), "/Your_details");
	File path_string;
	File ncFile;
	File vehicleFile;
	File detailsFile;
	
	public Application_files_explorer(String nckey){
		if (!TextUtils.isEmpty(nckey)) {
			this.ncFile = new File(new File(getFilesDir(), "/Your_details"), "/Nominated_contact_"+nckey+".txt");
		} 
	}
	
	public Application_files_explorer(){	
	}
	
	public String[] getNamesInfoNc(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(ncFile));
			int qw = 0;
		
			while ((str = br.readLine()) != null)
			{
				nc_details[qw] = str;
				qw++;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nc_details;
	}
	
	
	public String get_path_string(){
		return path_string.toString();
	}
	
	public void set_path_string(File path){
		this.path = path;
		this.ncFile= new File(path, "/Nominated_contact.txt");
		this.vehicleFile = new File(path, "/My_profile_vehicle.txt");
		this.detailsFile = new File(path, "/My_profile_details.txt");
	}
	
	public void getProfileDetailsfromFile(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(detailsFile));
			for (int i = 0; i < profile_details.length; i++) {
				profile_details[i] = br.readLine();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getVehicleValuesfromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(vehicleFile));
			for (int i = 0; i < vehicle_details.length; i++) {
				vehicle_details[i] = br.readLine();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getNcValuesfromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(ncFile));
			for (int i = 0; i < nc_details.length; i++) {
				nc_details[i] = br.readLine();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String get_nc_email(){
		getNcValuesfromFile();
		return nc_details[8];
	}
	
	public String get_profile_name(){
		getProfileDetailsfromFile();
		return profile_details[0] +" "+profile_details[1];
	}
	
	public String get_profile_phone(){
		getProfileDetailsfromFile();
		return profile_details[5];
	}

}
