package de.christian_heinisch.hilferundumskfz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static de.christian_heinisch.hilferundumskfz.database.WhatINeedDbHelper.TABLE_TANK_LIST;

/**
 * Created by chris on 01.07.2017.
 */

public class TankDataSource {

    private static final String LOG_TAG = TankDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private WhatINeedDbHelper dbHelper;

    private String[] columns = {
            WhatINeedDbHelper.COLUMN_ID,
            WhatINeedDbHelper.COLUMN_TANK_LITER,
            WhatINeedDbHelper.COLUMN_TANK_MONEY,
            WhatINeedDbHelper.COLUMN_TANK_KILOMETER,
            WhatINeedDbHelper.COLUMN_TANK_DATE
    };

    public TankDataSource(Context context) {
        //Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new WhatINeedDbHelper(context);
    }

    public void open() {
        //Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        //Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        //Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }


    public Tank createTank(double liter, double euro, double kilometer, String date) {

        long newdate = getDateLong(date);

        String sql =
                "INSERT INTO tank_list (liter, date, money, kilometer) VALUES('"+liter+"','"+newdate+"','"+euro+"','"+kilometer+"')" ;
        database.execSQL(sql);
        System.out.println(sql);

        Tank tank = null;
        return tank;
    }

    public void deleteTank(long id) {
        database.delete(TABLE_TANK_LIST,
                WhatINeedDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }

    public void updateTank(long id, double liter, double euro, double kilometer, String datum) {

        long newdatum = getDateLong(datum);

        ContentValues values = new ContentValues();
        values.put(WhatINeedDbHelper.COLUMN_TANK_DATE, newdatum);
        values.put(WhatINeedDbHelper.COLUMN_TANK_KILOMETER, kilometer);
        values.put(WhatINeedDbHelper.COLUMN_TANK_MONEY, euro);
        values.put(WhatINeedDbHelper.COLUMN_TANK_LITER, liter);

        database.update(WhatINeedDbHelper.TABLE_TANK_LIST,
                values,
                WhatINeedDbHelper.COLUMN_ID + "=" + id,
                null);
    }


    private Tank cursorToTank(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(WhatINeedDbHelper.COLUMN_ID);
        int idMoney = cursor.getColumnIndex(WhatINeedDbHelper.COLUMN_TANK_MONEY);
        int idDate = cursor.getColumnIndex(WhatINeedDbHelper.COLUMN_TANK_DATE);
        int idKilometer = cursor.getColumnIndex(WhatINeedDbHelper.COLUMN_TANK_KILOMETER);
        int idLiter = cursor.getColumnIndex(WhatINeedDbHelper.COLUMN_TANK_LITER);


        double euro = cursor.getDouble(idMoney);
        String date = getDateString(cursor.getLong(idDate));
        double liter = cursor.getDouble(idLiter);
        double kilometer = cursor.getDouble(idKilometer);




        String[] parts = date.split("-");
        int jahr = Integer.parseInt(parts[0]);
        int monat = Integer.parseInt(parts[1]);
        int tag = Integer.parseInt(parts[2]);

        long id = cursor.getLong(idIndex);

        Tank tank = new Tank(id, euro, liter, kilometer, jahr ,monat, tag);

        return tank;
    }


    public ArrayList<Tank> getTankforMonth(String startdate, String enddate) {
        ArrayList<Tank> listitems = new ArrayList<Tank>();

        long newenddate = getDateLong(enddate);
        long newstart = getDateLong(startdate);


        Cursor cursor;
        String sqlQry;
            cursor = database.rawQuery("SELECT * FROM tank_list WHERE date BETWEEN "+newstart+" AND "+newenddate+" ORDER BY date ASC", null);


        cursor.moveToFirst();
        Tank tank;

        while (!cursor.isAfterLast()) {
            tank = cursorToTank(cursor);
            listitems.add(new Tank(tank.getId(), tank.getEuro(), tank.getLiter(), tank.getKilometer(), tank.getJahr(), tank.getMonat(), tank.getTag()));

            cursor.moveToNext();
        }

        cursor.close();

        return listitems;
    }

    public Tank getTank(long id){

        Cursor cursor = database.query(WhatINeedDbHelper.TABLE_TANK_LIST,
                columns, WhatINeedDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        Tank tank;

        if (cursor!= null && cursor.moveToFirst());
        do {
            tank = cursorToTank(cursor);
        } while (cursor.moveToNext());

        return tank;
    }

    private String getDateString(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-m-d", cal).toString();
        return date;
    }

    private long getDateLong(String textdate)  {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-m-d");
        Date date = null;
        try {
            date = (Date)formatter.parse(textdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Timestamp: " + date.getTime() + " Echtzeit: " + getDateString(date.getTime()));

        return date.getTime();
    }


}

