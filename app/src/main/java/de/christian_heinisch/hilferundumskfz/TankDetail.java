package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.hilferundumskfz.adapter.RVTankDetailAdapter;
import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class TankDetail extends Fragment {


    private View rootview;
    int jahr;
    int monat;
    TextView gesamtText;
    TextView ausgabe;
    TextView einnahme;
    private RecyclerView mRecyclerView;
    private TankDataSource datasource;

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

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rvMoneyDetail);

        datasource = new TankDataSource(getActivity());

        gesamtText = (TextView) rootview.findViewById(R.id.textView_Gesamt);
        einnahme = (TextView) rootview.findViewById(R.id.textView_Einnahme);
        ausgabe = (TextView) rootview.findViewById(R.id.textView_Ausgabe);

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
        double einnahmen = 0;
        double ausgaben = 0;
        double newgesamt;

        String newmonat;
        int tempmonat = monat + 1;
        if(tempmonat<10){
            newmonat = "0"+tempmonat;
        }else{
            newmonat = ""+tempmonat;
        }

        String startMonat =jahr+"-"+newmonat+"-01";
        String endMonat =jahr+"-"+newmonat+"-"+31;

        System.out.println("Monat" + startMonat);


        arrayOfTank = datasource.getTankforMonth(startMonat, endMonat);
        Tank obj = null;

        for(int j = 0; j < arrayOfTank.size(); j++)
        {


            obj = new Tank(count, arrayOfTank.get(j).getEuro(), arrayOfTank.get(j).getLiter(), arrayOfTank.get(j).getKilometer(), jahr, arrayOfTank.get(j).getMonat(), arrayOfTank.get(j).getTag());

            results.add(count, obj);
            count = count + 1;
        }
        datasource.close();

        return results;
    }

}
