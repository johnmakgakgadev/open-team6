package com.hailrideapp.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler().postDelayed({
                              Intent( createPackageContext(this@MainActivity, MainActivity3::class.java))
            ActivityResultContracts.StartIntentSenderForResult
            finish()
        }, delay(3000))

    }
}