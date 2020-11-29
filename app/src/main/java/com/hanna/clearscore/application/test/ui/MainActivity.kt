package com.hanna.clearscore.application.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hanna.clearscore.application.test.R


//The single fragment is inflated in the xml.
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}