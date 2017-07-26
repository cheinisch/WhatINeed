package de.christian_heinisch.hilferundumskfz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_TAB_about extends Fragment {


    public About_TAB_about() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_about_tab_about, container, false);
        // Macht den Link im TextView der Aboutseite anklickbar
        TextView t2 = (TextView) rootview.findViewById(R.id.textViewURL);
        setAsLink(t2, "hilfe-rund-ums-auto.de");
        TextView version = (TextView) rootview.findViewById(R.id.textVersion);
        version.setText("Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ") "+BuildConfig.FLAVOR);

        return rootview;

    }



    private void setAsLink(TextView view, String url){
        Pattern pattern = Pattern.compile(url);
        Linkify.addLinks(view, pattern, "http://");
        view.setText(Html.fromHtml("<a href='http://"+url+"'>http://"+url+"</a>"));
    }

}
