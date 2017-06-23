package de.christian_heinisch.hilferundumskfz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = json.getJSONArray("DATA");
            for (int i = 0; i < jArray.length(); i++) {
                List<String> listenelement = new ArrayList<String>();
                JSONObject json_data = jArray.getJSONObject(i);
                listDataHeader.add(json_data.getString("Typ"));
                JSONArray arr = json_data.getJSONArray("Dokumente");
                for(int j = 0; j < arr.length(); j++) {
                    JSONObject innerData = arr.getJSONObject(j);
                    String Text = innerData.getString("Titel");
                    listenelement.add(Text);
                }

                listDataChild.put(listDataHeader.get(i), listenelement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;

        String json_file = "dokumente.json";
        try {

            InputStream is = getContext().getAssets().open(json_file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }






}
