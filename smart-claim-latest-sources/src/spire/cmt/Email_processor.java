package spire.cmt;

import  javax.mail.internet.InternetAddress;

public class Email_processor {
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
			   javax.mail.internet.InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (javax.mail.internet.AddressException ex) {
		      result = false;
		   }
		   return result;
		}

}
