package com.example.locationhomepage

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() , LocationListener{
    private lateinit var locationManager: LocationManager
    private lateinit var  Longitude : TextView
    private lateinit var  Latitude : TextView
    private val locationPermissionCode = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Latitude = findViewById(R.id.Lat_txt)
        val button: Button = findViewById(R.id.getLocation)
        Longitude = findViewById(R.id.Long_txt)

        button.setOnClickListener {
            getLocation()
        }
    }

    override fun onLocationChanged(location: Location) {
      Latitude = findViewById(R.id.Lat_txt)
        Latitude.text = "Your latitude is:"+ location.latitude


        Longitude.text = "Your latitude is:"+ location.longitude

    }

    private fun isLocationEnabled(): Boolean{
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLocation(){
        if (isLocationEnabled()){

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION)
                        !=PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),locationPermissionCode)
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f,this)
        }

        else{
            Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

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


}