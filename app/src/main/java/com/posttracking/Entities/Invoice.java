package com.posttracking.Entities;

public class Invoice {
    int invoice_id = 0;
    int cust_id = 0;
    int pack_id = 0;
    double deliveryTime = 0.0;
    double amount = 0.0;
    int status = 0;

    public Invoice() {}



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
        this.pack_id = invoice_id;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(double deliveryTime) { this.deliveryTime = deliveryTime; }

    public double getAmount() {
        return amount;
    }

    public void generateAmount(double weight, double volume) {
        this.amount = ((weight * 0.9) + (volume * 0.8)) *  (1.0 / (double) this.deliveryTime);
    }

    public void setAmount(double amt) {
        this.amount = amt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
