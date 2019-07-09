package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.api.models.Package;

public class ViewPackagesActivity extends AppCompatActivity {

    ListView lvPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_packages);

        PackageDAO pDAO = new PackageDAO(this);
        Log.d("sizeeeeeee", String.valueOf(pDAO.getPackages(LocalConfig.customerId).size()));
        lvPackages = findViewById(R.id.lvPackages);
        ArrayAdapter<Package> adap = new ArrayAdapter<Package>(this, R.layout.login_list_view, pDAO.getPackages(LocalConfig.customerId));
        lvPackages.setAdapter(adap);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
