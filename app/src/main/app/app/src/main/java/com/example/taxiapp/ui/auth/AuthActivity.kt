package com.example.taxiapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taxiapp.databinding.ActivityAuthBinding
import com.example.taxiapp.ui.admin.AdminActivity
import com.example.taxiapp.ui.driver.DriverActivity
import com.example.taxiapp.ui.passenger.PassengerActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnPassengerLogin.setOnClickListener {
            startActivity(Intent(this, PassengerActivity::class.java))
        }

        binding.btnDriverLogin.setOnClickListener {
            startActivity(Intent(this, DriverActivity::class.java))
        }

        binding.btnAdminLogin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
    }
}
