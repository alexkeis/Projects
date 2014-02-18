package spire.cmt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomOnClickListener implements android.content.DialogInterface.OnClickListener  {
    
	private int id;
	private Nominated_contact nc;
	
    public CustomOnClickListener(int id, Nominated_contact nc) {
       this.id = id;
       this.nc = nc;
    }
    
    public int getId(){
    	return id;
    }

    public void onClick(DialogInterface dialog, int which){
    	
    }

}