package spire.cmt;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CMT_service extends Service {
 
   String tag="TestService";
   @Override
   public void onCreate() {
       super.onCreate();
       Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();      
       Log.i(tag, "Service created...");
       
       int ONGOING_NOTIFICATION_ID = 1000;
       Notification notification = new Notification(R.drawable.icon, "HELLO MY NAME IS ALEX",
		        System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, CMT_service.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, "ALEX TITLE",
		        "ALEX TEXT", pendingIntent);
	    startForeground(ONGOING_NOTIFICATION_ID, notification);
   }
 
   @Override
   public void onStart(Intent intent, int startId) {      
       super.onStart(intent, startId);  
       Log.i(tag, "Service started...");
   }
   @Override
   public void onDestroy() {
       super.onDestroy();
       Toast.makeText(this, "Service destroyed...", Toast.LENGTH_LONG).show();
   }

   @Override
   public IBinder onBind(Intent intent) {
       return null;
   }
}