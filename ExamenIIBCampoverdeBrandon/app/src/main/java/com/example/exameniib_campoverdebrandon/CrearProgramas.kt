package com.example.exameniib_campoverdebrandon

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CrearProgramas : AppCompatActivity() {

    var idSOGlobal:Long=0

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
        setContentView(R.layout.activity_crear_programas)
        val idSO = intent.getLongExtra("idSO",0)
        idSOGlobal=idSO
        val btnAniadirProgramaFirestore = findViewById<Button>(R.id.btn_crear_programa)
        val edittextNombrePrograma = findViewById<EditText>(R.id.et_nombre_programa)
        btnAniadirProgramaFirestore.setOnClickListener {
            aniadirProgramaFirestore(edittextNombrePrograma.text.toString())
            edittextNombrePrograma.setText("")
            abrirDialogo()
        }
        val btnSalirCrearPrograma = findViewById<Button>(R.id.btn_salir_crear_programa)
        btnSalirCrearPrograma.setOnClickListener {
            abrirActividadParametros(VerProgramas::class.java)
        }
    }

    fun aniadirProgramaFirestore(nombreprog:String){
        val db = Firebase.firestore
        val progRefUnico = db.collection("exameniib")
            .document("${idSOGlobal}")
            .collection("Programas")
        val identificador = Date().time
        val datosPrograma = hashMapOf(
            "idProg" to identificador,
            "nombreProg" to nombreprog
        )
        progRefUnico.document(identificador.toString()).set(datosPrograma)

    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregado Exitosamente")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                abrirActividadParametros(VerProgramas::class.java)
            }
        )
        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idSO",idSOGlobal)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }
}