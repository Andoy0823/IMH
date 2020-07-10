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

import com.example.imh_mega.R;


public class IncidentReportFragment extends Fragment{

    NavController navController;
    Spinner spinnerReportType;
    Button btnPlotIncident;
    TextView txtViewIncidentLat, txtViewIncidentLong, txtViewHospitalLoc, txtViewPoliceLoc;

    public IncidentReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incident_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Hooks
        btnPlotIncident = view.findViewById(R.id.btnPlotIncidentLocID);
        txtViewIncidentLat = view.findViewById(R.id.txtViewIncidentLatID);
        txtViewIncidentLong = view.findViewById(R.id.txtViewIncidentLongID);
        txtViewHospitalLoc = view.findViewById(R.id.txtViewIncidentHospitalID);
        txtViewPoliceLoc = view.findViewById(R.id.txtViewIncidentPoliceID);
        spinnerReportType = view.findViewById(R.id.spinnerReportTypeID);

        //Experimental Value (Delete Later)
        txtViewIncidentLat.setText("14.400995");
        txtViewIncidentLong.setText("120.965846");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.reportTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReportType.setAdapter(adapter);
        spinnerReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();
                navController = Navigation.findNavController(view);
                if (text.equals("Location History Report")){
                    navController.navigate(R.id.action_incidentReportFragment_to_locationHistoryReportFragment);
                }
                else if (text.equals("Hospital Locations Report")){
                    navController.navigate(R.id.action_incidentReportFragment_to_hospitalLocationReportFragment);
                }
                else if (text.equals("Police Locations Report")){
                    navController.navigate(R.id.action_incidentReportFragment_to_policeLocationReportFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPlotIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncidentReportFragmentDirections.ActionIncidentReportFragmentToReportMapViewFragment action = IncidentReportFragmentDirections.actionIncidentReportFragmentToReportMapViewFragment();

                action.setLatitude(txtViewIncidentLat.getText().toString().trim());
                action.setLongitude(txtViewIncidentLong.getText().toString().trim());
                action.setFragmentBackStack(1);
                action.setPlotAllHospital(false);

                navController.navigate(action);
            }
        });

    }

}