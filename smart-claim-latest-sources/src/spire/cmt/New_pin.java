package spire.cmt;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
public class New_pin extends Activity {
	static String pin1;
	static String pin2;
	static String[] name_pin1={"","","",""};
	static String[] name_pin2={"","","",""};
	static String key="";
	static int id=-1;
	ImageView im1;
	ImageView im2;
	ImageView im3;
	ImageView im4;
	boolean verify = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pin);
		im1 = (ImageView) findViewById(R.id.im1);
		im2 = (ImageView) findViewById(R.id.im2);
		im3 = (ImageView) findViewById(R.id.im3);
		im4 = (ImageView) findViewById(R.id.im4);
	}
    private void SavePreferences(String key, String value){
    	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	editor.putString(key, value);
    	editor.commit();
    }
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
          AlertDialog.Builder adb = new AlertDialog.Builder(this);
          adb.setTitle("PIN Mismatch");
          adb.setMessage("The PIN you have entered was not recognised");
          adb.setIcon(android.R.drawable.ic_dialog_info);
          adb.setNeutralButton("Ok", myClickListener);
          return adb.create();
        }
        return super.onCreateDialog(id);
      }
    OnClickListener myClickListener = new OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
      switch (which) {
      case Dialog.BUTTON_POSITIVE:
        finish();
        break;
      case Dialog.BUTTON_NEGATIVE:
        finish();
        break;
      case Dialog.BUTTON_NEUTRAL:
        break;
      }
    }
  };
	public void delete()
	{
		if(id-1>-2)
		{
			
			if(id==0)
			{
				im1.setVisibility(View.INVISIBLE);
			}
			else if(id==1)
			{
				im2.setVisibility(View.INVISIBLE);
			}
			else if(id==2)
			{
				im3.setVisibility(View.INVISIBLE);
			}
			else if(id==3)
			{
				im4.setVisibility(View.INVISIBLE);
			}
			id--;
		}


	}
	public void image()
	{
		if(id==0)
		{
			im1.setVisibility(View.VISIBLE);
		}
		else if(id==1)
		{
			im2.setVisibility(View.VISIBLE);
		}
		else if(id==2)
		{
			im3.setVisibility(View.VISIBLE);
		}
		else if(id==3)
		{
			im4.setVisibility(View.VISIBLE);
		}
	}
	public void check()
	{
		image();
		if(verify==false)
		{
		if(id<3)
		{

			name_pin1[id]=key;

		}
		else
		{	name_pin1[3]=key;
			pin1=name_pin1[0]+name_pin1[1]+name_pin1[2]+name_pin1[3];
			verify=true;
			id=-1;
			im1.setVisibility(View.INVISIBLE);
			im2.setVisibility(View.INVISIBLE);
			im3.setVisibility(View.INVISIBLE);
			im4.setVisibility(View.INVISIBLE);
		}
		}
		else if (verify == true)
		{
			if(id<3)
			{

				name_pin2[id]=key;

			}
			else
			{
				name_pin2[3]=key;
				pin2=name_pin2[0]+name_pin2[1]+name_pin2[2]+name_pin2[3];
				if(pin1.equals(pin2)){
				id=-1;
				SavePreferences("MEM1", pin2);

				finish();
				}
				else {
					id=-1;
					showDialog(1);
					verify=false;
					im1.setVisibility(View.INVISIBLE);
					im2.setVisibility(View.INVISIBLE);
					im3.setVisibility(View.INVISIBLE);
					im4.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	public void click1(View view)
	{
		id++;
		key="1";
		check();
	}
	public void click2(View view)
	{
		id++;
		key="2";
		check();
	}
	public void click3(View view)
	{
		id++;
		key="3";
		check();
	}
	public void click4(View view)
	{
		id++;
		key="4";
		check();
	}
	public void click5(View view)
	{
		id++;
		key="5";
		check();
	}
	public void click6(View view)
	{
		id++;
		key="6";
		check();
	}
	public void click7(View view)
	{
		id++;
		key="7";
		check();
	}
	public void click8(View view)
	{
		id++;
		key="8";
		check();
	}
	public void click9(View view)
	{
		id++;
		key="9";
		check();
	}
	public void click0(View view)
	{
		id++;
		key="0";
		check();
	}
	public void click_del(View view)
	{	
		delete();

	}

}
