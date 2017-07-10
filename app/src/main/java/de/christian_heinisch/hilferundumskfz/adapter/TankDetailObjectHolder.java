package de.christian_heinisch.hilferundumskfz.adapter;

/**
 * Created by chris on 28.06.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.christian_heinisch.hilferundumskfz.R;


public class TankDetailObjectHolder extends RecyclerView.ViewHolder {

    public TextView Tag;
    public TextView Monat;


    public TankDetailObjectHolder(View view) {
        super(view);

        Tag = (TextView) view.findViewById(R.id.textViewTankDetailTag);
        Monat = (TextView) view.findViewById(R.id.textViewTankDetailMonat);

    }
}