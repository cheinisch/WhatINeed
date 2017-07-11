package de.christian_heinisch.hilferundumskfz;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddTankDetailFragment extends DialogFragment {

    View rootview;
    private TankDataSource dataSource;
    double euro;
    double kilometer;
    double liter;
    String datum;
    EditText tvEuro;
    EditText tvDate;
    EditText tvKilometer;
    EditText tvLiter;
    DatePickerDialog datePickerDialog;


    public DialogAddTankDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_tank, null);

        dataSource = new TankDataSource(getActivity());

        tvDate = (EditText) rootview.findViewById(R.id.editTextDate);
        tvKilometer = (EditText) rootview.findViewById(R.id.editTextTankDialogKilometer);
        tvEuro = (EditText) rootview.findViewById(R.id.editTextTankDialogEuro);
        tvLiter = (EditText) rootview.findViewById(R.id.editTextTankDialogLiter);

        // perform click event on edit text
        tvDate.setOnClickListener(new View.OnClickListener() {
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
                                tvDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

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

                        datum = getDateforDB(tvDate.getText().toString());
                        kilometer = Double.parseDouble(tvKilometer.getText().toString());
                        euro = Double.parseDouble(tvEuro.getText().toString());
                        liter = Double.parseDouble(tvLiter.getText().toString());

                        dataSource.open();
                        dataSource.createTank(liter, euro, kilometer, datum);
                        dataSource.close();
                        ((MainActivity) getActivity()).tanken();
                    }
                })
                .create();
    }

    public long convertTime(String newdate) throws ParseException {



        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date)formatter.parse(newdate);

        long returnDate = date.getTime();

        System.out.println("NEUES DATUM: " + returnDate);

        return returnDate;
    }

    private String getDateforDB(String oldDate){

        String newDate;

        String[] tempString = oldDate.split("-");

        int tempTag;
        int tempMonat;

        tempMonat = Integer.parseInt(tempString[1]);

        if(tempMonat < 10){
            tempString[1] = "0" + tempString[1];
        }

        tempTag = Integer.parseInt(tempString[2]);


        if(tempTag < 10){
            tempString[2] = "0" + tempString[2];
        }

        newDate = tempString[0] + "-" + tempString[1] + "-" + tempString[2];

        return newDate;
    }

}
