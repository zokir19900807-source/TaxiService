package com.example.taxiapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var layoutAuth: LinearLayout
    private lateinit var layoutPassenger: LinearLayout
    private lateinit var layoutDriver: LinearLayout
    private lateinit var layoutAdmin: LinearLayout

    private var driverBalance = 0
    private var isDriverApproved = false
    private var isDriverSubscribed = false
    private val rides = mutableListOf<String>()
    private val drivers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        showAuthScreen()
    }

    private fun initViews() {
        layoutAuth = findViewById(R.id.layoutAuth)
        layoutPassenger = findViewById(R.id.layoutPassenger)
        layoutDriver = findViewById(R.id.layoutDriver)
        layoutAdmin = findViewById(R.id.layoutAdmin)

        findViewById<Button>(R.id.btnPassenger).setOnClickListener { showPassengerScreen() }
        findViewById<Button>(R.id.btnDriver).setOnClickListener { showDriverScreen() }
        findViewById<Button>(R.id.btnAdmin).setOnClickListener { showAdminScreen() }

        findViewById<Button>(R.id.btnPassengerBack).setOnClickListener { showAuthScreen() }
        findViewById<Button>(R.id.btnDriverBack).setOnClickListener { showAuthScreen() }
        findViewById<Button>(R.id.btnAdminBack).setOnClickListener { showAuthScreen() }

        findViewById<Button>(R.id.btnRequestRide).setOnClickListener { requestRide() }
        findViewById<Button>(R.id.btnToggleOnline).setOnClickListener { toggleOnline() }
        findViewById<Button>(R.id.btnPaySubscription).setOnClickListener { paySubscription() }
        findViewById<Button>(R.id.btnApproveDriver).setOnClickListener { approveDriver() }
        findViewById<Button>(R.id.btnAddRide).setOnClickListener { addRide() }
    }

    private fun hideAll() {
        layoutAuth.visibility = View.GONE
        layoutPassenger.visibility = View.GONE
        layoutDriver.visibility = View.GONE
        layoutAdmin.visibility = View.GONE
    }

    private fun showAuthScreen() {
        hideAll()
        layoutAuth.visibility = View.VISIBLE
    }

    private fun showPassengerScreen() {
        hideAll()
        layoutPassenger.visibility = View.VISIBLE
        updatePassengerRides()
    }

    private fun showDriverScreen() {
        hideAll()
        layoutDriver.visibility = View.VISIBLE
        updateDriverStatus()
    }

    private fun showAdminScreen() {
        hideAll()
        layoutAdmin.visibility = View.VISIBLE
        updateAdminStats()
    }

    private fun requestRide() {
        val from = findViewById<EditText>(R.id.etFrom).text.toString()
        val to = findViewById<EditText>(R.id.etTo).text.toString()
        if (from.isNotEmpty() && to.isNotEmpty()) {
            rides.add("Yo'lovchi: $from → $to | Narx: 15000 so'm")
            Toast.makeText(this, "Taksi chaqirildi!", Toast.LENGTH_SHORT).show()
            updatePassengerRides()
        }
    }

    private fun updatePassengerRides() {
        val tv = findViewById<TextView>(R.id.tvPassengerRides)
        tv.text = if (rides.isEmpty()) "Hali safar yo'q" else rides.joinToString("\n")
    }

    private fun toggleOnline() {
        val btn = findViewById<Button>(R.id.btnToggleOnline)
        val tv = findViewById<TextView>(R.id.tvDriverStatus)
        if (!isDriverApproved) {
            Toast.makeText(this, "Admin tasdig'ini kuting!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isDriverSubscribed) {
            Toast.makeText(this, "Avval obuna to'lang!", Toast.LENGTH_SHORT).show()
            return
        }
        if (btn.text == "Onlayn bo'lish") {
            btn.text = "Offlayn bo'lish"
            tv.text = "Status: 🟢 Onlayn"
        } else {
            btn.text = "Onlayn bo'lish"
            tv.text = "Status: 🔴 Offlayn"
        }
    }

    private fun paySubscription() {
        isDriverSubscribed = true
        driverBalance += 50000
        Toast.makeText(this, "Obuna faollashdi! 50000 so'm", Toast.LENGTH_SHORT).show()
        updateDriverStatus()
    }

    private fun updateDriverStatus() {
        val tv = findViewById<TextView>(R.id.tvDriverStatus)
        val status = when {
            !isDriverApproved -> "Status: ⏳ Tasdiqlanish kutilmoqda"
            !isDriverSubscribed -> "Status: 💳 Obuna yo'q"
            else -> "Status: 🔴 Offlayn"
        }
        tv.text = status
        findViewById<TextView>(R.id.tvDriverBalance).text = "Balans: $driverBalance so'm"
    }

    private fun approveDriver() {
        isDriverApproved = true
        drivers.add("Haydovchi 1 - Tasdiqlandi")
        Toast.makeText(this, "Haydovchi tasdiqlandi!", Toast.LENGTH_SHORT).show()
        updateAdminStats()
    }

    private fun addRide() {
        rides.add("Admin: Yangi safar qo'shildi")
        Toast.makeText(this, "Safar qo'shildi!", Toast.LENGTH_SHORT).show()
        updateAdminStats()
    }

    private fun updateAdminStats() {
        findViewById<TextView>(R.id.tvAdminStats).text = """
            📊 Statistika:
            Jami haydovchilar: ${drivers.size}
            Jami safarlar: ${rides.size}
            Daromad: ${driverBalance} so'm
        """.trimIndent()
    }
}
