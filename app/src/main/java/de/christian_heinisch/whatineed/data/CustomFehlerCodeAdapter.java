package de.christian_heinisch.whatineed.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.whatineed.ItemDetailActivity;
import de.christian_heinisch.whatineed.R;

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
        TextView tvBeschreibung = (TextView) convertView.findViewById(R.id.textBeschreibung);

        tvName.setText(user.name);
        tvBeschreibung.setText(user.beschreibung);

        return convertView;

    }

    public void openitem(View view){


        ListItemOverview user = (ListItemOverview) view.getTag();

        // Erstelle einen neuen Intent und weise ihm eine Actvity zu
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);

        //Werte an DetailActivity übergeben
        intent.putExtra("Titelleiste", user.name);
        intent.putExtra("Beschreibung", user.langtext);
        intent.putExtra("URL", user.bild);

        // Starte Activity
        getContext().startActivity(intent);

    }



}
