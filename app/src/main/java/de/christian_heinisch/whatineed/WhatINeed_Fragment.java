package de.christian_heinisch.whatineed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatINeed_Fragment extends Fragment {

    View rootview;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public WhatINeed_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_what_i_need, container, false);

        // get the listview
        expListView = (ExpandableListView) rootview.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return rootview;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Zulassung Neufahrzeug");
        listDataHeader.add("Zulassung Gebrauchtfahrzeug");
        /*listDataHeader.add("TÜV");
        listDataHeader.add("Coming Soon..");*/

        // Adding child data
        List<String> zulassung = new ArrayList<String>();
        zulassung.add("EG-Übereinstimmungsbescheinigung");
        zulassung.add("Personalausweis");
        zulassung.add("Elektronische Versicherungsbestätigung (eVB)");
        zulassung.add("Fahrzeugbrief/Zulassungsbescheinigung Teil II");
        zulassung.add("Einzugsermächtigung für Kfz-Steuer");
        zulassung.add("Handelsregisterauszug oder Gewerbeanmeldung bei Nutzung als Firmenfahrzeug");
        zulassung.add("Geld");

        List<String> zulassung_gebraucht = new ArrayList<String>();
        zulassung_gebraucht.add("Personalausweis");
        zulassung_gebraucht.add("Elektronische Versicherungsbestätigung (eVB)");
        zulassung_gebraucht.add("Fahrzeugbrief/Zulassungsbescheinigung Teil II");
        zulassung_gebraucht.add("Fahrzeugschein/Zulassungsbescheinigung Teil I");
        zulassung_gebraucht.add("HU-Bericht");
        zulassung_gebraucht.add("KFZ-Kennzeichen (Bei Kreiswechsel)");
        zulassung_gebraucht.add("Einzugsermächtigung für Kfz-Steuer");
        zulassung_gebraucht.add("Handelsregisterauszug oder Gewerbeanmeldung bei Nutzung als Firmenfahrzeug");
        zulassung_gebraucht.add("Geld");

        List<String> tuv = new ArrayList<String>();
        tuv.add("The Conjuring");
        tuv.add("Despicable Me 2");
        tuv.add("Turbo");
        tuv.add("Grown Ups 2");
        tuv.add("Red 2");
        tuv.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), zulassung); // Header, Child data
        listDataChild.put(listDataHeader.get(1), zulassung_gebraucht);
        //listDataChild.put(listDataHeader.get(2), comingSoon);
    }

}
