package de.christian_heinisch.hilferundumskfz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class BussgeldRechnerFragment extends Fragment {

    View rootview;
    ExpandableListAdapter_Punkte listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button button;
    EditText maxGeschwindigkeit;
    EditText eigeneGeschwindigkeit;
    int intEigeneGeschwindigkeit;
    int intErlaubteGeschwindigkeit;


    public BussgeldRechnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_bussgeld_rechner, container, false);

        //Add Button und Sonstige Felder
        button = (Button) rootview.findViewById(R.id.bussgeld_rechner_button);
        maxGeschwindigkeit = (EditText) rootview.findViewById(R.id.editText_Bussgeldrechner_erlaubte_Geschwindigkeit);
        eigeneGeschwindigkeit = (EditText) rootview.findViewById(R.id.editText_Bussgeldrechner_eigene_Geschwindigkeit);



        // Set onClickListener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                // Berechne die Strafe
                berechnen();

            }
        });

        return rootview;
    }

    private void berechnen() {


        // hole die Textfelder zum Rechnen
        intEigeneGeschwindigkeit = Integer.parseInt(eigeneGeschwindigkeit.getText().toString());
        intErlaubteGeschwindigkeit = Integer.parseInt(maxGeschwindigkeit.getText().toString());


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = json.getJSONArray("DATA");
            for (int i = 0; i < jArray.length(); i++) {
                List<String> listenelement = new ArrayList<String>();
                JSONObject json_data = jArray.getJSONObject(i);
                listDataHeader.add(json_data.getString("Vergehen"));
                if(json_data.getString("Typ").equalsIgnoreCase("innerhalb")) {
                    JSONArray arr = json_data.getJSONArray("Stufe");
                    for (int j = 0; j < arr.length(); j++) {

                        JSONObject innerData = arr.getJSONObject(j);

                        int differenz = intEigeneGeschwindigkeit - intErlaubteGeschwindigkeit;

                        int min = Integer.parseInt(innerData.getString("min"));
                        int max = Integer.parseInt(innerData.getString("max"));

                        if ( differenz >= min && differenz <= max ) {
                            System.out.println("es kostet: " + innerData.getString("Geldstrafe"));
                        }
                    }
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
