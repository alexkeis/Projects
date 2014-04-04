package spire.cmt;

import android.text.TextUtils;

public class Temp_nominated_contact {
	
	private String[] names_info = { "", "", "", "", "", "", "", "", ""};
	private String date;
			
			
	public Temp_nominated_contact(String[] info, String date){
		this.names_info = info;
		this.date = date;
	}
	
	public String getDate(){
		String s = "/Date(" + this.date
				+ ")/";
		return s;
	}
	
	
	public String getType(){
		if(names_info[3].equals("Personal Contact (e.g family)")){
			return "1";
		}
		else if(names_info[3].equals("Smash Repair Center")){
			return "2";
		} 
		else{
			return "3";
		} 
	}
	
	public String getContactVia(){
		
		// decide what to return based on which values are available
		if(names_info[6].equals("Email")){
			return "2";
		}
		else{
			return "1";
		}
			
	}
	
	public String getName(){
		if(TextUtils.isEmpty(names_info[4])){
			return "N/A";
		}
		else 
			return names_info[4];
		
	}
	
	public String getSurname(){
		return null;
		
	}
	
	public String getEmail(){
		return names_info[8];
		
	}
	
	public String getPhone(){
		return names_info[7];	
	}
	
	public String getCountry(){
		return names_info[0];
		
	}
	
	public String getAddress(){
		return null;
		
	}
	public String getCity(){
		return names_info[2];
		
	}
	public String getState(){
		return names_info[1];
	}
	
	public String getCompany(){
		return names_info[5];
		
	}
	
	
}
