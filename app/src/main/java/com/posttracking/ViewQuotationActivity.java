package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.Entities.Invoice;
import com.posttracking.Entities.Quotation;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.Package;
import com.posttracking.api.models.Path;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewQuotationActivity extends AppCompatActivity {

     InvoiceDAO iDAO = new InvoiceDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        final ViewQuotationActivity _this = this;
        final ListView lv = findViewById(R.id.lvQuotation);

        int package_id = getIntent().getIntExtra("package_id", 0);
        if(package_id==0) {
            //Show Error
            finish();
        }

        PackageDAO pDAO = new PackageDAO(this);
        Package p = pDAO.getPackage(package_id);

        PostTrackingAPI api = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);
        Call<List<Path>> call = api.getQuotation(String.valueOf(p.getOrigin().getId()),
                String.valueOf(p.getDestination().getId()),String.valueOf(p.getWeight()),
                String.valueOf(p.getVolume()));

        call.enqueue(new Callback<List<Path>>() {
            ArrayAdapter adap;
            @Override
            public void onResponse(Call<List<Path>> call, Response<List<Path>> response) {
                if(response.body().isEmpty()) {
                    adap = new ArrayAdapter<String>(_this, R.layout.quotation_list_view, new String[] {"We couldn't find an Path to Deliver Your Package"});
                } else {
                    adap = new ArrayAdapter<Path>(_this, R.layout.quotation_list_view, response.body());
                }
                lv.setAdapter(adap);
            }

            @Override
            public void onFailure(Call<List<Path>> call, Throwable t) {
                adap = new ArrayAdapter<String>(_this, R.layout.quotation_list_view, new String[] {"We couldn't connect to the API"});
                lv.setAdapter(adap);
            }

        });

        // Mudar para pegar Path e Transformar em uma Invoice
        //QuotationDAO qDAO = new QuotationDAO(this);
        //final ArrayAdapter<Quotation> adap = new ArrayAdapter<Quotation>(this, R.layout.quotation_list_view, qDAO.getQuotations(LocalConfig.packageId));
        //lv.setAdapter(adap);


        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quotation q = adap.getItem(lv.getSelectedItemPosition());

                Invoice i = new Invoice();
                i.setCust_id(LocalConfig.customerId);
                i.setPack_id(LocalConfig.packageId);
                //i.setQuote_id(q.getQuote_id());
                //i.setAmount(q.getAmount());
                i.setStatus("1");

                iDAO.createInvoice(i);
            }
        });
    }
}
