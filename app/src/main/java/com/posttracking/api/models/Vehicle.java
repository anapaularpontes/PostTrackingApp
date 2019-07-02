package com.posttracking.api.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
  private int Id = 0;
  private String description;
  private int maxWeight; // Kg
  private int maxVolume; // dc3
  private boolean available = true;
  List<Journey> routes = new ArrayList<Journey>();

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(int maxWeight) {
    this.maxWeight = maxWeight;
  }

  public int getMaxVolume() {
    return maxVolume;
  }

  public void setMaxVolume(int maxVolume) {
    this.maxVolume = maxVolume;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public List<Journey> getRoutes() {
    return routes;
  }

  public void setRoutes(List<Journey> routes) {
    this.routes = routes;
  }
}
