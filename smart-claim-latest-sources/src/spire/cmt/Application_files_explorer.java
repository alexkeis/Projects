package spire.cmt;


import java.io.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.apache.commons.io.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.*;

import javax.json.*;

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
			path.mkdirs();  
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////
			// new code, connecting to getClient web service
			this.ncFile = new File(path, "/Nominated_contact.txt");
			this.detailsFile = new File(path, "/My_profile_details.txt");
			this.vehicleFile = new File(path, "/My_profile_vehicle.txt");
////////////////////////////////////////////////////////////////////////////////////////////////////////
						
			getNcsfromFiles();
		

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public Application_files_explorer(){	
	}
	
	
	public void parseJsontoFiles(JsonObject obj){
		String[] names_info;
	
		names_info = this.detailsFromJson(obj);
		this.writeNamesInfotoFile(names_info, this.detailsFile);
		
		names_info = this.vehicleFromJson(obj);
		this.writeNamesInfotoFile(names_info, this.vehicleFile);
		
//		String [][] names_info_array = this.nomcontactsFromJson(obj);
//		for(int i=0; i < names_info_array.length; i++){
//			names_info = names_info_array[i];
//			this.writeNamesInfotoFile(names_info, this.ncFile);
//		}
	}
	
	
	public String[] detailsFromJson(JsonObject obj){
		
		String[] names_info = new String[11];
		JsonObject personalobj  = obj.getJsonObject("PersonalData");
		
		names_info[0] = personalobj.getString("FirstName");
		names_info[1] = personalobj.getString("Surname");
		names_info[2] = ""; //DOB
		names_info[3] = "";  //Drivers License
		names_info[4] = "";  //Exp Date
		names_info[5] = personalobj.getString("Phone");  //Phone
		names_info[6] = personalobj.getString("Email");  //Email
		names_info[7] = personalobj.getString("Address1");  // Street Address
		names_info[8] = personalobj.getString("Address2");  // Suburb
		names_info[9] = personalobj.getString("Postcode");  // Post code
		names_info[10] = personalobj.getString("StateCode"); // State
		
		return names_info;
	}
	
	
	public String[] vehicleFromJson(JsonObject obj){
		String[] names_info = new String[3];
		
		try{
			names_info[0] = obj.getString("VehicleMake");
			names_info[1] = obj.getString("VehicleModelUser");
			
			if(names_info[0].isEmpty()){
				String tempmakemodel = names_info[1].split("::")[0];
				names_info[0] = tempmakemodel;
				tempmakemodel = names_info[1].split("::")[1];
				names_info[1] = tempmakemodel;
			}
			names_info[2] = obj.getString("VehicleRego");
		}
		catch (java.lang.ArrayIndexOutOfBoundsException e){
		}
		return names_info;	
		
	}

	
	
	public String[][] nomcontactsFromJson(JsonObject obj){
		String[][] names_info = null;
		return names_info;
	}
	
	
	
	public void writeNamesInfotoFile(String[] names_info, File sdFile){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			int i = 0;
			for (i = 0; i < names_info.length - 1; i++) {

				bw.write(names_info[i] + "\n");

			}
			bw.write(names_info[i]);

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backup_your_details(){
		Runtime r = Runtime.getRuntime();
		File[] files = new File(this.path.toString()).listFiles();
		
		if(this.path.exists()){
			try {
				Process p = r.exec("rm -r "+this.path.toString()+"_back/");
				p.waitFor();
				p = r.exec("mkdir "+this.path.toString()+"_back/");
				p.waitFor();
				
				for(File file : files){
					if(file.isDirectory()){continue;}
					p = r.exec(new String[]{"sh", "-c", "cat '"+file.getAbsolutePath()+"' > "+"'"+this.path.toString()+"_back/"+file.getName()+"'"});
					p.waitFor();
			
//					p = r.exec(new String[]{"cat file.getAbsolutePath()"});
//					OutputStream stdin = p.getOutputStream();
//					stdin.write("this.path.toString()+");
					
				}
			    //p = r.exec("mv "+this.path.toString()+" "+this.path.toString()+"_back");
			    //p = r.exec("cp -R "+this.path.toString()+" "+this.path.toString()+"_back");
				//p.waitFor();
			}
			catch (Exception e){
				
			}
		}
	}
	
	public boolean have_files_changed(){
		File backupdir = new File(this.path.toString()+"_back");
		
		if(backupdir.exists()){
			
			File[] files = new File(this.path.toString()+"_back").listFiles();
		
			for(File file : files){
				if(file.isDirectory()){continue;}
				try {
					File file2 = new File(this.path.toString(), "/"+file.getName());
					if(file2.exists()){
						if(FileUtils.contentEquals(file, file2)){
							continue;
						}
						else
							return true;
					}
					else
						return true;
				} catch (java.io.IOException e) {
					return true;
				}
			}
			
			
			files = new File(this.path.toString()).listFiles();
			
			for(File file : files){
				if(file.isDirectory()){continue;}
				try {
					File file2 = new File(this.path.toString()+"_back", "/"+file.getName());
					if(file2.exists()){
						if(FileUtils.contentEquals(file, file2)){
							continue;
						}
						else
							return true;
					}
					else
						return true;
				} catch (java.io.IOException e) {
					return true;
				}
			}
			
			return false;
		}
		return false;
	}
	
	public boolean hasNominatedContacts(){
		if(contacts.isEmpty()){
			return false;
		}
		else
			return true;
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

	
	
/////////////////////////////////////////////// WEB SERVER COMMUNICAITON //////////////////////////////////////////////////
	

	
}
