package de.christian_heinisch.hilferundumskfz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.christian_heinisch.hilferundumskfz.data.CustomFehlerCodeAdapter_old;
import de.christian_heinisch.hilferundumskfz.data.ListItemOverview;

/**
 * A placeholder fragment containing a simple view.
 */
public class ErrorCodeFragment extends Fragment {

    View rootview;
    boolean mTwoPane;


    public ErrorCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_error_code, container, false);

        if (rootview.findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


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
        CustomFehlerCodeAdapter_old adapter = new CustomFehlerCodeAdapter_old(getActivity(), arrayOfUsers);
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
            String Bild_gross = obj.getString("Bild_gross");
            String Bild_klein = obj.getString("Bild_klein");
            String langtext = obj.getString("Langtext");
            listitems.add(new ListItemOverview(titel, beschreibung, Bild_gross, Bild_klein, langtext));
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
        String gross = listitem.getBild_gross();
        String fulltext = listitem.getLangtext();

        Bundle bundle = new Bundle();
        bundle.putString("Titelleiste", title);
        bundle.putString("Bild_gross", gross);
        bundle.putString("Beschreibung", fulltext);

        if(mTwoPane){
            ItemDetailFragment overviewFragment = new ItemDetailFragment();
            overviewFragment.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.item_detail_container,
                    overviewFragment,
                    overviewFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();
        }else{
            ItemDetailFragment overviewFragment = new ItemDetailFragment();
            overviewFragment.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_main,
                    overviewFragment,
                    overviewFragment.getTag()
            )
                    .addToBackStack(null)
                    .commit();
        }







    }
}
