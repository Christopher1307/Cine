package com.example.cine.Activitys

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cine.R
import java.util.*

class ReservationFormActivity : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var movieTitleEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_form)

        dateEditText = findViewById(R.id.dateEditText)
        timeEditText = findViewById(R.id.timeEditText)
        nameEditText = findViewById(R.id.nameEditText)
        surnameEditText = findViewById(R.id.surnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        dobEditText = findViewById(R.id.dobEditText)
        movieTitleEditText = findViewById(R.id.movieTitleEditText)
        submitButton = findViewById(R.id.submitButton)

        dateEditText.setOnClickListener { showDatePickerDialog(dateEditText) }
        timeEditText.setOnClickListener { showTimePickerDialog() }
        dobEditText.setOnClickListener { showDatePickerDialog(dobEditText) }

        submitButton.setOnClickListener {
            if (validateFields()) {
                saveReservation()
                val intent = Intent(this, ReservationListActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            editText.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            timeEditText.setText("$selectedHour:$selectedMinute")
        }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun validateFields(): Boolean {
        return dateEditText.text.isNotEmpty() &&
                timeEditText.text.isNotEmpty() &&
                nameEditText.text.isNotEmpty() &&
                surnameEditText.text.isNotEmpty() &&
                emailEditText.text.isNotEmpty() &&
                dobEditText.text.isNotEmpty() &&
                movieTitleEditText.text.isNotEmpty()
    }

    private fun saveReservation() {
        val sharedPreferences = getSharedPreferences("reservations", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val reservationId = UUID.randomUUID().toString()

        editor.putString("${reservationId}_date", dateEditText.text.toString())
        editor.putString("${reservationId}_time", timeEditText.text.toString())
        editor.putString("${reservationId}_name", nameEditText.text.toString())
        editor.putString("${reservationId}_surname", surnameEditText.text.toString())
        editor.putString("${reservationId}_email", emailEditText.text.toString())
        editor.putString("${reservationId}_dob", dobEditText.text.toString())
        editor.putString("${reservationId}_movieTitle", movieTitleEditText.text.toString())
        editor.apply()
    }
}