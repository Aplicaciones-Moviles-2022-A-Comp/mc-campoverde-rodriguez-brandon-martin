package com.example.examen_campoverde_brandon

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class aniadirSO : AppCompatActivity() {

    val arreglo : ArrayList<SistemasOperativos> = BBaseDatosMemoria.ListaProgramas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_so)

        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )

        val botonAnadirListSO = findViewById<Button>(
            R.id.btn_crear_so
        )
        botonAnadirListSO.setOnClickListener {
            anadirNumero(adaptador)
            abrirDialogo()
        }
        val botonIrASO = findViewById<Button>(R.id.btn_iraverso)
        botonIrASO.setOnClickListener{
            irActividad(MainActivity::class.java)
        }
    }

    fun anadirNumero(
        adaptador: ArrayAdapter<SistemasOperativos>
    ){
        var cajaNombreSO=findViewById<EditText>(R.id.txt_input_nombreso)
        var nombreSO=cajaNombreSO.text.toString()
        arreglo.add(
            SistemasOperativos(nombreSO)
        )
        adaptador.notifyDataSetChanged()
    }
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Añadido Exitosamente")
        builder.setPositiveButton(
            "Aceptar",
             null
        )

        val dialog = builder.create()
        dialog.show()
    }
    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}