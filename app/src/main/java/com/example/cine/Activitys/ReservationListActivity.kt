package com.example.cine.Activitys

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
        // Retrieve reservations from storage or database
        return listOf()
    }
}