package com.example.locationhomepage

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.locationhomepage.Maps.MapsActivity


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
        val mapsbtn : Button = findViewById(R.id.maps)
        Longitude = findViewById(R.id.Long_txt)
        getLocation()


        button.setOnClickListener {
            getLocation()
        }

        mapsbtn.setOnClickListener {

            if (IsTextfieldEmpty()){

                Toast.makeText(applicationContext, "Check if Location is enabled",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("Longitude", Longitude.text)
                intent.putExtra("Latitude", Latitude.text)

                startActivity(intent)
            }

        }


    }

    private fun IsTextfieldEmpty() : Boolean{
      if(     Longitude.text == null && Latitude.text==null){

      }


             return false
    }


    override fun onLocationChanged(location: Location) {
      Latitude = findViewById(R.id.Lat_txt)
        Latitude.text = location.latitude.toString()


        Longitude.text = location.longitude.toString()

    }

    private fun isLocationEnabled(): Boolean{
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLocation(){
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
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),locationPermissionCode)

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