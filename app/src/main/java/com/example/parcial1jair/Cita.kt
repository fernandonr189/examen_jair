package com.example.parcial1jair

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Cita : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val btnAgendar = findViewById<Button>(R.id.btnAgendar)

        btnAgendar.setOnClickListener {
            val fecha = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val hora = "${timePicker.hour}:${timePicker.minute}"
            Toast.makeText(this, "Cita agendada para el $fecha a las $hora", Toast.LENGTH_LONG).show()
        }
    }
}