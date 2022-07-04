package com.example.movcompbmcr2022a

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)

        val entranadorPrinc = intent.getParcelableExtra<BEntrenador>("entrenador_Principal")

        var datosunitos = nombre+" "+apellido+" "+edad+"\n"+entranadorPrinc
        abrirDialogo(datosunitos)
        val botonRespuesta = findViewById<Button>(R.id.btn_devolver_respuesta)
        botonRespuesta.setOnClickListener{
            devolverRespuesta()
        }
    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado","Martin")
        intentDevolverParametros.putExtra("edadModificado",22)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
    fun abrirDialogo(mensaje:String?){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mostrar Datos")
        builder.setPositiveButton(
            mensaje,
            DialogInterface.OnClickListener{
                    dialog, which ->
            }
        )

        val dialog = builder.create()
        dialog.show()
    }
}