package com.example.locationhomepage.Interface

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
import com.example.locationhomepage.PricingActivity
import com.example.locationhomepage.R
import com.example.locationhomepage.databinding.ActivityBikesBinding.inflate
import com.example.locationhomepage.databinding.ActivityMapsBinding
import com.example.locationhomepage.databinding.PersistentBottomSheetBinding
import com.example.locationhomepage.databinding.PersistentBottomSheetBinding.*

class PersistentBottomSheetActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var  Longitude : TextView
    private lateinit var  Latitude : TextView
    private lateinit var binding: PersistentBottomSheetBinding
    private val locationPermissionCode = 2


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersistentBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Latitude = findViewById(R.id.set_location_txt)

        val button: Button = findViewById(R.id.getLocation)
        val pricecomp_btn: Button =findViewById(R.id.getprice_btn)
        val mapsbtn : Button = findViewById(R.id.maps)
        Longitude = findViewById(R.id.Long_txt)


        pricecomp_btn.setOnClickListener {
            val intent = Intent(applicationContext, PricingActivity::class.java)
            startActivity(intent)
        }




        }





    }
