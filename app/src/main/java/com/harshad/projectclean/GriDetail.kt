package com.harshad.projectclean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harshad.projectclean.databinding.ActivityGriDetailBinding

class GriDetail : AppCompatActivity() {

    private lateinit var binding: ActivityGriDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGriDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}