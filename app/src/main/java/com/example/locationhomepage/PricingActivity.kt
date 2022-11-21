package com.example.locationhomepage;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locationhomepage.R
import com.example.locationhomepage.databinding.ActivityPricingBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class PricingActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityPricingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityPricingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomview.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_car ->{
                    startActivity(Intent(this@PricingActivity, CarsActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_vans->{
                    startActivity(Intent(this@PricingActivity, VansActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_bike->{
                    startActivity(Intent(this@PricingActivity, BikesActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        })
    }
}