package com.example.imh_mega.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.imh_mega.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ReportMapViewFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    double Latitude = 14.444444;
    double Longitude = 120.888888;
    GoogleMap mGoogleMap;

    NavController navController;
    Button btnBackToReport;

    String locHistLat, locHistLong;

    public ReportMapViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_map_view, container, false);

        mapView = view.findViewById(R.id.mapViewReportID);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBackToReport = view.findViewById(R.id.btnBackToReportID);
        navController = Navigation.findNavController(view);

        if (getArguments() != null){
            ReportMapViewFragmentArgs args = ReportMapViewFragmentArgs.fromBundle(getArguments());
            locHistLat = args.getLatitude();
            locHistLong = args.getLongitude();
        }

        btnBackToReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_reportMapViewFragment_to_locationHistoryReportFragment);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong))));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong)), 15));

        //mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude)));

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}