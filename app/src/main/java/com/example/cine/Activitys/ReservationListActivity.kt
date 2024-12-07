package com.example.cine.Activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cine.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReservationListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_list)

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ReservationAdapter(getReservations())

        fab.setOnClickListener {
            val intent = Intent(this, ReservationFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getReservations(): List<Reservation> {
        val sharedPreferences = getSharedPreferences("reservations", Context.MODE_PRIVATE)
        val reservations = mutableListOf<Reservation>()

        sharedPreferences.all.forEach { (key, value) ->
            if (key.endsWith("_date")) {
                val reservationId = key.removeSuffix("_date")
                val date = value as String
                val time = sharedPreferences.getString("${reservationId}_time", "") ?: ""
                reservations.add(Reservation(date, time))
            }
        }

        return reservations
    }
}