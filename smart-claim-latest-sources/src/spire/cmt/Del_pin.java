package spire.cmt;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Del_pin extends Activity {

	
	boolean verify = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.del_pin);
		
	}
	// 
	// There will be no PIN entry screen when Application starts up.
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
          AlertDialog.Builder adb = new AlertDialog.Builder(this);
          adb.setTitle("PIN Removed");
          adb.setMessage("There will be no PIN entry screen when Application starts up.");
          adb.setIcon(android.R.drawable.ic_dialog_info);
          adb.setNeutralButton("Ok", myClickListener);
          return adb.create();
        }
        return super.onCreateDialog(id);
      }
    OnClickListener myClickListener = new OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
      switch (which) { 
      case Dialog.BUTTON_NEUTRAL:
  		SavePreferences("MEM1", "");
  		finish();
        break;
      }
    }
  };
	public void del_pincod(View view)
	{
		showDialog(1);
		

		
	}

    private void SavePreferences(String key, String value){
    	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	editor.putString(key, value);
    	editor.commit();
    }


}

