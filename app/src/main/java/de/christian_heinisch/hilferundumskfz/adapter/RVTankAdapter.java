package de.christian_heinisch.hilferundumskfz.adapter;

/**
 * Created by chris on 28.06.2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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
import de.christian_heinisch.hilferundumskfz.TankFragment;
import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource;


public class RVTankAdapter extends RecyclerView.Adapter<TankObjectHolder> {

    private ArrayList<String> itemsPendingRemoval;
    private ArrayList<Tank> mDataset;
    private Context mContext;

    TankDataSource dataSource = new TankDataSource(mContext);

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public RVTankAdapter(Context context, ArrayList<Tank> myDataset) {
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public TankObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tank_item, parent, false);
        return new TankObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TankObjectHolder holder, final int position) {

        final String gesamt = mDataset.get(position).getEuro() + " €";
        final String liter = mDataset.get(position).getLiter() + " l";
        final String spritpreis = mDataset.get(position).getKilometer() + " €";
        final String monat = getMonthForInt(mDataset.get(position).getMonat());
        final int jahr = mDataset.get(position).getJahr();

        holder.ItemEinnahmen.setText(liter);
        holder.ItemGesamt.setText(gesamt);
        holder.ItemAusgaben.setText(spritpreis);
        holder.Monat.setText(monat);
        holder.Jahr.setText(jahr +"");

        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newFragment(mDataset.get(position).getMonat(), mDataset.get(position).getJahr());
            }
        });

    }

    private void newFragment(int monat, int jahr) {


        Bundle args = new Bundle();
        args.putInt("monat", monat);
        args.putInt("jahr", jahr);
        Fragment f = new TankDetail();
        FragmentManager fragmentManager;
        fragmentManager =((Activity) mContext).getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        f.setArguments(args);
        ft.replace(R.id.content_main, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void reload(){

        Fragment f = new TankFragment();
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