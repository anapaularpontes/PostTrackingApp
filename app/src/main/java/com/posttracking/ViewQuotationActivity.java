package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Boundaries.LocalConfig;
import com.posttracking.Boundaries.QuotationDAO;
import com.posttracking.Entities.Invoice;
import com.posttracking.Entities.Quotation;

public class ViewQuotationActivity extends AppCompatActivity {

    ListView lvQuotations;

    InvoiceDAO iDAO = new InvoiceDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        QuotationDAO qDAO = new QuotationDAO(this);
        Log.d("# of Quotations", String.valueOf(qDAO.getQuotations(LocalConfig.packageId).size()));
        lvQuotations = findViewById(R.id.lvQuotation);
        final ArrayAdapter<Quotation> adap = new ArrayAdapter<Quotation>(this, R.layout.quotation_list_view, qDAO.getQuotations(LocalConfig.packageId));
        lvQuotations.setAdapter(adap);


        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quotation q = adap.getItem(lvQuotations.getSelectedItemPosition());

                Invoice i = new Invoice();
                i.setCust_id(LocalConfig.customerId);
                i.setPack_id(LocalConfig.packageId);
                i.setQuote_id(q.getQuote_id());
                i.setAmount(q.getAmount());
                i.setStatus(1);

                iDAO.createInvoice(i);
            }
        });
    }
}
