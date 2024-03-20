package com.example.parcial1jair
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private val channel_id = "Notificacion"
    private lateinit var curpEditText: EditText
    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var domicilioEditText: EditText
    private lateinit var cantidadIngresoEditText: EditText
    private lateinit var tipoCreditoSpinner: Spinner
    private lateinit var btnValidar: Button
    private lateinit var btnLimpiar: Button

    companion object {
        private var notificationId = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        curpEditText = findViewById(R.id.curpEditText)
        nombreEditText = findViewById(R.id.nombreEditText)
        apellidosEditText = findViewById(R.id.apellidosEditText)
        domicilioEditText = findViewById(R.id.domicilioEditText)
        cantidadIngresoEditText = findViewById(R.id.ingresoEditText)
        tipoCreditoSpinner = findViewById(R.id.spinnerTipoCredito)
        btnValidar = findViewById(R.id.btnValidar)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        setupSpinner()

        btnValidar.setOnClickListener {
            validarSolicitud()
        }

        btnLimpiar.setOnClickListener {
            limpiarFormulario()
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.creditos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tipoCreditoSpinner.adapter = adapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validarSolicitud() {
        val cantidadIngreso = cantidadIngresoEditText.text.toString().toDoubleOrNull()
        if (cantidadIngreso != null) {
            val solicitud = Solicitud(
                curpEditText.text.toString(),
                nombreEditText.text.toString(),
                apellidosEditText.text.toString(),
                domicilioEditText.text.toString(),
                cantidadIngreso,
                ""
            )
            if (solicitud.validarIngreso()) {
                Toast.makeText(this, "Apto para tarjeta ${solicitud.tipoCredito}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No apto para tarjeta de crédito", Toast.LENGTH_LONG).show()
            }
            mostrarNotificacion(solicitud.tipoCredito)
        } else {
            Toast.makeText(this, "Por favor, ingrese una cantidad de ingreso válida", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostrarNotificacion(tipoCredito: String) {
        val channelId = "creditAppChannel"
        val channelName = "Canal de notificación para la aplicación de créditos"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Canal de notificación para la aplicación de créditos"
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        val citaIntent = Intent(this, Cita::class.java)
        val citaPendingIntent = PendingIntent.getActivity(this, 0, citaIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val beneficiosIntent = Intent(this, Beneficios::class.java)
        val beneficiosPendingIntent = PendingIntent.getActivity(this, 1, beneficiosIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Estatus de Solicitud")
            .setContentText("Apto para tarjeta $tipoCredito")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_foreground, "Cita", citaPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Beneficios", beneficiosPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId++, builder.build())
        }
    }

    private fun limpiarFormulario() {
        curpEditText.text.clear()
        nombreEditText.text.clear()
        apellidosEditText.text.clear()
        domicilioEditText.text.clear()
        cantidadIngresoEditText.text.clear()
        tipoCreditoSpinner.setSelection(0)
    }
}
