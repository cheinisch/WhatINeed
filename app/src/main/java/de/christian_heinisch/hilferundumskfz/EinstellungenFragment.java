package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class EinstellungenFragment extends Fragment {

    View rootview;
    Button delete_table;
    private TankDataSource datasource;


    public EinstellungenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        datasource = new TankDataSource(getActivity());

        rootview = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        delete_table = (Button) rootview.findViewById(R.id.btnSettingDelete);


        delete_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.open();
                //datasource.deleteDatabase();
                datasource.close();
            }
        });

        return rootview;
    }

}
