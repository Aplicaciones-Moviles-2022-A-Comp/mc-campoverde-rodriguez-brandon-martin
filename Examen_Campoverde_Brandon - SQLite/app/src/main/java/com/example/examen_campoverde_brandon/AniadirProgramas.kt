package com.example.examen_campoverde_brandon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog


class AniadirProgramas : AppCompatActivity() {
    var idItemSeleccionadoPrograma = 0
    var id=0;
    var arregloprog = ArrayList<Programas>()
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            if (result.data != null){

            }
        }
    }
    var nombreProgGlobal=""
    var idProgramaGlobal=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_programas)
        val idcreado = intent.getIntExtra("idSO",0)
        id=idcreado

        arregloprog= BBaseDatosMemoria.TablaSO!!.obtenerProg(id)
        val listView = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arregloprog
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btn_saliraMain = findViewById<Button>(R.id.btn_salir_aniadir_prog)
        btn_saliraMain.setOnClickListener{
            irActividad(MainActivity::class.java)
        }

        val btn_aniadirProg = findViewById<Button>(R.id.btn_aniadir_programa)
        btn_aniadirProg.setOnClickListener{
            anadirPrograma()
            val cajaNombrePrograma=findViewById<EditText>(R.id.input_programas)
            cajaNombrePrograma.setText("")
        }
        registerForContextMenu(listView)
    }


    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun anadirPrograma(
    ){
        val cajaNombrePrograma=findViewById<EditText>(R.id.input_programas)
        val nombrePrograma=cajaNombrePrograma.text.toString()
        BBaseDatosMemoria.TablaSO!!.crearPrograma(nombrePrograma,id)
        val listView = findViewById<ListView>(R.id.lv_programas)
        arregloprog= BBaseDatosMemoria.TablaSO!!.obtenerProg(id)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arregloprog
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuprogramas,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionadoPrograma=id
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_Pro->{
                val listView = findViewById<ListView>(R.id.lv_programas)
                var itemselect = listView.getItemAtPosition(idItemSeleccionadoPrograma)
                idProgramaGlobal=getIDProg(itemselect as Programas).toInt()
                abrirActividadParametros(EditarNombrePrograma::class.java)
                return true
            }
            R.id.mi_eliminar_Pro->{
                val listView = findViewById<ListView>(R.id.lv_programas)
                var itemselect = listView.getItemAtPosition(idItemSeleccionadoPrograma)
                nombreProgGlobal= getNombreProg(itemselect as Programas)
                BBaseDatosMemoria.TablaSO!!.eliminarPrograma(nombreProgGlobal)
                arregloprog= BBaseDatosMemoria.TablaSO!!.obtenerProg(id)
                val adaptador = ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Como se va a ver el xml
                    arregloprog
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun abrirActividadParametros(
        clase:Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombrePro",nombreProgGlobal)
        intentExplicito.putExtra("idProg",idProgramaGlobal)
        intentExplicito.putExtra("idSOAct",id)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

    fun getNombreProg(programa: Programas): String {
        return "" + programa.nombreprog
    }
    fun getIDProg(programa: Programas): String {
        return "" + programa.id
    }

    override fun onResume() {
        super.onResume()
        val listView = findViewById<ListView>(R.id.lv_programas)
        arregloprog= BBaseDatosMemoria.TablaSO!!.obtenerProg(id)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arregloprog
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }
}
