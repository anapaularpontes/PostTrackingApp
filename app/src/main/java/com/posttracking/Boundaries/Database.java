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
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateCustomer = ("CREATE TABLE customer (" +
                " id INTEGER PRIMARY KEY," +
                " firstName TEXT," +
                " lastName TEXT," +
                " emailAddress TEXT," +
                " apiID INT DEFAULT 0," +
                " password TEXT)");
        db.execSQL(sqlCreateCustomer);
        Log.d(LOGTAG, "Creating table customer" );

        String sqlCreatePackage = ("CREATE TABLE package (" +
                " pack_id INTEGER PRIMARY KEY," +
                " recipient TEXT," +
                " address TEXT," +
                " weight NUMERIC," +
                " volume NUMERIC," +
                " origin INTEGER," +
                " destination INTEGER," +
                " customer INT," +
                " status INT DEFAULT 0," +
                " apiID INT DEFAULT 0," +
                " FOREIGN KEY (customer) REFERENCES customer(customer_id))");
        db.execSQL(sqlCreatePackage);
        Log.d(LOGTAG, "Creating table pack" );

        String sqlCreateInvoice = ("CREATE TABLE invoice (" +
                " inv_id INTEGER PRIMARY KEY," +
                " cust_id INT," +
                " pack_id INT," +
                " deliveryTime INT," +
                " amount DECIMAL," +
                " status INT DEFAULT 0," +
                " FOREIGN KEY (cust_id) REFERENCES customer(customer_id), " +
                " FOREIGN KEY (pack_id) REFERENCES package (pack_id))");
        db.execSQL(sqlCreateInvoice);
        Log.d(LOGTAG, "Creating table invoice" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS package");
        db.execSQL("DROP TABLE IF EXISTS invoice");
    }
}
