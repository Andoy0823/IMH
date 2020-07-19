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


public class HospitalLocationReportFragment extends Fragment {

    Spinner spinnerHospitalReport, spinnerHospitalChooser;
    NavController navController;
    TextView txtViewHospitalLat, txtViewHospitalLong, txtViewHospitalAddress, txtViewHospitalContact;
    Button btnPlotSpecHospital, btnPlotAllHospital;
    String hospitalContact[] = {"09209536984", "09209536984", "09752955837", "09752955837", "09752955837", "09177280117", "09177280117", "09177280117"};

    public HospitalLocationReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_location_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Hooks
        spinnerHospitalReport = view.findViewById(R.id.spinnerHospitalReportID);
        spinnerHospitalChooser = view.findViewById(R.id.spinnerHospitalChooserID);
        txtViewHospitalLat = view.findViewById(R.id.txtViewHospitalLatID);
        txtViewHospitalLong = view.findViewById(R.id.txtViewHospitalLongID);
        txtViewHospitalAddress = view.findViewById(R.id.txtViewHospitalAddressID);
        btnPlotSpecHospital = view.findViewById(R.id.btnPlotSpecHospitalID);
        btnPlotAllHospital = view.findViewById(R.id.btnPlotAllHospitalID);
        txtViewHospitalContact = view.findViewById(R.id.txtViewHospitalContactID);
        navController = Navigation.findNavController(view);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.reportTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospitalReport.setAdapter(adapter);
        spinnerHospitalReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();
                navController = Navigation.findNavController(view);
                if (text.equals("Incident Report")){
                    navController.navigate(R.id.action_hospitalLocationReportFragment_to_incidentReportFragment);
                }
                else if (text.equals("Location History Report")){
                    navController.navigate(R.id.action_hospitalLocationReportFragment_to_locationHistoryReportFragment);
                }
                else if (text.equals("Police Locations Report")){
                    navController.navigate(R.id.action_hospitalLocationReportFragment_to_policeLocationReportFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapterHospital = ArrayAdapter.createFromResource(getActivity(), R.array.hospitals, android.R.layout.simple_spinner_item);
        adapterHospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospitalChooser.setAdapter(adapterHospital);
        spinnerHospitalChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();
                switch (text){
                    case "Medical Center Imus":
                        txtViewHospitalLat.setText("14.426269");
                        txtViewHospitalLong.setText("120.946531");
                        txtViewHospitalAddress.setText("EAH, IMUS");
                        txtViewHospitalContact.setText(hospitalContact[0]);
                        break;
                    case "City of Imus Doctors Hospital":
                        txtViewHospitalLat.setText("14.399959");
                        txtViewHospitalLong.setText("120.939514");
                        txtViewHospitalAddress.setText("EAH, IMUS");
                        txtViewHospitalContact.setText(hospitalContact[1]);
                        break;
                    case "Medicard Cavite Clinic":
                        txtViewHospitalLat.setText("14.376212");
                        txtViewHospitalLong.setText("120.938735");
                        txtViewHospitalAddress.setText("EAH, IMUS");
                        txtViewHospitalContact.setText(hospitalContact[2]);
                        break;
                    case "Metrosouth Medical Center":
                        txtViewHospitalLat.setText("14.373747");
                        txtViewHospitalLong.setText("120.979819");
                        txtViewHospitalAddress.setText("Molino Rd, BACOOR");
                        txtViewHospitalContact.setText(hospitalContact[3]);
                        break;
                    case "San Agustin Medical Clinic":
                        txtViewHospitalLat.setText("14.395625");
                        txtViewHospitalLong.setText("120.987978");
                        txtViewHospitalAddress.setText("QueensRow Subd, BACOOR");
                        txtViewHospitalContact.setText(hospitalContact[4]);
                        break;
                    case "Southeast Asian Medical Center":
                        txtViewHospitalLat.setText("14.405804");
                        txtViewHospitalLong.setText("120.976867");
                        txtViewHospitalAddress.setText("Molino Rd, BACOOR");
                        txtViewHospitalContact.setText(hospitalContact[5]);
                        break;
                    case "Molino Doctors Hospital":
                        txtViewHospitalLat.setText("14.410574");
                        txtViewHospitalLong.setText("120.976085");
                        txtViewHospitalAddress.setText("Molino Rd, BACOOR");
                        txtViewHospitalContact.setText(hospitalContact[6]);
                        break;
                    case "Las Pinas City Medical Center":
                        txtViewHospitalLat.setText("14.429737");
                        txtViewHospitalLong.setText("121.003543");
                        txtViewHospitalAddress.setText("Marcos Alvarez Rd, Las Pinas");
                        txtViewHospitalContact.setText(hospitalContact[7]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPlotSpecHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtViewHospitalLat.equals("") || txtViewHospitalLong.equals("")){
                    Toast.makeText(getActivity(), "Missing Coordinates", Toast.LENGTH_SHORT).show();
                }
                else {
                    HospitalLocationReportFragmentDirections.ActionHospitalLocationReportFragmentToReportMapViewFragment action
                            = HospitalLocationReportFragmentDirections.actionHospitalLocationReportFragmentToReportMapViewFragment();

                    action.setLatitude(txtViewHospitalLat.getText().toString().trim());
                    action.setLongitude(txtViewHospitalLong.getText().toString().trim());
                    action.setFragmentBackStack(3);
                    action.setPlotAllHospital(false);
                    navController.navigate(action);
                }
            }
        });

        btnPlotAllHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalLocationReportFragmentDirections.ActionHospitalLocationReportFragmentToReportMapViewFragment action
                        = HospitalLocationReportFragmentDirections.actionHospitalLocationReportFragmentToReportMapViewFragment();

                action.setLatitude(txtViewHospitalLat.getText().toString().trim());
                action.setLongitude(txtViewHospitalLong.getText().toString().trim());
                action.setFragmentBackStack(3);
                action.setPlotAllHospital(true);
                navController.navigate(action);
            }
        });

    }
}