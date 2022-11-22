package com.hailrideapp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton



<import>

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3);


        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);



        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin

        val loginbtn = null
        loginbtn.SetOnClickListener(new View.OnClickListener() {
            val public
            override@
            public void onClick(View v) {
                if (!(!username.getText().string().equals("admin") || !password.getText().string().equals("admin"))){
                    //correct
                    Toast.makeText(context: MainActivity.this, text: "LOGIN SUCCESSFUL", Toast.LENGHT_SHORT).show();
                }else
                //incorrect
                    Toast.makeText(context: MainActivity.this, text: "LOGIN FAILED !!!", Toast.LENGHT_SHORT).show();

            }
            }
        })


    }
}