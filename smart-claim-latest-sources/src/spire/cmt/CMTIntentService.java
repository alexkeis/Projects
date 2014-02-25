package spire.cmt;

import android.app.IntentService;
import android.widget.Toast;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.*;


public class CMTIntentService extends IntentService {

  /**
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public CMTIntentService() {
      super("CMTIntentService");
      
  }
  
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
	  int ONGOING_NOTIFICATION_ID = 1000;
		
		
	  Notification notification = new Notification(R.drawable.icon, "HELLO THIS IS MY INTENT",
		        System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, CMTIntentService.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, "ALEX TITLE INTENT",
		        "ALEX TEXT INTENT", pendingIntent);
	    startForeground(ONGOING_NOTIFICATION_ID, notification);
	    
	  return startId;
  }
  

  /**
   * The IntentService calls this method from the default worker thread with
   * the intent that started the service. When this method returns, IntentService
   * stops the service, as appropriate.
   */
  @Override
  protected void onHandleIntent(Intent intent) {
	  Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();      
  }
}