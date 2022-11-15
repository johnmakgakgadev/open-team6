package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityCarsBinding

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