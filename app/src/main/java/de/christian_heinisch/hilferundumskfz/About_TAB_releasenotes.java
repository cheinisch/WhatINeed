package de.christian_heinisch.hilferundumskfz;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class About_TAB_releasenotes extends Fragment {


    public About_TAB_releasenotes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_about_tab_releasenote, container, false);

        TextView releaseText = (TextView) rootview.findViewById(R.id.textViewTabReleasenotes);
        String Text = "";
        // LESE Releasenotes ein
        try {
            Text = readFromAssets(getActivity(),"releasenotes.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        releaseText.setText(Text);

        return rootview;
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
