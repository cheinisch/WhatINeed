package de.christian_heinisch.hilferundumskfz.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import de.christian_heinisch.hilferundumskfz.MainActivity;
import de.christian_heinisch.hilferundumskfz.R;
import de.christian_heinisch.hilferundumskfz.database.Tank_new;

import static de.christian_heinisch.hilferundumskfz.database.Tank_new.LIST_TYPE;
import static de.christian_heinisch.hilferundumskfz.database.Tank_new.OVERVIEW_TYPE;

/**
 * Created by chris on 12.08.2017.
 */

public class rvTankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tank_new> mList;
    Context mContext;


    public rvTankAdapter(Context context, List<Tank_new> list) {
        this.mList = list;
        this.mContext = context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case OVERVIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tank_item, parent, false);
                return new OverViewHolder(view);
            case LIST_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tank_item_detail, parent, false);
                return new DetailViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Tank_new object = mList.get(position);
        if (object != null) {
            switch (object.getType()) {
                case OVERVIEW_TYPE:
                    ((OverViewHolder) holder).Monat.setText(getMonthForInt(object.getMonat()+1));
                    //((OverViewHolder) holder).Jahr.setText(object.getJahr());
                    ((OverViewHolder) holder).ItemLiter.setText(object.getLiter() + " l");
                    ((OverViewHolder) holder).ItemEuro.setText(object.getEuro() + " €");
                    ((OverViewHolder) holder).ItemSpritpreis.setText(round(object.getEuro() / object.getLiter(),2) + " €");
                    break;
                case LIST_TYPE:
                    ((DetailViewHolder) holder).Getankt.setText(object.getLiter() + " l");
                    ((DetailViewHolder) holder).Bezahlt.setText(object.getEuro() + " €");
                    ((DetailViewHolder) holder).Monat.setText(getMonthForInt(object.getMonat()));

                    ((DetailViewHolder) holder).Tag.setText(object.getTag() + ".");
                    ((DetailViewHolder) holder).Kilometer.setText(object.getKilometer() + " km");

                    ((DetailViewHolder) holder).Layout.setOnLongClickListener(new View.OnLongClickListener(){
                        @Override
                        public boolean onLongClick(View v) {
                            ((MainActivity) mContext).DialogEditTanken(object.getId());
                            return false;
                        }
                    });
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            Tank_new object = mList.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }
    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        public TextView Tag;
        public TextView Monat;
        public TextView Bezahlt;
        public TextView Getankt;
        public TextView Kilometer;
        public LinearLayout Layout;
        public DetailViewHolder(View view) {
            super(view);
            Layout = (LinearLayout) view.findViewById(R.id.LinearLayoutDetail);
            Tag = (TextView) view.findViewById(R.id.textViewTankDetailTag);
            Monat = (TextView) view.findViewById(R.id.textViewTankDetailMonat);
            Bezahlt = (TextView) view.findViewById(R.id.textViewTankDetailBezahlt);
            Getankt = (TextView) view.findViewById(R.id.textViewTankDetailGetankt);
            Kilometer = (TextView) view.findViewById(R.id.textViewTankDetailKilometer);
        }
    }
    public static class OverViewHolder extends RecyclerView.ViewHolder {

        public TextView ItemLiter;
        public TextView ItemEuro;
        public TextView ItemSpritpreis;
        public TextView Monat;
        public TextView Jahr;
        public CardView card;

        public OverViewHolder(View itemView) {
            super(itemView);
            ItemLiter = (TextView) itemView.findViewById(R.id.textViewOverviewLiter);
            ItemEuro = (TextView) itemView.findViewById(R.id.textViewOverviewEuro);
            ItemSpritpreis  = (TextView) itemView.findViewById(R.id.textViewOverviewSpritpreis);
            Monat  = (TextView) itemView.findViewById(R.id.textViewMoneyOverviewMonat);
            Jahr  = (TextView) itemView.findViewById(R.id.textViewMoneyOverviewJahr);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    String getMonthForInt(int num) {

        num = num - 1;
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }

        return month;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}