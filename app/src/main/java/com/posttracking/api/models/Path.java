package com.posttracking.api.models;

import java.util.ArrayList;

public class Path {
  private ArrayList<Journey> path = new ArrayList<>();
  private int position; // Distribution Center ID
  private ArrayList<Integer> visited = new ArrayList<Integer>();
}
