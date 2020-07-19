package com.example.imh_mega;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Models.IncidentCheckerModel;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    APIInterface apiInterface;

    int a, b, counter = 0;

    double allHospitalLat[] = {14.426269, 14.399959, 14.376212, 14.373747, 14.395625, 14.405804, 14.410574, 14.429737};
    double allHospitalLong[] = {120.946531, 120.939514, 120.938735, 120.979819, 120.987978, 120.976867, 120.976085, 121.003543};
    String hospitalName[] = {"Medical Center Imus","City of Imus Doctors Hospital", "Medicard Cavite Clinic", "Metrosouth Medical Center", "San Agustin Medical Clinic", "Southeast Asian Medical Center",
            "Molino Doctors Hospital", "Las Pinas City Medical Center"};
    String hospitalContact[] = {"09209536984", "09209536984", "09752955837", "09752955837", "09752955837", "09177280117", "09177280117", "09177280117"};

    double allPoliceLat[] = {14.388826, 14.382097, 14.373063, 14.376789, 14.350296, 14.325721, 14.422035, 14.42297};
    double allPoliceLong[] = {120.984376, 120.988721, 120.980103, 120.9388, 120.937898, 120.94076, 120.94639, 120.941584};
    String policeName[] = {"Bacoor City Police - Camella Springeville", "Bacoor City Community Police - Daang Hari", "Molino IV PNP Sub-Station - Molino Rd.", "Police Community Precinct 3 - EAH, IMUS"
    , "Police Community Precinct 3 - EAH, IMUS", "Police Station - Waltermart, DASMARINAS", "Police Station - Buhay Na Tubig, IMUS", "Imus Police Station - Nueno Ave., IMUS"};
    String policeContact[] = {"09209536984", "09209536984", "09752955837", "09752955837", "09752955837", "09177280117", "09177280117", "09177280117"};


    double shortestHospitalFromIncident, shortestHospitalFromIncidentFinal, shortestPoliceFromIncident, shortestPoliceFromIncidentFinal, tempDistanceHospital, tempDistancePolice;
    String shortestHospitalStr, shortestPoliceStr, shortestHospitalContact, shortestPoliceContact;

    Location initialLocation, hospitalLocation, policeLocation;

    String lawtitude, lowgitude;

    Handler handler;
    Runnable runnable;
    Vibrator vibrator;
    int startScanner=10;
    Boolean scanning = true;

    MediaPlayer alarmMP;

    //Array Check Loop
    private Handler arrayCheckHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        initialLocation = new Location("");
        hospitalLocation = new Location("");
        policeLocation = new Location("");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //Scanning every 5 Seconds
                if (startScanner%5 == 0){
                    //Static Value of Incident
                    initialLocation.setLatitude(14.402138);
                    initialLocation.setLongitude(120.989708);

                    //Logic for Shortest Distance of Hospital and Police.
                    for (int i=0; i<allHospitalLat.length; i++){

                        hospitalLocation.setLatitude(allHospitalLat[i]);
                        hospitalLocation.setLongitude(allHospitalLong[i]);

                        policeLocation.setLatitude(allPoliceLat[i]);
                        policeLocation.setLongitude(allPoliceLong[i]);

                        tempDistanceHospital = initialLocation.distanceTo(hospitalLocation);
                        tempDistancePolice = initialLocation.distanceTo(policeLocation);
                        if (i == 0){
                            shortestHospitalFromIncident = tempDistanceHospital;
                            shortestHospitalStr = hospitalName[i];

                            shortestPoliceFromIncident = tempDistancePolice;
                            shortestPoliceStr = policeName[i];
                        }
                        else {
                            if (tempDistanceHospital < shortestHospitalFromIncident){

                                shortestHospitalFromIncident = tempDistanceHospital;
                                shortestHospitalStr = hospitalName[i];
                            }

                            if (tempDistancePolice < shortestPoliceFromIncident){

                                shortestPoliceFromIncident = tempDistancePolice;
                                shortestPoliceStr = policeName[i];
                            }
                        }
                    }

                    //Use this as final value
                    shortestHospitalFromIncidentFinal = shortestHospitalFromIncident / 1000;
                    shortestPoliceFromIncidentFinal = shortestPoliceFromIncident / 1000;

                    /*
                    Toast.makeText(MainActivity.this, "Shortest Distance is " + shortestHospitalFromIncidentFinal + " km. At " + shortestHospitalStr + ". And Police is "
                            + shortestPoliceFromIncidentFinal + "km. At " + shortestPoliceStr, Toast.LENGTH_SHORT).show();

                     */
                }
                startScanner--;
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);

        arrayCheckRunnable.run();


        /*
        //Static Value of Incident
        initialLocation.setLatitude(14.402138);
        initialLocation.setLongitude(120.989708);

        //Logic for Shortest Distance of Hospital and Police.
        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);

            policeLocation.setLatitude(allPoliceLat[i]);
            policeLocation.setLongitude(allPoliceLong[i]);

            tempDistanceHospital = initialLocation.distanceTo(hospitalLocation);
            tempDistancePolice = initialLocation.distanceTo(policeLocation);
            if (i == 0){
                shortestHospitalFromIncident = tempDistanceHospital;
                shortestHospitalStr = hospitalName[i];

                shortestPoliceFromIncident = tempDistancePolice;
                shortestPoliceStr = policeName[i];
            }
            else {
                if (tempDistanceHospital < shortestHospitalFromIncident){

                    shortestHospitalFromIncident = tempDistanceHospital;
                    shortestHospitalStr = hospitalName[i];
                }

                if (tempDistancePolice < shortestPoliceFromIncident){

                    shortestPoliceFromIncident = tempDistancePolice;
                    shortestPoliceStr = policeName[i];
                }
            }
        }

        //Use this as final value
        shortestHospitalFromIncidentFinal = shortestHospitalFromIncident / 1000;
        shortestPoliceFromIncidentFinal = shortestPoliceFromIncident / 1000;

        Toast.makeText(this, "Shortest Distance is " + shortestHospitalFromIncidentFinal + " km. At " + shortestHospitalStr + ". And Police is "
                + shortestPoliceFromIncidentFinal + "km. At " + shortestPoliceStr, Toast.LENGTH_LONG).show();
        */
    }

    public void startAlarm(){
        alarmMP = MediaPlayer.create(this, R.raw.thesisalarmfinal);

        alarmMP.start();
    }

    private Runnable arrayCheckRunnable = new Runnable() {
        @Override
        public void run() {
            Call<List<IncidentCheckerModel>> incidentModelCall = apiInterface.checkIncident();

            incidentModelCall.enqueue(new Callback<List<IncidentCheckerModel>>() {
                @Override
                public void onResponse(Call<List<IncidentCheckerModel>> call, Response<List<IncidentCheckerModel>> response) {

                    if (!response.isSuccessful()){
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }

                    List<IncidentCheckerModel> incidentCheckerMowdelList = response.body();

                    if (counter == 0){
                        a = incidentCheckerMowdelList.size();
                        b = incidentCheckerMowdelList.size();
                        counter++;
                    }
                    if (counter == 1){
                        a = incidentCheckerMowdelList.size();
                        if (a > b){
                            Toast.makeText(MainActivity.this, "Array Size: " + a + " > " + b, Toast.LENGTH_SHORT).show();



                            for (IncidentCheckerModel incidentCheckerModel : incidentCheckerMowdelList){

                                lawtitude = incidentCheckerModel.getLatitude();
                                lowgitude = incidentCheckerModel.getLongitude();

                            }

                            Toast.makeText(MainActivity.this, "Latitude: " + lawtitude + " Longitude: " + lowgitude, Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= 26 ){
                                vibrator.vibrate(VibrationEffect.createOneShot(7000, VibrationEffect.DEFAULT_AMPLITUDE));
                                startAlarm();
                            }
                            else{
                                vibrator.vibrate(7000);
                                startAlarm();
                            }

                            counter = 0;
                        }

                        if (a < b){
                            Toast.makeText(MainActivity.this, "Row deleted", Toast.LENGTH_SHORT).show();
                            counter = 0;
                        }

                        if (a == b){
                            Toast.makeText(MainActivity.this, "Array Size: " + a + " = " + b, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<IncidentCheckerModel>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            arrayCheckHandler.postDelayed(this, 3000);
        }
    };

}