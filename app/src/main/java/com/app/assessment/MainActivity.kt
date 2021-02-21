package com.app.assessment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn=findViewById<MaterialButton>(R.id.btn)
        val date=findViewById<EditText>(R.id.getsign)
        btn.setOnClickListener {
            val intent= Intent(this, signresult::class.java)
            intent.putExtra("date", date.text.toString())
            startActivity(intent)
        }
    }
}