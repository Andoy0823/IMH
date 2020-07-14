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

import com.example.imh_mega.Fragments.Models.autoCompleteModel;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncidentReportFragment extends Fragment{

    NavController navController;
    Spinner spinnerReportType, spinnerChooseDate;
    Button btnPlotIncident;
    TextView txtViewIncidentLat, txtViewIncidentLong, txtViewHospitalLoc, txtViewPoliceLoc;

    APIInterface apiInterface;

    ArrayList<String> dateList;

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
        spinnerChooseDate = view.findViewById(R.id.spinnerDateIncidentChooserID);

        dateList = new ArrayList<>();

        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dattebayo = simpleDateFormat.format(new Date());

        Toast.makeText(getActivity(), dattebayo, Toast.LENGTH_SHORT).show();

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

        getDates();

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

    private void getDates(){

        Call<List<autoCompleteModel>> listCall = apiInterface.getRiderNames();

        listCall.enqueue(new Callback<List<autoCompleteModel>>() {
            @Override
            public void onResponse(Call<List<autoCompleteModel>> call, Response<List<autoCompleteModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Hindi Tama", Toast.LENGTH_SHORT).show();
                }

                List<autoCompleteModel> autoCompleteMowdelList = response.body();

                for (autoCompleteModel autoCompleteModel : autoCompleteMowdelList){

                    String datebayow = "";

                    datebayow += autoCompleteModel.getRiderFullName();

                    dateList.add(datebayow);
                }
                ArrayAdapter<String> newAdepter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dateList);

                spinnerChooseDate.setAdapter(newAdepter);

            }

            @Override
            public void onFailure(Call<List<autoCompleteModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}