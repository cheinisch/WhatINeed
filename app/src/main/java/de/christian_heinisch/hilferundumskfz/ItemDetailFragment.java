package de.christian_heinisch.hilferundumskfz;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {

    View rootview;

    String ARG_TITEL = "item_titel";
    String ARG_BILD = "item_bild";
    String ARG_TEXT = "item_text";

    ImageView headimage;



    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Hole Strings aus dem Argumenten, die übergeben wurden
        ARG_TITEL = this.getArguments().getString("Titelleiste");
        ARG_TEXT = this.getArguments().getString("Beschreibung");
        ARG_BILD = this.getArguments().getString("Bild_gross");


        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Erstelle Textviews und setzte die Texte aus den Argumenten die Übergeben wurden
        TextView content = (TextView) rootview.findViewById(R.id.textDetailContent);
        content.setText(ARG_TEXT);

        TextView headline = (TextView) rootview.findViewById(R.id.textDetailHead);
        headline.setText(ARG_TITEL);


        // Setzte das Titelbild
        titelbild(ARG_BILD);

        return rootview;
    }

    public void titelbild(String url_load) {

        // Titelbild in imageView speichern
        ImageView imageView = (ImageView) rootview.findViewById(R.id.app_bar_image_fragment);

        // Titelbild der Detailseite setzten
        String mDrawableName = url_load;
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());

        Picasso.with(getContext())
                .load(resID)
                .fit()
                .into(imageView);


    }

}
