package org.example

import java.io.Serializable

data class Auto(
    val marca: String,
    val modelo: String,
    val anio: Int,
    val precio: Double,
    val kilometraje: Double,
    val estado: String,
    val vin: String
) : Serializable
