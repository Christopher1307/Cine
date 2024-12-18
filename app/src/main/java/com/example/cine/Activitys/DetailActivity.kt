package com.example.cine.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.cine.R
import com.example.cine.api.Movie

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Obtener la película pasada a través del Intent
        val movie = intent.getParcelableExtra<Movie>("movie")

        // Configurar los elementos de la interfaz con los datos de la película
        findViewById<TextView>(R.id.detail_title).text = movie?.title
        findViewById<TextView>(R.id.detail_description).text = movie?.overview
        findViewById<ImageView>(R.id.detail_image).apply {
            load("https://image.tmdb.org/t/p/w500${movie?.poster_path}") {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        // Navegar a ReservationFormActivity al hacer clic en el botón
        findViewById<Button>(R.id.reserveButton).setOnClickListener {
            val intent = Intent(this, ReservationFormActivity::class.java)
            startActivity(intent)
        }
    }
}