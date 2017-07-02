package de.christian_heinisch.hilferundumskfz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogReleaseNotesFragment extends DialogFragment {

    View rootview;
    TextView releaseText;
    String Text;


    public DialogReleaseNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_alte_version, null);
        releaseText = (TextView) rootview.findViewById(R.id.textViewReleaseNotes);

        // LESE Releasenotes ein
        try {
            Text = readFromAssets(getActivity(),"releasenotes.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        releaseText.setText(Text);


        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.release_title)
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })*/
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)

                    }
                })
                .create();
    }

    public static String readFromAssets(Context context, String filename) throws IOException {
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return "" ;
        }
    }


}
