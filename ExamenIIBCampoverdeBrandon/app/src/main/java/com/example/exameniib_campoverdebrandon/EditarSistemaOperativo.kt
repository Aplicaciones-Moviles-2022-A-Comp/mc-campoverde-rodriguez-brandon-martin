package com.example.exameniib_campoverdebrandon

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class EditarSistemaOperativo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_sistema_operativo)
        val nombreSO = intent.getStringExtra("nombreSO")
        val idSO = intent.getLongExtra("idSO",0)
        val textViewNombre = findViewById<TextView>(R.id.tv_nombtr_so_actual)
        textViewNombre.setText(nombreSO)
        val btnSalirEditar = findViewById<Button>(R.id.btn_salir_editar_so)
        btnSalirEditar.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        val etNuevoNombreSO= findViewById<EditText>(R.id.et_nuevo_nombre_so)
        val btnEditarNombreSO = findViewById<Button>(R.id.btn_editar_nombre_so)
        btnEditarNombreSO.setOnClickListener {
            editarNombreSO(etNuevoNombreSO.text.toString(),idSO)
            etNuevoNombreSO.setText("")
            irActividad(MainActivity::class.java)
        }
    }

    fun editarNombreSO(nuevoNombre:String,idSO:Long){
        val db = Firebase.firestore
        val so = db.collection("exameniib")
        val datosSO = hashMapOf(
            "idSO" to idSO,
            "nombre" to nuevoNombre
        )
        so.document(idSO.toString()).set(datosSO)
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}