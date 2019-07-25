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
        cv.put("emailAddress", c.getEmailAddress() );
        cv.put("password", c.getPassword() );
        return db.insert("customer",null, cv);
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, firstName, lastName, emailAddress," +
                " password, apiID from customer", null);
        if(cursor.moveToFirst()) {
            do {
                Customer c = new Customer();
                c.setFirstName(cursor.getString(1));
                c.setLastName(cursor.getString(2));
                c.setEmailAddress(cursor.getString(3));
                c.setPassword(cursor.getString(4));
                c.setId(cursor.getInt(0));
                c.setApiID(cursor.getInt(5));

                customers.add(c);
            } while (cursor.moveToNext());
        }
        return customers;
    }

    public Customer getCustomer(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, firstName, lastName, emailAddress," +
                " password, apiID from customer where emailAddress = ?", new String[] {email});
        Customer customer = new Customer();
        if(cursor.moveToFirst()) {
            customer.setFirstName(cursor.getString(1));
            customer.setLastName(cursor.getString(2));
            customer.setEmailAddress(cursor.getString(3));
            customer.setPassword(cursor.getString(4));
            customer.setId(cursor.getInt(0));
            customer.setApiID(cursor.getInt(5));
        }
        return customer;
    }

    public Customer getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, firstName, lastName, emailAddress," +
                " password, apiID from customer where id = ?", new String[] {String.valueOf(id)});
        Customer customer = new Customer();
        if(cursor.moveToFirst()) {
            customer.setFirstName(cursor.getString(1));
            customer.setLastName(cursor.getString(2));
            customer.setEmailAddress(cursor.getString(3));
            customer.setPassword(cursor.getString(4));
            customer.setId(cursor.getInt(0));
            customer.setApiID(cursor.getInt(5));
        }
        return customer;
    }

    public int updateCustomer(Customer c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstName", c.getFirstName());
        cv.put("lastName", c.getLastName());
        cv.put("emailAddress", c.getEmailAddress());
        cv.put("apiID", c.getApiID());
        cv.put("password", c.getPassword());
        return db.update("customer", cv, "id = ?",
                new String[] {String.valueOf(c.getId())} );
    }

    public boolean checkEmail(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select emailAddress from customer " +
                "where emailAddress = ?", new String[] {email});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select emailAddress, password from customer " +
                "where emailAddress = ? and password = ?", new String[] {email, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
