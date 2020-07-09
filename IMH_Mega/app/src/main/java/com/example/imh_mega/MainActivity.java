package com.example.imh_mega;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    double allHospitalLat[] = {14.426269, 14.399959, 14.376212, 14.373747, 14.395625, 14.405804, 14.410574, 14.429737};
    double allHospitalLong[] = {120.946531, 120.939514, 120.938735, 120.979819, 120.987978, 120.976867, 120.976085, 121.003543};
    String hospitalName[] = {"Medical Center Imus","City of Imus Doctors Hospital", "Medicard Cavite Clinic", "Metrosouth Medical Center", "San Agustin Medical Clinic", "Southeast Asian Medical Center",
            "Molino Doctors Hospital", "Las Pinas City Medical Center"};

    double shortestHospitalFromIncident, shortestHospitalFromIncidentFinal, tempDistance;
    String shortestHospitalStr;

    Location initialLocation, hospitalLocation, policeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        initialLocation = new Location("");
        hospitalLocation = new Location("");

        //Initial Testing of Distance checker. Init Value = Bahay ni Red.
        /*
        initialLocation.setLatitude(14.402138);
        initialLocation.setLongitude(120.989708);

        hospitalLocation.setLatitude(14.429737);
        hospitalLocation.setLongitude(121.003543);

        double distance = initialLocation.distanceTo(hospitalLocation);
        double distanceFinal = distance / 1000;

        Toast.makeText(this, "Distance = " + distanceFinal + " Km", Toast.LENGTH_SHORT).show();

         */

        //Static Value of Incident
        initialLocation.setLatitude(14.402138);
        initialLocation.setLongitude(120.989708);

        //Logic for Shortest Distance of Hospital.
        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);
            tempDistance = initialLocation.distanceTo(hospitalLocation);
            if (i == 0){
                shortestHospitalFromIncident = tempDistance;
                shortestHospitalStr = hospitalName[i];
            }
            else {
                if (tempDistance < shortestHospitalFromIncident){

                    shortestHospitalFromIncident = tempDistance;
                    shortestHospitalStr = hospitalName[i];

                }
            }
        }

        shortestHospitalFromIncidentFinal = shortestHospitalFromIncident / 1000;

        Toast.makeText(this, "Shortest Distance is " + shortestHospitalFromIncidentFinal + " km. At " + shortestHospitalStr, Toast.LENGTH_SHORT).show();

    }
}