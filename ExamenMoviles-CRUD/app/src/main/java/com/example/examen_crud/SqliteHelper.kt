package com.example.examen_crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "ConcesionariosDB",
    null,
    3
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSqlCrearConcesionario = """
            CREATE TABLE Concesionario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modelo VARCHAR(250),
                ubicacion VARCHAR(250),
                calificacion INTEGER
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearConcesionario)

        val scriptSqlCrearAuto = """
            CREATE TABLE Auto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modelo VARCHAR(255),
                anio INTEGER,
                estado VARCHAR(255),
                precio VARCHAR(15),
                concecionario_id INTEGER,
                FOREIGN KEY (concesionario_id) REFERENCES Concesionario(id) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearAuto)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS Auto")
            db?.execSQL("DROP TABLE IF EXISTS Concesionario")
            onCreate(db)
        }
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }
}
