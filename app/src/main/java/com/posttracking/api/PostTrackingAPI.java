package com.posttracking.api;

import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.RetroUsers;
import com.posttracking.api.models.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostTrackingAPI {

  //TESTS
  @GET("/users")
  Call<List<RetroUsers>> getAllUsers();

  @GET("api/vehicles")
  Call<List<Vehicle>> getAllVehicles();


  //Get DistributionCenters
  @GET("api/dcs")
  Call<List<DistributionCenter>> getAllDistributionCenter();
}
