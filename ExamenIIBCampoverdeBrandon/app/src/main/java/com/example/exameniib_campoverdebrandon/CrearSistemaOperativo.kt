package com.example.exameniib_campoverdebrandon

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CrearSistemaOperativo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sistema_operativo)
        val btnAniadirSOFirestore = findViewById<Button>(R.id.btn_aniadir_so_firestore)
        val edittextNombreSO = findViewById<EditText>(R.id.et_NombreSO)
        btnAniadirSOFirestore.setOnClickListener {
            aniadirSOFirestore(edittextNombreSO.text.toString())
            edittextNombreSO.setText("")
            abrirDialogo()
        }
        val btnSalirSOFirestore = findViewById<Button>(R.id.btn_salir_aniadir_so)
        btnSalirSOFirestore.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
    }

    fun aniadirSOFirestore(nombreso:String){
        val db = Firebase.firestore
        val so = db.collection("exameniib")
        val identificador = Date().time
        val datosSO = hashMapOf(
            "idSO" to identificador,
            "nombre" to nombreso
        )
        so.document(identificador.toString()).set(datosSO)

    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregado Exitosamente")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                irActividad(MainActivity::class.java)
            }
        )
        val dialog = builder.create()
        dialog.show()
    }
}