package com.posttracking.api;

import com.posttracking.Entities.Customer;
import com.posttracking.api.models.DistributionCenter;
import com.posttracking.api.models.RetroUsers;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostTrackingAPI {

  //Get DistributionCenters
  @GET("api/dcs")
  Call<List<DistributionCenter>> getAllDistributionCenter();

  //Get Quote
  @GET("packages/seekpath/{origin}/{destination}/{weight_s}/{volume_s}")
  Call<List<com.posttracking.api.models.Path>> getQuotation(@Path("origin") String origin,
          @Path("destination") String destination,@Path("weight_s") String weight_s,@Path("volume_s") String volume_s);

  @GET("api/customerbymail/{email}")
  Call<List<Customer>> getCustomerByEmail(@Path("email") String email);

  @FormUrlEncoded
  @POST("/api/customers")
  Call<Customer> createCustomer(@Field("firstName") String firstName,@Field("lastName") String lastName,
                                @Field("emailAddress") String emailAddress);
}
