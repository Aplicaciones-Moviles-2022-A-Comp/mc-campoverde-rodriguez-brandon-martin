package com.example.examen_campoverde_brandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class EditarNombrePrograma : AppCompatActivity() {


    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }
   var idSOGlobal=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_nombre_programa)
        val nombreprograma = intent.getStringExtra("nombrePro")
        val idpro = intent.getIntExtra("idProg",0)
        val idsoact = intent.getIntExtra("idSOAct",0)
        idSOGlobal=idsoact
        val nombreprogramaactual = findViewById<TextView>(R.id.tv_nombre_programa_actual)
        nombreprogramaactual.setText(nombreprograma)
        val btn_salir_Programa = findViewById<Button>(R.id.btn_salir_a_programas)
        btn_salir_Programa.setOnClickListener{
            abrirActividadParametros(AniadirProgramas::class.java)
        }
        val inputNuevoNombre = findViewById<EditText>(R.id.input_nuevo_nombre_programa)
        val btn_actualizar_Programa = findViewById<Button>(R.id.btn_actualizar_nombre_programa)
        btn_actualizar_Programa.setOnClickListener{
            BBaseDatosMemoria.TablaSO!!.actualizarProg(inputNuevoNombre.text.toString(),idpro)
            abrirActividadParametros(AniadirProgramas::class.java)
        }
    }

    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar",
            null
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