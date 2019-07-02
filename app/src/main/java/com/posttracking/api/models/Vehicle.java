package com.posttracking.api.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
  private int id;
  private String description;
  private int maxWeight; // Kg
  private int maxVolume; // dc3
  private boolean available;
  List<Journey> routes;

  public Vehicle(int id, String description, int maxWeight, int maxVolume, boolean available, List<Journey> routes) {
    this.id = id;
    this.description = description;
    this.maxWeight = maxWeight;
    this.maxVolume = maxVolume;
    this.available = available;
    this.routes = routes;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    id = id;
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
