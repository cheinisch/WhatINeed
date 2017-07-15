package de.christian_heinisch.hilferundumskfz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * Created by chris on 11.07.2017.
 */

public class DialogChangeTankFragment extends DialogFragment {

    View rootview;
    AlertDialog alertDialog;
    private Button Delete;
    private Button Edit;
    private long id;
    private int counter;

    public DialogChangeTankFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        id = this.getArguments().getLong("id");
        counter = this.getArguments().getInt("count",1);

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_change, null);

        // Set Buttons
        Delete = (Button) rootview.findViewById(R.id.buttonTankChangeDelete);
        Edit = (Button) rootview.findViewById(R.id.buttonTankChangeEdit);

        alertDialog = new AlertDialog.Builder(getActivity())
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


        // Setze Buttonfunktionen

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id, counter);

                alertDialog.dismiss();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).DialogEditTanken(id);
                alertDialog.dismiss();
            }
        });




        return alertDialog;
    }

    public void delete(long id, int counter){

        TankDataSource dataSource = new TankDataSource(getActivity());
        dataSource.open();
        dataSource.deleteTank(id);
        dataSource.close();

        if(counter == 1){
            ((MainActivity)getActivity()).tanken();
        }else{
            ((MainActivity)getActivity()).tanken();
        }

    }

}
