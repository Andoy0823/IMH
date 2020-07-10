package com.example.imh_mega.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.R;

import java.util.ArrayList;

public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.ViewHolder> {

    private ArrayList<LocationHistorySpinnerModel> spinnerMowdelArrayList = new ArrayList<>();
    private Context context;

    public LocationHistoryAdapter (Context context, ArrayList<LocationHistorySpinnerModel> spinnerMowdelArrayList){

        this.spinnerMowdelArrayList = spinnerMowdelArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public LocationHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_history_list, parent, false);

        return new LocationHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHistoryAdapter.ViewHolder holder, int position) {

        holder.txtTimeView.setText("Time: " + spinnerMowdelArrayList.get(position).getRtcTime());
        holder.txtDateView.setText("Date: " + spinnerMowdelArrayList.get(position).getRtcDate());
        holder.txtLatView.setText("Latitude: " + spinnerMowdelArrayList.get(position).getRtcLatitude());
        holder.txtLongView.setText("Longitude: " + spinnerMowdelArrayList.get(position).getRtcLongitude());
        holder.txtDivision.setText("----------------------------------------");

    }

    @Override
    public int getItemCount() {
        return spinnerMowdelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTimeView, txtDateView, txtLatView, txtLongView, txtDivision;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTimeView = itemView.findViewById(R.id.txtViewTimeHistoryID);
            txtDateView = itemView.findViewById(R.id.txtViewDateHistoryID);
            txtLatView = itemView.findViewById(R.id.txtViewLatHistoryID);
            txtLongView = itemView.findViewById(R.id.txtViewLongHistoryID);
            txtDivision = itemView.findViewById(R.id.txtViewDivisionID);

        }
    }
}
