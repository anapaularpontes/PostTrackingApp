package com.posttracking.Boundaries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    public static final int DBVersion = 1;
    public static final String DBName = "PostTrackingApp.db";
    public static final String LOGTAG = "**DATABASE**";


    public Database(Context context) {
        super(context, this.DBName, null, this.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateCustomer = ("CREATE TABLE customer (" +
                " customer_id INTEGER PRIMARY KEY," +
                " firstName TEXT," +
                " lastName TEXT," +
                " emailAddress TEXT," +
                " password TEXT)");
        db.execSQL(sqlCreateCustomer);
        Log.d(LOGTAG, "Creating table customer" );

        String sqlCreatePack = ("CREATE TABLE pack (" +
                " pack_id INTEGER PRIMARY KEY," +
                " recipient TEXT," +
                " weight TEXT," +
                " volume TEXT," +
                " customer INT," +
                " FOREIGN KEY (customer) REFERENCES customer(customer_id))");
        db.execSQL(sqlCreatePack);
        Log.d(LOGTAG, "Creating table pack" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
