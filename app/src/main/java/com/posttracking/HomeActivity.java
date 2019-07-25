package com.posttracking;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.posttracking.Boundaries.CustomerDAO;
import com.posttracking.Boundaries.Database;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Entities.Customer;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if(customerId!=0) {
            final CustomerDAO cDAO = new CustomerDAO(this);
            final Customer localCustomer = cDAO.getCustomer(customerId);
            if(localCustomer.getApiID()==0) {
                PostTrackingAPI postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);

                Call<List<Customer>> call = postTrackingAPI.getCustomerByEmail(localCustomer.getEmailAddress());
                call.enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if(true) {
                            Log.d("****API", "Create new");
                        } else {
                            //Log.d("****API", response.body().get(0).getId()+"");
                            localCustomer.setApiID(response.body().get(0).getId());
                            cDAO.updateCustomer(localCustomer);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Log.d("**Fail***", "unable to access the API");
                    }
                });

            }
        }

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
                menuItem = new Intent(this, CreateUpdatePackageActivity.class);
                Log.d("Menu Item", "Create Package");
                break;
            case 2:
                menuItem = new Intent(this, ViewInvoicesActivity.class);
                Log.d("Menu Item", "Invoices");
                break;
        }
        LocalConfig.customerId = customerId;
        startActivity(menuItem);
    }
}
