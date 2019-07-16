package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Entities.Invoice;

public class ViewInvoicesActivity extends AppCompatActivity {

    ListView lvInvoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoices);

        InvoiceDAO iDAO = new InvoiceDAO(this);
        Log.d("Number of invoices", String.valueOf(iDAO.getInvoices(LocalConfig.customerId).size()));
        lvInvoices = findViewById(R.id.lvInvoices);
        ArrayAdapter<Invoice> adap = new ArrayAdapter<Invoice>(this, R.layout.login_list_view, iDAO.getInvoices(LocalConfig.customerId));
        lvInvoices.setAdapter(adap);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
