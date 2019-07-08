package com.posttracking;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.posttracking.Boundaries.Database;

public class HomeActivity extends ListActivity {

    private Database db;
    private Intent menu;
    private int customerId;
    Intent menuItem;
    final String[] menuItems = new String[] {"Packages", "Create Package", "Invoices"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_home,R.id.listMenu,menuItems));
        customerId = getIntent().getIntExtra("customerId",0);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                menuItem = new Intent(this, ViewPackagesActivity.class);
                Log.d("Menu Item", "Packages");
                break;
            case 1:
                menuItem = new Intent(this, CreatePackageActivity.class);
                Log.d("Menu Item", "Create Package");
                break;
            case 2:
                menuItem = new Intent(this, ViewInvoicesActivity.class);
                Log.d("Menu Item", "Invoices");
                break;
        }
        startActivity(menuItem);
    }
}
