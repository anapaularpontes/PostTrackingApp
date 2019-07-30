package com.posttracking.api.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Package {
  private int id;
  private int apiId = 0;
  private DistributionCenter origin;
  private DistributionCenter destination;
  private DistributionCenter position;
  private Customer customer;
  private Set<Journey> journeys;
  private double weight;
  private double volume;
  private String recipient;
  private String address;
  private String city;
  private String province;
  private String zipCode;

  private int status = 0;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setApiId(int id) {
    this.apiId = id;
  }

  public int getApiId() {
    return apiId;
  }

  public DistributionCenter getOrigin() {
    return origin;
  }

  public void setOrigin(DistributionCenter origin) {
    this.origin = origin;
  }

  public DistributionCenter getDestination() {
    return destination;
  }

  public void setDestination(DistributionCenter destination) {
    this.destination = destination;
  }

  public DistributionCenter getPosition() {
    return position;
  }

  public void setPosition(DistributionCenter position) {
    this.position = position;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Set<Journey> getJourneys() {
    return journeys;
  }

  public void setJourneys(Set<Journey> journeys) {
    this.journeys = journeys;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }



  @Override
  public String toString() {

    String p = "# "+this.getId()+" To: "+this.recipient + "\n"
            +"Vol:"+this.volume+" Weigth:"+this.weight
            +"\nStatus: ";
    if(this.getApiId()==0) {
        p += "Ready to get Quotations";
    } else {
        switch (this.getStatus()) {
            case 0:
                p += "Awaiting Payment";
                break;
            case 1:
                p += "Ready to send";
                break;
            case 2:
                p += "Sent";
                break;
            default:
                p += "Unknown";
                break;
        }
    }
    return p;
  }

  public List<String> getPathInOrder() {
      List<String> l = new ArrayList<String>();
      DistributionCenter dc = this.origin;
      l.add("Origin:"+this.origin.getName());
      while(dc.getId()!=this.position.getId()){
        for(Journey j : this.getJourneys()) {
            if(j.getOrigin().getId()==dc.getId()) {
                l.add(j.getArrivalFormated()+" - "+j.getDestination().getName());
                dc = j.getDestination();
                break;
            }
        }
      }
      return l;
  }


}
