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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;

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
    TextView txtViewLocHistLat, txtViewLocHistLong;

    @BindView(R.id.spinnerLastLocID) Spinner spinLastLoc;

    APIInterface apiInterface;

    ArrayList<String> lastNhistory;

    Integer counter = 0;


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
        txtViewLocHistLat = view.findViewById(R.id.txtViewLocHistLatID);
        txtViewLocHistLong = view.findViewById(R.id.txtViewLocHistLongID);

        //Array

        lastNhistory = new ArrayList<>();

        //Api
        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        //Experimental Values. Delete later
        txtViewLocHistLat.setText("14.350099");
        txtViewLocHistLong.setText("120.944006");

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Location History Spinner

        Call<List<LocationHistorySpinnerModel>> historyRepowrt = apiInterface.getHistowry();

        historyRepowrt.enqueue(new Callback<List<LocationHistorySpinnerModel>>() {
            @Override
            public void onResponse(Call<List<LocationHistorySpinnerModel>> call, Response<List<LocationHistorySpinnerModel>> response) {

                if (!response.isSuccessful()){

                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                }

                List<LocationHistorySpinnerModel> spinnerModelList = response.body();

                for (LocationHistorySpinnerModel spinnerMowdel : spinnerModelList){

                    counter++;

                    if (counter == 1){

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



            }
        });

        btnPlotReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_locationHistoryReportFragment_to_reportMapViewFragment);
                LocationHistoryReportFragmentDirections.ActionLocationHistoryReportFragmentToReportMapViewFragment action
                        = LocationHistoryReportFragmentDirections.actionLocationHistoryReportFragmentToReportMapViewFragment();

                action.setLatitude(txtViewLocHistLat.getText().toString().trim());
                action.setLongitude(txtViewLocHistLong.getText().toString().trim());
                action.setFragmentBackStack(2);
                action.setPlotAllHospital(false);

                navController.navigate(action);

            }
        });

    }
}