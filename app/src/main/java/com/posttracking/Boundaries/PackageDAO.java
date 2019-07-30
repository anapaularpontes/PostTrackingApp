package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                        "pack_id, recipient, address,weight,volume,origin,destination," +
                        "customer,apiID, status" +
                        " from package where customer = ?",
                new String[] {String.valueOf(customerID)});
        DistributionCenter origin = new DistributionCenter();
        DistributionCenter destination = new DistributionCenter();;
        Customer c = new Customer();
        if(cursor.moveToFirst()) {
            do {
                Package p = new Package();
                fillPackage(cursor,p);
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
        cv.put("status", p.getStatus());
        cv.put("apiID", p.getApiId());
        cv.put("customer", LocalConfig.customerId);
        return db.insert("package", null, cv);
    }

    public int updatePackage(Package p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("recipient", p.getRecipient());
        cv.put("address", p.getAddress());
        cv.put("weight", p.getWeight());
        cv.put("volume", p.getVolume());
        cv.put("origin", p.getOrigin().getId());
        cv.put("destination", p.getDestination().getId());
        cv.put("apiID", p.getApiId());
        int i = db.update("package", cv, " pack_id = ?", new String[] {String.valueOf(p.getId())});
        db.close();
        return i;
    }

    public int deletePackage(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int i = db.delete("package", "pack_id = ?", new String[] {String.valueOf(id)});
        // Canceling Invoices
        ContentValues cv = new ContentValues();
        cv.put("status", 2);
        db.update("invoice", cv, "pack_id = ?",  new String[] {String.valueOf(id)});
        db.close();
        return i;
    }

    public Package getPackage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " +
                        "pack_id, recipient, address,weight,volume,origin,destination,customer," +
                        "apiID, status" +
                        " from package where pack_id = ?",
                new String[] {String.valueOf(id)});
        Package p = new Package();
        if(cursor.moveToFirst()) {
            fillPackage(cursor,p);
        }
        db.close();
        return p;
    }

    private void fillPackage(Cursor cursor, Package p) {
        DistributionCenter origin = new DistributionCenter();
        DistributionCenter destination = new DistributionCenter();;
        Customer c = new Customer();

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
        p.setApiId(cursor.getInt(8));
        p.setStatus(cursor.getInt(9));

    }

    public void deletePackagesCustomer(int customerID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("delete from package where customer = ?",
                new String[] {String.valueOf(customerID)});
        cursor.moveToFirst();
        db.close();
    }
}
