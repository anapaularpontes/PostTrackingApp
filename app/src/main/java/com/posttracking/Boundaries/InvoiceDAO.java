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
        Cursor cursor = db.rawQuery("select inv_id, package.pack_id, invoice.deliveryTime, invoice.amount, invoice.status" +
                        " from invoice" +
                        " inner join package ON package.pack_id = invoice.pack_id" +
                        " where invoice.cust_id = ?",
                new String[] {String.valueOf(customerID)});

        if(cursor.moveToFirst()) {
            do {
                Invoice i = new Invoice();
                i.setInvoice_id(cursor.getInt(0));
                i.setPack_id(cursor.getInt(1));
                i.setDeliveryTime(cursor.getInt(2));
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
        cv.put("cust_id", LocalConfig.customerId);
        cv.put("pack_id", LocalConfig.packageId);
        cv.put("deliveryTime", i.getDeliveryTime());
        cv.put("amount", i.getAmount());
        cv.put("status", 0);
        return db.insert("invoice", null, cv);
    }

    public Invoice getInvoice(int invoiceID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select inv_id, package.pack_id, invoice.deliveryTime, invoice.amount, invoice.status" +
                " from invoice" +
                " inner join package ON package.pack_id = invoice.pack_id" +
                " where invoiceID = ?", new String[] {String.valueOf(invoiceID)});
        Invoice i = new Invoice();
        if(cursor.moveToFirst()) {
            i.setInvoice_id(cursor.getInt(0));
            i.setPack_id(cursor.getInt(1));
            i.setDeliveryTime(cursor.getInt(2));
            i.setAmount(cursor.getDouble(3));
            i.setStatus(cursor.getInt(4));
        }
        return i;
    }
}
