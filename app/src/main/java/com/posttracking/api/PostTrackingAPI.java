package com.posttracking.api;

import com.posttracking.api.models.RetroUsers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostTrackingAPI {

  //TESTS
  @GET("/users")
  Call<List<RetroUsers>> getAllUsers();
}
