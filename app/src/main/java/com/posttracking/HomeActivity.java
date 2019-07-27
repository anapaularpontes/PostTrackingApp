package com.posttracking;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.posttracking.Boundaries.CustomerDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.Entities.Customer;
import com.posttracking.api.models.Package;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends ListActivity {

    private int customerId;
    Intent menuItem;
    final String[] menuItems = new String[] {"Packages", "Create Package", "Invoices"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_home,R.id.listMenu,menuItems));

        customerId = getIntent().getIntExtra("customerId",0);
        if(customerId!=0) {
            LocalConfig.customerId = customerId;
            final CustomerDAO cDAO = new CustomerDAO(this);
            final Customer localCustomer = cDAO.getCustomer(customerId);
            if(localCustomer.getApiID()==0) {
                final PostTrackingAPI postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);

                Call<List<Customer>> callCustomers = postTrackingAPI.getCustomerByEmail(localCustomer.getEmailAddress());
                callCustomers.enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if(response.body().size()==0) {
                            Log.d("****API", "Create new Customer on API");
                            Call<Customer> newCustomer = postTrackingAPI.createCustomer(
                                    localCustomer.getFirstName(),
                                    localCustomer.getLastName(),
                                    localCustomer.getEmailAddress());
                            newCustomer.enqueue(new Callback<Customer>() {
                                @Override
                                public void onResponse(Call<Customer> call, Response<Customer> response) {
                                    if(response.body() instanceof Customer) {
                                        localCustomer.setApiID(response.body().getId());
                                        cDAO.updateCustomer(localCustomer);
                                        LocalConfig.customerApiId = localCustomer.getApiID();
                                        Log.d("Customer INFO: ","ID Customer:"+localCustomer.getId()+
                                                " ID API:"+LocalConfig.customerApiId);
                                    } else {
                                        Log.d("***API","Fail Creating Customer");
                                        Log.d("***API",response.body()+"");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Customer> call, Throwable t) {
                                    Log.d("**Fail***", "unable to access the API (Create Customer)");
                                }
                            });

                        } else {
                            Log.d("****API", response.body().get(0).getId()+"");
                            localCustomer.setApiID(response.body().get(0).getId());
                            cDAO.updateCustomer(localCustomer);
                            LocalConfig.customerApiId = localCustomer.getApiID();
                            Call<List<Package>> getPackages =
                                    postTrackingAPI.searchPackages("0", "0",
                                            String.valueOf(localCustomer.getApiID()));
                            getPackages.enqueue(new Callback<List<Package>>() {
                                @Override
                                public void onResponse(Call<List<Package>> call, Response<List<Package>> response) {
                                    if(response.body().size()>0) {
                                        PackageDAO pDAO = new PackageDAO(getApplicationContext());
                                        for(Package p : response.body()) {
                                            Log.d("PACOTE", p.toString());
                                            p.setStatus(2);
                                            p.setApiId(p.getId());
                                            String.valueOf(pDAO.createPackage(p));
                                        }
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "Packages Updated",Toast.LENGTH_LONG);
                                        toast.show();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "There is no Old Packages",Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Package>> call, Throwable t) {
                                    Log.d("PACOTE", "Unable to GET old Packages");
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Unable to retrieve old Packages",Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                            Log.d("Customer INFO: ","ID Customer:"+localCustomer.getId()+
                                    " ID API:"+LocalConfig.customerApiId);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Log.d("**Fail***", "unable to access the API (Load Customer)");
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
        startActivity(menuItem);
    }
}
