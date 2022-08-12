package com.example.exameniib_campoverdebrandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarPrograma : AppCompatActivity() {
    var idProgramaGlobal:Long = 0
    var idSOGlobal:Long = 0

    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_programa)
        val idSOEditar = intent.getLongExtra("idSO",0)
        val idProgEditar = intent.getLongExtra("idProg",0)
        idProgramaGlobal = idProgEditar
        idSOGlobal = idSOEditar
        val nombreProgEditar = intent.getStringExtra("nombrePrograma")
        val btnSalirEditarPrograma = findViewById<Button>(R.id.btn_salir_editar_programa)
        val btnEditarPrograma = findViewById<Button>(R.id.btn_editar_programa)
        val textViewProgrma = findViewById<TextView>(R.id.tv_nombtr_programa)
        val editTextNombrePrograma = findViewById<EditText>(R.id.et_nuevo_nombre_programa)
        btnSalirEditarPrograma.setOnClickListener {
            abrirActividadParametros(VerProgramas::class.java)
        }
        btnEditarPrograma.setOnClickListener {
            editarNombrePrograma(editTextNombrePrograma.text.toString(),idProgramaGlobal)
            abrirActividadParametros(VerProgramas::class.java)
        }
        textViewProgrma.setText(nombreProgEditar)

    }

    fun editarNombrePrograma(nuevoNombre:String,idProgarma:Long){
        val db = Firebase.firestore
        val progRefUnico = db.collection("exameniib")
            .document("${idSOGlobal}")
            .collection("Programas")
        val datosPrograma = hashMapOf(
            "idProg" to idProgarma,
            "nombreProg" to nuevoNombre
        )
        progRefUnico.document(idProgarma.toString()).set(datosPrograma)
    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idProg",idProgramaGlobal)
        intentExplicito.putExtra("idSO",idSOGlobal)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }
}