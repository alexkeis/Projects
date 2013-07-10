package spire.example.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class Accident_description extends Activity implements OnGestureListener{
	 EditText edit_accident;
	public static String accident_text="";
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.accident_description);
	    edit_accident = (EditText) findViewById(R.id.editText_accident_description);
	    edit_accident.setText(accident_text);
	}



	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Lodge_claim lg = new Lodge_claim();
    	File path=new File(getFilesDir(),"/CMT/"+lg.nameFolder);
    	path.mkdirs();
      	File sdFile=new File(path,"Accident_description.txt");
			    try {
			      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));	      
			      bw.write(edit_accident.getText().toString());
			      bw.close();
			      
			     
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		
	}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
	





	
}
