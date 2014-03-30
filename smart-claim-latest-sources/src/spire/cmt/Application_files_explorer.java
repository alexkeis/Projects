package spire.cmt;

import java.io.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

public class Application_files_explorer  {

	public String str = "";
	public String[] nc_details= {"","","","","","","","",""};
	public String nc_date = "";
	public String[] vehicle_details = {"","",""};
	public String[] profile_details = {"", "", "", "", "", "", "", "", "", ""}; 		
	public ArrayList contacts = new ArrayList();
			
	Context context;
	File path;// = new File(getFilesDir(), "/Your_details");
	File path_string;
	File ncFile;
	File vehicleFile;
	File detailsFile;
	String date;
	
	public Application_files_explorer(File path, String nckey){
		this.path = path;
		if (!TextUtils.isEmpty(nckey)) {
			this.ncFile = new File(new File(this.path, "/Your_details"), "/Nominated_contact_"+nckey+".txt");
		} 
		getNcsfromFiles();
	}
	
	public Application_files_explorer(File path){	
		try{
			this.path = path;
			getNcsfromFiles();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public Application_files_explorer(){	
	}
	
	
	
	public void getNcsfromFiles(){
		
		File[] files = new File(this.path.toString()).listFiles();
		int i = 0;
		String[] nc_test;
		String[] nc_temp;
		String nc_date;
		//Temp_nominated_contact contact = null;		
		
		for(File file : files){
			if(file.toString().contains("Nominated_contact"))
			{
				this.ncFile = file;
				nc_temp = this.getNcValuesfromFile();
				nc_date = this.getDatefromNcFile();
				
				try{
					//contact = new Temp_nominated_contact(this.nc_details, this.date);
					//nc_temp = this.nc_details;
					this.contacts.add(new Temp_nominated_contact((nc_temp), nc_date));
					//nc_temp = new String[](this.nc_details);				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public ArrayList getNcs(){
		return this.contacts;
	}
	
	
	public void removeNcFile(String nckey){
		File ncFile = new File(new File(this.path, "/Your_details"), "/Nominated_contact_"+nckey+".txt");
		ncFile.delete();
	}
	

	
	
	public String[] getNamesInfoNc(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(ncFile));
			int qw = 0;
			String str;
		
			while ((str = br.readLine()) != null && qw <= nc_details.length-1)
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
	
	public String getDatefromNcFile() {
		String tmpdate = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(ncFile));
			String lastline = null;
			for (int i = 0; i <= nc_details.length; i++) {
				String s = br.readLine();
				if (s == null){
					s = "";  
				}
				lastline = s;
			}
			this.date = lastline;
			tmpdate = lastline;
			
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return tmpdate;
	}
	
	
	public String[] getNcValuesfromFile() {
		String[] tmpncvals = new String[] {"","","","","","","","",""};
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(ncFile));
			String lastline = null;
			for (int i = 0; i < nc_details.length; i++) {
				String s = br.readLine();
				if (s == null){
					s = "";  
				}
				nc_details[i] = s;
				tmpncvals[i] = s;
				lastline = s;
			}
			this.date = lastline;
			
		} catch (FileNotFoundException e) {

			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return tmpncvals;
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
