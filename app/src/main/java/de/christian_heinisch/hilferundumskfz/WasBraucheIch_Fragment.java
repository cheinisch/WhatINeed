package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.Locale;

import de.christian_heinisch.hilferundumskfz.adapter.ExpandableListAdapter_Need;


/**
 * A simple {@link Fragment} subclass.
 */
public class WasBraucheIch_Fragment extends Fragment {

    View rootview;
    ExpandableListAdapter_Need listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;




    public WasBraucheIch_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_was_brauche_ich, container, false);

        // get the listview
        expListView = (ExpandableListView) rootview.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter_Need(getActivity(), listDataHeader, listDataChild);

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
        // Systemsprache (für Spätere Übersetzungen)
        String locale = Locale.getDefault().getLanguage();
        if(locale.equalsIgnoreCase("de")){

        }else{
            locale = "de";
        }


        // Part für das gewählte LAND
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String country = prefs.getString("Country", "de");


        String json_file = "dokumente-"+country+"-"+locale+".json";
        try {

            InputStream is = getActivity().getAssets().open(json_file);
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
