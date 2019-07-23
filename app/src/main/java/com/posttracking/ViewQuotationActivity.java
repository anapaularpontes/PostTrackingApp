package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.Boundaries.QuotationDAO;
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

    ListView lvQuotations;

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
                    adap = new ArrayAdapter<String>(_this, R.layout.quotation_list_view, new String[] {"We couldn't find an Path"});
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

        //QuotationDAO qDAO = new QuotationDAO(this);
        /*Log.d("# of Quotations", String.valueOf(qDAO.getQuotations(LocalConfig.packageId).size()));
        lvQuotations = findViewById(R.id.lvQuotation);
        ArrayAdapter<Quotation> adap = new ArrayAdapter<Quotation>(this, R.layout.quotation_list_view, qDAO.getQuotations(LocalConfig.packageId));
        lvQuotations.setAdapter(adap);*/
    }
}
