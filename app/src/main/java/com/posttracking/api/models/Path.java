package com.posttracking.api.models;

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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Journey j: getJourneys()) {
      sb.append(String.format("%-15s%4s%-15s\n",j.getOrigin().getName()," => ",j.getDestination().getName()));
    }
    sb.append(String.format("%-12s%15s\n","Start Time: ",getJourneys().get(0).getStartFormated()));
    sb.append(String.format("%-12s%15s\n","End Time: ",getJourneys().get(getJourneys().size()-1).getArrivalFormated()));
    sb.append("\n");
    return sb.toString();
  }
}
