package com.example.exameniib_campoverdebrandon

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerProgramas : AppCompatActivity() {

    var idSOGlobal:Long = 0
    var idItemSeleccionado = 0
    var idProgramaGlobal:Long = 0
    var nombreProgGlobal = ""
    var arregloProg:ArrayList<Programas> = ArrayList<Programas>()

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
        setContentView(R.layout.activity_ver_programas)
        val idSO = intent.getLongExtra("idSO",0)
        idSOGlobal=idSO
        val listProgramas = findViewById<ListView>(R.id.lv_programas)
        cargarProgramasInicio()
        val btnSalirVerProgramas = findViewById<Button>(R.id.btn_salir_de_ver_programas)
        btnSalirVerProgramas.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        val btnIrCrearPrograma = findViewById<Button>(R.id.btn_ir_a_crear_programas)
        btnIrCrearPrograma.setOnClickListener {
            abrirActividadParametros(CrearProgramas::class.java)
        }
        registerForContextMenu(listProgramas)
    }

    fun cargarProgramasInicio(){
        val db = Firebase.firestore
        val progRefUnico = db.collection("exameniib")
            .document("${idSOGlobal}")
            .collection("Programas")
        progRefUnico.get()
            .addOnSuccessListener {
                arregloProg = ArrayList<Programas>()
                for (prog in it) {
                    aniadirPrograma(arregloProg, prog)
                }
                actualizarListView()
            }

    }


    fun actualizarListView(){
        val db = Firebase.firestore
        val progRefUnico = db.collection("exameniib")
            .document("${idSOGlobal}")
            .collection("Programas")
        progRefUnico
            .get()
            .addOnSuccessListener {
                arregloProg = ArrayList<Programas>()
                for (programa in it) {
                    aniadirPrograma(arregloProg, programa)
                }
                actualizarVistaListView()
            }

    }

    fun actualizarVistaListView(){
        val listProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloProg
        )
        listProgramas.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    fun aniadirPrograma(
        arregloNuevo: ArrayList<Programas>,
        so: QueryDocumentSnapshot
    ) {
        arregloNuevo.add(
            Programas(
                so.get("idProg") as Long?,
                so.get("nombreProg") as String?
            )
        )
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
        intentExplicito.putExtra("idProg",idProgramaGlobal)
        intentExplicito.putExtra("idSO",idSOGlobal)
        intentExplicito.putExtra("nombrePrograma",nombreProgGlobal)
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
        inflater.inflate(R.menu.menu_programas,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.it_editar_prog->{
                "${idItemSeleccionado}"
                val listView = findViewById<ListView>(R.id.lv_programas)
                val itemSelecionado = listView.getItemAtPosition(idItemSeleccionado)
                nombreProgGlobal = getNombrePrograma(itemSelecionado as Programas)
                idProgramaGlobal = getIDPrograma(itemSelecionado as Programas).toLong()
                abrirActividadParametros(EditarPrograma::class.java)
                return true
            }
            R.id.it_eliminar_prog->{
                "${idItemSeleccionado}"
                val listView = findViewById<ListView>(R.id.lv_programas)
                val itemSelecionado = listView.getItemAtPosition(idItemSeleccionado)
                val idProgactual = getIDPrograma(itemSelecionado as Programas)
                eliminarPrograma(idProgactual)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun getNombrePrograma(programa:Programas):String{
        return programa.nombreProg.toString()
    }

    fun getIDPrograma(programa: Programas):String{
        return programa.idProg.toString()
    }

    fun eliminarPrograma(nombreso:String){
        val db = Firebase.firestore
        val progRefUnico = db.collection("exameniib")
            .document("${idSOGlobal}")
            .collection("Programas")
        progRefUnico.document(nombreso).delete()
        actualizarListView()
    }

}