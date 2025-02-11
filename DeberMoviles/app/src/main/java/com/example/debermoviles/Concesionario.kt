package com.example.debermoviles

class Concesionario(
    val id: Int,
    var nombre: String,
    var edad: Int,
    var nacionalidad: String,
    var fechaNacimiento: String,
    var latitud: Double = 0.0,
    var altitud: Double = 0.0,
    var autos: MutableList<Auto> = mutableListOf()
)
