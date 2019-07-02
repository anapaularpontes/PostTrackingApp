package com.posttracking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.posttracking.R;
import com.posttracking.api.models.RetroUsers;

import java.util.List;

//Extend the RecyclerView.Adapter class//

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.CustomViewHolder> {

  private List<RetroUsers> dataList;

  public HomeListAdapter(List<RetroUsers> dataList){

    this.dataList = dataList;
  }

  class CustomViewHolder extends RecyclerView.ViewHolder {

    //Get a reference to the Views in our layout//
    public final View myView;

    TextView textUser;

    CustomViewHolder(View itemView) {
      super(itemView);
      myView = itemView;

      textUser = myView.findViewById(R.id.user);

    }
  }

  @Override
  public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
    return new CustomViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CustomViewHolder holder, int position) {
    holder.textUser.setText(dataList.get(position).getUser());
  }

  //Calculate the item count for the RecylerView//
  @Override
  public int getItemCount() {
    return dataList.size();
  }
}