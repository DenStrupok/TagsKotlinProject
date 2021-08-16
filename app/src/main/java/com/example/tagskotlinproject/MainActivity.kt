package com.example.tagskotlinproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tagskotlinproject.ui.listitem.ListItemFragment

class MainActivity : AppCompatActivity() {
    companion object{
        const val MY_TAG: String = "myTag"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   }
}