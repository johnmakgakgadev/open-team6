package com.example.locationhomepage.Maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.example.locationhomepage.R
import com.example.locationhomepage.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivityMapsBinding
    var getLongg:String? =null
    var getLatt:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLongg = intent.getStringExtra("Longitude")
       getLatt = intent.getStringExtra("Latitude")
       Toast.makeText(applicationContext,"Your lat is "+getLatt.toString(), Toast.LENGTH_SHORT).show()

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
    override fun onMapReady(googleMap: GoogleMap) {

        val doublelat : Double? = getLatt?.toDouble()
        val doublelong : Double? = getLongg?.toDouble()
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val CurrentLocation = LatLng(doublelat!!, doublelong!!)

        mMap.addMarker(MarkerOptions().position(CurrentLocation).title("Marker in Current Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation))
       mMap.setMinZoomPreference(18.0F)
    }
}