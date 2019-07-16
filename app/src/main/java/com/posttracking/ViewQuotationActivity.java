package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.QuotationDAO;
import com.posttracking.Entities.Quotation;

public class ViewQuotationActivity extends AppCompatActivity {

    ListView lvQuotations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        QuotationDAO qDAO = new QuotationDAO(this);
        Log.d("# of Quotations", String.valueOf(qDAO.getQuotations(LocalConfig.packageId).size()));
        lvQuotations = findViewById(R.id.lvQuotation);
        ArrayAdapter<Quotation> adap = new ArrayAdapter<Quotation>(this, R.layout.quotation_list_view, qDAO.getQuotations(LocalConfig.packageId));
        lvQuotations.setAdapter(adap);
    }
}
