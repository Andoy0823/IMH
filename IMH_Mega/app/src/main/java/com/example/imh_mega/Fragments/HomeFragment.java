package com.example.imh_mega.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Models.autoCompleteModel;
import com.example.imh_mega.Fragments.Models.searchRiderModel;
import com.example.imh_mega.LoadingDialog;
import com.example.imh_mega.MainActivity;
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


public class HomeFragment extends Fragment {

    Button btnPlotToMaps, btnUpdateLoc;
    NavController navController;
    String Latitude, Longitude;

    LoadingDialog loadingDialog;

    ArrayList<String> riderNameList;

    @BindView(R.id.txtViewLongitudeID) TextView textViewLong;
    @BindView(R.id.txtViewLatitudeID) TextView textViewLat;
    @BindView(R.id.txtViewRiderIDID) TextView textViewRider;

    @BindView(R.id.autoTxtRiderNameID) AutoCompleteTextView autoTextValue;
    @BindView(R.id.imageButton) ImageButton searchRiderButton;

    APIInterface apiInterface;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);
        btnPlotToMaps = view.findViewById(R.id.btnPlotToMapsID);
        btnUpdateLoc = view.findViewById(R.id.btnUpdateLocID);

        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);


        riderNameList = new ArrayList<>();

        //AutoCompleteTextView

        Call<List<autoCompleteModel>> atcNameListCall = apiInterface.getRiderNames();

        atcNameListCall.enqueue(new Callback<List<autoCompleteModel>>() {
            @Override
            public void onResponse(Call<List<autoCompleteModel>> call, Response<List<autoCompleteModel>> response) {

                if(!response.isSuccessful()){

                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                }

                List<autoCompleteModel> autoCompleteModelList = response.body();

                for (autoCompleteModel completeModel : autoCompleteModelList){

                    String riderName = "";

                    riderName += completeModel.getRiderFullName();

                    riderNameList.add(riderName);

                }

                ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, riderNameList);

                autoTextValue.setAdapter(newAdapter);

            }

            @Override
            public void onFailure(Call<List<autoCompleteModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //Buttons

        loadingDialog = new LoadingDialog(getActivity());

        btnPlotToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_homeFragment_to_realTimeMapViewFragment);
                if (textViewLat.length() == 0 && textViewLong.length() == 0){
                    Toast.makeText(getActivity(), "Please Update Location", Toast.LENGTH_SHORT).show();
                }
                else {

                    HomeFragmentDirections.ActionHomeFragmentToRealTimeMapViewFragment action = HomeFragmentDirections.actionHomeFragmentToRealTimeMapViewFragment();
                    //action.setLatitude(Latitude);
                    //action.setLongitude(Longitude);
                    action.setLatitude(textViewLat.getText().toString().trim());
                    action.setLongitude(textViewLong.getText().toString().trim());
                    navController.navigate(action);

                }
            }
        });

        btnUpdateLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Call<List<searchRiderModel>> updateLocationCall = apiInterface.getInformation();

                loadingDialog.startLoadingDialog();

                updateLocationCall.enqueue(new Callback<List<searchRiderModel>>() {
                    @Override
                    public void onResponse(Call<List<searchRiderModel>> call, Response<List<searchRiderModel>> response) {

                        if(!response.isSuccessful()){

                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                        }

                        String errorCheckValue = autoTextValue.getText().toString().trim();

                        if (errorCheckValue.equals("")){

                            Toast.makeText(getActivity(), "No value detected", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            List<searchRiderModel> updateModelList = response.body();

                            for (searchRiderModel updateRiderMowdel : updateModelList){

                                if (updateRiderMowdel.getRiderFullName().equals(errorCheckValue)){

                                    textViewRider.setText(updateRiderMowdel.getRiderID());
                                    textViewLat.setText(updateRiderMowdel.getRtcLatitude());
                                    textViewLong.setText(updateRiderMowdel.getRtcLongitude());

                                }

                                Latitude = updateRiderMowdel.getRtcLatitude();
                                Longitude = updateRiderMowdel.getRtcLongitude();

                            }

                        }
                        loadingDialog.dismissDialog();
                    }

                    @Override
                    public void onFailure(Call<List<searchRiderModel>> call, Throwable t) {


                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }
                });

            }
        });

        searchRiderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(getActivity());
                Call<List<searchRiderModel>> riderInformationCall = apiInterface.getInformation();

                riderInformationCall.enqueue(new Callback<List<searchRiderModel>>() {
                    @Override
                    public void onResponse(Call<List<searchRiderModel>> call, Response<List<searchRiderModel>> response) {

                        if(!response.isSuccessful()){

                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                        }

                        List<searchRiderModel> riderModelList = response.body();

                        String textViewValue = autoTextValue.getText().toString().trim();

                        if (textViewValue.equals("")){

                            Toast.makeText(getActivity(), "No value detected", Toast.LENGTH_SHORT).show();

                        }

                        else{

                            for (searchRiderModel searchRiderMowdel : riderModelList){

                                if (searchRiderMowdel.getRiderFullName().equals(textViewValue)){

                                    textViewRider.setText(searchRiderMowdel.getRiderID());
                                    textViewLat.setText(searchRiderMowdel.getRtcLatitude());
                                    textViewLong.setText(searchRiderMowdel.getRtcLongitude());

                                }

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<List<searchRiderModel>> call, Throwable t) {

                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

    public void hideSoftKeyboard(Activity activity){
        if (activity.getCurrentFocus() == null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}