package com.example.imh_mega.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Adapters.LocationHistoryAdapter;
import com.example.imh_mega.Fragments.Models.CoordinateLatitudeModel;
import com.example.imh_mega.Fragments.Models.CoordinateLongitudeModel;
import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.LoadingDialog;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationHistoryReportFragment extends Fragment {

    NavController navController;
    Spinner spinnerLocReportType;
    Button btnPlotReport;

    APIInterface apiInterface;

    //Choose how many locations to be outputed
    ArrayList<String> lastNhistory;
    Integer counter = 1;

    //Recycler View

    private LocationHistoryAdapter locationHistoryAdapter;
    private RecyclerView locationHistory_recyclerView;
    ArrayList<LocationHistorySpinnerModel> lowcationHistoryArray = new ArrayList<>();

    //Global latitude/longitude coordinates(Arguments for map fragment)
    String globalLatitude[];
    String globalLongitude[];

    LoadingDialog loadingDialog;

    //Refresh RecyclerView
    private Handler refreshRecyclerViewHandler = new Handler();


    public LocationHistoryReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_history_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        //Hooks
        navController = Navigation.findNavController(view);
        spinnerLocReportType = view.findViewById(R.id.spinnerLocReportTypeID);
        btnPlotReport = view.findViewById(R.id.btnPlotHistoryLocID);
        loadingDialog = new LoadingDialog(getActivity());

        //Array

        lastNhistory = new ArrayList<>();

        //Api
        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        //Spinner for Reports

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.reportTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocReportType.setAdapter(adapter);
        spinnerLocReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();
                navController = Navigation.findNavController(view);
                switch (text) {
                    case "Incident Report":
                        navController.navigate(R.id.action_locationHistoryReportFragment_to_incidentReportFragment);
                        break;
                    case "Hospital Locations Report":
                        navController.navigate(R.id.action_locationHistoryReportFragment_to_hospitalLocationReportFragment);
                        break;
                    case "Police Locations Report":
                        navController.navigate(R.id.action_locationHistoryReportFragment_to_policeLocationReportFragment);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        refreshRunnable.run();

        //Initialize Latitude and Longitude to be plotted.
        loadingDialog.startLoadingDialog();
        getLatCoord();
        getLongCoord();
        loadingDialog.dismissDialog();

        //setBtnPlotReport();

        btnPlotReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationHistoryReportFragmentDirections.ActionLocationHistoryReportFragmentToReportMapViewFragment action
                        = LocationHistoryReportFragmentDirections.actionLocationHistoryReportFragmentToReportMapViewFragment();


                //Set Fragment Arguments
                action.setFragmentBackStack(2);
                action.setPlotAllHospital(false);
                action.setLatitude(globalLatitude[0]);
                action.setLongitude(globalLongitude[0]);
                action.setPlotLastTenLoc(true);

                //Set Last 10 Location Arguments(Latitude)
                action.setLastLat0(globalLatitude[0]);
                action.setLastLat1(globalLatitude[1]);
                action.setLastLat2(globalLatitude[2]);
                action.setLastLat3(globalLatitude[3]);
                action.setLastLat4(globalLatitude[4]);
                action.setLastLat5(globalLatitude[5]);
                action.setLastLat6(globalLatitude[6]);
                action.setLastLat7(globalLatitude[7]);
                action.setLastLat8(globalLatitude[8]);
                action.setLastLat9(globalLatitude[9]);

                //Set Last 10 Location Arguments(Longitude)
                action.setLastLong0(globalLongitude[0]);
                action.setLastLong1(globalLongitude[1]);
                action.setLastLong2(globalLongitude[2]);
                action.setLastLong3(globalLongitude[3]);
                action.setLastLong4(globalLongitude[4]);
                action.setLastLong5(globalLongitude[5]);
                action.setLastLong6(globalLongitude[6]);
                action.setLastLong7(globalLongitude[7]);
                action.setLastLong8(globalLongitude[8]);
                action.setLastLong9(globalLongitude[9]);

                navController.navigate(action);
                //Toast.makeText(getActivity(), "Latitude= " + Arrays.toString(globalLatitude), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "Longitude= " + Arrays.toString(globalLongitude), Toast.LENGTH_SHORT).show();

            }
        });

        //Recycler View
        locationHistory_recyclerView = view.findViewById(R.id.locationHistoryRecyclerView);
        locationHistory_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }


    /* public static String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    } */

    private void getLocationHistory(){

        Call<List<LocationHistorySpinnerModel>> historyListCall = apiInterface.getHistowry();

        historyListCall.enqueue(new Callback<List<LocationHistorySpinnerModel>>() {
            @Override
            public void onResponse(Call<List<LocationHistorySpinnerModel>> call, Response<List<LocationHistorySpinnerModel>> response) {

                lowcationHistoryArray = new ArrayList<>(response.body());

                for (LocationHistorySpinnerModel lowcationHistorySpinnerModel : lowcationHistoryArray){

                    locationHistoryAdapter = new LocationHistoryAdapter(getActivity(), lowcationHistoryArray);

                    locationHistory_recyclerView.setAdapter(locationHistoryAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<LocationHistorySpinnerModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setBtnPlotReport(){

        //Plot Report

        btnPlotReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationHistoryReportFragmentDirections.ActionLocationHistoryReportFragmentToReportMapViewFragment action
                        = LocationHistoryReportFragmentDirections.actionLocationHistoryReportFragmentToReportMapViewFragment();


                //Initialize Latitude and Longitude to be plotted.
                getLatCoord();
                getLongCoord();


                //Set Fragment Arguments
                action.setFragmentBackStack(2);
                action.setPlotAllHospital(false);


                Toast.makeText(getActivity(), "Latitude= " + Arrays.toString(globalLatitude), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "Longitude= " + Arrays.toString(globalLongitude), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void getLatCoord(){

        Call<List<CoordinateLatitudeModel>> latitudeListCall = apiInterface.getLatitudeList();
        latitudeListCall.enqueue(new Callback<List<CoordinateLatitudeModel>>() {
            @Override
            public void onResponse(Call<List<CoordinateLatitudeModel>> call, Response<List<CoordinateLatitudeModel>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }

                List<CoordinateLatitudeModel> coordinateLatitudeMowdelList = response.body();

                String latitudeCoord[] = new String[coordinateLatitudeMowdelList.size()];

                for (int i = 0; i < coordinateLatitudeMowdelList.size(); i++){

                    latitudeCoord[i] = coordinateLatitudeMowdelList.get(i).getRtcLatitude();

                }
                globalLatitude = new String[latitudeCoord.length];
                for (int i=0; i<latitudeCoord.length; i++){
                    globalLatitude[i] = latitudeCoord[i];
                }
                //Toast.makeText(getActivity(), "Latitude: " + Arrays.toString(latitudeCoord), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<CoordinateLatitudeModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void getLongCoord(){

        Call<List<CoordinateLongitudeModel>> longitudeListCall = apiInterface.getLongitudeList();

        longitudeListCall.enqueue(new Callback<List<CoordinateLongitudeModel>>() {
            @Override
            public void onResponse(Call<List<CoordinateLongitudeModel>> call, Response<List<CoordinateLongitudeModel>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }

                List<CoordinateLongitudeModel> coordinateLongitudeMowdelList = response.body();

                String longitudeCoord[] = new String[coordinateLongitudeMowdelList.size()];

                for (int i = 0; i < coordinateLongitudeMowdelList.size(); i++){

                    longitudeCoord[i] = coordinateLongitudeMowdelList.get(i).getRtcLongitude();

                }

                globalLongitude = new String[10];
                for (int i=0; i<longitudeCoord.length; i++){
                    globalLongitude[i] = longitudeCoord[i];
                }
                //Toast.makeText(getActivity(), "Longitude: " + Arrays.toString(longitudeCoord), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CoordinateLongitudeModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            getLocationHistory();
            refreshRecyclerViewHandler.postDelayed(this, 5000);
        }
    };

}