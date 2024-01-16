package com.example.plantillaexamen.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plantillaexamen.databinding.ActivitySecondBinding

class SecondActivityBottom: AppCompatActivity()  {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}