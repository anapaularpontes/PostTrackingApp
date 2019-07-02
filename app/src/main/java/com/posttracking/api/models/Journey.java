package com.posttracking.api.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Journey {
  protected int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
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

  public Set<Package> getPackages() {
    return packages;
  }

  public void setPackages(Set<Package> packages) {
    this.packages = packages;
  }

  public Timestamp getStart() {
    return start;
  }

  public void setStart(Timestamp start) {
    this.start = start;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public long getRestart() {
    return restart;
  }

  public void setRestart(long restart) {
    this.restart = restart;
  }

  protected Vehicle vehicle;
  protected DistributionCenter origin;
  protected DistributionCenter destination;
  private Set<Package> packages = new HashSet<Package>();
  protected Timestamp start;
  protected int duration;
  protected boolean available;
  protected long restart;
}
