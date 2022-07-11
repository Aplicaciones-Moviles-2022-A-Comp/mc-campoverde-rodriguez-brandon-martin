package com.example.examen_campoverde_brandon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EditarNombreSO : AppCompatActivity() {
    val arreglo : ArrayList<SistemasOperativos> = BBaseDatosMemoria.ListaProgramas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_nombre_so)
        val nombre = intent.getStringExtra("nombreSO")
        val idSOE = intent.getIntExtra("idSO", 0)
        val nombreActual = findViewById<TextView>(R.id.tv_nombre_actual_so)
        nombreActual.setText(nombre)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_editar_nombre_so)
        val btnActualizarNombreSO = findViewById<Button>(R.id.btn_actualizar_nombre_so)
        val inputNombreSO = findViewById<EditText>(R.id.input_actualizar_nombre_so)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        btnCancelar.setOnClickListener{
            inputNombreSO.setText("")
            irActividad(MainActivity::class.java)
        }
        btnActualizarNombreSO.setOnClickListener{
            arreglo[idSOE].nombre=inputNombreSO.text.toString()
            adaptador.notifyDataSetChanged()
            irActividad(MainActivity::class.java)
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}