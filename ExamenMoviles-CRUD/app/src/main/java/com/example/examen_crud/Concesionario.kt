package com.example.examen_crud

class Concesionario (
    val id: Int,
    var nombre: String,
    var ubicacion: String,
    var calificacion: Int,
    var autos: MutableList<Auto> = mutableListOf()
)