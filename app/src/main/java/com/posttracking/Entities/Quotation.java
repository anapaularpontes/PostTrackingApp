package com.posttracking.Entities;

public class Quotation {
    int quote_id = 0;
    int pack_id = 0;
    int deliveryTime = 0;
    double amount = 0;

    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(int quote_id) { this.quote_id = quote_id; }

    public int getPack_id() {
        return pack_id;
    }

    public void setPack_id(int pack_id) { this.pack_id = pack_id; }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) { this.amount = amount; }

}
