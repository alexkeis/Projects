package spire.cmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class DiskCache {
	public static final String SIGNS_PATH = "/CMT Cache/signs";
	private String abs_path = "";
	private Context ctx;

	public DiskCache(Context ctx) {
		this.ctx = ctx;
		if (isSdExist()) {
			File sdPath = Environment.getExternalStorageDirectory();
			abs_path = sdPath.getAbsolutePath();
		}
	}

	public void writeToDisk(String file_name, Bitmap image) {

		if (image == null)
			return;
		try {

			File saveDir = new File(SIGNS_PATH);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}

			FileOutputStream os = new FileOutputStream(file_name);
			image.compress(CompressFormat.JPEG, 80, os);
			os.flush();
			os.close();
			image = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Bitmap readFromDisk(String file_name) {
		Bitmap bm = null;
		// try {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		bm = BitmapFactory.decodeFile(file_name);

		return bm;
	}

	public void saveImageToDisk(Bitmap image, String name, String IMAGES_PATH, boolean isSign) {
		try {
			File saveDir = new File(IMAGES_PATH);//abs_path +  dima
			Log.d("Lognn при сохранении", saveDir.toString());

			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			String path =  IMAGES_PATH;

			FileOutputStream os;
				os = new FileOutputStream(path + "/" + name);
			image.compress(CompressFormat.JPEG, 80, os);
			os.flush();
			os.close();
			image = null;
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap loadImageFromDisk(String name, boolean isSign) {

		Bitmap bm = null;
		try {
			if (abs_path.equals("")) {
				FileInputStream is = ctx.openFileInput(name);
				bm = BitmapFactory.decodeStream(is);
			} else {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				String path = SIGNS_PATH;
				bm = BitmapFactory.decodeFile(abs_path + path + "/" + name,
						options);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bm;
	}

	public boolean isSdExist() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}
}