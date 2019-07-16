package com.posttracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.PackageDAO;
import com.posttracking.api.models.Package;

import java.util.List;

public class ViewPackagesActivity extends AppCompatActivity {

    ListView lvPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_packages);

        PackageDAO pDAO = new PackageDAO(this);
        lvPackages = findViewById(R.id.lvPackages);
        final List<Package> packages = pDAO.getPackages(LocalConfig.customerId);
        ArrayAdapter<Package> adap = new ArrayAdapter<Package>(this, R.layout.login_list_view, packages);
        lvPackages.setAdapter(adap);

        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Package p = packages.get(position);
                Log.d("CLICK TO EDIT:", p.getRecipient());
                Intent intent = new Intent(getApplicationContext(), CreateUpdatePackageActivity.class);
                intent.putExtra("package_id", p.getId());
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
