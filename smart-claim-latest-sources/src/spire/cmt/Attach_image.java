package spire.cmt;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Attach_image extends Activity implements
		android.view.View.OnClickListener {

	String IMAGES_PATH;
	private Bitmap[] photoArray = new Bitmap[10];
	final int CHOOSE_PICTURE_DIALOG = 3;
	final int PICK_FROM_CAMERA = 0;
	final int PICK_FROM_FILE = 1;
	final int CROP_FROM_CAMERA = 2;
	String[] path = { "Photo Camera", "Photo Library" };
	private File file;
	private Uri mImageCaptureUri;
	int counter = 1;
	ActionMode actionMode;
	RelativeLayout mainLayout;
	boolean check = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attach_image);
		IMAGES_PATH = Lodge_claim.path_d.toString();

		File imagePath = new File(IMAGES_PATH);
		File[] file = imagePath.listFiles();

		for (int i = 0; i < photoArray.length; i++)
			photoArray[i] = null;

		try {

			for (int i = 0; i < file.length; i++) {
				if (file[i].toString()
						.substring(file[i].toString().length() - 1).equals("g")) {

					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					photoArray[counter] = BitmapFactory.decodeFile(
							file[i].toString(), options);
					file[i].delete();
					counter++;
				}

			}

		} catch (Exception e) {
			counter = 1;
		}

		createLayout(counter);
	}

	@Override
	public void onBackPressed() {
		for (int i = 1; i < 10; i++) {

			if (photoArray[i] != null) {
				Log.d("Log", i + "");
				saveImage(photoArray[i], "Image" + i + ".jpg", IMAGES_PATH,
						false);

			}
		}
		finish();
	}

	protected void saveImage(Bitmap image, String name, String IMAGES_PATH,
			boolean isSign) {
		DiskCache dc = new DiskCache(this);
		dc.saveImageToDisk(image, name, IMAGES_PATH, isSign);
	}

	void createLayout(int counter) {
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		mainLayout.removeAllViews();
		Display display = getWindowManager().getDefaultDisplay();

		float x = 0, y = 0;
		int width = (display.getWidth() / 3), height = (display.getHeight() / 3 - 25);
		for (int i = 1; i < counter; i++) {

			RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(
					width, height);

			RelativeLayout conteinerView = new RelativeLayout(this);
			conteinerView.setId(i);
			conteinerView.setLayoutParams(viewParams);
			conteinerView.setX(x);
			conteinerView.setY(y);
			if (i % 3 == 0 && i != 0) {
				x = 0;
				y += height;

			} else {
				x += width;

			}

			mainLayout.addView(conteinerView);

			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(viewParams);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			imageView.setImageBitmap(photoArray[i]);
			imageView.setId(i + 10);
			conteinerView.addView(imageView);
			if (check == true) {
				CheckBox checkBox = new CheckBox(this);
				checkBox.setId(i + 100);
				RelativeLayout.LayoutParams checkBoxParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				checkBoxParams
						.addRule(android.widget.RelativeLayout.ALIGN_PARENT_RIGHT);
				checkBoxParams
						.addRule(android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM);
				checkBox.setVisibility(View.VISIBLE);
				conteinerView.addView(checkBox, checkBoxParams);
				imageView.setOnClickListener(this);

			}
		}

		if (check == false) {
			if (counter < 10) {
				Log.d("Log", "Button add");
				RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(
						width, height);

				RelativeLayout conteinerView = new RelativeLayout(this);
				conteinerView.setId(counter);
				conteinerView.setLayoutParams(viewParams);
				conteinerView.setX(x);
				conteinerView.setY(y);
				if (x + width > display.getWidth()) {
					x = 0;
					y += height;

				} else {
					x += width;

				}

				mainLayout.addView(conteinerView);

				ImageView imageView = new ImageView(this);

				imageView.setId(counter + 10);
				imageView.setLayoutParams(viewParams);
				imageView.setScaleType(ScaleType.CENTER_INSIDE);
				imageView.setImageResource(R.drawable.camera_new);
				imageView.setOnClickListener(this);
				conteinerView.addView(imageView);
			}
		}
	}

	@Override
	public void onClick(View v) {
		Log.d("Log", v.getId() + "");
		// TODO Auto-generated method stub
		if (check == true) {
			CheckBox checkBox = (CheckBox) findViewById(v.getId() - 10 + 100);
			checkBox.setChecked(!checkBox.isChecked());
		} else {
			if (Camera.getNumberOfCameras() > 0) {
				showDialog(CHOOSE_PICTURE_DIALOG);
			}

			else {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Complete action using"),
						PICK_FROM_FILE);
			}
		}

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		switch (id) {

		case CHOOSE_PICTURE_DIALOG:
			adb.setTitle("Choose an image");
			adb.setItems(path, imageChooser);
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
				for (int i = 1; i < 10; i++) {
					if (photoArray[i] == null) {
						photoArray[i] = extras.getParcelable("data");
						createLayout(i + 1);
						counter = i + 1;

						break;
					}
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

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionMode == null)
			actionMode = startActionMode(callback);
		else
			actionMode.finish();
		check = true;
		createLayout(counter);

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
			for (int i = 101; i < counter + 100; i++) {
				CheckBox checkBox = (CheckBox) findViewById(i);
				if (checkBox.isChecked() == true) {
					photoArray[i - 100] = null;
					Log.d("Log", i + " : null");
				}
			}

			// for (int i = 1; i < 10; i++) {
			// if (photoArray[i] == null && i + 1 < 10) {
			// for (int b = i + 1; b < 10; b++) {
			// if (photoArray[b] != null) {
			// photoArray[i] = photoArray[b];
			// photoArray[b] = null;
			// }
			// }
			// }
			//
			// }
			Bitmap[] photoArray_old = new Bitmap[10];
			int count = 1;
			for (int i = 1; i < 10; i++) {
				photoArray_old[i]=null;
				if (photoArray[i] != null) {
					photoArray_old[count] = photoArray[i];
					count++;
				}
			}
			for (int i = 1; i < 10; i++) {
				photoArray[i] = photoArray_old[i];

			}

			counter = 1;
			for (int i = 1; i < 10; i++) {
				if (photoArray[i] != null)
					counter++;
			}

			check = true;
			createLayout(counter);
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// check_invisible();
			// clicked = false;
			// image_v();
			check = false;
			createLayout(counter);
			actionMode = null;
		}

	};

}
