package com.example.examen_campoverde_brandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
//Examen Campoverde Brandon
class MainActivity : AppCompatActivity() {
    val arreglo : ArrayList<SistemasOperativos> = BBaseDatosMemoria.ListaProgramas
    var idItemSeleccionado = 0
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.lv_SO)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonIrAniadirSO = findViewById<Button>(R.id.btn_ir_aniadirSO)
        botonIrAniadirSO.setOnClickListener{
            irActividad(aniadirSO::class.java)
        }

        registerForContextMenu(listView)
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadParametros(
        clase:Class<*>,
        id:Int
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreSO", arreglo["${idItemSeleccionado}".toString().toInt()].nombre)
        intentExplicito.putExtra("idSO",id)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sistemaoperativo,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_verprogramas->{
                abrirActividadParametros(AniadirProgramas::class.java,"${idItemSeleccionado}".toString().toInt()+1)
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_editar_SO->{
                abrirActividadParametros(EditarNombreSO::class.java,"${idItemSeleccionado}".toString().toInt())
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar_SO->{
                arreglo.removeAt("${idItemSeleccionado}".toInt())
                "${idItemSeleccionado}"
                val listView = findViewById<ListView>(R.id.lv_SO)
                val adaptador = ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Como se va a ver el xml
                    arreglo
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val listView = findViewById<ListView>(R.id.lv_SO)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }
}