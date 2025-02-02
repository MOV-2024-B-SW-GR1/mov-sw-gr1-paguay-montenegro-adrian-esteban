package com.example.examen_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAgregarEditarauto : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etModeloauto: EditText
    private lateinit var etAnioauto: EditText
    private lateinit var etEstadoauto: EditText
    private lateinit var etPrecioauto: EditText
    private lateinit var btnGuardarauto: Button
    private var concesionarioId: Int = 0
    private var autoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_auto)

        controlador = Controlador(this)
        etModeloauto = findViewById(R.id.etModeloAuto)
        etAnioauto = findViewById(R.id.etAnioAuto)
        etEstadoauto = findViewById(R.id.etEstadoAuto)
        etPrecioauto = findViewById(R.id.etPrecioAuto)
        btnGuardarauto = findViewById(R.id.btnGuardarAuto)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)

        concesionarioId = intent.getIntExtra("concesionarioId", 0)
        autoId = intent.getIntExtra("autoId", 0).takeIf { it != 0 }

        // Actualiza el título basado en si estamos editando o creando
        tvFormularioTitulo.text = if (autoId != null) "Editar auto" else "Agregar auto"

        // Cargar datos del auto si estamos editando
        if (autoId != null) {
            val auto = controlador.listarautosPorconcesionario(concesionarioId).find { it.id == autoId }
            auto?.let {
                etModeloauto.setText(it.modelo)
                etAnioauto.setText(it.anio.toString())
                etEstadoauto.setText(it.estado)
                etPrecioauto.setText(it.precio.toString())
            }
        }

        btnGuardarauto.setOnClickListener {
            guardarauto()
        }
    }

    private fun guardarauto() {
        val nombre = etModeloauto.text.toString()
        val edad = etAnioauto.text.toString().toIntOrNull()
        val email = etEstadoauto.text.toString()
        val telefono = etPrecioauto.text.toString().toIntOrNull()

        if (nombre.isNotEmpty() && edad != null && email.isNotEmpty() && telefono != null) {
            if (autoId != null) {
                // Actualizar auto existente
                controlador.actualizarauto(
                    Auto(autoId!!, nombre, edad, email, telefono)
                )
                Toast.makeText(this, "auto actualizado", Toast.LENGTH_SHORT).show()
            } else {
                // Crear nuevo auto
                controlador.crearauto(concesionarioId, Auto(0, nombre, edad, email, telefono))
                Toast.makeText(this, "auto creado", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
