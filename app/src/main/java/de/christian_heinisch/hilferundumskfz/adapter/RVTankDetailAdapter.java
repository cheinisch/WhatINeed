package de.christian_heinisch.hilferundumskfz.adapter;

/**
 * Created by chris on 28.06.2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import de.christian_heinisch.hilferundumskfz.MainActivity;
import de.christian_heinisch.hilferundumskfz.R;
import de.christian_heinisch.hilferundumskfz.TankDetail;
import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource;


public class RVTankDetailAdapter extends RecyclerView.Adapter<TankDetailObjectHolder> {

    private ArrayList<String> itemsPendingRemoval;
    private ArrayList<Tank> mDataset;
    private Context mContext;

    TankDataSource dataSource = new TankDataSource(mContext);

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public RVTankDetailAdapter(Context context, ArrayList<Tank> myDataset) {
        //mDataset = myDataset;
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();

        Collections.sort(mDataset, new Comparator<Tank>() {
            @Override
            public int compare(Tank item, Tank t1) {
                String s1 = item.getTag()+"";
                String s2 = t1.getTag()+"";
                return s1.compareToIgnoreCase(s2);
            }

        });


    }

    @Override
    public TankDetailObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tank_item_detail, parent, false);
        return new TankDetailObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TankDetailObjectHolder holder, final int position) {

        final String tag = mDataset.get(position).getTag()+"";
        final String monat = getMonthForInt(mDataset.get(position).getMonat());
        final String bezahlt = mDataset.get(position).getEuro() + " €";
        final String liter = mDataset.get(position).getLiter() + " l";
        final String kilometer = mDataset.get(position).getKilometer() + " km";
        final long id = mDataset.get(position).getId();


        holder.Tag.setText(tag);
        holder.Monat.setText(monat);
        holder.Bezahlt.setText(bezahlt);
        holder.Getankt.setText(liter);
        holder.Kilometer.setText(kilometer);


        holder.Layout.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                ((MainActivity) mContext).DialogChangeTanken(id);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void reload(){

        Fragment f = new TankDetail();
        FragmentManager fragmentManager;
        fragmentManager =((Activity) mContext).getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_main, f);
        ft.addToBackStack(null);
        ft.commit();

    }

    /**
     * Implementation of callback for getting updates on person list changes.
     */
    private class TankListCallback extends SortedList.Callback<Tank> {

        @Override
        public int compare(Tank t1, Tank t2) {
            int tag1 = t1.getTag();
            int tag2 = t2.getTag();
            String k1 = String.valueOf(t1.getKilometer());
            String k2 = String.valueOf(t2.getKilometer());

            int diff = tag1 - tag2;
            return (diff == 0) ? k1.compareTo(k2) : diff;
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemInserted(position);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRemoved(position);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
        }

        @Override
        public void onChanged(int position, int count) {
        }

        @Override
        public boolean areContentsTheSame(Tank oldItem, Tank newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Tank item1, Tank item2) {
            return false;
        }

    }

    private void edit(long l){

        //((StartActivity)mContext).DialogEditToDO(l);

    }

    String getMonthForInt(int num) {

        num = num - 1;
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }

        return month;
    }



}
