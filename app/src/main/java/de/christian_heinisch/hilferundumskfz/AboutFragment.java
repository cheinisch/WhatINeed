package de.christian_heinisch.hilferundumskfz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    View rootview;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_about, container, false);

        // Macht den Link im TextView der Aboutseite anklickbar
        TextView t2 = (TextView) rootview.findViewById(R.id.textViewURL);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        setVersion();

        return rootview;
    }

    public void setVersion() {
        TextView version = (TextView) rootview.findViewById(R.id.textVersion);
        version.setText("Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ") "+BuildConfig.FLAVOR);
    }

}
