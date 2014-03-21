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
	
	
	public void addCell(String s){
		MyCell cell = new MyCell();
		cell.name = s;
		mAnimList.add(cell);
		mMyAnimListAdapter.notifyDataSetChanged();
	}
//	
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

	private void deleteCell(final View v, final int index) {
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

		collapse(v, al);
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


	private class MyCell {
		public String name;
	}

	private class ViewHolder {
		public boolean needInflate;
		public TextView text;
		ImageButton imageButton;
	}

	public class MyAnimListAdapter extends ArrayAdapter<MyCell> {
		private LayoutInflater mInflater;
		private int resId;

		public MyAnimListAdapter(Context context, int textViewResourceId, List<MyCell> objects) {
			super(context, textViewResourceId, objects);
			this.resId = textViewResourceId;
			this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view ;
			ViewHolder vh;
			MyCell cell = (MyCell)getItem(position);

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
			vh.text.setText(cell.name);
			vh.imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteCell(view, position);
				}
			});

			return view;
		}

		private void setViewHolder(View view) {
			ViewHolder vh = new ViewHolder();
			vh.text = (TextView)view.findViewById(R.id.cell_name_textview);
			vh.imageButton = (ImageButton) view.findViewById(R.id.cell_trash_button);
			vh.needInflate = false;
			view.setTag(vh);
		}
	}
	
	public void addContactClicked(View view){
		Intent intent1 = new Intent();
		intent1.setClass(getApplicationContext(),
						 
						//Nominated_contact.class);
						Nominated_contact.class);			
		//startActivity(intent1);
		startActivityForResult(intent1, STATIC_INTEGER_VALUE);
	}
}