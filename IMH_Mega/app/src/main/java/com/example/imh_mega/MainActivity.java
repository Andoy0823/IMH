package com.example.imh_mega;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imh_mega.Fragments.Models.IncidentCheckerModel;
import com.example.imh_mega.Fragments.Models.incidentFinalModel;
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

    Location initialLocation, hospitalLocation, policeLocation, incidentLocation;

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

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

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

    public void startAlarm(String latitude, String longitude, String hospital, String police, String hospitalNumber, String policeNumber){

        //ALARM SOUND
        alarmMP = MediaPlayer.create(this, R.raw.thesisalarmfinal);
        alarmMP.start();

        //ALARM TEXT TO HOSPITAL
        SmsManager smsManager = SmsManager.getDefault();
        String txtMessage = "URGENT HOSPITAL! Incident Detected! Coordinates at: " + latitude + ", " + longitude ;
        smsManager.sendTextMessage(hospitalNumber, null, txtMessage, null, null);


        //ALARM TEXT TO POLICE
        SmsManager smsManagerPolice = SmsManager.getDefault();
        String txtPolice = "URGENT POLICE! Incident Detected! Coordinates at: " + latitude + ", " + longitude ;
        smsManagerPolice.sendTextMessage(policeNumber, null, txtPolice, null, null);

        Toast.makeText(MainActivity.this, "Message Sent to " + hospitalNumber + ", " + policeNumber, Toast.LENGTH_SHORT).show();

        //ALARM DIALOG
        Dialog DialogAlert = new Dialog(MainActivity.this);
        DialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogAlert.setContentView(R.layout.alarm_alert_dialog);

        //Hooks
        Button btnAlertWaze = DialogAlert.findViewById(R.id.btnAlertWazeID);
        Button btnAlertBack = DialogAlert.findViewById(R.id.btnAlertBackID);
        TextView txtViewAlertLat = DialogAlert.findViewById(R.id.txtViewAlertLatitudeID);
        TextView txtViewAlertLong = DialogAlert.findViewById(R.id.txtViewAlertLongitudeID);
        TextView txtViewAlertHospital = DialogAlert.findViewById(R.id.txtViewAlertHospitalID);
        TextView txtViewAlertPolice = DialogAlert.findViewById(R.id.txtViewAlertPoliceID);

        txtViewAlertLat.setText("LATITUDE: " + latitude);
        txtViewAlertLong.setText("LONGITUDE: " + longitude);
        txtViewAlertHospital.setText("HOSPITAL: " + hospital);
        txtViewAlertPolice.setText("POLICE: " + police);

        Call<incidentFinalModel> incidentFinalModelCall = apiInterface.insertToFinal(
                latitude,
                longitude,
                hospital,
                police
                );
        
        incidentFinalModelCall.enqueue(new Callback<incidentFinalModel>() {
            @Override
            public void onResponse(Call<incidentFinalModel> call, Response<incidentFinalModel> response) {

                if (response.body() != null){

                    incidentFinalModel incidentFinalMowdel = response.body();

                    if (incidentFinalMowdel.isSuccess()){
                        Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<incidentFinalModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnAlertBack.setEnabled(true);
        btnAlertBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAlert.cancel();
            }
        });
        btnAlertWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naviWaze = ("waze://?ll="+latitude+","+longitude+"&navigate=yes");
                Intent intentToWaze = new Intent(Intent.ACTION_VIEW, Uri.parse(naviWaze));
                startActivity(intentToWaze);
            }
        });

        DialogAlert.show();
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
                            //Toast.makeText(MainActivity.this, "Array Size: " + a + " > " + b, Toast.LENGTH_SHORT).show()

                            for (IncidentCheckerModel incidentCheckerModel : incidentCheckerMowdelList){

                                lawtitude = incidentCheckerModel.getLatitude();
                                lowgitude = incidentCheckerModel.getLongitude();

                            }

                            Toast.makeText(MainActivity.this, "Latitude: " + lawtitude + " Longitude: " + lowgitude, Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= 26 ){
                                vibrator.vibrate(VibrationEffect.createOneShot(7000, VibrationEffect.DEFAULT_AMPLITUDE));
                                String nearestHospital = getNearestHospital(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String nearestPolice = getNearestPolice(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String hospitalNumber = getNearestHospitalContact(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String policeNumber = getNearestPoliceContact(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                startAlarm(lawtitude, lowgitude, nearestHospital, nearestPolice, hospitalNumber, policeNumber);
                            }
                            else{
                                vibrator.vibrate(7000);
                                String nearestHospital = getNearestHospital(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String nearestPolice = getNearestPolice(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String hospitalNumber = getNearestHospitalContact(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                String policeNumber = getNearestPoliceContact(Double.parseDouble(lawtitude), Double.parseDouble(lowgitude));
                                startAlarm(lawtitude, lowgitude, nearestHospital, nearestPolice, hospitalNumber, policeNumber);
                            }

                            counter = 0;
                        }

                        if (a < b){
                            Toast.makeText(MainActivity.this, "Row deleted", Toast.LENGTH_SHORT).show();
                            counter = 0;
                        }

                        if (a == b){
                            //Toast.makeText(MainActivity.this, "Array Size: " + a + " = " + b, Toast.LENGTH_SHORT).show();
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

    public String getNearestHospital(Double incidentLatitude, Double incidentLongitude){
        incidentLocation = new Location("");
        hospitalLocation = new Location("");
        policeLocation = new Location("");

        incidentLocation.setLatitude(incidentLatitude);
        incidentLocation.setLongitude(incidentLongitude);

        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);

            policeLocation.setLatitude(allPoliceLat[i]);
            policeLocation.setLongitude(allPoliceLong[i]);

            tempDistanceHospital = incidentLocation.distanceTo(hospitalLocation);
            tempDistancePolice = incidentLocation.distanceTo(policeLocation);
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

        return shortestHospitalStr;

    }

    public String getNearestPolice(Double incidentLatitude, Double incidentLongitude){
        incidentLocation = new Location("");
        hospitalLocation = new Location("");
        policeLocation = new Location("");

        incidentLocation.setLatitude(incidentLatitude);
        incidentLocation.setLongitude(incidentLongitude);

        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);

            policeLocation.setLatitude(allPoliceLat[i]);
            policeLocation.setLongitude(allPoliceLong[i]);

            tempDistanceHospital = incidentLocation.distanceTo(hospitalLocation);
            tempDistancePolice = incidentLocation.distanceTo(policeLocation);
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

        return shortestPoliceStr;
    }

    public String getNearestHospitalContact(Double incidentLatitude, Double incidentLongitude){
        incidentLocation = new Location("");
        hospitalLocation = new Location("");
        policeLocation = new Location("");

        incidentLocation.setLatitude(incidentLatitude);
        incidentLocation.setLongitude(incidentLongitude);

        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);

            policeLocation.setLatitude(allPoliceLat[i]);
            policeLocation.setLongitude(allPoliceLong[i]);

            tempDistanceHospital = incidentLocation.distanceTo(hospitalLocation);
            tempDistancePolice = incidentLocation.distanceTo(policeLocation);
            if (i == 0){
                shortestHospitalFromIncident = tempDistanceHospital;
                shortestHospitalStr = hospitalName[i];
                shortestHospitalContact = hospitalContact[i];

                shortestPoliceFromIncident = tempDistancePolice;
                shortestPoliceStr = policeName[i];
                shortestPoliceContact = policeContact[i];
            }
            else {
                if (tempDistanceHospital < shortestHospitalFromIncident){

                    shortestHospitalFromIncident = tempDistanceHospital;
                    shortestHospitalStr = hospitalName[i];
                    shortestHospitalContact = hospitalContact[i];
                }

                if (tempDistancePolice < shortestPoliceFromIncident){

                    shortestPoliceFromIncident = tempDistancePolice;
                    shortestPoliceStr = policeName[i];
                    shortestPoliceContact = policeContact[i];
                }
            }
        }

        return shortestHospitalContact;
    }

    public String getNearestPoliceContact(Double incidentLatitude, Double incidentLongitude){
        incidentLocation = new Location("");
        hospitalLocation = new Location("");
        policeLocation = new Location("");

        incidentLocation.setLatitude(incidentLatitude);
        incidentLocation.setLongitude(incidentLongitude);

        for (int i=0; i<allHospitalLat.length; i++){

            hospitalLocation.setLatitude(allHospitalLat[i]);
            hospitalLocation.setLongitude(allHospitalLong[i]);

            policeLocation.setLatitude(allPoliceLat[i]);
            policeLocation.setLongitude(allPoliceLong[i]);

            tempDistanceHospital = incidentLocation.distanceTo(hospitalLocation);
            tempDistancePolice = incidentLocation.distanceTo(policeLocation);
            if (i == 0){
                shortestHospitalFromIncident = tempDistanceHospital;
                shortestHospitalStr = hospitalName[i];
                shortestHospitalContact = hospitalContact[i];

                shortestPoliceFromIncident = tempDistancePolice;
                shortestPoliceStr = policeName[i];
                shortestPoliceContact = policeContact[i];
            }
            else {
                if (tempDistanceHospital < shortestHospitalFromIncident){

                    shortestHospitalFromIncident = tempDistanceHospital;
                    shortestHospitalStr = hospitalName[i];
                    shortestHospitalContact = hospitalContact[i];
                }

                if (tempDistancePolice < shortestPoliceFromIncident){

                    shortestPoliceFromIncident = tempDistancePolice;
                    shortestPoliceStr = policeName[i];
                    shortestPoliceContact = policeContact[i];
                }
            }
        }

        return shortestPoliceContact;
    }
}