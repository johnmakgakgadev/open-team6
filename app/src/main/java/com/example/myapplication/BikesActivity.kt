package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityBikesBinding

class BikesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBikesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBikesBinding.inflate(layoutInflater)
                super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}