package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.posttracking.Entities.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends Database {

    public InvoiceDAO(Context context) {
        super(context);
    }

    public List<Invoice> getInvoices(int customerID) {
        List<Invoice> list = new ArrayList<Invoice>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " +
                        "inv_id, cust_id, pack_id, amount, status" +
                        " from invoice where cust_id = ?",
                new String[] {String.valueOf(customerID)});

        if(cursor.moveToFirst()) {
            do {
                Invoice i = new Invoice();
                i.setInvoice_id(cursor.getInt(0));
                i.setCust_id(cursor.getInt(1));
                i.setPack_id(cursor.getInt(2));
                i.setAmount(cursor.getDouble(3));
                i.setStatus(cursor.getInt(4));
                list.add(i);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public long createInvoice(Invoice i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cust_id", i.getCust_id());
        cv.put("pack_id", i.getPack_id());
        cv.put("amount", i.getAmount());
        cv.put("status", i.getStatus());
        return db.insert("invoice", null, cv);
    }

}
