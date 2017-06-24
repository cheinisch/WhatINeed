package de.christian_heinisch.hilferundumskfz.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        ListItemOverview item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_error_code, parent, false);

        }

        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        tvName.setText(item.name);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewList);

        String mDrawableName = item.bild_klein;
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



    }


}
