package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.hilferundumskfz.adapter.RVTankDetailAdapter;
import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource_old;


/**
 * A simple {@link Fragment} subclass.
 */
public class TankDetail extends Fragment {


    private View rootview;
    int jahr;
    int monat;
    TextView Bezahlt;
    TextView Liter;
    private RecyclerView mRecyclerView;
    private TankDataSource_old datasource;

    public TankDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        jahr = this.getArguments().getInt("jahr");
        monat = this.getArguments().getInt("monat");

        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tank_detail, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.floatingActionButtonTankenDetail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).DialogAddTanken();
            }
        });

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rvMoneyDetail);

        datasource = new TankDataSource_old(getActivity());

        Liter = (TextView) rootview.findViewById(R.id.textViewGesamtGetankt);
        Bezahlt = (TextView) rootview.findViewById(R.id.textViewGesamtBezahlt);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recyclerview für noch zu erledigende Listenelemente
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RVTankDetailAdapter rvAdapter = new RVTankDetailAdapter(getActivity(), getDataSet());
        mRecyclerView.setAdapter(rvAdapter);

    }

    private ArrayList<Tank> getDataSet() {

        int count = 0;
        ArrayList results = new ArrayList<Tank>();
        datasource.open();
        ArrayList<Tank> arrayOfTank = null;
        double bezahlt = 0;
        double getankt = 0;

        String newmonat;
        int tempmonat = monat + 1;
        if(tempmonat<10){
            newmonat = "0"+tempmonat;
        }else{
            newmonat = ""+tempmonat;
        }

        String startMonat =jahr+"-"+newmonat+"-01";
        String endMonat =jahr+"-"+newmonat+"-"+31;

        arrayOfTank = datasource.getTankforMonth(startMonat, endMonat);
        Tank obj = null;

        for(int j = 0; j < arrayOfTank.size(); j++)
        {
            obj = new Tank(arrayOfTank.get(j).getId(), arrayOfTank.get(j).getEuro(), arrayOfTank.get(j).getLiter(), arrayOfTank.get(j).getKilometer(), jahr, arrayOfTank.get(j).getMonat(), arrayOfTank.get(j).getTag());

            results.add(j, obj);
            bezahlt = bezahlt + arrayOfTank.get(j).getEuro();
            getankt = getankt + arrayOfTank.get(j).getLiter();
        }
        datasource.close();

        Bezahlt.setText("Bezahlt: " + round(bezahlt,2) + " €");
        Liter.setText("Getankt: " + round(getankt,2) + " l");

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
