package spire.cmt;

import java.io.*;

import android.app.Activity;
import android.content.Context;

public class Application_files_explorer extends Activity {

	public String str = "";
	public static String[] details_nc= {"","","","","","","",""};
	Context context;
//	
//	public Application_files_explorer(Context cont){
//		this.context = context;
//	}
	
	//File path = new File(getFilesDir(), "/Your_details");
//	File ncFile = new File(path, "Nominated_contact.txt");
//
//	public void getNcValuesfromFile() {
//		
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(ncFile));
//
//			for (int i = 0; i < details_nc.length - 1; i++) {
//				details_nc[i] = br.readLine();
//			}
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	public String get_email_from_nc(){
//		getNcValuesfromFile();
//		String email = "";
//		if(details_nc[7].equals("") || details_nc[7].equals("Email:\n")){
//			return email;			
//		}
//		else{
//			return details_nc[7];
//		}	
//	}

}
