package org.example

fun crearConcesionaria(): Concesionaria {
    println("Ingrese los datos de la concesionaria:")
    print("Nombre: ")
    val nombre = readln()
    print("Dirección: ")
    val direccion = readln()
    print("Teléfono: ")
    val telefono = readln()

    return Concesionaria(nombre, direccion, telefono, mutableListOf(), 0)
}

fun listarConcesionarias(concesionarias: List<Concesionaria>) {
    if (concesionarias.isEmpty()) {
        println("No hay concesionarias registradas.")
        return
    }
    println("\n--- Lista de Concesionarias ---")
    concesionarias.forEachIndexed { index, concesionaria ->
        println("Concesionaria #$index")
        println("  Nombre: ${concesionaria.nombre}")
        println("  Dirección: ${concesionaria.direccion}")
        println("  Teléfono: ${concesionaria.telefono}")
        println("  Autos Vendidos: ${concesionaria.cantidadAutosVendidos}")
        println("  Autos Disponibles:")
        listarAutos(concesionaria.listaAutos)
        println("-".repeat(40)) // Separador entre concesionarias
    }
}


fun actualizarConcesionaria(concesionarias: MutableList<Concesionaria>) {
    listarConcesionarias(concesionarias)
    print("Seleccione el índice de la concesionaria a actualizar: ")
    val index = readln().toIntOrNull()
    if (index != null && index in concesionarias.indices) {
        val concesionariaActualizada = crearConcesionaria()
        concesionarias[index] = concesionariaActualizada
        println("Concesionaria actualizada correctamente.")
    } else {
        println("Índice inválido.")
    }
}

fun eliminarConcesionaria(concesionarias: MutableList<Concesionaria>) {
    listarConcesionarias(concesionarias)
    print("Seleccione el índice de la concesionaria a eliminar: ")
    val index = readln().toIntOrNull()
    if (index != null && index in concesionarias.indices) {
        concesionarias.removeAt(index)
        println("Concesionaria eliminada correctamente.")
    } else {
        println("Índice inválido.")
    }
}
