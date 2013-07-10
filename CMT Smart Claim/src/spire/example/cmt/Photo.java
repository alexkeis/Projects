package spire.example.cmt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class Photo  extends Activity{
	public static  String IMAGES_PATH;
	ActionMode actionMode;
	boolean clicked = false;
	String[] path= {"Photo Camera","Photo Library"};
	private Uri mImageCaptureUri;
	private File file;
	private Bitmap[] photo_ = new Bitmap[9]; 
	int index = 0;
	int tag_img = 0;
	ImageView[] image_views= new ImageView[9];
	CheckBox[] check_box = new CheckBox[9];
	private static final int CHOOSE_PICTURE_DIALOG = 3;
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_FILE = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final String[] IMAGE_NAME_ = {"image1.jpg","image2.jpg","image3.jpg",
		"image4.jpg","image5.jpg","image6.jpg","image7.jpg","image8.jpg","image9.jpg",};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		IMAGES_PATH = Lodge_claim.path_d.toString();
		
		image_views[0]=(ImageView)findViewById(R.id.image1);
		image_views[1]=(ImageView)findViewById(R.id.image2);
		image_views[2]=(ImageView)findViewById(R.id.image3);
		image_views[3]=(ImageView)findViewById(R.id.image4);
		image_views[4]=(ImageView)findViewById(R.id.image5);
		image_views[5]=(ImageView)findViewById(R.id.image6);
		image_views[6]=(ImageView)findViewById(R.id.image7);
		image_views[7]=(ImageView)findViewById(R.id.image8);
		image_views[8]=(ImageView)findViewById(R.id.image9);
		
		check_box[0]=(CheckBox)findViewById(R.id.check1);
		check_box[1]=(CheckBox)findViewById(R.id.check2);
		check_box[2]=(CheckBox)findViewById(R.id.check3);
		check_box[3]=(CheckBox)findViewById(R.id.check4);
		check_box[4]=(CheckBox)findViewById(R.id.check5);
		check_box[5]=(CheckBox)findViewById(R.id.check6);
		check_box[6]=(CheckBox)findViewById(R.id.check7);
		check_box[7]=(CheckBox)findViewById(R.id.check8);
		check_box[8]=(CheckBox)findViewById(R.id.check9);
		image_v();
		File sdPath2 = new File(IMAGES_PATH);
		Log.d("Logn при запуске",sdPath2.toString());
		File[] file = sdPath2.listFiles();
		try
		{
			
			for(int i = 0; i<file.length; i++)
			{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			photo_[i] = BitmapFactory.decodeFile(file[i].toString(), options);
			image_views[i].setImageBitmap(photo_[i]);
			image_views[i].setVisibility(View.VISIBLE);
			image_views[i].setClickable(false);		
			
			}
			index = file.length;
			image_views[index].setVisibility(View.VISIBLE);
			image_views[index].setClickable(true);
			
		} 
		catch (Exception e)
		{			
		}

	}
	public void image_v()
	{
		for(int i  = 0 ; i<image_views.length; i++){	
		image_views[i].setOnClickListener(new OnClickListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				if(clicked == false)
				{
				if (Camera.getNumberOfCameras() > 0)
					showDialog(CHOOSE_PICTURE_DIALOG);
				else {			
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent,
							"Complete action using"), PICK_FROM_FILE);
				}
			}

			}
		});
		if(photo_[i]!=null)
		{
			image_views[i].setClickable(false);
		}
		}
	}
	public void delete_image()
	{
		for(int i = 0; i<photo_.length; i++)
		{
			if(check_box[i].isChecked() == true)
			{
				File sdPath2 = new File(Lodge_claim.path_d+ "/" + IMAGE_NAME_[i]);
				Log.d("Lognn при удалении", sdPath2.toString());
				sdPath2.delete();
				photo_[i]=null;	
			}
		}
		for(int i = 0; i<photo_.length; i++)
		{
			if(photo_[i]==null)
			{
				for(int j = i+1; j<photo_.length; j ++)
				{
					if(photo_[j]!=null)
					{
						photo_[i]=photo_[j];
						File sdPath2 = new File(Lodge_claim.path_d + "/" + IMAGE_NAME_[j]);//File sdPath2 = new File(Lodge_claim.path_d + IMAGE_NAME_[i]);
						Log.d("Lognn при удалении", sdPath2.toString());
						 sdPath2.delete(); 
						photo_[j]=null;
						break;
					}
				}
			}
		}
		for(int i = 0; i< image_views.length; i++)
		{
			if(photo_[i]!=null)
			{
				image_views[i].setImageBitmap(photo_[i]);
				image_views[i].setVisibility(View.VISIBLE);
				index = i;
			}
		}
		for(int i = 0; i< image_views.length; i++)
		{
			if(photo_[i]==null)
			{
				
				image_views[i].setVisibility(View.INVISIBLE);
			}
		}
		int b = 0;
		Resources myResources = getResources();
		Drawable myIcon = myResources.getDrawable(R.drawable.camera_new);
		for(int i = 0; i < image_views.length; i++)
		{
			if(photo_[i]!=null)
			{
				
				b++;
			}
			else
				image_views[i].setImageDrawable(myIcon);
		}
		if(b==0){
			index = b;
			image_views[0].setImageDrawable(myIcon);
			image_views[0].setVisibility(View.VISIBLE);
			image_views[0].setClickable(true);
		}
		else{
		image_views[index+1].setImageDrawable(myIcon);
		image_views[index+1].setVisibility(View.VISIBLE);
		image_views[index+1].setClickable(true);
		}	
	}
	  @Override
	    public void onBackPressed()
	    {
			String path_doc="";
			for(int i=0; i<photo_.length; i++)
			{
			if(photo_[i]!=null){
			  saveImage(photo_[i], IMAGE_NAME_[i],IMAGES_PATH,false);
			  path_doc = IMAGE_NAME_[i];
			} 
			}
			finish();
	    }
	    protected void saveImage(Bitmap image, String name, String IMAGES_PATH, boolean isSign) {
	    	DiskCache dc = new DiskCache(getApplicationContext());
	    	dc.saveImageToDisk(image, name,IMAGES_PATH,isSign);
		}
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
		protected Dialog onCreateDialog(int id) {
	    	
			AlertDialog.Builder adb = new AlertDialog.Builder(this);	
			switch (id) {

			case CHOOSE_PICTURE_DIALOG:
				adb.setTitle("Choose an image");
				adb.setItems(path,
						imageChooser);
				break;
			}
			return adb.create();
		}
		android.content.DialogInterface.OnClickListener imageChooser = new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					file = new File(Environment.getExternalStorageDirectory(),
							"tmp_avatar_"
									+ String.valueOf(System.currentTimeMillis())
									+ ".jpg");
					mImageCaptureUri = Uri.fromFile(file);

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							mImageCaptureUri);
					try {
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else if (which == 1) { 
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(
							Intent.createChooser(intent, "Complete action using"),
							PICK_FROM_FILE);
				}
			}
		};
			
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode != RESULT_OK)
				return;
			switch (requestCode) {
			case PICK_FROM_CAMERA:
				doCrop();

				break;

			case PICK_FROM_FILE:
				mImageCaptureUri = data.getData();

				doCrop();

				break;

			case CROP_FROM_CAMERA:
				Bundle extras = data.getExtras();
				if (extras != null) {
						photo_[index] = extras.getParcelable("data");
						image_views[index].setImageBitmap(photo_[index]);
					if(index<8){
						image_views[index].setClickable(false);
					index++;
					image_views[index].setVisibility(View.VISIBLE);
					}
				}
				break;
			}
		}
		private void doCrop() {
			final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
			                                                                                                
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setType("image/*");

			List<ResolveInfo> list = getPackageManager().queryIntentActivities(
					intent, 0);

			int size = list.size();

			if (size == 0) {
				Toast.makeText(this, "Can not find image crop app",
						Toast.LENGTH_SHORT).show();

				return;
			} else {
				intent.setData(mImageCaptureUri);

				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 200);
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);

				if (size == 1) {
					Intent i = new Intent(intent);
					ResolveInfo res = list.get(0);

					i.setComponent(new ComponentName(res.activityInfo.packageName,
							res.activityInfo.name));

					startActivityForResult(i, CROP_FROM_CAMERA);
				} else {
					for (ResolveInfo res : list) {
						final CropOption co = new CropOption();

						co.title = getPackageManager().getApplicationLabel(
								res.activityInfo.applicationInfo);
						co.icon = getPackageManager().getApplicationIcon(
								res.activityInfo.applicationInfo);
						co.appIntent = new Intent(intent);
						co.id = R.id.image1;

						co.appIntent
								.setComponent(new ComponentName(
										res.activityInfo.packageName,
										res.activityInfo.name));

						cropOptions.add(co);
					}

					CropOptionAdapter adapter = new CropOptionAdapter(
							getApplicationContext(), cropOptions);

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Choose Crop App");
					builder.setAdapter(adapter,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									startActivityForResult(
											cropOptions.get(item).appIntent,
											CROP_FROM_CAMERA);
								}
							});

					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {

							if (mImageCaptureUri != null) {
								getContentResolver().delete(mImageCaptureUri, null,
										null);
								mImageCaptureUri = null;
							}
						}
					});

					AlertDialog alert = builder.create();

					alert.show();
				}
			}
		}
		
		public void check_invisible()
		{
			for(int i = 0; i<check_box.length; i++)
			{
				check_box[i].setChecked(false);
				check_box[i].setVisibility(View.INVISIBLE);
			}
		}
		
		public void check_visible()
		{
			for(int i = 0; i<check_box.length; i++)
			{
				if(photo_[i]!=null)
				check_box[i].setVisibility(View.VISIBLE);
				if(photo_[i]==null)
					check_box[i].setVisibility(View.INVISIBLE);
			}
			
		}
	  	  public void clicked()
	  	  {
		  		image_views[0].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[0].isChecked()==true)
							{
								check_box[0].setChecked(false);
							}
							else
								check_box[0].setChecked(true);
							
						}
					}
				});
		  		image_views[1].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[1].isChecked()==true)
							{
								check_box[1].setChecked(false);
							}
							else
								check_box[1].setChecked(true);
							
						}
					}
				});
		  		image_views[2].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[2].isChecked()==true)
							{
								check_box[2].setChecked(false);
							}
							else
								check_box[2].setChecked(true);
							
						}
					}
				});
		  		image_views[3].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[3].isChecked()==true)
							{
								check_box[3].setChecked(false);
							}
							else
								check_box[3].setChecked(true);
							
						}
					}
				});
		  		image_views[4].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[4].isChecked()==true)
							{
								check_box[4].setChecked(false);
							}
							else
								check_box[4].setChecked(true);
							
						}
					}
				});
		  		image_views[5].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[5].isChecked()==true)
							{
								check_box[5].setChecked(false);
							}
							else
								check_box[5].setChecked(true);
							
						}
					}
				});
		  		image_views[6].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[6].isChecked()==true)
							{
								check_box[6].setChecked(false);
							}
							else
								check_box[6].setChecked(true);
							
						}
					}
				});
		  		image_views[7].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[7].isChecked()==true)
							{
								check_box[7].setChecked(false);
							}
							else
								check_box[7].setChecked(true);
							
						}
					}
				});
		  		image_views[8].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(clicked == true)
						{
							if(check_box[8].isChecked()==true)
							{
								check_box[8].setChecked(false);
							}
							else
								check_box[8].setChecked(true);
							
						}
					}
				});
	  		
	  	  }
		
		public boolean onCreateOptionsMenu(Menu menu)
		  {
		    getMenuInflater().inflate(R.menu.main, menu);
		    return super.onCreateOptionsMenu(menu);
		  }

			 @Override
			    public boolean onOptionsItemSelected(MenuItem item) {
			      if (actionMode == null)
			          actionMode = startActionMode(callback);
			        else
			          actionMode.finish();
			      clicked = true;
			      check_visible();
			      clicked();
			      for(int i = 0; i<photo_.length; i++)
			      {
			    	  if(photo_[i]!=null)
			    	  {
			    	  image_views[i].setClickable(true);
			    	  }
			    	  }
		
			      return super.onOptionsItemSelected(item);
			    }
			 private ActionMode.Callback callback = new ActionMode.Callback() {

				    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				      mode.getMenuInflater().inflate(R.menu.context, menu);
				      return true;
				    }

				    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				      return false;
				    }

				    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				    	delete_image();
				    	check_visible();     
				            return false;
				    }
				  
				    @Override
				    public void onDestroyActionMode(ActionMode mode) {
				    	check_invisible();
				    	clicked = false;
				    	image_v();
				      actionMode = null;				      
				    }

				  };
					@Override
					protected void onStop() {
						// TODO Auto-generated method stub
						super.onStop();
						
					}		
}