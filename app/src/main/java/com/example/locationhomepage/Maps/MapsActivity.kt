package com.example.locationhomepage.Maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.*
import android.location.LocationListener
import android.location.LocationRequest
import com.google.android.gms.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat

import com.example.locationhomepage.Login1
import com.example.locationhomepage.PricingActivity


import com.example.locationhomepage.R
import com.example.locationhomepage.databinding.ActivityMapsBinding
import com.example.locationhomepage.databinding.LayoutPersistentBottomSheetBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.SphericalUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,LocationListener ,PermissionListener{
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var Calcbtn : Button
    private lateinit var SetDest : EditText
    private lateinit var SetLoc : EditText


    companion object {
        const val REQUEST_CHECK_SETTINGS = 43
    }
    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivityMapsBinding
    var getLongg:Double? =null
    var getLatt:Double? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


     binding = ActivityMapsBinding.inflate(layoutInflater)

     setContentView(binding.root)
        Calcbtn = findViewById(R.id.clickforprice)
        SetDest = findViewById(R.id.set_dest_txt)
        SetLoc = findViewById(R.id.set_location_txt)
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet1))


        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset:Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(this@MapsActivity, "COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(this@MapsActivity, "EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(this@MapsActivity, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                  else -> Toast.makeText(this@MapsActivity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }
        })




Calcbtn.setOnClickListener{
    val intent = Intent(applicationContext, PricingActivity::class.java)
    Toast.makeText(applicationContext,"This button works",Toast.LENGTH_SHORT).show()
    startActivity(intent)
}


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
              fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        //   getLocation()


    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        setMapLongClick(map)
        mMap = map
        if (isPermissionGiven()){

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }



            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = false

            getCurrentLocation()

        } else {
            givePermission()
        }
    }

    private fun isPermissionGiven(): Boolean{
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun givePermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(this)
            .check()
    }



    private fun isLocationEnabled(): Boolean{
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getCurrentLocation() {

        val locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
        locationRequest.interval = (10 * 1000).toLong()
        locationRequest.fastestInterval = 5000

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val result = LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest)
        result.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val mLastLocation = task.result

            }
            try {

                val response = task.getResult(ApiException::class.java)
                if (response!!.locationSettingsStates?.isLocationPresent == true){
                    getLastLocation()
                }
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                    } catch (e: IntentSender.SendIntentException) {
                    } catch (e: ClassCastException) {
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> { }
                }
            }
        }
    }




    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }


        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    val mLastLocation = task.result

                    var address = "No known address"

                    val gcd = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address>
                    try {
                        addresses = gcd.getFromLocation(mLastLocation!!.latitude, mLastLocation.longitude, 1)
                        if (addresses.isNotEmpty()) {
                            address = addresses[0].getAddressLine(0)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                       val icon=  (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    val getcoordinates = LatLng(mLastLocation.latitude,mLastLocation.longitude)
                    SetDest.setText(getcoordinates.toString())
                    mMap.addMarker(
                        MarkerOptions()
                            .position(getcoordinates)
                            .draggable(true)
                            .title(getcoordinates.toString())
                            .snippet(address)
                            .icon(icon)
                    )


                    val cameraPosition = CameraPosition.Builder()
                        .target(getcoordinates)
                        .zoom(17f)
                        .build()

                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                } else {
                    Toast.makeText(this, "No current location found", Toast.LENGTH_LONG).show()
                }
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

    override fun onLocationChanged(location: Location) {
        //Latitude = findViewById(R.id.Lat_txt)

            getLatt = location.latitude



            getLongg = location.longitude



    }
    var counter : Int = 0

    private fun setMapLongClick(map:GoogleMap){
        val picture = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_pickup))


        var getcoordinates : LatLng


        map.setOnMapLongClickListener { LatLng ->

            Toast.makeText(this,counter.toString(), Toast.LENGTH_SHORT).show()

            getcoordinates = LatLng(LatLng.latitude,LatLng.longitude)
            val markerOptions = MarkerOptions().position(getcoordinates)



            markerOptions.draggable(true)
            markerOptions.icon(picture)
            markerOptions.title(getcoordinates.toString().removePrefix("lat/lng:"))


            if(counter >1 && markerOptions.isVisible){

map.clear()
                map.addMarker(markerOptions)
                counter --
                 }


            else{

                SetLoc.setText(getcoordinates.toString().removePrefix("lat/lng:"))

            }
            counter++




        }





    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        getCurrentLocation()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Permission required for showing location", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token!!.continuePermissionRequest()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    getCurrentLocation()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}