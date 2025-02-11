package com.example.examen_crud

import android.content.ContentValues
import android.content.Context

class Controlador (context: Context) {
    private val dbHelper = SqliteHelper(context)

    //Crear concesionario
    fun crearconcesionario(concesionario: Concesionario) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", concesionario.id)
            put("modelo", concesionario.nombre)
            put("ubicacion", concesionario.ubicacion)
            put("calificacion", concesionario.calificacion)
        }
        db.insert("concesionario", null, valores)
        db.close()
    }

    //Listar concesionarios
    fun listarconcesionario(): List<Concesionario> {
        val db = dbHelper.writableDatabase
        val concesionarior = db.rawQuery("SELECT * FROM concesionario", null)
        val concesionarios = mutableListOf<Concesionario>()
        while (concesionarior.moveToNext()) {
            val id = concesionarior.getInt(0)
            val nombre = concesionarior.getString(1)
            val ubicacion = concesionarior.getString(2)
            val calificacion = concesionarior.getInt(3)
            concesionarios.add(Concesionario(id, nombre, ubicacion, calificacion))
        }
        concesionarior.close()
        db.close()
        return concesionarios
    }

    //Actualizar concesionario
    fun actualizarconcesionario(concesionario: Concesionario): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", concesionario.nombre)
            put("ubicacion", concesionario.ubicacion)
            put("calificacion", concesionario.calificacion)
        }
        val rows = db.update(
            "concesionario",
            valores,
            "id = ?",
            arrayOf(concesionario.id.toString())
        )
        db.close()
        return rows > 0
    }

    //Eliminar concesionario
    fun eliminarconcesionario (concesionarioId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(
            "concesionario",
            "id = ?",
            arrayOf(concesionarioId.toString())
        )
        db.close()
        return rows > 0
    }

    //Crear auto
    fun crearauto(concesionarioId: Int, auto: Auto) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("modelo", auto.modelo)
            put("anio", auto.anio)
            put("estado", auto.estado)
            put("precio", auto.precio)
            put("concesionario_id", concesionarioId)
        }
        db.insert("auto", null, valores)
        db.close()
    }

    //Listar autos de un concesionario
    fun listarautosPorconcesionario(concesionarioId: Int): List<Auto> {
        val db = dbHelper.readableDatabase
        val concesionarior = db.rawQuery(
            "SELECT * FROM auto WHERE concesionario_id = ?", // Actualizado
            arrayOf(concesionarioId.toString())
        )
        val autos = mutableListOf<Auto>()
        while (concesionarior.moveToNext()) {
            val id = concesionarior.getInt(concesionarior.getColumnIndexOrThrow("id"))
            val nombre = concesionarior.getString(concesionarior.getColumnIndexOrThrow("modelo"))
            val edad = concesionarior.getInt(concesionarior.getColumnIndexOrThrow("anio"))
            val email = concesionarior.getString(concesionarior.getColumnIndexOrThrow("estado"))
            val telefono = concesionarior.getInt(concesionarior.getColumnIndexOrThrow("precio"))
            autos.add(Auto(id, nombre, edad, email, telefono))
        }
        concesionarior.close()
        db.close()
        return autos
    }

    //Actualizar auto
    fun actualizarauto(auto: Auto): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("modelo", auto.modelo)
            put("anio", auto.anio)
            put("estado", auto.estado)
            put("precio", auto.precio)
        }
        val rows = db.update("auto", values, "id = ?", arrayOf(auto.id.toString()))
        db.close()
        return rows > 0
    }

    // Eliminar auto
    fun eliminarauto(autoId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete("auto", "id = ?", arrayOf(autoId.toString()))
        db.close()
        return rows > 0
    }

    fun depurarautos() {
        val db = dbHelper.readableDatabase
        val concesionarior = db.rawQuery("SELECT * FROM auto", null)
        while (concesionarior.moveToNext()) {
            val id = concesionarior.getInt(concesionarior.getColumnIndexOrThrow("id"))
            val nombre = concesionarior.getString(concesionarior.getColumnIndexOrThrow("modelo"))
            val concesionarioId = concesionarior.getInt(concesionarior.getColumnIndexOrThrow("concesionario_id"))
            println("auto: $id, Nombre: $nombre, concesionario ID: $concesionarioId")
        }
        concesionarior.close()
        db.close()
    }

}