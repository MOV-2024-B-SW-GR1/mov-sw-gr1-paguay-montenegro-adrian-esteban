package com.example.examen_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityautoList : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var tvconcesionarioTitulo: TextView
    private lateinit var listViewautos: ListView
    private lateinit var btnAgregarauto: Button
    private var concesionarioId: Int = 0
    private var selectedAuto: Auto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_list)

        controlador = Controlador(this)
        tvconcesionarioTitulo = findViewById(R.id.tvconcesionarioTitulo)
        listViewautos = findViewById(R.id.listViewautos)
        btnAgregarauto = findViewById(R.id.btnAgregarauto)

        // Obtener el ID del concesionario
        concesionarioId = intent.getIntExtra("concesionarioId", 0)

        // Mostrar el modelo del concesionario en el título
        val concesionario = controlador.listarconcesionario().find { it.id == concesionarioId }
        if (concesionario != null) {
            tvconcesionarioTitulo.text = concesionario.nombre
        } else {
            tvconcesionarioTitulo.text = "concesionario no encontrado"
        }

        btnAgregarauto.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarauto::class.java)
            intent.putExtra("concesionarioId", concesionarioId)
            startActivity(intent)
        }

        registerForContextMenu(listViewautos)
        actualizarLista()
    }

    private fun actualizarLista() {
        val autos = controlador.listarautosPorconcesionario(concesionarioId)
        if (autos.isEmpty()) {
            Toast.makeText(this, "No hay autos en este concesionario", Toast.LENGTH_SHORT).show()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, autos.map { it.modelo })
        listViewautos.adapter = adapter

        listViewautos.setOnItemLongClickListener { _, _, position, _ ->
            selectedAuto = autos[position]
            false // Esto permite que el menú contextual se muestre
        }
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_auto, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuEditarauto -> {
                val intent = Intent(this, ActivityAgregarEditarauto::class.java)
                intent.putExtra("concesionarioId", concesionarioId)
                intent.putExtra("autoId", selectedAuto?.id)
                startActivity(intent)
            }
            R.id.menuEliminarauto -> {
                if (selectedAuto != null) {
                    controlador.eliminarauto(selectedAuto!!.id)
                    Toast.makeText(this, "auto eliminado", Toast.LENGTH_SHORT).show()
                    actualizarLista()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        controlador.depurarautos() // Agrega esta línea para depurar
        actualizarLista()
    }
}

