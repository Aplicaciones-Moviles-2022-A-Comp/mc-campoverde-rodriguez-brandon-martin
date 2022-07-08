package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class BCRUDEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bcrudentrenador)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
            EBaseDEDatos.TablaEntrenador!!.crearEmtrador(
                nombre.text.toString(),
                descripcion.text.toString()
            )

        }

        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDEDatos.TablaEntrenador!!.consultarUsuarioPorId(
                id.text.toString().toInt()
            )
            id.setText(entrenador.id.toString())
            nombre.setText(entrenador.nombre)
            descripcion.setText(entrenador.descripcion.toString())
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener{
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val id = findViewById<EditText>(R.id.input_id)
            EBaseDEDatos.TablaEntrenador!!.actualizarUsuarioFormulario(
                nombre.text.toString(),
                descripcion.text.toString(),
                id.text.toString().toInt()
            )

        }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            EBaseDEDatos.TablaEntrenador!!.eliminarUsuarioFormulario(
                id.text.toString().toInt()
            )
        }

    }
}