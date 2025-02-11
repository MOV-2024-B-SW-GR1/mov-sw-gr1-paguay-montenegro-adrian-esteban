package com.example.examen_crud


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityConcesionarioList : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var listViewConcesionarios: ListView
    private lateinit var btnAgregarConcesionario: Button
    private var selectedConcesionario: Concesionario? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concesionario_list)

        controlador = Controlador(this)
        listViewConcesionarios = findViewById(R.id.listViewConcesionarios)
        btnAgregarConcesionario = findViewById(R.id.btnAgregarConcesionario)

        btnAgregarConcesionario.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarConcesionario::class.java)
            startActivity(intent)
        }

        registerForContextMenu(listViewConcesionarios)
        actualizarLista()
    }

    private fun actualizarLista() {
        val concesionarios = controlador.listarconcesionario()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, concesionarios.map { it.nombre })
        listViewConcesionarios.adapter = adapter

        listViewConcesionarios.setOnItemClickListener { _, _, position, _ ->
            selectedConcesionario = controlador.listarconcesionario()[position]
            listViewConcesionarios.showContextMenu()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_concesionario, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuVerautos -> {
                val intent = Intent(this, ActivityautoList::class.java)
                intent.putExtra("concesionarioId", selectedConcesionario?.id)
                startActivity(intent)
            }
            R.id.menuEditarconcesionario -> {
                val intent = Intent(this, ActivityAgregarEditarConcesionario::class.java)
                intent.putExtra("concesionarioId", selectedConcesionario?.id)
                startActivity(intent)
            }
            R.id.menuEliminarconcesionario -> {
                if (selectedConcesionario != null) {
                    controlador.eliminarconcesionario(selectedConcesionario!!.id)
                    Toast.makeText(this, "Concesionario eliminado", Toast.LENGTH_SHORT).show()
                    actualizarLista()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}
