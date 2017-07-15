package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import static org.xmlpull.v1.XmlPullParser.TYPES;


/**
 * A simple {@link Fragment} subclass.
 */
public class EinstellungenFragment extends Fragment {

    View rootview;
    Button delete_table;
    private Button save_table;
    private Button restore_table;
    private TextView headline_backup;


    public EinstellungenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        delete_table = (Button) rootview.findViewById(R.id.btnSettingDelete);
        save_table = (Button) rootview.findViewById(R.id.btnSettingSave);
        restore_table = (Button) rootview.findViewById(R.id.btnSettingRestore);
        headline_backup = (TextView) rootview.findViewById(R.id.textViewSettingsDatensicherung);
        final Spinner spinner = (Spinner) rootview.findViewById(R.id.spinner_country);

        // Setzte Default WErt des Spinners
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String country = prefs.getString("Country", "de");
        int index = 0;
        String[] TYPES = getResources().getStringArray(R.array.country_values);
        for (int i=0;i<TYPES.length;i++) {
            if (TYPES[i].equals(country)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);


        String Country = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                int spinner_pos = spinner.getSelectedItemPosition();
                String[] country_values = getResources().getStringArray(R.array.country_values);
                String locale = country_values[spinner_pos];
                System.out.println("Country: " + locale);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Country", locale);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do Nothing
            }
        });



        delete_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).DialogDeleteHint();
            }
        });

        hideelements();

        return rootview;
    }


    private void hideelements(){



        if(BuildConfig.FLAVOR.equalsIgnoreCase("lite") ||BuildConfig.FLAVOR.equalsIgnoreCase("pro")) {
            // Setzte Backup Items auf Unsichtbar, wenn es die Lite oder Pro Version ist.
            save_table.setVisibility(rootview.GONE);
            restore_table.setVisibility(rootview.GONE);
            headline_backup.setVisibility(rootview.GONE);
        }

    }

}
