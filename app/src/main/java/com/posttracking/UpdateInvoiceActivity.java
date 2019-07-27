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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_invoice);
        int invoice_id = getIntent().getIntExtra("invoice_id", 0);

        final TextView inv_id =  findViewById(R.id.txtInvID);
        final TextView pack_id = findViewById(R.id.txtPackageID);
        final TextView deliveryTime = findViewById(R.id.txtTime);
        final TextView amount = findViewById(R.id.txtAmount);
        Button btnUpdate =  findViewById(R.id.btnUpdate);

        final InvoiceDAO iDAO = new InvoiceDAO(this);
        final Invoice i = iDAO.getInvoice(invoice_id);

        String comp = "";

        if(i.getDeliveryTime() > 1)
            comp = " days";
        else
            comp = " day";

        inv_id.setText(String.valueOf(i.getInvoice_id()));
        pack_id.setText(String.valueOf(i.getPack_id()));
        deliveryTime.setText(String.valueOf(i.getDeliveryTime()) + comp);
        amount.setText(formatter.format(i.getAmount()));

        final Spinner spinner = findViewById(R.id.spPaymStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.invoicePayment_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(i.getStatus());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    i.setStatus(spinner.getSelectedItemPosition());
                    iDAO.setInvoiceStatus(i);
                    finish();
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(), "Please, review your fields", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    public void packStatus(int statusPack)
    {
        /*switch(statusPack) {
            case 0:
                status.setText("Awaiting payment");
                break;
            case 1:
                status.setText("Ready to send");
                break;
            case 2:
                status.setText("Cancelled");
                break;
        }*/
    }
}
