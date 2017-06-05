package de.christian_heinisch.hilferundumskfz.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.christian_heinisch.hilferundumskfz.ItemDetailActivity;
import de.christian_heinisch.hilferundumskfz.R;


/**
 * Created by chris on 09.10.2016.
 */
public class CustomFehlerCodeAdapter extends ArrayAdapter<ListItemOverview> {

    public CustomFehlerCodeAdapter(Context context, ArrayList<ListItemOverview> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListItemOverview user = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        }

        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        tvName.setText(user.name);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewList);

        // Titelbild der Detailseite setzten
        System.out.println("Bild: " + user.bild_klein);

        String mDrawableName = user.bild_klein;
        int resID = getContext().getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());

        Picasso.with(getContext())
                .load(resID)
                //.placeholder(R.drawable.ic_placeholder) // optional
                //.error(R.drawable.ic_error_fallback)         // optional
                .fit()
                .into(imageView);


        return convertView;

    }

    public void openitem(View view){


        ListItemOverview user = (ListItemOverview) view.getTag();

        // Erstelle einen neuen Intent und weise ihm eine Actvity zu
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);

        //Werte an DetailActivity übergeben
        intent.putExtra("Titelleiste", user.name);
        intent.putExtra("Beschreibung", user.langtext);
        intent.putExtra("Bild_gross", user.bild_gross);

        // Starte Activity
        getContext().startActivity(intent);

    }


}
