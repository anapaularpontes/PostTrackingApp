package com.posttracking.Boundaries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.posttracking.Entities.Customer;

import java.util.ArrayList;

public class CustomerDAO extends Database{

    public CustomerDAO(Context context) {
        super(context);
    }

    public long addCustomer(Customer c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstName", c.getFirstName() );
        cv.put("lastName", c.getLastName() );
        cv.put("emailAddress", c.getEmail() );
        cv.put("password", c.getPassword() );
        return db.insert("customer",null, cv);
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select customer_id, firstName, lastName, emailAddress from customer", null);
        if(cursor.moveToFirst()) {
            do {
                Customer c = new Customer();
                c.setFirstName(cursor.getString(1));
                c.setLastName(cursor.getString(2));
                c.setEmail(cursor.getString(3));
                c.setCustomer_id(cursor.getInt(0));
                customers.add(c);
            } while (cursor.moveToNext());
        }
        return customers;
    }

}
