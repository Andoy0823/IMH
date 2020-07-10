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


public class PoliceLocationReportFragment extends Fragment {

    NavController navController;
    Spinner spinnerReportType, spinnerPoliceChooser;
    TextView txtViewPoliceLat, txtViewPoliceLong, txtViewPoliceAddress, txtViewPoliceContact;
    double allPoliceLat[] = {14.388826, 14.382097, 14.373063, 14.376789, 14.350296, 14.325721, 14.422035, 14.42297};
    double allPoliceLong[] = {120.984376, 120.988721, 120.980103, 120.9388, 120.937898, 120.94076, 120.94639, 120.941584};
    String policeAddress[] = {"Camella Springeville, BACOOR","Daang Hari, BACOOR","Molino Rd., BACOOR","EAH, IMUS","EAH, DASMARINAS","EAH, DASMARINAS","Buhay Na Tubig Rd., IMUS","Nueno Ave., IMUS"};
    String policeContact[] = {"09209536984", "09209536984", "09752955837", "09752955837", "09752955837", "09177280117", "09177280117", "09177280117"};

    Button btnPlotSpecPolice, btnPlotAllPolice;

    public PoliceLocationReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_police_location_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Hooks
        spinnerReportType = view.findViewById(R.id.spinnerPoliceReportID);
        spinnerPoliceChooser = view.findViewById(R.id.spinnerPoliceChooserID);
        txtViewPoliceLat = view.findViewById(R.id.txtViewPoliceLatID);
        txtViewPoliceLong = view.findViewById(R.id.txtViewPoliceLongID);
        txtViewPoliceAddress = view.findViewById(R.id.txtViewPoliceAddressID);
        txtViewPoliceContact = view.findViewById(R.id.txtViewPoliceContactID);
        btnPlotAllPolice = view.findViewById(R.id.btnPlotAllPoliceID);
        btnPlotSpecPolice = view.findViewById(R.id.btnPlotSpecPoliceID);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.reportTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReportType.setAdapter(adapter);
        spinnerReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();
                navController = Navigation.findNavController(view);

                if (text.equals("Location History Report")){
                    navController.navigate(R.id.action_policeLocationReportFragment_to_locationHistoryReportFragment);
                }
                else if (text.equals("Hospital Locations Report")){
                    navController.navigate(R.id.action_policeLocationReportFragment_to_hospitalLocationReportFragment);
                }
                else if (text.equals("Incident Report")){
                    navController.navigate(R.id.action_policeLocationReportFragment_to_incidentReportFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapterPolice = ArrayAdapter.createFromResource(getActivity(), R.array.policeStations, android.R.layout.simple_spinner_item);
        adapterPolice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPoliceChooser.setAdapter(adapterPolice);
        spinnerPoliceChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String text = parent.getItemAtPosition(position).toString();

                switch (text){
                    case "Bacoor City Police - Camela Springeville":

                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[0]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[0]));
                        txtViewPoliceAddress.setText(policeAddress[0]);
                        txtViewPoliceContact.setText(policeContact[0]);
                        break;
                    case "Bacoor City Community Police - Daang Hari":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[1]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[1]));
                        txtViewPoliceAddress.setText(policeAddress[1]);
                        txtViewPoliceContact.setText(policeContact[1]);
                        break;
                    case "Molino IV PNP Sub-Station - Molino Rd.":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[2]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[2]));
                        txtViewPoliceAddress.setText(policeAddress[2]);
                        txtViewPoliceContact.setText(policeContact[2]);
                        break;
                    case "Police Community Precinct 3 - EAH, IMUS":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[3]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[3]));
                        txtViewPoliceAddress.setText(policeAddress[3]);
                        txtViewPoliceContact.setText(policeContact[3]);
                        break;
                    case "Salitran Police Station - EAH, DASMARINAS":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[4]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[4]));
                        txtViewPoliceAddress.setText(policeAddress[4]);
                        txtViewPoliceContact.setText(policeContact[4]);
                        break;
                    case "Police Station - Waltermart, DASMARINAS":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[5]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[5]));
                        txtViewPoliceAddress.setText(policeAddress[5]);
                        txtViewPoliceContact.setText(policeContact[5]);
                        break;
                    case "Police Station - Buhay Na Tubig, IMUS":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[6]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[6]));
                        txtViewPoliceAddress.setText(policeAddress[6]);
                        txtViewPoliceContact.setText(policeContact[6]);
                        break;
                    case "Imus Police Station - Nueno Ave., IMUS":
                        txtViewPoliceLat.setText(String.valueOf(allPoliceLat[7]));
                        txtViewPoliceLong.setText(String.valueOf(allPoliceLong[7]));
                        txtViewPoliceAddress.setText(policeAddress[7]);
                        txtViewPoliceContact.setText(policeContact[7]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPlotAllPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoliceLocationReportFragmentDirections.ActionPoliceLocationReportFragmentToReportMapViewFragment action
                        = PoliceLocationReportFragmentDirections.actionPoliceLocationReportFragmentToReportMapViewFragment();

                action.setLatitude(txtViewPoliceLat.getText().toString());
                action.setLongitude(txtViewPoliceLong.getText().toString());
                action.setFragmentBackStack(4);
                action.setPlotAllHospital(false);
                action.setPlotAllPolice(true);
                navController.navigate(action);
            }
        });

        btnPlotSpecPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtViewPoliceLat.equals("") || txtViewPoliceLong.equals("")){
                    Toast.makeText(getActivity(), "Missing Coordinates", Toast.LENGTH_SHORT).show();
                }
                else {
                    PoliceLocationReportFragmentDirections.ActionPoliceLocationReportFragmentToReportMapViewFragment action
                            = PoliceLocationReportFragmentDirections.actionPoliceLocationReportFragmentToReportMapViewFragment();

                    action.setLatitude(txtViewPoliceLat.getText().toString());
                    action.setLongitude(txtViewPoliceLong.getText().toString());
                    action.setFragmentBackStack(4);
                    action.setPlotAllHospital(false);
                    action.setPlotAllPolice(false);
                    navController.navigate(action);
                }
            }
        });


    }
}