package com.example.examen_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAgregarEditarConcesionario : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etNombreConcesionario: EditText
    private lateinit var etUbicacionConcesionario: EditText
    private lateinit var etCalificacionConcesionario: EditText
    private lateinit var btnGuardarConcesionario: Button
    private var concesionarioId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_concesionario)

        controlador = Controlador(this)
        etNombreConcesionario = findViewById(R.id.etNombreconcesionario)
        etUbicacionConcesionario = findViewById(R.id.etDescripcionconcesionario)
        etCalificacionConcesionario = findViewById(R.id.etDuracionconcesionario)
        btnGuardarConcesionario = findViewById(R.id.btnGuardarconcesionario)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)
        tvFormularioTitulo.text = if (concesionarioId != null) "Editar Concesionario" else "Agregar Concesionario"

        concesionarioId = intent.getIntExtra("concesionarioId", 0).takeIf { it != 0 }

        if (concesionarioId != null) {
            val concesionario = controlador.listarconcesionario().find { it.id == concesionarioId }
            concesionario?.let {
                etNombreConcesionario.setText(it.nombre)
                etUbicacionConcesionario.setText(it.ubicacion)
                etCalificacionConcesionario.setText(it.calificacion.toString())
            }
        }

        btnGuardarConcesionario.setOnClickListener {
            val nombre = etNombreConcesionario.text.toString()
            val descripcion = etUbicacionConcesionario.text.toString()
            val duracion = etCalificacionConcesionario.text.toString().toIntOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && duracion != null) {
                if (concesionarioId != null) {
                    controlador.actualizarconcesionario(Concesionario(concesionarioId!!, nombre, descripcion, duracion))
                    Toast.makeText(this, "Concesionario actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevoId = (controlador.listarconcesionario().maxOfOrNull { it.id } ?: 0) + 1
                    controlador.crearconcesionario(Concesionario(nuevoId, nombre, descripcion, duracion))
                    Toast.makeText(this, "Concesionario creado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
