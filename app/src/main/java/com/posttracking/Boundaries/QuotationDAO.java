package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.posttracking.Entities.Quotation;

import java.util.ArrayList;
import java.util.List;

public class QuotationDAO extends Database {

    public QuotationDAO(Context context) {
        super(context);
    }

    public List<Quotation> getQuotations(int packID) {
        List<Quotation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select quote_id, deliveryTime, amount from quotation where pack_id = ?", new String[] {String.valueOf(packID)});

        if(cursor.moveToFirst()) {
            do {
                Quotation q = new Quotation();
                q.setQuote_id(cursor.getInt(0));
                q.setDeliveryTime(cursor.getInt(1));
                q.setAmount(cursor.getDouble(2));
                list.add(q);
            } while(cursor.moveToNext());
        }

        return list;
    }

    public long createQuotation(Quotation q) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pack_id", LocalConfig.packageId);
        cv.put("deliveryTime", q.getDeliveryTime());
        cv.put("amount", q.getAmount());
        return db.insert("quotation", null, cv);
    }
}
