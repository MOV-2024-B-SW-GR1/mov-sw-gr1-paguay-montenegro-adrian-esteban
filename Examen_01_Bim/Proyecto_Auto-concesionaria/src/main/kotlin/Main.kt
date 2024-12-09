package org.example

import java.io.File
import kotlin.system.exitProcess
/*
Examen 01 - Lunes 9 de Diciembre de 2024
Escribir un programa CRUD (Create Read Update Delete)(Crear Leer Actualizar Delete).
Estas operaciones deben de realizarse en las dos entidades.
Las entidades deben de tener 5 datos cada una
Entre los 10 datos de las entidades Las entidades estan relacionadas de UNO a MUCHOS.
Toda la información se va a guardar en ARCHIVOS y el codigo escrito en el repositorio del curso.

PAGUAY MONTENEGRO ADRIAN ESTEBAN	Concesionaria - Auto
*/



fun main() {
    // Títulos y autor
    println("=====================================")
    println("      Proyecto Móviles 1-Bim")
    println("      Autor: Adrian Paguay")
    println("=====================================")

    val archivoConcesionarias = "data/concesionarias.txt"
    val concesionarias: MutableList<Concesionaria> = cargarConcesionariasDesdeArchivo(archivoConcesionarias)

    while (true) {
        println("\n--- Menú Principal ---")
        println("1. Crear Concesionaria")
        println("2. Listar Concesionarias")
        println("3. Actualizar Concesionaria")
        println("4. Eliminar Concesionaria")
        println("5. Gestionar Autos de una Concesionaria")
        println("6. Salir")
        print("Seleccione una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                concesionarias.add(crearConcesionaria())
                guardarConcesionariasEnArchivo(archivoConcesionarias, concesionarias)
            }
            2 -> listarConcesionarias(concesionarias)
            3 -> {
                actualizarConcesionaria(concesionarias)
                guardarConcesionariasEnArchivo(archivoConcesionarias, concesionarias)
            }
            4 -> {
                eliminarConcesionaria(concesionarias)
                guardarConcesionariasEnArchivo(archivoConcesionarias, concesionarias)
            }
            5 -> {
                listarConcesionarias(concesionarias)
                print("Seleccione el índice de la concesionaria: ")
                val index = readln().toIntOrNull()
                if (index != null && index in concesionarias.indices) {
                    gestionarAutos(concesionarias[index], archivoConcesionarias, concesionarias)
                } else {
                    println("Índice inválido.")
                }
            }
            6 -> {
                println("Saliendo del programa...")
                return
            }
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}


fun gestionarAutos(concesionaria: Concesionaria, archivo: String, concesionarias: MutableList<Concesionaria>) {
    while (true) {
        println("\n--- Gestión de Autos (${concesionaria.nombre}) ---")
        println("1. Agregar Auto")
        println("2. Listar Autos")
        println("3. Actualizar Auto")
        println("4. Eliminar Auto")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                concesionaria.listaAutos.add(crearAuto())
                guardarConcesionariasEnArchivo(archivo, concesionarias)
            }
            2 -> listarAutos(concesionaria.listaAutos)
            3 -> {
                actualizarAuto(concesionaria.listaAutos)
                guardarConcesionariasEnArchivo(archivo, concesionarias)
            }
            4 -> {
                eliminarAuto(concesionaria.listaAutos)
                guardarConcesionariasEnArchivo(archivo, concesionarias)
            }
            5 -> return
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}

