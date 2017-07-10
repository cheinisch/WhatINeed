package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
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

        TabHost host = (TabHost)rootview.findViewById(R.id.tabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(getString(R.string.about_tabs_tab_about));
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.about_tabs_tab_about));
        host.addTab(spec);

        //Tab 2

        spec = host.newTabSpec(getString(R.string.about_tabs_tab_datenschutz));
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.about_tabs_tab_datenschutz));
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec(getString(R.string.about_quellen_titel));
        spec.setContent(R.id.tab3);
        spec.setIndicator(getString(R.string.about_quellen_titel));
        host.addTab(spec);

        // Setzte Werte für About TAB
        getData();

        return rootview;
    }


    public void getData()
    {

        //View aboutview = inflater.inflate(R.layout.fragment_about, container, false);

        // Macht den Link im TextView der Aboutseite anklickbar
        TextView t2 = (TextView) rootview.findViewById(R.id.textViewURL);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView version = (TextView) rootview.findViewById(R.id.textVersion);
        version.setText("Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ") "+BuildConfig.FLAVOR);
    }

}
