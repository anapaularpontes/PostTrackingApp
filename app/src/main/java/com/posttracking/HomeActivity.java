package com.posttracking;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.posttracking.adapters.HomeListAdapter;
import com.posttracking.api.PostTrackingAPI;
import com.posttracking.api.RetrofitClient;
import com.posttracking.api.models.RetroUsers;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

  private static final String TAG = "HomeActivity";

  private HomeListAdapter homeListAdapter;
  RecyclerView homeRecyclerView;

  PostTrackingAPI postTrackingAPI;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    Log.i(TAG, "OnCreate");
    homeRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);

    // Create the retrofit instance
    postTrackingAPI = RetrofitClient.getRetrofitInstance().create(PostTrackingAPI.class);

    Call<List<RetroUsers>> call = postTrackingAPI.getAllUsers();
    call.enqueue(new Callback<List<RetroUsers>>() {
      @Override
      public void onResponse(Call<List<RetroUsers>> call, Response<List<RetroUsers>> response) {
        loadDataList(response.body());
      }

      @Override
      public void onFailure(Call<List<RetroUsers>> call, Throwable t) {
        Toast.makeText(HomeActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
      }
    });
  }

  //Display the retrieved data as a list//
  private void loadDataList(List<RetroUsers> usersList) {
    homeListAdapter = new HomeListAdapter(usersList);

    //Use a LinearLayoutManager with default vertical orientation//
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
    homeRecyclerView.setLayoutManager(layoutManager);

    //Set the Adapter to the RecyclerView//
    homeRecyclerView.setAdapter(homeListAdapter);
  }

}
