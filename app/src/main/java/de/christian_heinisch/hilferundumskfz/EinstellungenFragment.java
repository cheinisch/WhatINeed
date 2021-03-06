package de.christian_heinisch.hilferundumskfz;


import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EinstellungenFragment extends Fragment {

    View rootview;
    Button delete_table;
    private Button save_table;
    private Button restore_table;
    private TextView headline_backup;
    String packagename;
    String databasename = "wasbraucheich.db";


    public EinstellungenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        packagename = getActivity().getPackageName();

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

        save_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
            }
        });

        restore_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //importDB();
                checkPermission();
                openFolder();
            }
        });

        return rootview;
    }


    private void hideelements(){



        if(BuildConfig.FLAVOR.equalsIgnoreCase("lite")) {
            // Setzte Backup Items auf Unsichtbar, wenn es die Lite oder Pro Version ist.
            save_table.setVisibility(rootview.GONE);
            restore_table.setVisibility(rootview.GONE);
            headline_backup.setVisibility(rootview.GONE);
        }

    }
    /*
    Datenimport
     */
    public void openFolder(){
        /*
        Startet den Filebrowser
         */
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), 1);

    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){

        /*
        Inizialisiere die Datenbank um DB fehler zu umgehen
         */
        TankDataSource datasource = new TankDataSource(getActivity());
        datasource.open();
        datasource.close();

        /*
        Beginne mit dem Daten import
         */

        super.onActivityResult(reqCode, resCode, data);

        if (reqCode == 1 && resCode == RESULT_OK && data != null) {
            Uri currFileURI = data.getData();
            String[] path=currFileURI.getPath().split(":");
            try {
                File sd = Environment.getExternalStorageDirectory();

                File newdata  = Environment.getDataDirectory();


                if (sd.canWrite()) {

                    String  currentDBPath= "//data//" + packagename
                            + "//databases//" + databasename;
                    //String backupDBPath  = "/hilferundumsauto/database.db";
                    String backupDBPath = "/" + path[1];
                    System.out.println("Pfad: " + backupDBPath);
                    File  backupDB= new File(newdata, currentDBPath);
                    File currentDB  = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getActivity(), getString(R.string.einstellungen_restore_message),
                            Toast.LENGTH_LONG).show();

                }else{
                    System.out.println("Zugriff auf SD nicht möglich " + Environment.getRootDirectory());
                }
            } catch (Exception e) {

                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                        .show();
                System.out.println("Fehler: " + e.toString());

            }
        }
    }

    //exporting database
    private void exportDB() {

        // Prüfe Zugriffsrechte
        checkPermission();

        File direct = new File(Environment.getExternalStorageDirectory() + "/hilferundumsauto");

        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
                System.out.println("Ordner erstellt");
            }

        }


        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            /*
            * Setze das aktuelle Datum als String
            */

            Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();

            DateFormat date = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss");

            String localTime = date.format(currentLocalTime);


            if (sd.canWrite()) {

                System.out.println("Test 3");

                String  currentDBPath= "//data//" + packagename
                        + "//databases//" + databasename;
                String backupDBPath  = "/hilferundumsauto/database-"+localTime+".db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), getString(R.string.einstellungen_backup_message),
                        Toast.LENGTH_LONG).show();

            }else{
                System.out.println("Zugriff auf SD nicht möglich" + Environment.getRootDirectory());
            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();
            System.out.println(e.toString());

        }
    }

    public void checkPermission(){
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }
    }

    private class RequestMaker {
    }
}
