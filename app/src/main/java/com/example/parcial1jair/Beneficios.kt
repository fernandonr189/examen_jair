package com.example.parcial1jair

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Beneficios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beneficios)

        val textViewBeneficios = findViewById<TextView>(R.id.textViewBeneficios)
        textViewBeneficios.text = getString(R.string.texto_beneficios)

        val imageViewBeneficios = findViewById<ImageView>(R.id.imageViewBeneficios)
        imageViewBeneficios.setImageResource(R.drawable.beneficios)
    }
}