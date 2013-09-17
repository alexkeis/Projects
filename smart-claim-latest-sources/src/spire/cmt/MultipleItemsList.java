package spire.cmt;

import java.util.ArrayList;
import java.util.TreeSet;

import spire.cmt.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MultipleItemsList extends ListActivity {

    public MyCustomAdapter mAdapter;
    static int id_tab;
    ListView lvSimple;
    TextView txt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lodge_claim_your_details lgd = new Lodge_claim_your_details();
        Lodge_claim lg = new Lodge_claim();
        Lodge_claim_your_vehicle vehicle = new Lodge_claim_your_vehicle();
        Lodge_claim_your_insurer insurer = new Lodge_claim_your_insurer();
        Lodge_claim_smash_repair repair = new Lodge_claim_smash_repair();
        Log.d("mylog", String.valueOf(Lodge_claim.id));
        	
        mAdapter = new MyCustomAdapter();
        mAdapter.addSeparatorItem("Claim Type");
        if(id_tab == 0){
        mAdapter.addItem("Who's fault: Not My Foult");
        }
        if(id_tab == 1){
        mAdapter.addItem("Who's fault: My Foult");
        }
        if(id_tab == 2){
        mAdapter.addItem("Who's fault: Other");
        }
        mAdapter.addItem("Number of Vehicles Involed: " + lg.colvo);
        mAdapter.addSeparatorItem("Where and When");
        mAdapter.addItem("When?: "+ lg.names_znach[0]);
        mAdapter.addItem("Where?: "+ lg.str_l);
        mAdapter.addSeparatorItem("Your Details");
        for(int i = 0 ; i<lgd.names_title.length ; i ++)
        {
        	mAdapter.addItem(lgd.names_title[i]);
        }        
       mAdapter.addSeparatorItem("Your Vehicle");
       for(int i = 0 ; i<vehicle.names_title.length ; i ++)
       {
       	mAdapter.addItem(vehicle.names_title[i]);
       }  
        mAdapter.addSeparatorItem("Your Insurer");
        for(int i = 0 ; i<insurer.names_title.length ; i ++)
        {
        	mAdapter.addItem(insurer.names_title[i]);
        }  
        mAdapter.addSeparatorItem("Smash Repair");
        for(int i = 0 ; i<repair.names_title.length ; i ++)
        {
        	mAdapter.addItem(repair.names_title[i]);
        }  
        mAdapter.addSeparatorItem("Photos");
        mAdapter.addItem("Photos: Check Photos");
        
      
        setListAdapter(mAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_SEPARATOR2 = 2;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 3;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        public void addSeparatorItem(final String item) {
            mData.add(item);
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }
        public void addSeparatorItem2(final String item) {
            mData.add(item);
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }


        @Override
        public int getItemViewType(int position) {
            return mSeparatorsSet.contains(position)  ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
        	if(position==32)
        	{
        		Intent intent1 = new Intent();
        	    intent1.setClass(getApplicationContext(), /*Photo*/Attach_image.class);
        	    startActivity(intent1);
        	}
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {;
            ViewHolder holder = null;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.item1, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.item2, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
                    break;

                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(mData.get(position));
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
        public Button button;
    }
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	
	}
}
