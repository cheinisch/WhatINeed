package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
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

import de.christian_heinisch.hilferundumskfz.adapter.ExpandableListAdapter_Punkte;


/**
 * A simple {@link Fragment} subclass.
 */
public class BussgeldFragment extends Fragment {

    View rootview;
    ExpandableListAdapter_Punkte listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public BussgeldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_punkte, container, false);

        // get the listview
        expListView = (ExpandableListView) rootview.findViewById(R.id.lvExpPunkte);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter_Punkte(getContext(), listDataHeader, listDataChild);

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
                listDataHeader.add(json_data.getString("Vergehen"));
                JSONArray arr = json_data.getJSONArray("Stufe");
                for(int j = 0; j < arr.length(); j++) {
                    JSONObject innerData = arr.getJSONObject(j);
                    String Text_1 = innerData.getString("Ordnungswidrigkeit");
                    String Text_2 = innerData.getString("Punkte");
                    String Text_3 = innerData.getString("Geldstrafe");
                    String Text_4 = innerData.getString("Fahrverbot");

                    String Text = Text_1 + "#" + Text_2 + "#" + Text_3 + "#" + Text_4;

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

        String json_file = "punkte.json";
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
