package de.christian_heinisch.hilferundumskfz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDeleteHintFragment extends DialogFragment {

    View rootview;
    private TankDataSource datasource;


    public DialogDeleteHintFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        datasource = new TankDataSource(getActivity());

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_delete_hinweis, null);



        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.delete_title)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Löscht die Daten aus der Datenbank
                        delete();

                    }
                })
                .create();
    }

    private void delete(){
        datasource.open();
        datasource.deleteDatabase();
        datasource.close();
    }


}
