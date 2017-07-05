package de.christian_heinisch.hilferundumskfz;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button button;
    EditText maxGeschwindigkeit;
    EditText eigeneGeschwindigkeit;
    int intEigeneGeschwindigkeit;
    int intErlaubteGeschwindigkeit;
    TextView Punkte;
    TextView Fahrverbot;
    TextView Geldstrafe;
    CheckBox toleranz;
    int differenz;
    RadioGroup auswahl;
    RadioButton radioButton;


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
        Punkte = (TextView) rootview.findViewById(R.id.textView_Bussgeldrechner_Punkte);
        Geldstrafe = (TextView) rootview.findViewById(R.id.textView_Bussgeldrechner_Bussgeld);
        Fahrverbot = (TextView) rootview.findViewById(R.id.textView_Bussgeldrechner_Fahrverbot);
        toleranz = (CheckBox) rootview.findViewById(R.id.checkBox_Bussgeldrechner);
        auswahl = (RadioGroup) rootview.findViewById(R.id.radioGroup_Bussgeldrechner);



        // Set onClickListener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                // Verstecke die tastatur

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rootview.getWindowToken(), 0);


                //Prüfe ob alle Felder ausgefüllt wurden
                if(eigeneGeschwindigkeit.getText().toString().equalsIgnoreCase("") || maxGeschwindigkeit.getText().toString().equalsIgnoreCase("")){

                    Toast.makeText(getActivity(), "Alle Felder ausfüllen!",
                            Toast.LENGTH_LONG).show();

                }else {
                    berechnen();
                }
            }
        });

        return rootview;
    }

    private void berechnen() {

        // Erstelle String für die Auswahl
        String wo = "";


        // hole die Textfelder zum Rechnen
        intEigeneGeschwindigkeit = Integer.parseInt(eigeneGeschwindigkeit.getText().toString());
        intErlaubteGeschwindigkeit = Integer.parseInt(maxGeschwindigkeit.getText().toString());

        if(toleranz.isChecked()){

                            if (intEigeneGeschwindigkeit > 100) {

                                long speed = Math.round(intEigeneGeschwindigkeit / 1.03);
                                intEigeneGeschwindigkeit = (int) speed;

                            } else {
                                intEigeneGeschwindigkeit = intEigeneGeschwindigkeit - 3;
                            }

            //intEigeneGeschwindigkeit = intEigeneGeschwindigkeit - 3;
        }

        if(intEigeneGeschwindigkeit < intErlaubteGeschwindigkeit)
        {
            differenz = 0;
        }else{
            differenz = intEigeneGeschwindigkeit - intErlaubteGeschwindigkeit;
        }

        int selectedId = auswahl.getCheckedRadioButtonId();

        switch(selectedId){
            case R.id.radioButton_Bussgeld_innerhalb:
                // do operations specific to this selection
                wo = "innerhalb";
                break;
            case R.id.radioButton_Bussgeld_ausserhalb:
                // do operations specific to this selection
                wo = "ausserhalb";

                break;

            default:
                // Wenn keine auswahl getroffen wurde, nehme an, es sein innerhalb geschlossener ortschaft passiert
                wo = "innerhalb";
                break;
        }

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = json.getJSONArray("DATA");
            for (int i = 0; i < jArray.length(); i++) {
                List<String> listenelement = new ArrayList<String>();
                JSONObject json_data = jArray.getJSONObject(i);
                listDataHeader.add(json_data.getString("Vergehen"));
                if(json_data.getString("Typ").equalsIgnoreCase(wo)) {
                    JSONArray arr = json_data.getJSONArray("Stufe");
                    for (int j = 0; j < arr.length(); j++) {

                        JSONObject innerData = arr.getJSONObject(j);




                        int min = Integer.parseInt(innerData.getString("min"));
                        int max = Integer.parseInt(innerData.getString("max"));

                        if ( differenz >= min && differenz <= max ) {
                            Fahrverbot.setText(innerData.getString("Fahrverbot"));
                            Punkte.setText(innerData.getString("Punkte"));
                            Geldstrafe.setText(innerData.getString("Geldstrafe"));
                            break;
                        }else if(differenz == 0){
                            Fahrverbot.setText("");
                            Punkte.setText("");
                            Geldstrafe.setText("");
                        }
                    }

                }
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
