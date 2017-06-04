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
 * A placeholder fragment containing a simple view.
 */
public class ErrorCodeFragment extends Fragment {

    View rootview;
    ErrorExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public ErrorCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_error_code, container, false);


            // get the listview
        expListView = (ExpandableListView) rootview.findViewById(R.id.lvExpError);

        // preparing list data
        prepareListData();

        listAdapter = new ErrorExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
            expListView.setAdapter(listAdapter);

        return rootview;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Rote Kontrollleuchten");
        listDataHeader.add("Zulassung Gebrauchtfahrzeug");
        /*listDataHeader.add("TÜV");
        listDataHeader.add("Coming Soon..");*/

        // Adding child data
        List<String> kontrollleuchte_rot = new ArrayList<String>();
        kontrollleuchte_rot.add("Motorkontrollleuchte");


        List<String> kontrollleuchte_gelb = new ArrayList<String>();
        kontrollleuchte_gelb.add("Tanksymbol");


        listDataChild.put(listDataHeader.get(0), kontrollleuchte_rot); // Header, Child data
        listDataChild.put(listDataHeader.get(1), kontrollleuchte_gelb);
    }
}
