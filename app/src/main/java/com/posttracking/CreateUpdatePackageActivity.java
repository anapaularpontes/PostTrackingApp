package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.Package;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUpdatePackageActivity extends AppCompatActivity {

    private PostTrackingAPI postTrackingAPI;
    CreateUpdatePackageActivity _this = this;
    private Spinner origin;
    private Spinner destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_package);
        // Prepare to Edit
        int package_id = getIntent().getIntExtra("package_id", 0);

        origin = findViewById(R.id.origin);
        destination = findViewById(R.id.destination);
        Button savePackage = findViewById(R.id.btnSave);

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

        final TextView recipient = findViewById(R.id.txtRecipient);
        final TextView address = findViewById(R.id.txtAddress);
        final TextView volume = findViewById(R.id.txtVolume);
        final TextView weight = findViewById(R.id.txtWeight);
        final ProgressDialog progressDialog = new ProgressDialog(CreateUpdatePackageActivity.this);
        progressDialog.setIndeterminate(true);

        if(package_id==0) { //Creating a new Project
            savePackage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setMessage("Creating Package");

                    try {

                        Package p = new Package();
                        p.setRecipient((recipient.getText().toString().length()==0?"Unknown":
                                recipient.getText().toString()));
                        p.setAddress(address.getText().toString());
                        p.setVolume(Double.parseDouble(volume.getText().toString()));
                        p.setWeight(Double.parseDouble(weight.getText().toString()));
                        p.setOrigin((DistributionCenter) origin.getSelectedItem());
                        p.setDestination((DistributionCenter) destination.getSelectedItem());

                        progressDialog.show();
                        PackageDAO pDAO = new PackageDAO(_this);
                        pDAO.createPackage(p);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                }, 1500);

                    } catch (Exception e) {
                        Toast t = Toast.makeText(getApplicationContext(), "Please, review your fields", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            });
        } else { //Editing Package
            TextView title = findViewById(R.id.txtTitle);
            title.setText("Edit Package");
            savePackage.setText("Update");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

