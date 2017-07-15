package de.christian_heinisch.hilferundumskfz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.christian_heinisch.hilferundumskfz.database.Tank;
import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * Created by chris on 11.07.2017.
 */

public class DialogEditTankFragment extends DialogFragment {

    View rootview;
    private long id;
    private TankDataSource dataSource;
    double euro;
    double kilometer;
    double liter;
    String datum;
    EditText tvEuro;
    TextView textViewDate;
    EditText tvKilometer;
    EditText tvLiter;
    DatePickerDialog datePickerDialog;

    public DialogEditTankFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_tank, null);

        dataSource = new TankDataSource(getActivity());

        textViewDate = (TextView) rootview.findViewById(R.id.editTextDate);
        tvKilometer = (EditText) rootview.findViewById(R.id.editTextTankDialogKilometer);
        tvEuro = (EditText) rootview.findViewById(R.id.editTextTankDialogEuro);
        tvLiter = (EditText) rootview.findViewById(R.id.editTextTankDialogLiter);

        getTank();

        // perform click event on edit text

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verstecke die tastatur

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rootview.getWindowToken(), 0);

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String day;
                                String month;
                                if(dayOfMonth < 10)
                                {
                                    day = "0" + dayOfMonth;
                                }else{
                                    day = String.valueOf(dayOfMonth);
                                }

                                if((monthOfYear +1) < 10)
                                {
                                    month = "0" + (monthOfYear + 1);
                                }else{
                                    month = String.valueOf((monthOfYear + 1));
                                }

                                textViewDate.setText(year + "-" + month + "-" + day);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.tanken_dialog_titel)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something

                        datum = textViewDate.getText().toString();
                        kilometer = Double.parseDouble(tvKilometer.getText().toString());
                        euro = Double.parseDouble(tvEuro.getText().toString());
                        liter = Double.parseDouble(tvLiter.getText().toString());

                        dataSource.open();
                        dataSource.updateTank(id, liter, euro, kilometer, datum);
                        dataSource.close();
                        ((MainActivity) getActivity()).tanken();
                    }
                })
                .create();
    }

    public void getTank(){

        Tank tank;

        dataSource.open();
        tank = dataSource.getTank(id);
        dataSource.close();

        double kilometer = tank.getKilometer();
        double liter = tank.getLiter();
        double euro = tank.getEuro();

        String day;
        String month;
        if(tank.getTag() < 10)
        {
            day = "0" + tank.getTag();
        }else{
            day = String.valueOf(tank.getTag());
        }

        if(tank.getMonat() < 10)
        {
            month = "0" + tank.getMonat();
        }else{
            month = String.valueOf(tank.getMonat());
        }

        String newdatum = tank.getJahr()+"-"+month+"-"+day;

        textViewDate.setText(newdatum);
        tvKilometer.setText(String.valueOf(kilometer));
        tvEuro.setText(String.valueOf(euro));
        tvLiter.setText(String.valueOf(liter));

    }

    public long convertTime(String newdate) throws ParseException {



        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date)formatter.parse(newdate);

        long returnDate = date.getTime();

        System.out.println("NEUES DATUM: " + returnDate);

        return returnDate;
    }

}
