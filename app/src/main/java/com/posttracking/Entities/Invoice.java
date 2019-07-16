package com.posttracking.Entities;

public class Invoice {
    int invoice_id = 0;
    int cust_id = 0;
    int pack_id = 0;
    int quote_id = 0;
    double amount = 0;
    int status = 0;

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public int getPack_id() {
        return pack_id;
    }

    public void setPack_id(int invoice_id) {
        this.pack_id = pack_id;
    }

    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(int quote_id) { this.quote_id = quote_id; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) { this.amount = amount; }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
