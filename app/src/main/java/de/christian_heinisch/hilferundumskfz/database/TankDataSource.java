package de.christian_heinisch.hilferundumskfz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


    public void createTank(double liter, double euro, double kilometer, String date) {

        String sql =
                "INSERT INTO tank_list (liter, date, money, kilometer) VALUES('"+liter+"','"+date+"','"+euro+"','"+kilometer+"')" ;
        database.execSQL(sql);
    }

    public void deleteTank(long id) {
        Log.d(LOG_TAG, "Eintrag wird gelöscht! ID: " + id);

        String sql = "DELETE FROM tank_list WHERE _id = "+id;
        database.execSQL(sql);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }

    public void updateTank(long id, double liter, double euro, double kilometer, String datum) {
        ContentValues values = new ContentValues();
        values.put(WhatINeedDbHelper.COLUMN_TANK_DATE, datum);
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
        String date = cursor.getString(idDate);
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


        Cursor cursor;
        String sqlQry;
        cursor = database.rawQuery("SELECT * FROM tank_list WHERE date BETWEEN date('"+startdate+"') AND date('"+enddate+"') ORDER BY date ASC", null);


        cursor.moveToFirst();
        Tank tank;

        while (!cursor.isAfterLast()) {
            tank = cursorToTank(cursor);
            System.out.println("TAGE: " + tank.getTag());
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


}

