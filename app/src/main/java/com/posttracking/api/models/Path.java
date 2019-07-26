package com.posttracking.api.models;

import com.posttracking.Boundaries.LocalConfig;

import java.util.ArrayList;

public class Path {
  private ArrayList<Journey> journeys = new ArrayList<>();
  private int position; // Distribution Center ID
  private ArrayList<Integer> visited = new ArrayList<Integer>();

  public ArrayList<Journey> getJourneys() {
    return journeys;
  }

  public ArrayList<Integer> getVisited() {
    return visited;
  }

  public int getPosition() {
    return position;
  }

  public double getNOfDays(long delivery) {
    long now = System.currentTimeMillis();
    return Math.ceil((delivery - now) / (86400000.00));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format("%-15s%4s%-15s\n",getJourneys().get(0).getOrigin().getName(),
            " => ",getJourneys().get(getJourneys().size()-1).getDestination().getName()));
    //sb.append(String.format("%-12s%15s\n","Start Time: ",getJourneys().get(0).getStartFormated()));

    sb.append(String.format("%-20s%14s\n","Est. Delivery Days: ",
            getNOfDays(getJourneys().get(getJourneys().size()-1).getArrival().getTime())));
    sb.append("\n");
    return sb.toString();
  }
}
