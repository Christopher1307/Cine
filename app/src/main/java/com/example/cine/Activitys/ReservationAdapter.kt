package com.example.cine.Activitys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cine.R

data class Reservation(val date: String, val time: String, val movieTitle: String)

class ReservationAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.dateTextView.text = reservation.date
        holder.timeTextView.text = reservation.time
        holder.movieTitleTextView.text = reservation.movieTitle
    }

    override fun getItemCount(): Int = reservations.size

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitleTextView)
    }
}