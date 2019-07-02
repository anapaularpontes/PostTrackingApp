package com.posttracking.api.models;

import com.google.gson.annotations.SerializedName;

public class RetroUsers {

//Give the field a custom name//

  @SerializedName("name")
  private String name;

  public RetroUsers(String name) {
    this.name = name;

  }

  public String getUser() {
    return name;
  }

  public void setUser(String name) {
    this.name = name;
  }

}