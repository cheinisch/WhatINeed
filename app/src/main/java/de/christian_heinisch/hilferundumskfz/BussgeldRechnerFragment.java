package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.util.Locale;


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
    private RadioButton autobahn;
    String country;
    private TextView Differenz;


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
        Differenz = (TextView) rootview.findViewById(R.id.textView_Bussgeldrechner_differenz);
        toleranz = (CheckBox) rootview.findViewById(R.id.checkBox_Bussgeldrechner);
        auswahl = (RadioGroup) rootview.findViewById(R.id.radioGroup_Bussgeldrechner);

        autobahn = (RadioButton) rootview.findViewById(R.id.radioButton_Bussgeld_autobahn);

        hideautobahn();



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

    private void hideautobahn() {

        /*
        Weil in der Schweiz es auch Bussgelder für die Autobahn gibt,
        wird die Autobahn in Deutschland und Österreich ausgeblendet.
         */

        /*
        Hole den String aus den Countrypreferences
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        country = prefs.getString("Country", "de");

        /*
        Wenn der String nicht "ch" ist, blende die Autobahn aus
         */
        if(!country.equalsIgnoreCase("ch"))
        {
            autobahn.setVisibility(View.GONE);
        }

    }

    private void berechnen() {

        // Erstelle String für die Auswahl
        String wo = "";
        int toleranztempo = 0;
        int toleranzmessung = 0;


        // hole die Textfelder zum Rechnen
        intEigeneGeschwindigkeit = Integer.parseInt(eigeneGeschwindigkeit.getText().toString());
        intErlaubteGeschwindigkeit = Integer.parseInt(maxGeschwindigkeit.getText().toString());

        // Hole die Toleranzwerte für die Jeweiligen Länder
        if(country.equalsIgnoreCase("de"))
        {
            toleranzmessung = 3;
            toleranztempo = 100;
        }else if(country.equalsIgnoreCase("at"))
        {
            toleranzmessung = 3;
            toleranztempo = 80;
        }else if(country.equalsIgnoreCase("ch"))
        {
            /*
                (Die Schweiz macht es besonders super
                Quelle: https://www.comparis.ch/autoversicherung/junglenker/bussen)
                Die unterschiedlichen Toleranztempowerte dienen nur dazu, um zu vermeiden, das doch prozentual gerechnet wird
                Die Schweiz kennt keine Prozente, sondern nur KM/H Abzug
             */

            if(intEigeneGeschwindigkeit <= 100) {
                toleranzmessung = 3;
                toleranztempo = 200;
            }else if(intEigeneGeschwindigkeit > 100 && intEigeneGeschwindigkeit <= 150){
                toleranzmessung = 4;
                toleranztempo = 200;
            }else{
                toleranzmessung = 5;
                toleranztempo = 999;
            }
        }

        if(toleranz.isChecked()){

            if (intEigeneGeschwindigkeit > toleranztempo) {

                long speed = Math.round((intEigeneGeschwindigkeit / 100 * toleranzmessung) + intEigeneGeschwindigkeit);
                intEigeneGeschwindigkeit = (int) speed;

            } else {
                intEigeneGeschwindigkeit = intEigeneGeschwindigkeit - toleranzmessung;
            }

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

            case R.id.radioButton_Bussgeld_autobahn:
                // do operations specific to this selection
                wo = "autobahn";

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
            String locale = Locale.getDefault().getLanguage();
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
                            Differenz.setText(differenz + " km/h");
                            break;
                        }else if(differenz <= 0){
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

        // Systemsprache (für Spätere Übersetzungen)
        String locale = Locale.getDefault().getLanguage();
        if(locale.equalsIgnoreCase("de")){

        }else{
            locale = "de";
        }


        // Part für das gewählte LAND
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String country = prefs.getString("Country", "de");


        String json_file = "vergehen-"+country+"-"+locale+".json";
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
