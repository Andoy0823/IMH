package com.example.imh_mega.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Adapters.LocationHistoryAdapter;
import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    @BindView(R.id.btnLocationHistoryUpdateID) Button historyUpdateButton;

    APIInterface apiInterface;

    //Choose how many locations to be outputed

    @BindView(R.id.spinnerLastLocID) Spinner spinLastLoc;
    ArrayList<String> lastNhistory;
    Integer counter = 0;

    //Recycler View

    private LocationHistoryAdapter locationHistoryAdapter;
    private RecyclerView locationHistory_recyclerView;
    ArrayList<LocationHistorySpinnerModel> lowcationHistoryArray = new ArrayList<>();

    /////////////////////////////////////////////////////////////////////////////////


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
                if (text.equals("Incident Report")){
                    navController.navigate(R.id.action_locationHistoryReportFragment_to_incidentReportFragment);
                }
                else if (text.equals("Hospital Locations Report")){
                    navController.navigate(R.id.action_locationHistoryReportFragment_to_hospitalLocationReportFragment);
                }
                else if (text.equals("Police Locations Report")){
                    navController.navigate(R.id.action_locationHistoryReportFragment_to_policeLocationReportFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Location History Spinner

        Call<List<LocationHistorySpinnerModel>> historyRepowrtspinner = apiInterface.getHistowry();

        historyRepowrtspinner.enqueue(new Callback<List<LocationHistorySpinnerModel>>() {
            @Override
            public void onResponse(Call<List<LocationHistorySpinnerModel>> call, Response<List<LocationHistorySpinnerModel>> response) {

                if (!response.isSuccessful()){

                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                }

                List<LocationHistorySpinnerModel> spinnerModelList = response.body();

                for (LocationHistorySpinnerModel spinnerMowdel : spinnerModelList){

                    counter++;

                    if (counter.equals(1)){

                        String historyCheck = "Last " + counter + " location";
                        lastNhistory.add(historyCheck);

                    }

                    else {

                        String historyCheck = "Last " + counter + " locations";

                        lastNhistory.add(historyCheck);

                    }

                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, lastNhistory);

                spinLastLoc.setAdapter(spinnerAdapter);

            }

            @Override
            public void onFailure(Call<List<LocationHistorySpinnerModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //Location History Report

        historyUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Plot Report

        btnPlotReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_locationHistoryReportFragment_to_reportMapViewFragment);
                LocationHistoryReportFragmentDirections.ActionLocationHistoryReportFragmentToReportMapViewFragment action
                        = LocationHistoryReportFragmentDirections.actionLocationHistoryReportFragmentToReportMapViewFragment();

                action.setFragmentBackStack(2);
                action.setPlotAllHospital(false);

                navController.navigate(action);

            }
        });

        //Recycler View

        locationHistory_recyclerView = view.findViewById(R.id.locationHistoryRecyclerView);
        locationHistory_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLocationHistory();


    }

    public static String extractNumber(final String str) {

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
    }

    private void getLocationHistory(){

        Call<List<LocationHistorySpinnerModel>> historyListCall = apiInterface.getHistowry();

        historyListCall.enqueue(new Callback<List<LocationHistorySpinnerModel>>() {
            @Override
            public void onResponse(Call<List<LocationHistorySpinnerModel>> call, Response<List<LocationHistorySpinnerModel>> response) {

                lowcationHistoryArray = new ArrayList<>(response.body());

                String spinnerValue = spinLastLoc.getSelectedItem().toString();

                String convertedSpinnerValue = extractNumber(spinnerValue);

                Integer integeredSpinnerValue = Integer.parseInt(convertedSpinnerValue);

                Integer newintegeredSpinnerValue = integeredSpinnerValue + 102000000;

                for (LocationHistorySpinnerModel lowcationHistorySpinnerModel : lowcationHistoryArray){

                    if (lowcationHistorySpinnerModel.getRtcID().equals(newintegeredSpinnerValue - 1)){

                        locationHistoryAdapter = new LocationHistoryAdapter(getActivity(), lowcationHistoryArray);

                        locationHistory_recyclerView.setAdapter(locationHistoryAdapter);

                        Toast.makeText(getActivity(), "Yey", Toast.LENGTH_SHORT).show();

                    }

                    else{

                        Toast.makeText(getActivity(), "Whoops", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<List<LocationHistorySpinnerModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}