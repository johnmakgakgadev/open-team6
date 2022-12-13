package com.example.locationhomepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import com.example.locationhomepage.Maps.MapsActivity

class Login1 : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        fun isLocationEnabled(): Boolean{
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)
        }

        val login_btn : Button = findViewById(R.id.loginbtn)

        login_btn.setOnClickListener {
            if (isLocationEnabled()){
                val intent = Intent(applicationContext, MapsActivity::class.java)
                startActivity(intent)
            }

            else{
                Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }



        }
    }


}