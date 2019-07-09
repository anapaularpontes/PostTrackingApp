package com.posttracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ProgressDialog;

import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.Customer;
import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.Package;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePackageActivity extends AppCompatActivity {

    private PostTrackingAPI postTrackingAPI;
    CreatePackageActivity _this = this;
    private Spinner origin;
    private Spinner destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);
        origin = findViewById(R.id.origin);
        destination = findViewById(R.id.destination);
        Button createPackage = findViewById(R.id.btnCreate);

        postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);
        Call<List<DistributionCenter>> call = postTrackingAPI.getAllDistributionCenter();
        call.enqueue(new Callback<List<DistributionCenter>>() {
            @Override
            public void onResponse(Call<List<DistributionCenter>> call, Response<List<DistributionCenter>> response) {
                Log.d("CHAMOUUUUU", response.body().toString());
                ArrayAdapter<DistributionCenter> adap = new ArrayAdapter<DistributionCenter>(_this, R.layout.support_simple_spinner_dropdown_item,response.body());
                origin.setAdapter(adap);
                destination.setAdapter(adap);
            }

            @Override
            public void onFailure(Call<List<DistributionCenter>> call, Throwable t) {
                //TODO create a backup list of Distribution Center
                Log.d("CHAMOUUUUU", "falhaaaaaa");
            }
        });

        createPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(CreatePackageActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Package");
                progressDialog.show();

                TextView recipient = findViewById(R.id.txtRecipient);
                TextView address = findViewById(R.id.txtAddress);
                TextView volume = findViewById(R.id.txtVolume);
                TextView weight = findViewById(R.id.txtWeight);

                Package p = new Package();
                p.setRecipient(recipient.getText().toString());
                p.setAddress(address.getText().toString());
                p.setVolume(Double.parseDouble(volume.getText().toString()));
                p.setWeight(Double.parseDouble(weight.getText().toString()));
                p.setOrigin((DistributionCenter) origin.getSelectedItem());
                p.setDestination((DistributionCenter) destination.getSelectedItem());

                PackageDAO pDAO = new PackageDAO(_this);
                pDAO.createPackage(p);


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                finish();
                                progressDialog.dismiss();
                            }
                        }, 1500);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

