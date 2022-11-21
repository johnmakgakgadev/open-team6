package com.example.locationhomepage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.locationhomepage.Maps.MapsActivity

class Login1 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        val login_btn : Button = findViewById(R.id.loginbtn)

        login_btn.setOnClickListener {
            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}