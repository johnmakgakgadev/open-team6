package com.example.locationhomepage.Interface

import android.content.Intent
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.locationhomepage.Login1
import com.example.locationhomepage.MainActivity
import com.example.locationhomepage.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity(){
    private lateinit var binding: ActivitySplashScreenBinding
    var getLongg:String? =null
    var getLatt:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Login1::class.java)
            startActivity(intent)
            finish()
        }, 3000)





    }

    }
