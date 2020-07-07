package com.example.imh_mega.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class RiderDetailsFragment extends Fragment {


    CustomAdapter customAdapter;

    String[] riderName = {"Rider Jovenir", "Rider Favorito", "Rider Landicho"};
    String[] riderID = {"701500003", "701500004", "701500005"};
    String[] riderPhone = {"09945834651", "09978523415", "09976541523"};
    String[] riderAddress = {"Pasay City", "Pasig City", "Quezon City"};

    ListView listViewRider;
    Button btnAddRider;
    StringBuilder riderDetailBuilder;

    int listPosition;

    public RiderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rider_details, container, false);

        listViewRider = v.findViewById(R.id.listViewRiderDetailsID);
        customAdapter = new CustomAdapter();
        listViewRider.setAdapter(customAdapter);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Hooks
        btnAddRider = view.findViewById(R.id.btnAddRiderID);
        riderDetailBuilder = new StringBuilder();

        listViewRider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long longID) {

                //Toast.makeText(getActivity(), "asdasdasd " + position, Toast.LENGTH_SHORT).show();
                listPosition = position;

                riderDetailBuilder.append("Rider Name: " + riderName[listPosition] + "\n");
                riderDetailBuilder.append("Rider I.D.: " + riderID[listPosition] + "\n");
                riderDetailBuilder.append("Contact: " + riderPhone[listPosition] + "\n");
                riderDetailBuilder.append("Address: " + riderAddress[listPosition] + "\n");
                riderDetailAlert(riderDetailBuilder.toString());
                riderDetailBuilder = new StringBuilder();
            }
        });

        btnAddRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "*FEATURE IN PROGRESS*", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void riderDetailAlert(String riderFullDetails){
        final MaterialAlertDialogBuilder builderHistory = new MaterialAlertDialogBuilder(getActivity());
        builderHistory.setCancelable(true);
        builderHistory.setTitle("RIDER INFORMATION");
        builderHistory.setMessage(riderFullDetails);
        builderHistory.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ALERT = builderHistory.create();
        ALERT.show();
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return riderName.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view2, ViewGroup viewGroup) {
            view2 = getLayoutInflater().inflate(R.layout.rider_list_layout, null);

            TextView txtViewRiderListName, txtViewRiderListID;

            txtViewRiderListName = view2.findViewById(R.id.txtViewRiderListNameID);
            txtViewRiderListID = view2.findViewById(R.id.txtViewRiderListIDID);

            txtViewRiderListName.setText("Rider Name: " + riderName[i]);
            txtViewRiderListID.setText("Rider I.D.: " + riderID[i]);
            return view2;
        }
    }
}