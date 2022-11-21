package com.example.locationhomepage.Maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.example.locationhomepage.Login1


import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.example.locationhomepage.R
import com.example.locationhomepage.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivityMapsBinding
    var getLongg:Double? =null
    var getLatt:Double? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        var mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)



       // map:mapID = "192fd3274887097d"


            getLocation()

       var  mapFragments = MapFragment.newInstance(
            GoogleMapOptions()
                .mapId(resources.getString(R.string.map_id))
        )


      // Toast.makeText(applicationContext,"Your lat is "+getLatt.toString(), Toast.LENGTH_SHORT).show()

    }






    private fun isLocationEnabled(): Boolean{
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


     private  fun getLocation(){
        try {
            if (isLocationEnabled()){

                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),locationPermissionCode)

                    return
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f,this)
            }

            else{
                Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        }
        catch (_:Exception){}

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        //Latitude = findViewById(R.id.Lat_txt)

            getLatt = location.latitude



            getLongg = location.longitude



    }


    override fun onMapReady(googleMap: GoogleMap) {


        Handler(Looper.getMainLooper()).postDelayed({
            try {


                mMap = googleMap
                // Add a marker in Sydney and move the camera

                val CurrentLocation = LatLng(getLatt!!, getLongg!!)

                mMap.addMarker(MarkerOptions().position(CurrentLocation).title("Marker in Current Location"))
               //  mMap.addMarker(MarkerOptions().draggable(true))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation))

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 16.0F))
            } catch (e:Exception){

                Toast.makeText(applicationContext,"Check if Location is enabled",Toast.LENGTH_SHORT).show()
            }


        }, 5000)


    }
}