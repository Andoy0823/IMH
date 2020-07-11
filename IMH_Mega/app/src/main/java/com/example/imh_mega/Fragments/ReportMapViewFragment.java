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

import java.util.Arrays;


public class ReportMapViewFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    double Latitude = 14.444444;
    double Longitude = 120.888888;
    double allHospitalLat[] = {14.426269, 14.399959, 14.376212, 14.373747, 14.395625, 14.405804, 14.410574, 14.429737};
    double allHospitalLong[] = {120.946531, 120.939514, 120.938735, 120.979819, 120.987978, 120.976867, 120.976085, 121.003543};
    String hospitalName[] = {"Medical Center Imus","City of Imus Doctors Hospital", "Medicard Cavite Clinic", "Metrosouth Medical Center", "San Agustin Medical Clinic", "Southeast Asian Medical Center",
    "Molino Doctors Hospital", "Las Pinas City Medical Center"};

    double allPoliceLat[] = {14.388826, 14.382097, 14.373063, 14.376789, 14.350296, 14.325721, 14.422035, 14.42297};
    double allPoliceLong[] = {120.984376, 120.988721, 120.980103, 120.9388, 120.937898, 120.94076, 120.94639, 120.941584};
    String policeName[] = {"Bacoor City Police - Camella Springeville", "Bacoor City Community Police - Daang Hari", "Molino IV PNP Sub-Station - Molino Rd.", "Police Community Precinct 3 - EAH, IMUS"
            , "Police Community Precinct 3 - EAH, IMUS", "Police Station - Waltermart, DASMARINAS", "Police Station - Buhay Na Tubig, IMUS", "Imus Police Station - Nueno Ave., IMUS"};



    GoogleMap mGoogleMap;

    NavController navController;
    Button btnBackToReport;

    String locHistLat, locHistLong;
    //String lastLat0, lastLat1, lastLat2, lastLat3, lastLat4, lastLat5, lastLat6, lastLat7, lastLat8, lastLat9;
    //String lastLong0, lastLong1, lastLong2, lastLong3, lastLong4, lastLong5, lastLong6, lastLong7, lastLong8, lastLong9;

    String lastLat[];
    String lastLong[];
    int backFragment;
    Boolean plotAll=false, plotAllPolice=false, plotLastTen=false;

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

        lastLat = new String[10];
        lastLong = new String[10];
        //Argument Receiver from other Fragments
        if (getArguments() != null){
            ReportMapViewFragmentArgs args = ReportMapViewFragmentArgs.fromBundle(getArguments());
            locHistLat = args.getLatitude();
            locHistLong = args.getLongitude();
            backFragment = args.getFragmentBackStack();
            plotAll = args.getPlotAllHospital();
            plotAllPolice = args.getPlotAllPolice();
            plotLastTen = args.getPlotLastTenLoc();

            //Arguments for last 10 Locations(Latitude)
            lastLat[0] = args.getLastLat0();
            lastLat[1] = args.getLastLat1();
            lastLat[2] = args.getLastLat2();
            lastLat[3] = args.getLastLat3();
            lastLat[4] = args.getLastLat4();
            lastLat[5] = args.getLastLat5();
            lastLat[6] = args.getLastLat6();
            lastLat[7] = args.getLastLat7();
            lastLat[8] = args.getLastLat8();
            lastLat[9] = args.getLastLat9();

            //Arguments for Last 10 Locations(Longitude)
            lastLong[0] = args.getLastLong0();
            lastLong[1] = args.getLastLong1();
            lastLong[2] = args.getLastLong2();
            lastLong[3] = args.getLastLong3();
            lastLong[4] = args.getLastLong4();
            lastLong[5] = args.getLastLong5();
            lastLong[6] = args.getLastLong6();
            lastLong[7] = args.getLastLong7();
            lastLong[8] = args.getLastLong8();
            lastLong[9] = args.getLastLong9();
        }

        btnBackToReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_reportMapViewFragment_to_locationHistoryReportFragment);
                if (backFragment == 1){
                    navController.navigate(R.id.action_reportMapViewFragment_to_incidentReportFragment);
                }
                else if (backFragment == 2){
                    navController.navigate(R.id.action_reportMapViewFragment_to_locationHistoryReportFragment);
                }
                else if (backFragment == 3){
                    navController.navigate(R.id.action_reportMapViewFragment_to_hospitalLocationReportFragment);
                }
                else {
                    navController.navigate(R.id.action_reportMapViewFragment_to_policeLocationReportFragment);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        if (!plotAll && !plotAllPolice && !plotLastTen){
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong))).title("This be a title!"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong)), 15));
        }
        else if (plotAll && !plotAllPolice && !plotLastTen){
            for (int i = 0; i<allHospitalLat.length; i++){
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(allHospitalLat[i], allHospitalLong[i])).title(hospitalName[i]));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.405804, 120.976867), 12));
            }
        }
        else if (plotAllPolice && !plotAll && !plotLastTen){
            for (int i = 0; i<allPoliceLat.length; i++){
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(allPoliceLat[i], allPoliceLong[i])).title(policeName[i]));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.405804, 120.976867), 12));
            }
        }
        else if (plotLastTen && !plotAll && !plotAllPolice){
            for (int i = 0; i<lastLat.length; i++){
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lastLat[i]), Double.parseDouble(lastLong[i]))).title("Location No." + i));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lastLat[0]), Double.parseDouble(lastLong[0])), 12));
            }
        }
        //mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong))).title("This be a title!"));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(locHistLat), Double.parseDouble(locHistLong)), 15));
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