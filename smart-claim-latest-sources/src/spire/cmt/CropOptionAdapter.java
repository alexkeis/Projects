package spire.cmt;

import java.util.ArrayList;

import spire.cmt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CropOptionAdapter extends ArrayAdapter<CropOption> {
	private ArrayList<CropOption> mOptions;
	private LayoutInflater mInflater;

	public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
		super(context, R.layout.crop_selector, options);

		mOptions = options;

		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.crop_selector, null);

		CropOption item = mOptions.get(position);

		if (item != null) {
			((ImageView) convertView
					.findViewById(item.id))
					.setImageDrawable(item.icon);

			return convertView;
		}

		return null;
	}
}
