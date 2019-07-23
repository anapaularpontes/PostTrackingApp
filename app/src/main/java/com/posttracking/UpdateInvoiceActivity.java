package com.posttracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.posttracking.Boundaries.InvoiceDAO;
import com.posttracking.Entities.Invoice;

import java.text.NumberFormat;

public class UpdateInvoiceActivity extends AppCompatActivity {

    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    final TextView status = (TextView) findViewById(R.id.txtStatus);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_invoice);

        int invoice_id = getIntent().getIntExtra("invoice_id", 0);

        final TextView inv_id = (TextView) findViewById(R.id.txtInvID);
        final TextView pack_id = (TextView) findViewById(R.id.txtPackageID);
        final TextView amount = (TextView) findViewById(R.id.txtAmount);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);


        InvoiceDAO iDAO = new InvoiceDAO(this);

        final Invoice i = iDAO.getInvoice(invoice_id);
        inv_id.setText(String.valueOf(i.getInvoice_id()));
        pack_id.setText(String.valueOf(i.getPack_id()));
        amount.setText(formatter.format(i.getAmount()));

        final Spinner spinner = (Spinner) findViewById(R.id.spPaymStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.invoicePayment_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        switch(i.getStatus())
        {
            case "Not paid":
                spinner.setSelection(0);
                break;
            case "Paid":
                spinner.setSelection(1);
                break;
            case "Cancelled":
                spinner.setSelection(2);
                break;
        }


        packStatus(spinner.getSelectedItem().toString());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packStatus(spinner.getSelectedItem().toString());

                try{
                    i.setStatus(spinner.getSelectedItem().toString());
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(), "Please, review your fields", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    public void packStatus(String statusPack)
    {
        switch(statusPack) {
            case "Not paid":
                status.setText("Awaiting payment");
                break;
            case "Paid":
                status.setText("Ready to send");
                break;
            case "Cancelled":
                status.setText("Cancelled");
                break;
        }
    }
}
