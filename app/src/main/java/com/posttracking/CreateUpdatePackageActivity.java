package com.posttracking;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.Package;

import java.nio.file.Files;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUpdatePackageActivity extends AppCompatActivity {

    private PostTrackingAPI postTrackingAPI;
    CreateUpdatePackageActivity _this = this;
    private Spinner origin;
    private Spinner destination;
    private ArrayAdapter<DistributionCenter> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_package);
        // Prepare to Edit
        int package_id = getIntent().getIntExtra("package_id", 0);

        origin = findViewById(R.id.origin);
        destination = findViewById(R.id.destination);

        Button savePackage = findViewById(R.id.btnSave);
        Button deletePackage = findViewById(R.id.btnDelete);
        Button getQuotation = findViewById(R.id.btnGetQuotation);
        Button btnCheckStatus = findViewById(R.id.btnCheckStatus);
        final TextView recipient = findViewById(R.id.txtRecipient);
        final TextView address = findViewById(R.id.txtAddress);
        final TextView volume = findViewById(R.id.txtVolume);
        final TextView weight = findViewById(R.id.txtWeight);


        final ProgressDialog progressDialog = new ProgressDialog(CreateUpdatePackageActivity.this);
        progressDialog.setIndeterminate(true);

        final PackageDAO pDAO = new PackageDAO(_this);

        if(package_id==0) { //Creating a new Project

            updateSpinners(null);
            deletePackage.setVisibility(View.GONE);
            getQuotation.setVisibility(View.GONE);
            btnCheckStatus.setVisibility(View.GONE);

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

                        pDAO.createPackage(p);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                }, 1500);

                    } catch (Exception e) {
                        Toast t = Toast.makeText(getApplicationContext(), "Please, review your fields", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });
        } else { //Editing Package
            TextView title = findViewById(R.id.txtTitle);
            title.setText("Edit Package");
            savePackage.setText("Update");


            final Package p = pDAO.getPackage(package_id);
            if(p.getApiId()!=0) {
                btnCheckStatus.setVisibility(View.VISIBLE);
            } else {
                btnCheckStatus.setVisibility(View.GONE);
            }
            recipient.setText(p.getRecipient());
            address.setText(p.getAddress());
            volume.setText(String.valueOf(p.getVolume()));
            weight.setText(String.valueOf(p.getWeight()));

            updateSpinners(p);

            savePackage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setMessage("Updating Package");
                    try {
                        p.setRecipient((recipient.getText().toString().length()==0?"Unknown":
                                recipient.getText().toString()));
                        p.setAddress(address.getText().toString());
                        p.setVolume(Double.parseDouble(volume.getText().toString()));
                        p.setWeight(Double.parseDouble(weight.getText().toString()));
                        p.setOrigin((DistributionCenter) origin.getSelectedItem());
                        p.setDestination((DistributionCenter) destination.getSelectedItem());

                        progressDialog.show();

                        pDAO.updatePackage(p);

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

            deletePackage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pDAO.deletePackage(p.getId());
                    finish();
                }
            });

            getQuotation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ViewQuotationActivity.class);
                    intent.putExtra("package_id", p.getId());
                    startActivity(intent);
                }
            });

            btnCheckStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Context", getApplicationContext().toString());
                    Log.d("Context", _this.toString());
                    AlertDialog alertDialog = new AlertDialog.Builder(_this).create();
                    alertDialog.setTitle("Package position");
                    alertDialog.setMessage("Alert message to be shown");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void updateSpinners(final Package p) {
        postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);
        Call<List<DistributionCenter>> call = postTrackingAPI.getAllDistributionCenter();
        call.enqueue(new Callback<List<DistributionCenter>>() {
            @Override
            public void onResponse(Call<List<DistributionCenter>> call, Response<List<DistributionCenter>> response) {
                Log.d("*****API******", response.body().toString());
                adap = new ArrayAdapter<DistributionCenter>(_this, R.layout.support_simple_spinner_dropdown_item,response.body());
                origin.setAdapter(adap);
                destination.setAdapter(adap);
                if(p!=null) {
                    for(int x=0; x < adap.getCount(); ++x) {
                        if(adap.getItem(x).getId()==p.getOrigin().getId()) {
                            origin.setSelection(x);
                            break;
                        }
                    }
                    for(int x=0; x < adap.getCount(); ++x) {
                        if(adap.getItem(x).getId()==p.getDestination().getId()) {
                            destination.setSelection(x);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DistributionCenter>> call, Throwable t) {
                //TODO create a backup list of Distribution Center
                Log.d("*****API******", "Fail updating Spinners");
            }
        });
    }
}

