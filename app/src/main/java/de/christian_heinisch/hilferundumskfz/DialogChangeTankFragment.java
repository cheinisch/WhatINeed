package de.christian_heinisch.hilferundumskfz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by chris on 11.07.2017.
 */

public class DialogChangeTankFragment extends DialogFragment {

    View rootview;
    private Button Delete;
    private Button Edit;
    private long id;

    public DialogChangeTankFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_change, null);

        // Set Buttons
        Delete = (Button) rootview.findViewById(R.id.buttonTankChangeDelete);
        Edit = (Button) rootview.findViewById(R.id.buttonTankChangeEdit);

        // Setze Buttonfunktionen

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).DialogEditTanken(id);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.tanken_dialog_change_titel)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                /*.setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something
                    }
                })*/
                .create();
    }

}
