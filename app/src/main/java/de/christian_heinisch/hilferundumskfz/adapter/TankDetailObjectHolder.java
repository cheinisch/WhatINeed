package de.christian_heinisch.hilferundumskfz.adapter;

/**
 * Created by chris on 28.06.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.christian_heinisch.hilferundumskfz.R;


public class TankDetailObjectHolder extends RecyclerView.ViewHolder {

    public TextView Tag;
    public TextView Monat;
    public TextView Bezahlt;
    public TextView Getankt;
    public TextView Kilometer;
    public LinearLayout Layout;


    public TankDetailObjectHolder(View view) {
        super(view);

        Layout = (LinearLayout) view.findViewById(R.id.LinearLayoutDetail);
        Tag = (TextView) view.findViewById(R.id.textViewTankDetailTag);
        Monat = (TextView) view.findViewById(R.id.textViewTankDetailMonat);
        Bezahlt = (TextView) view.findViewById(R.id.textViewTankDetailBezahlt);
        Getankt = (TextView) view.findViewById(R.id.textViewTankDetailGetankt);
        Kilometer = (TextView) view.findViewById(R.id.textViewTankDetailKilometer);

    }
}