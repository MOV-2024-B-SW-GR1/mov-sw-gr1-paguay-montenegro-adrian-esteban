package com.example.debermoviles

import android.content.ContentValues
import android.content.Context

class Controlador(context: Context) {
    private val dbHelper = SqliteHelper(context)

    // Crear Concesionario
    fun crearArtista(concesionario: Concesionario) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", concesionario.id)
            put("nombre", concesionario.nombre)
            put("edad", concesionario.edad)
            put("nacionalidad", concesionario.nacionalidad)
            put("fecha_nacimiento", concesionario.fechaNacimiento)
            put("latitud", concesionario.latitud)
            put("altitud", concesionario.altitud)
        }
        db.insert("Concesionario", null, valores)
        db.close()
    }

    // Listar Artistas
    fun listarArtistas(): List<Concesionario> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Concesionario", null)
        val concesionarios = mutableListOf<Concesionario>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
            val nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow("nacionalidad"))
            val fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento"))
            val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
            val altitud = cursor.getDouble(cursor.getColumnIndexOrThrow("altitud"))

            concesionarios.add(Concesionario(id, nombre, edad, nacionalidad, fechaNacimiento, latitud, altitud))
        }
        cursor.close()
        db.close()
        return concesionarios
    }

    // Actualizar Concesionario
    fun actualizarArtista(concesionario: Concesionario): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", concesionario.nombre)
            put("edad", concesionario.edad)
            put("nacionalidad", concesionario.nacionalidad)
            put("fecha_nacimiento", concesionario.fechaNacimiento)
            put("latitud", concesionario.latitud)
            put("altitud", concesionario.altitud)
        }
        val rows = db.update(
            "Concesionario",
            valores,
            "id = ?",
            arrayOf(concesionario.id.toString())
        )
        db.close()
        return rows > 0
    }

    // Eliminar Concesionario
    fun eliminarArtista(artistaId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(
            "Concesionario",
            "id = ?",
            arrayOf(artistaId.toString())
        )
        db.close()
        return rows > 0
    }

    // Crear Auto
    fun crearObra(artistaId: Int, auto: Auto) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("titulo", auto.titulo)
            put("anio_creacion", auto.anioCreacion)
            put("tipo_obra", auto.tipoObra)
            put("precio_estimado", auto.precioEstimado)
            put("disponible", auto.disponible)
            put("artista_id", artistaId)
        }
        db.insert("Auto", null, valores)
        db.close()
    }

    // Listar Obras de un Concesionario
    fun listarObrasPorArtista(artistaId: Int): List<Auto> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Auto WHERE artista_id = ?",
            arrayOf(artistaId.toString())
        )
        val autos = mutableListOf<Auto>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val anioCreacion = cursor.getInt(cursor.getColumnIndexOrThrow("anio_creacion"))
            val tipoObra = cursor.getString(cursor.getColumnIndexOrThrow("tipo_obra"))
            val precioEstimado = cursor.getDouble(cursor.getColumnIndexOrThrow("precio_estimado"))
            val disponible = cursor.getInt(cursor.getColumnIndexOrThrow("disponible")) != 0
            autos.add(Auto(id, titulo, anioCreacion, tipoObra, precioEstimado, disponible))
        }
        cursor.close()
        db.close()
        return autos
    }

    // Actualizar Auto
    fun actualizarObra(auto: Auto): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", auto.titulo)
            put("anio_creacion", auto.anioCreacion)
            put("tipo_obra", auto.tipoObra)
            put("precio_estimado", auto.precioEstimado)
            put("disponible", auto.disponible)
        }
        val rows = db.update("Auto", values, "id = ?", arrayOf(auto.id.toString()))
        db.close()
        return rows > 0
    }

    // Eliminar Auto
    fun eliminarObra(obraId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete("Auto", "id = ?", arrayOf(obraId.toString()))
        db.close()
        return rows > 0
    }

    fun depurarObras() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Auto", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val cursoId = cursor.getInt(cursor.getColumnIndexOrThrow("artista_id"))
            println("Auto: $id, Título: $titulo, Concesionario ID: $cursoId")
        }
        cursor.close()
        db.close()
    }
}
