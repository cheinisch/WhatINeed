package de.christian_heinisch.hilferundumskfz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chris on 13.06.2017.
 */

public class WhatINeedDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = WhatINeedDbHelper.class.getSimpleName();


    public static final String DB_NAME = "wasbraucheich.db";
    public static final int DB_VERSION = 1;

    /*Spalten To Do Liste*/
    public static final String COLUMN_ID = "_id";

    /*Tabelle Geld*/
    public static final String TABLE_TANK_LIST = "tank_list";
    /*Spalten Geld*/
    public static final String COLUMN_TANK_LITER = "liter";
    public static final String COLUMN_TANK_DATE = "date";
    public static final String COLUMN_TANK_MONEY = "money";
    public static final String COLUMN_TANK_KILOMETER = "kilometer";


    public static final String SQL_CREATE_TANK =
            "CREATE TABLE " + TABLE_TANK_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TANK_LITER + " REAL NOT NULL, " +
                    COLUMN_TANK_DATE + " INTEGER NOT NULL, " +
                    COLUMN_TANK_MONEY + " REAL NOT NULL, " +
                    COLUMN_TANK_KILOMETER + " REAL NOT NULL);";

    public static final String SQL_DROP_TANK = "DROP TABLE IF EXISTS " + TABLE_TANK_LIST;


    public WhatINeedDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            Log.d(LOG_TAG, "Die Tabelle Zeug wird mit SQL-Befehl: " + SQL_CREATE_TANK + " angelegt.");
            db.execSQL(SQL_CREATE_TANK);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }

    }

    // Die onUpgrade-Methode wird aufgerufen, sobald die neue Versionsnummer höher
    // als die alte Versionsnummer ist und somit ein Upgrade notwendig wird
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Die Tabelle mit Versionsnummer " + oldVersion + " wird entfernt.");
        db.execSQL(SQL_DROP_TANK);
        onCreate(db);
    }
}