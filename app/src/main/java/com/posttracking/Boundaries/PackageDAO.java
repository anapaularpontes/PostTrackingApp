package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.posttracking.api.models.Customer;
import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.Package;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO extends Database {

    public PackageDAO(Context context) {
        super(context);
    }

    public List<Package> getPackages(int customerID) {
        List<Package> list = new ArrayList<Package>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " +
                        "pack_id, recipient, address,weight,volume,origin,destination,customer" +
                        " from package where customer = ?",
                new String[] {String.valueOf(customerID)});
        DistributionCenter origin = new DistributionCenter();
        DistributionCenter destination = new DistributionCenter();;
        Customer c = new Customer();
        if(cursor.moveToFirst()) {
            do {
                Package p = new Package();
                p.setId(cursor.getInt(0));
                p.setRecipient(cursor.getString(1));
                p.setAddress(cursor.getString(2));
                p.setWeight(cursor.getDouble(3));
                p.setVolume(cursor.getDouble(4));
                origin.setId(cursor.getInt(5));
                p.setOrigin(origin);
                destination.setId(cursor.getInt(6));
                p.setDestination(destination);
                c.setId(cursor.getInt(7));
                p.setCustomer(c);
                list.add(p);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public long createPackage(Package p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("recipient", p.getRecipient());
        cv.put("address", p.getAddress());
        cv.put("weight", p.getWeight());
        cv.put("volume", p.getVolume());
        cv.put("origin", p.getOrigin().getId());
        cv.put("destination", p.getDestination().getId());
        cv.put("customer", LocalConfig.customerId);
        return db.insert("package", null, cv);
    }
}
