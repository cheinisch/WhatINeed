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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

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
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public TankDetailObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tank_item_detail, parent, false);
        return new TankDetailObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TankDetailObjectHolder holder, final int position) {


        final String tag = mDataset.get(position).getTag()+"";
        final String monat = getMonthForInt(mDataset.get(position).getTag());
        holder.Tag.setText(tag);
        holder.Monat.setText(monat);

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

    private void edit(long l){

        //((StartActivity)mContext).DialogEditToDO(l);

    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }

        return month;
    }



}
