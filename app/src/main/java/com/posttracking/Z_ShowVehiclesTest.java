package com.posttracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.posttracking.adapters.HomeListAdapter;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.Vehicle;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Z_ShowVehiclesTest extends AppCompatActivity {

  private static final String TAG = "Z_ShowVehiclesTest";

  private HomeListAdapter homeListAdapter;
  RecyclerView homeRecyclerView;

  PostTrackingAPI postTrackingAPI;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_z_show_vehicles_test);
    Log.i(TAG, "OnCreate");
    homeRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);

    // Create the retrofit instance
    postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);

    Call<List<Vehicle>> call = postTrackingAPI.getAllVehicles();
    call.enqueue(new Callback<List<Vehicle>>() {
      @Override
      public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
        Log.i(TAG, response.body().toString());
        loadDataList(response.body());
      }

      @Override
      public void onFailure(Call<List<Vehicle>> call, Throwable t) {
          Log.i(TAG, t.getMessage());
        Toast.makeText(Z_ShowVehiclesTest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onBackPressed() {
    Intent it = new Intent(this, LoginActivity.class);
    startActivity(it);
  }

  //Display the retrieved data as a list//
  private void loadDataList(List<Vehicle> usersList) {
    homeListAdapter = new HomeListAdapter(usersList);

    //Use a LinearLayoutManager with default vertical orientation//
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Z_ShowVehiclesTest.this);
    homeRecyclerView.setLayoutManager(layoutManager);

    //Set the Adapter to the RecyclerView//
    homeRecyclerView.setAdapter(homeListAdapter);
  }

}
