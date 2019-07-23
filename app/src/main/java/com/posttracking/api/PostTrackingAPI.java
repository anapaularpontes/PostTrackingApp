package com.posttracking.api;

import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.RetroUsers;
import com.posttracking.api.models.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostTrackingAPI {

  //TESTS
  @GET("/users")
  Call<List<RetroUsers>> getAllUsers();

  @GET("api/vehicles")
  Call<List<Vehicle>> getAllVehicles();

  //Get DistributionCenters
  @GET("api/dcs")
  Call<List<DistributionCenter>> getAllDistributionCenter();

  //Get Quote
  @GET("packages/seekpath/{origin}/{destination}/{weight_s}/{volume_s}")
  Call<List<com.posttracking.api.models.Path>> getQuotation(@Path("origin") String origin,
          @Path("destination") String destination,@Path("weight_s") String weight_s,@Path("volume_s") String volume_s);
}
