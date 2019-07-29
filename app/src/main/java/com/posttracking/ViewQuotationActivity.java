package com.posttracking;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.Entities.Invoice;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.Journey;
import com.posttracking.api.models.Package;
import com.posttracking.api.models.Path;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewQuotationActivity extends AppCompatActivity {

     final InvoiceDAO iDAO = new InvoiceDAO(this);
     final PackageDAO pDAO = new PackageDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        final ViewQuotationActivity _this = this;
        final RadioGroup rg = findViewById(R.id.rdGroup);
        final TextView tvMessage = findViewById(R.id.tvMessage);
        tvMessage.setText("Working...");
        final Button btnGenerateInvoice = findViewById(R.id.btnGenerate);
        btnGenerateInvoice.setVisibility(View.GONE);
        final List<Path> paths = new ArrayList<Path>();
        final List<Invoice> invoices = new ArrayList<Invoice>();
        final NumberFormat nf = NumberFormat.getCurrencyInstance();


        final int package_id = getIntent().getIntExtra("package_id", 0);
        if(package_id==0) {
            //Show Error
            finish();
        }

        final Package p = pDAO.getPackage(package_id);

        final PostTrackingAPI api = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);
        Call<List<Path>> call = api.getQuotation(String.valueOf(p.getOrigin().getId()),
                String.valueOf(p.getDestination().getId()),String.valueOf(p.getWeight()),
                String.valueOf(p.getVolume()));

        call.enqueue(new Callback<List<Path>>() {
            //ArrayAdapter adap;
            @Override
            public void onResponse(Call<List<Path>> call, Response<List<Path>> response) {
                List<Double> nOfDaysList = new ArrayList<Double>();
                if(response.body().size()==0) {
                    tvMessage.setText("Unable to find a Quotation");
                } else {
                    for(int i=0; (i < response.body().size() && i < 5); ++i) {
                        if(!nOfDaysList.contains(response.body().get(i).getNOfDays())) {
                            Double ndays = response.body().get(i).getNOfDays();
                            paths.add(response.body().get(i));
                            // Generating Invoice
                            Invoice inv = new Invoice();
                            inv.setDeliveryTime(ndays);
                            inv.setCust_id(LocalConfig.customerId);
                            inv.setPack_id(package_id);
                            inv.generateAmount(p.getWeight(), p.getVolume());
                            invoices.add(inv);
                            //Generating Radio
                            RadioButton rdbtn = new RadioButton(_this);
                            rdbtn.setId(View.generateViewId());
                            rdbtn.setTextColor(Color.BLACK);
                            rdbtn.setTextSize(24);
                            rdbtn.setText(response.body().get(i).toString() +
                                    "Amount: "+nf.format(inv.getAmount()));
                            rdbtn.setId(paths.size()-1);
                            rg.addView(rdbtn);
                            nOfDaysList.add(ndays);
                        }
                    }
                btnGenerateInvoice.setVisibility(View.VISIBLE);
                tvMessage.setText("");
                tvMessage.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Path>> call, Throwable t) {
                tvMessage.setText("Sorry, Unable to connect to the API");
            }
        });

        btnGenerateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Journey> journeys = paths.get(rg.getCheckedRadioButtonId()).getJourneys();
                String journeys_st = "";
                for(Journey j : journeys) {
                    journeys_st += j.getId()+",";
                }
                Log.d("Journeys:", journeys_st.substring(0,journeys_st.length()-1));
                Call<com.posttracking.api.models.Package> persistPackage = api.createPackage(
                        String.valueOf(LocalConfig.customerApiId),
                        String.valueOf(p.getOrigin().getId()),
                        String.valueOf(p.getDestination().getId()),
                        // (-1) Get ride of last comma
                        journeys_st.substring(0,journeys_st.length()-1),
                        String.valueOf(p.getWeight()),
                        String.valueOf(p.getVolume()),
                        p.getRecipient(),
                        p.getAddress(),
                        p.getCity(),
                        p.getProvince(),
                        p.getZipCode());
                persistPackage.enqueue(new Callback<Package>() {
                    @Override
                    public void onResponse(Call<Package> call, Response<Package> response) {
                        // The answer ID is the ID on API
                        p.setApiId(response.body().getId());
                        pDAO.updatePackage(p);
                        iDAO.createInvoice(invoices.get(rg.getCheckedRadioButtonId()));
                    }

                    @Override
                    public void onFailure(Call<Package> call, Throwable t) {
                        Log.d("FAIL API", t.getMessage());
                        Log.d("FAIL API", "APICustomer: "+LocalConfig.customerApiId);
                        Toast toast = Toast.makeText(getApplicationContext(),
                            "Unable to persist the Package. Please try again later", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });
    }
}
