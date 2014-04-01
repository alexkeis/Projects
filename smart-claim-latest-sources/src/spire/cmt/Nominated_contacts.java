package spire.cmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import spire.cmt.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

public class Nominated_contacts extends Activity {

	static final int ANIMATION_DURATION = 200;
	private static List<MyCell> mAnimList = new ArrayList<MyCell>();
	private MyAnimListAdapter mMyAnimListAdapter;
	int  STATIC_INTEGER_VALUE = 0;
	String PUBLIC_STATIC_STRING_IDENTIFIER = "done";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nominated_contacts);

//		for (int i=0;i<50;i++) {
//			MyCell cell = new MyCell();
//			cell.name = "Cell No."+Integer.toString(i);
//			mAnimList.add(cell);
//		}

		mMyAnimListAdapter = new MyAnimListAdapter(this, R.layout.chain_cell, mAnimList);
		ListView myListView = (ListView) findViewById(R.id.chainListView);
		myListView.setAdapter(mMyAnimListAdapter);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
		String key = settings.getString("deletekey", null);
		this.removeNc(key);
	}
	
	
	public void addCell(String s){
		MyCell cell = new MyCell();
		cell.name = s;
		
		if(!mAnimList.contains(cell) && s != null && !s.equals(""))
		{
			mAnimList.add(cell);
			mMyAnimListAdapter.notifyDataSetChanged();
		}
		
	}
//	
	
	
	public boolean hasBeenProcessed(){
		
		java.util.Iterator iterator = mAnimList.iterator();

		        while (iterator.hasNext())
		        {
		            MyCell o = (MyCell) iterator.next();
		            if(! mAnimList.contains(o)) return true;
		        }
		         return false;
	}
	
	
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//if (data !=null){
		Intent testintent = data;
		String PREFS_NAME = null;
		
		super.onActivityResult(requestCode, resultCode, data);
		SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_SCREEN_VALUES", MODE_PRIVATE);
		String keystring  = settings.getString("done", null);
		addCell(keystring);
				
//
//		//switch (requestCode) {
//		//case (0): {
//			//if (resultCode == Activity.RESULT_OK) {
//			//	String newText = data
//				//		.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
			//	addCell(data.getStringExtra("done"));
//			//}
//			//break;
//		//}
//		//}
		//}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void deleteCell(final View v, final int index, String key) {
		AnimationListener al = new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				mAnimList.remove(index);

				ViewHolder vh = (ViewHolder)v.getTag();
				vh.needInflate = true;

				mMyAnimListAdapter.notifyDataSetChanged();
			}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationStart(Animation animation) {}
		};

		removeNcFile(key);
		collapse(v, al);
	
		SharedPreferences sharedPreferences = getSharedPreferences("MY_CLIENT", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("Contact_edited", "true");
		editor.commit();
	}

	
	private void removeNcFile(String key){
		Application_files_explorer ap = new Application_files_explorer(getFilesDir());
		ap.removeNcFile(key);
	}
	
	private void collapse(final View v, AnimationListener al) {
		final int initialHeight = v.getMeasuredHeight();

		Animation anim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				}
				else {
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (al!=null) {
			anim.setAnimationListener(al);
		}
		anim.setDuration(ANIMATION_DURATION);
		v.startAnimation(anim);
	}

	
	public void removeNc(String key){
		
		if(key != null){
			MyCell cell = new MyCell();
			cell.name = key;
			
			mAnimList.remove(cell);
			this.removeNcFile(key);
		}
	}

	private class MyCell {
		public String name;
		
		@Override
		public boolean equals(Object object)
	    {
		    boolean sameSame = false;

	        if (object != null && object instanceof MyCell)
	        {
	            sameSame = this.name.equals(((MyCell)object).name);
	        }
	        return sameSame;
	    }
	}

	private class ViewHolder {
		public boolean needInflate;
		public TextView text;
		public ImageButton imageButton;
		public Button keyButton;
	}

	public class MyAnimListAdapter extends ArrayAdapter<MyCell> {
		private LayoutInflater mInflater;
		private int resId;
		private Context context;

		public MyAnimListAdapter(Context context, int textViewResourceId, List<MyCell> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			this.resId = textViewResourceId;
			this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view ;
			ViewHolder vh;
			MyCell cell = (MyCell)getItem(position);
			final String cellname = cell.name;
			
			if (convertView==null) {
				view = mInflater.inflate(R.layout.chain_cell, parent, false);
			    setViewHolder(view);
			}
			else if (((ViewHolder)convertView.getTag()).needInflate) {
				view = mInflater.inflate(R.layout.chain_cell, parent, false);
				setViewHolder(view);
			}
			else {
				view = convertView;
			}

			vh = (ViewHolder)view.getTag();
			//vh.text.setText(cell.name);
			vh.keyButton.setText(cell.name);
			
			vh.keyButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					editContactClicked(v, cellname);
				}
			});
			
			
			vh.imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteCell(view, position, cellname);
					
				}
			});

			return view;
		}
		

		private void setViewHolder(View view) {
			ViewHolder vh = new ViewHolder();
			//vh.text = (TextView)view.findViewById(R.id.cell_name_textview);
			vh.imageButton = (ImageButton) view.findViewById(R.id.cell_trash_button);
			vh.keyButton = (Button)  view.findViewById(R.id.cell_key_button);
			vh.needInflate = false;
			view.setTag(vh);
		}
	}
	
	public void editContactClicked(View view, String key){
		clickedNominatedContactKeySet(key);
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(),
						 
						//Nominated_contact.class);
						Nominated_contact.class);			
		//startActivity(intent1);
		startActivityForResult(intent1, STATIC_INTEGER_VALUE);
	}
	
	public void addContactClicked(View view){
		clickedNominatedContactKeySet("firsttime");
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(),
						 
						//Nominated_contact.class);
						Nominated_contact.class);			
		//startActivity(intent1);
		startActivityForResult(intent1, STATIC_INTEGER_VALUE);
	}
	
	private void clickedNominatedContactKeySet(String key){
		  SharedPreferences settings = getSharedPreferences("NOMINATED_CONTACT_KEY", MODE_PRIVATE);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("key", key);
	      // Commit the edits!
	      editor.commit();
	}
	
}