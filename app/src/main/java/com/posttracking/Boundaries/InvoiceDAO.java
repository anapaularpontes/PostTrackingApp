package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.posttracking.Entities.Invoice;
import com.posttracking.Entities.Quotation;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends Database {

    public InvoiceDAO(Context context) {
        super(context);
    }

    public List<Invoice> getInvoices(int customerID) {
        List<Invoice> list = new ArrayList<Invoice>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select inv_id, pack_id, deliveryTime, amount, status" +
                        " from invoice" +
                        " where cust_id = ?",
                new String[] {String.valueOf(customerID)});

        if(cursor.moveToFirst()) {
            do {
                Invoice i = new Invoice();
                i.setInvoice_id(cursor.getInt(0));
                i.setPack_id(cursor.getInt(1));
                i.setDeliveryTime(cursor.getDouble(2));
                i.setAmount(cursor.getDouble(3));
                i.setStatus(cursor.getInt(4));
                list.add(i);
            } while(cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public long createInvoice(Invoice i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put("status", 2);
        db.update("invoice",cvUpdate," pack_id = ?", new String[] {String.valueOf(i.getPack_id())});
        ContentValues cv = new ContentValues();
        cv.put("cust_id", i.getCust_id());
        cv.put("pack_id", i.getPack_id());
        cv.put("deliveryTime", i.getDeliveryTime());
        cv.put("amount", i.getAmount());
        cv.put("status", 0);
        long id = db.insert("invoice", null, cv);
        db.close();
        return id;
    }

    public Invoice getInvoice(int invoiceID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select inv_id, pack_id, deliveryTime, amount, status" +
                " from invoice" +
                " where invoiceID = ?", new String[] {String.valueOf(invoiceID)});
        Invoice i = new Invoice();
        if(cursor.moveToFirst()) {
            i.setInvoice_id(cursor.getInt(0));
            i.setPack_id(cursor.getInt(1));
            i.setDeliveryTime(cursor.getDouble(2));
            i.setAmount(cursor.getDouble(3));
            i.setStatus(cursor.getInt(4));
        }
        db.close();
        return i;
    }
}
