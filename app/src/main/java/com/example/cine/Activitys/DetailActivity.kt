package com.example.cine.Activitys

import android.os.Bundle
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

        val movie = intent.getParcelableExtra<Movie>("movie")

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
    }
}