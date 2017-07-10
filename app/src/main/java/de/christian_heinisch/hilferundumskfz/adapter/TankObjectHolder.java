package de.christian_heinisch.hilferundumskfz.adapter;

/**
 * Created by chris on 28.06.2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.christian_heinisch.hilferundumskfz.R;


public class TankObjectHolder extends RecyclerView.ViewHolder {

    public TextView ItemGesamt;
    public TextView ItemEinnahmen;
    public TextView ItemAusgaben;
    public TextView Monat;
    public TextView Jahr;
    public CardView card;


    public TankObjectHolder(View view) {
        super(view);

        ItemGesamt = (TextView) view.findViewById(R.id.textViewMoneyOverviewGesamt);
        ItemEinnahmen = (TextView) view.findViewById(R.id.textViewMoneyOverviewEinnahme);
        ItemAusgaben  = (TextView) view.findViewById(R.id.textViewMoneyOverviewAusgaben);
        Monat  = (TextView) view.findViewById(R.id.textViewMoneyOverviewMonat);
        Jahr  = (TextView) view.findViewById(R.id.textViewMoneyOverviewJahr);
        card = (CardView) view.findViewById(R.id.card_view);

    }
}