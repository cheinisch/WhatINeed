package de.christian_heinisch.whatineed;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.christian_heinisch.whatineed.data.CustomFehlerCodeAdapter;
import de.christian_heinisch.whatineed.data.ListItemOverview;

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


        try {
            populateUserslist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootview;
    }

    public void populateUserslist() throws JSONException {

        MainActivity activity = new MainActivity();

        ArrayList<ListItemOverview> arrayOfUsers = null;
        arrayOfUsers = getContent();
        CustomFehlerCodeAdapter adapter = new CustomFehlerCodeAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootview.findViewById(R.id.listOverview);
        listView.setAdapter(adapter);

        //TODO: handle title, description, url, fulltext of listitem to openIten(...)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItemOverview listitem = (ListItemOverview) parent.getAdapter().getItem(position);
                openItem(view, listitem);
            }
        });


    }
    public ArrayList<ListItemOverview> getContent() throws JSONException {
        ArrayList<ListItemOverview> listitems = new ArrayList<ListItemOverview>();

        // Läd JSON Daten aus der Datei in ein Objekt
        JSONObject jsondata = new JSONObject(loadJSONFromAsset());


        // Läd bestimmte Daten aus dem Objekt in ein Array
        JSONArray jsonarraylist = jsondata.getJSONArray("listdata");

        for (int i = 0; i < jsonarraylist.length(); i++) {
            JSONObject obj = jsonarraylist.getJSONObject(i);

            String titel = obj.getString("Titel");
            String beschreibung = obj.getString("Beschreibung");
            String URL = obj.getString("URL");
            String langtext = obj.getString("Langtext");
            listitems.add(new ListItemOverview(titel, beschreibung, URL, langtext));
        }
        return listitems;
    }

    public String loadJSONFromAsset() {
        String json = null;

        String json_file = "data.json";
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

    public void openItem(View view, ListItemOverview listitem){

        String title = listitem.getName();
        String description = listitem.getBeschreibung();
        String url = listitem.getBild();
        String fulltext = listitem.getLangtext();

        // Erstelle einen neuen Intent und weise ihm eine Actvity zu
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);

        //Werte an DetailActivity übergeben
        intent.putExtra("Titelleiste", title);
        intent.putExtra("URL", url);
        intent.putExtra("Beschreibung", fulltext);

        // Starte Activity
        getContext().startActivity(intent);

    }
}
