package com.example.parcial1jair

class Solicitud(
    var curp: String,
    var nombre: String,
    var apellidos: String,
    var domicilio: String,
    var cantidadIngreso: Double,
    var tipoCredito: String
) {
    fun validarIngreso(): Boolean {
        tipoCredito = when {
            cantidadIngreso in 15000.0..35000.0 -> "Premier"
            cantidadIngreso in 35000.0..50000.0 -> "Oro"
            cantidadIngreso > 50000.0 -> "Platinum"
            else -> ""
        }
        return tipoCredito.isNotEmpty()
    }
}