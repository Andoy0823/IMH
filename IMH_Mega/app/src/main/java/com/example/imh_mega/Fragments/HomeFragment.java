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
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Models.NewUpdtLocModel;
import com.example.imh_mega.Fragments.Models.UpdtLocModel;
import com.example.imh_mega.LoadingDialog;
import com.example.imh_mega.MainActivity;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;

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

    @BindView(R.id.txtViewLongitudeID) TextView textViewLong;
    @BindView(R.id.txtViewLatitudeID) TextView textViewLat;
    @BindView(R.id.txtViewRiderIDID) TextView textViewRider;

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
                    action.setLatitude(Latitude);
                    action.setLongitude(Longitude);
                    navController.navigate(action);
                }
            }
        });

        btnUpdateLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.startLoadingDialog();
                Call<List<UpdtLocModel>> listCall = apiInterface.getLowcation();

                listCall.enqueue(new Callback<List<UpdtLocModel>>() {
                    @Override
                    public void onResponse(Call<List<UpdtLocModel>> call, Response<List<UpdtLocModel>> response) {

                        if (!response.isSuccessful()){

                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();

                        }

                        List<UpdtLocModel> updtLocModelList = response.body();


                        for (UpdtLocModel updtLocMowdel : updtLocModelList){

                            textViewRider.setText(updtLocMowdel.getRiderID());
                            textViewLat.setText(updtLocMowdel.getRtcLatitude());
                            textViewLong.setText(updtLocMowdel.getRtcLongitude());

                            Latitude = updtLocMowdel.getRtcLatitude();
                            Longitude = updtLocMowdel.getRtcLongitude();


                        }
                        loadingDialog.dismissDialog();
                    }

                    @Override
                    public void onFailure(Call<List<UpdtLocModel>> call, Throwable t) {

                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }
                });



            }
        });
    }
}