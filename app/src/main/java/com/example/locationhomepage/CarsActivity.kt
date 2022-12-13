package com.example.locationhomepage;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locationhomepage.databinding.ActivityCarsBinding


class CarsActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityCarsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCarsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (actionBar?.isShowing == true){
            actionBar!!.hide()
        }
    }
}