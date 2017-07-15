package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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
