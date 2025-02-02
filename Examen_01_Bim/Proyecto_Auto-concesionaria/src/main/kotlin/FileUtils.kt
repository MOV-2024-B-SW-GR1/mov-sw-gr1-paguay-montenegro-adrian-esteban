package org.example

import java.io.File

// Guardar concesionarias en archivo TXT
fun guardarConcesionariasEnArchivo(fileName: String, concesionarias: List<Concesionaria>) {
    File(fileName).bufferedWriter().use { writer ->
        for (concesionaria in concesionarias) {
            writer.write("${concesionaria.nombre}|${concesionaria.direccion}|${concesionaria.telefono}|${concesionaria.cantidadAutosVendidos}\n")
            for (auto in concesionaria.listaAutos) {
                writer.write("AUTO|${auto.marca}|${auto.modelo}|${auto.anio}|${auto.precio}|${auto.kilometraje}|${auto.estado}|${auto.vin}\n")
            }
        }
    }
}

// Cargar concesionarias desde archivo TXT
fun cargarConcesionariasDesdeArchivo(fileName: String): MutableList<Concesionaria> {
    val concesionarias = mutableListOf<Concesionaria>()

    if (!File(fileName).exists()) return concesionarias

    var concesionariaActual: Concesionaria? = null
    File(fileName).bufferedReader().forEachLine { line ->
        val parts = line.split("|")
        if (parts[0] != "AUTO") {
            // Nueva concesionaria
            concesionariaActual = Concesionaria(parts[0], parts[1], parts[2], mutableListOf(), parts[3].toInt())
            concesionarias.add(concesionariaActual!!)
        } else {
            // Auto de la concesionaria actual
            concesionariaActual?.listaAutos?.add(
                Auto(parts[1], parts[2], parts[3].toInt(), parts[4].toDouble(), parts[5].toDouble(), parts[6], parts[7])
            )
        }
    }

    return concesionarias
}
