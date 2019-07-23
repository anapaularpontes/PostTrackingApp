package com.posttracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Entities.Invoice;

import java.util.List;

public class ViewInvoicesActivity extends AppCompatActivity {

    ListView lvInvoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoices);

        InvoiceDAO iDAO = new InvoiceDAO(this);
        Log.d("Number of invoices", String.valueOf(iDAO.getInvoices(LocalConfig.customerId).size()));
        lvInvoices = findViewById(R.id.lvInvoices);
        final List<Invoice> invoices = iDAO.getInvoices(LocalConfig.customerId);
        ArrayAdapter<Invoice> adap = new ArrayAdapter<Invoice>(this, R.layout.invoice_list_view, invoices);
        lvInvoices.setAdapter(adap);

        lvInvoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Invoice i = invoices.get(position);
                Intent intent = new Intent(getApplicationContext(), UpdateInvoiceActivity.class);
                intent.putExtra("invoice_id", i.getInvoice_id());
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
