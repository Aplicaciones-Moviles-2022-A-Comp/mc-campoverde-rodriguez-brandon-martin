package com.example.movcompbmcr2022a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import io.sentry.Sentry
import io.sentry.SentryLevel

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val CODIGO_RESPUESTA_INTENT_IMPLICITO = 402

    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                Log.i("intent-epn","${data?.getStringExtra("nombreModificado")}")
            }
        }
    }

    val contenidoIntentIMplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val uri: Uri = result.data!!.data!!
                val cursor = contentResolver.query(
                    uri,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                cursor?.moveToFirst()
                val indiceTelefono = cursor?.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val telefono = cursor?.getString(
                    indiceTelefono!!
                )
                cursor?.close()
                Log.i("intent-epn","Telefono ${telefono}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Base de datos sqlite
        EBaseDEDatos.TablaEntrenador = ESqliteHelperEntrenador(this)

        Sentry.captureMessage("testing SDK setup", SentryLevel.INFO);

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.
                setOnClickListener{
                    irActividad(ACicloVida::class.java)
                }
       // val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        val botonActividad09 = findViewById<Button>(R.id.ir_a_09)
        botonActividad09.setOnClickListener{
            irActividad(AClase09::class.java)
        }
        val botonListView = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
        val botonIntenExplicito = findViewById<Button>(R.id.btn_intent)
        botonIntenExplicito.setOnClickListener{
            abrirActividadParametros(CIntentExplicitoParametros::class.java)
        }
        val botonIrCRUDEntrenador = findViewById<Button>(R.id.btn_ir_crud_entrenador)
        botonIrCRUDEntrenador.setOnClickListener{
            abrirActividadParametros(BCRUDEntrenador::class.java)
        }
        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            contenidoIntentIMplicito.launch(intentConRespuesta)
            startActivityForResult(intentConRespuesta, CODIGO_RESPUESTA_INTENT_IMPLICITO)
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombre","Brandon")
        intentExplicito.putExtra("apellido","Campoverde")
        intentExplicito.putExtra("edad",21)

        intentExplicito.putExtra("entrenador_Principal",
            BEntrenador(10,"Brandon","De Zabala"))

        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }
}