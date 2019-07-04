package com.posttracking;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;


import com.posttracking.Boundaries.Database;

public class HomeActivity extends ListActivity {

    private Database db;

    final String[] menuItems = new String[] {"Packages", "Create Package", "Invoices"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_home,R.id.mainMenu,menuItems));

        //db = new Database(this);
        //final SQLiteDatabase wdb = db.getWritableDatabase();
    }

}
