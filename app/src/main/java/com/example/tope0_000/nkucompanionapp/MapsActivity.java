package com.example.tope0_000.nkucompanionapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, AdapterView.OnItemClickListener {

    private GoogleMap mMap;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private Marker myMarker;
    private LocationManager locationManager;
    public boolean darkTheme = false;

    //define variables for the widgets
    private Button mapTypeButton;
    private ListView myListView;

    // define the SharedPreferences object and editor
    private SharedPreferences savedValues;
    private SharedPreferences.Editor editor;

    //define instance variables that should be saved
    private boolean theme = false;

    //LatLng for NKU Buildings
    LatLng bBTArena = new LatLng(39.032034, -84.459153);
    LatLng baptistStudentCenter = new LatLng(39.033246, -84.464406);
    LatLng businessCenter = new LatLng(39.031008, -84.461603);
    LatLng campbellHall = new LatLng(39.040920, -84.466870);
    LatLng healthCenter = new LatLng(39.029204, -84.466098);
    LatLng ceramicsSculpture = new LatLng(39.037325, -84.465011);
    LatLng scienceCenter = new LatLng(39.032345, -84.466233);
    LatLng fineArtsCenter = new LatLng(39.031231, -84.463723);
    LatLng foundersHall = new LatLng(39.031727, -84.465101);
    LatLng griffinHall = new LatLng(39.030917, -84.466684);
    LatLng studentUnion = new LatLng(39.030163, -84.465417);
    LatLng landrumCenter = new LatLng(39.032511, -84.464584);
    LatLng nunnHall = new LatLng(39.030893, -84.464822);
    LatLng administrativeCenter = new LatLng(39.029669, -84.463475);
    LatLng mathPsycologyCenter = new LatLng(39.030032, -84.462601);
    LatLng regentsHall = new LatLng(39.029369, -84.464968);
    LatLng soccerStadium = new LatLng(39.032229, -84.457019);
    LatLng universityCenter = new LatLng(39.030135, -84.463995);
    LatLng steelyLibrary = new LatLng(39.031878, -84.463886);
    LatLng welcomeCenter = new LatLng(39.032492, -84.460987);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Get theme saved value
        darkTheme = savedValues.getBoolean("theme", false);

        //Set Theme
        if(darkTheme)
            this.setTheme(R.style.AppTheme_dark);

        setContentView(R.layout.activity_maps);

        //Get references to the widgets
        mapTypeButton = (Button) findViewById(R.id.mapTypeButton);
        myListView = (ListView) findViewById(R.id.listView);


        //Set the listeners
        mapTypeButton.setOnClickListener(this);
        myListView.setOnItemClickListener(this);

        if(darkTheme) {
            mapTypeButton.setBackgroundResource(R.drawable.button_dark);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }

       /* //check if the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the longitude
                    double longitude = location.getLongitude();
                    //instantiate the class, LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //instantiate the class, Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + " ";
                        str += addressList.get(0).getCountryName();
                        if (myMarker != null)
                            myMarker.remove();
                        myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

        //check if the gps provider is enabled
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the longitude
                    double longitude = location.getLongitude();
                    //instantiate the class, LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //instantiate the class, Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + " ";
                        str += addressList.get(0).getCountryName();
                        if (myMarker != null)
                            myMarker.remove();
                        myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        //Add a marker for each NKU Building
        mMap.addMarker(new MarkerOptions().position(bBTArena).title("BB&T Arena"));
        mMap.addMarker(new MarkerOptions().position(baptistStudentCenter).title("Baptist Student Center"));
        mMap.addMarker(new MarkerOptions().position(businessCenter).title("Business Academic Center"));
        mMap.addMarker(new MarkerOptions().position(campbellHall).title("Campbell Hall"));
        mMap.addMarker(new MarkerOptions().position(healthCenter).title("A.D. Albright Health Center"));
        mMap.addMarker(new MarkerOptions().position(ceramicsSculpture).title("Cermaics & Sculpture"));
        mMap.addMarker(new MarkerOptions().position(scienceCenter).title("Dorothy Westerman Herrmann Natural Science Center"));
        mMap.addMarker(new MarkerOptions().position(fineArtsCenter).title("Fine Arts Center"));
        mMap.addMarker(new MarkerOptions().position(foundersHall).title("Founders Hall"));
        mMap.addMarker(new MarkerOptions().position(griffinHall).title("Griffin Hall"));
        mMap.addMarker(new MarkerOptions().position(studentUnion).title("James C. and Rachel M. Votruba Student Union"));
        mMap.addMarker(new MarkerOptions().position(landrumCenter).title("Landrum Academic Center"));
        mMap.addMarker(new MarkerOptions().position(nunnHall).title("Louie B. Nunn Hall"));
        mMap.addMarker(new MarkerOptions().position(administrativeCenter).title("Lucas Administrative Center"));
        mMap.addMarker(new MarkerOptions().position(mathPsycologyCenter).title("Math-Education-Psychology Center"));
        mMap.addMarker(new MarkerOptions().position(regentsHall).title("Regents Hall"));
        mMap.addMarker(new MarkerOptions().position(soccerStadium).title("Soccer Stadium"));
        mMap.addMarker(new MarkerOptions().position(universityCenter).title("University Center"));
        mMap.addMarker(new MarkerOptions().position(steelyLibrary).title("W. Frank Steely Library"));
        mMap.addMarker(new MarkerOptions().position(welcomeCenter).title("Welcome Center"));
        */


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng nku = new LatLng(39.031635, -84.464239);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nku, 15.0f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.recreate();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClick(View v) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedFromList = myListView.getItemAtPosition(position).toString();
        Toast.makeText(this, selectedFromList, Toast.LENGTH_SHORT).show();
        if (myMarker != null)
            myMarker.remove();
        switch (selectedFromList) {
            case "Administrative Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(administrativeCenter).title("Lucas Administrative Center"));
                break;
            case "Baptist Student Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(baptistStudentCenter).title("Baptist Student Center"));
                break;
            case "BB&T Arena":
                myMarker = mMap.addMarker(new MarkerOptions().position(bBTArena).title("BB&T Arena"));
                break;
            case "Business Academic Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(businessCenter).title("Business Academic Center"));
                break;
            case "Campbell Hall":
                myMarker = mMap.addMarker(new MarkerOptions().position(campbellHall).title("Campbell Hall"));
                break;
            case "Ceramics & Sculpture":
                myMarker = mMap.addMarker(new MarkerOptions().position(ceramicsSculpture).title("Cermaics & Sculpture"));
                break;
            case "Fine Arts Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(fineArtsCenter).title("Fine Arts Center"));
                break;
            case "Founders Hall":
                myMarker = mMap.addMarker(new MarkerOptions().position(foundersHall).title("Founders Hall"));
                break;
            case "Griffin Hall":
                myMarker = mMap.addMarker(new MarkerOptions().position(griffinHall).title("Griffin Hall"));
                break;
            case "Health Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(healthCenter).title("A.D. Albright Health Center"));
                break;
            case "Landrum":
                myMarker = mMap.addMarker(new MarkerOptions().position(landrumCenter).title("Landrum Academic Center"));
                break;
            case "Louie B. Nunn Hall":
                myMarker = mMap.addMarker(new MarkerOptions().position(nunnHall).title("Louie B. Nunn Hall"));
                break;
            case "MEP Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(mathPsycologyCenter).title("Math-Education-Psychology Center"));
                break;
            case "Regents Hall":
                myMarker = mMap.addMarker(new MarkerOptions().position(regentsHall).title("Regents Hall"));
                break;
            case "Science Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(scienceCenter).title("Dorothy Westerman Herrmann Natural Science Center"));
                break;
            case "Soccer Stadium":
                myMarker = mMap.addMarker(new MarkerOptions().position(soccerStadium).title("Soccer Stadium"));
                break;
            case "Steely Library":
                myMarker = mMap.addMarker(new MarkerOptions().position(steelyLibrary).title("W. Frank Steely Library"));
                break;
            case "Student Union":
                myMarker = mMap.addMarker(new MarkerOptions().position(studentUnion).title("James C. and Rachel M. Votruba Student Union"));
                break;
            case "University Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(universityCenter).title("University Center"));
                break;
            case "Welcome Center":
                myMarker = mMap.addMarker(new MarkerOptions().position(welcomeCenter).title("Welcome Center"));
                break;
        }
    }

    @Override
    public void onPause() {
        editor = savedValues.edit();
        editor.putBoolean("theme", darkTheme);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        darkTheme = savedValues.getBoolean("theme", false);
    }
}
