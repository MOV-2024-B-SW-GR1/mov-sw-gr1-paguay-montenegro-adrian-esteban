package org.example

import java.io.Serializable

data class Concesionaria(
    val nombre: String,
    val direccion: String,
    val telefono: String,
    var listaAutos: MutableList<Auto>,
    var cantidadAutosVendidos: Int = 0
) : Serializable
