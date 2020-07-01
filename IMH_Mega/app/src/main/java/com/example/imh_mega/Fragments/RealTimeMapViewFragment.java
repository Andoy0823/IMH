package com.example.imh_mega.Fragments;

import android.content.Intent;
import android.net.Uri;
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

import com.example.imh_mega.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class RealTimeMapViewFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap mGoogleMap;
    double Latitude = 14.350099;
    double Longitude = 120.944006;

    NavController navController;
    Button btnBackToHome, btnRealTimeWaze;


    public RealTimeMapViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_time_map_view, container, false);
        mapView = view.findViewById(R.id.mapViewRTID);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        btnBackToHome = view.findViewById(R.id.btnBackToHomeID);
        btnRealTimeWaze = view.findViewById(R.id.btnRealTimeWazeID);

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_realTimeMapViewFragment_to_homeFragment);
            }
        });

        btnRealTimeWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naviWaze = ("waze://?ll="+Latitude+","+Longitude+"&navigate=yes");
                Intent intentToWaze = new Intent(Intent.ACTION_VIEW, Uri.parse(naviWaze));
                startActivity(intentToWaze);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 15));
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