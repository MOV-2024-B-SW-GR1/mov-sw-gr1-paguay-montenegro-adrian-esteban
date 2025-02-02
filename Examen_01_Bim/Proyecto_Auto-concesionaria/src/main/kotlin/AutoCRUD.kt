package org.example

fun crearAuto(): Auto {
    println("Ingrese los datos del auto:")
    print("Marca: ")
    val marca = readln()
    print("Modelo: ")
    val modelo = readln()
    print("Año: ")
    val anio = readln().toInt()
    print("Precio: ")
    val precio = readln().toDouble()
    print("Kilometraje: ")
    val kilometraje = readln().toDouble()
    print("Estado (Nuevo/Usado): ")
    val estado = readln()
    print("VIN (Número de serie): ")
    val vin = readln()

    return Auto(marca, modelo, anio, precio, kilometraje, estado, vin)
}

fun listarAutos(autos: List<Auto>) {
    if (autos.isEmpty()) {
        println("    No hay autos registrados.")
        return
    }
    autos.forEachIndexed { index, auto ->
        println("    Auto #$index:")
        println("      Marca: ${auto.marca}")
        println("      Modelo: ${auto.modelo}")
        println("      Año: ${auto.anio}")
        println("      Precio: $${auto.precio}")
        println("      Kilometraje: ${auto.kilometraje} km")
        println("      Estado: ${auto.estado}")
        println("      VIN: ${auto.vin}")
        println("    " + "-".repeat(30)) // Separador entre autos
    }
}


fun actualizarAuto(autos: MutableList<Auto>) {
    listarAutos(autos)
    print("Seleccione el índice del auto a actualizar: ")
    val index = readln().toIntOrNull()
    if (index != null && index in autos.indices) {
        val autoActualizado = crearAuto()
        autos[index] = autoActualizado
        println("Auto actualizado correctamente.")
    } else {
        println("Índice inválido.")
    }
}

fun eliminarAuto(autos: MutableList<Auto>) {
    listarAutos(autos)
    print("Seleccione el índice del auto a eliminar: ")
    val index = readln().toIntOrNull()
    if (index != null && index in autos.indices) {
        autos.removeAt(index)
        println("Auto eliminado correctamente.")
    } else {
        println("Índice inválido.")
    }
}
