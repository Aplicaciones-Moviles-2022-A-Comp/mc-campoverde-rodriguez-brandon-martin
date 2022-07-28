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
import androidx.appcompat.app.AlertDialog

//Examen Campoverde Brandon
class MainActivity : AppCompatActivity() {

   var arreglo:ArrayList<SistemasOperativos> =ArrayList<SistemasOperativos>()
    var idItemSeleccionado = 0
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }
    var idSOGlobal=0
    var nombreSOGlobal=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inicializar base
        BBaseDatosMemoria.TablaSO = ESqliteHelperSO(this)
        arreglo= BBaseDatosMemoria.TablaSO!!.obtenerSO()

        val listView = findViewById<ListView>(R.id.lv_SO)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            //BBaseDatosMemoria.TablaSO!!.obtenerSO()
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
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreSO", nombreSOGlobal)
        intentExplicito.putExtra("idSO",idSOGlobal)
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
                val listView = findViewById<ListView>(R.id.lv_SO)
                var itemselect = listView.getItemAtPosition(idItemSeleccionado)
                nombreSOGlobal= getNombreSO(itemselect as SistemasOperativos)
                idSOGlobal = getIDSO(itemselect as SistemasOperativos).toInt()
                abrirActividadParametros(AniadirProgramas::class.java)
                return true
            }
            R.id.mi_editar_SO->{
                val listView = findViewById<ListView>(R.id.lv_SO)
                var itemselect = listView.getItemAtPosition(idItemSeleccionado)
                nombreSOGlobal= getNombreSO(itemselect as SistemasOperativos)
                idSOGlobal = getIDSO(itemselect as SistemasOperativos).toInt()
                abrirActividadParametros(EditarNombreSO::class.java)

                return true
            }
            R.id.mi_eliminar_SO->{
                val listView = findViewById<ListView>(R.id.lv_SO)
                var itemselect = listView.getItemAtPosition(idItemSeleccionado)
                nombreSOGlobal= getNombreSO(itemselect as SistemasOperativos)
                BBaseDatosMemoria.TablaSO!!.eliminarSO(nombreSOGlobal)
                arreglo= BBaseDatosMemoria.TablaSO!!.obtenerSO()

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
        arreglo= BBaseDatosMemoria.TablaSO!!.obtenerSO()
       /*
        if(arreglo == null){
            ArrayList<SistemasOperativos>()
        }*/
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }
    fun getNombreSO(so: SistemasOperativos): String {
        return "" + so.nombre
    }
    fun getIDSO(so: SistemasOperativos): String {
        return "" + so.id
    }

}