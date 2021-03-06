package de.christian_heinisch.hilferundumskfz;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.christian_heinisch.hilferundumskfz.database.TankDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddTankFragment extends DialogFragment {

    View rootview;
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


    public DialogAddTankFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_tank, null);

        dataSource = new TankDataSource(getActivity());

        textViewDate = (TextView) rootview.findViewById(R.id.editTextDate);
        tvKilometer = (EditText) rootview.findViewById(R.id.editTextTankDialogKilometer);
        tvEuro = (EditText) rootview.findViewById(R.id.editTextTankDialogEuro);
        tvLiter = (EditText) rootview.findViewById(R.id.editTextTankDialogLiter);

        setCurrentDate();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
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
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button theButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new CustomListener(alertDialog));

        return alertDialog;
    }

    private void setCurrentDate() {

        Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int monthOfYear = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

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

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {
            // put your code here

            datum =textViewDate.getText().toString();

            String tempstring = tvKilometer.getText().toString() +" ";
            String templiter = tvLiter.getText().toString() + " ";
            String tempeuro = tvEuro.getText().toString() + " ";
            if(validateEmpty(tempstring) && validateEmpty(tempstring) && validateEmpty(tempstring))
            {
                System.out.println("LEER");
                Toast.makeText(getActivity(), getString(R.string.tanken_dialog_error), Toast.LENGTH_SHORT).show();
            }else{
                kilometer = Double.parseDouble(tempstring);
                euro = Double.parseDouble(tempeuro);
                liter = Double.parseDouble(templiter);
                dataSource.open();
                dataSource.createTank(liter, euro, kilometer, datum);
                dataSource.close();
                ((MainActivity) getActivity()).tanken();
                dialog.dismiss();
            }
        }

        private boolean validateEmpty(String string) {
            boolean value = false;

            string = string.replaceAll("\\s","");

            if(!TextUtils.isEmpty(String.valueOf(string)))
            {
                value = false;
            }else{
                value = true;
            }

            return value;
        }
    }

}
