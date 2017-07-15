package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    View rootview;
    private TextView releaseText;


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

        //Tab 4
        spec = host.newTabSpec(getString(R.string.about_releasenotes_titel));
        spec.setContent(R.id.tab4);
        spec.setIndicator(getString(R.string.about_releasenotes_titel));
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
        setAsLink(t2, "hilfe-rund-ums-auto.de");

        TextView version = (TextView) rootview.findViewById(R.id.textVersion);
        version.setText("Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ") "+BuildConfig.FLAVOR);

        // Text Für Releasenotes zuweisen

        releaseText = (TextView) rootview.findViewById(R.id.textViewTabReleasenotes);
        String Text = "";
        // LESE Releasenotes ein
        try {
            Text = readFromAssets(getActivity(),"releasenotes.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        releaseText.setText(Text);
    }

    private void setAsLink(TextView view, String url){
        Pattern pattern = Pattern.compile(url);
        Linkify.addLinks(view, pattern, "http://");
        view.setText(Html.fromHtml("<a href='http://"+url+"'>http://"+url+"</a>"));
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
