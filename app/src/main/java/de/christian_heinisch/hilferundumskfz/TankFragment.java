package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.christian_heinisch.hilferundumskfz.adapter.RVTankAdapter;
import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class TankFragment extends Fragment {


    View rootview;
    RecyclerView mRecyclerView;
    private TankDataSource datasource;


    public TankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tank, container, false);


        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);



        datasource = new TankDataSource(getActivity());

        /*datasource.open();
        datasource.createTank(45,72.2,100.0,"2017-07-18");
        datasource.createTank(45,62.2,100.0,"2017-06-18");
        datasource.close();*/


        return rootview;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Recyclerview für noch zu erledigende Listenelemente
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RVTankAdapter rvAdapter = new RVTankAdapter(getActivity(), getDataSet());
        mRecyclerView.setAdapter(rvAdapter);

    }

    private ArrayList<Tank> getDataSet() {

        int startJahr = 2016;
        int endJahr = 2017;
        int count = 0;
        double gesamt = 0;

        ArrayList results = new ArrayList<Tank>();
        datasource.open();
        ArrayList<Tank> arrayOftank = null;

        for(int jahr = startJahr; jahr <= endJahr; jahr++){

            for(int monat =0; monat <= 11; monat++){

                double euro = 0;
                double liter = 0;
                double spritpreis = 0;

                String newmonat;
                int tempmonat = monat + 1;
                if(tempmonat<10){
                    newmonat = "0"+tempmonat;
                }else{
                    newmonat = ""+tempmonat;
                }

                String startMonat =jahr+"-"+newmonat+"-01";
                String endMonat =jahr+"-"+newmonat+"-"+31;


                arrayOftank = datasource.getTankforMonth(startMonat, endMonat);


                for(int i = 0; i < arrayOftank.size(); i++)
                {
                        euro = euro + arrayOftank.get(i).getEuro();
                        liter = liter + arrayOftank.get(i).getLiter();


                }

                if(euro > 1) {

                    euro = round(euro, 2);

                    spritpreis = euro / liter;

                    spritpreis = round(spritpreis, 2);

                    Tank obj = new Tank(count, euro, liter, spritpreis, jahr, monat, 0);
                    //results.add(count, obj);
                    results.add(count, obj);
                }
            }

        }
        datasource.close();
        return results;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
