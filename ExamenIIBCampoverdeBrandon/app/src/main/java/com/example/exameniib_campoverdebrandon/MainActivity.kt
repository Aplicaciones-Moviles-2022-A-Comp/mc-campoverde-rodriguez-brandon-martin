package com.example.exameniib_campoverdebrandon

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity() {

    var idItemSeleccionado=0
    var nombreSOGlobal=""
    var idSOGlobal:Long=0
    var arreglo:ArrayList<SistemasOperativos> = ArrayList<SistemasOperativos>()

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
        setContentView(R.layout.activity_main)
        val listSistemas = findViewById<ListView>(R.id.lv_sistemas_operativos)
        cargarSOInicio()
        val btnAniadirSO = findViewById<Button>(R.id.btn_aniadir_so)

        btnAniadirSO.setOnClickListener {
            irActividad(CrearSistemaOperativo::class.java)
        }
        registerForContextMenu(listSistemas)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sistemas_operativos,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.it_ver_programas->{
                val listView = findViewById<ListView>(R.id.lv_sistemas_operativos)
                val itemSelecionado = listView.getItemAtPosition(idItemSeleccionado)
                idSOGlobal = getIDSO(itemSelecionado as SistemasOperativos).toLong()
                abrirActividadParametros(VerProgramas::class.java)
                "${idItemSeleccionado}"
                return true
            }
            R.id.it_editar_sistemas_operativos->{
                "${idItemSeleccionado}"
                val listView = findViewById<ListView>(R.id.lv_sistemas_operativos)
                val itemSelecionado = listView.getItemAtPosition(idItemSeleccionado)
                nombreSOGlobal = getNombreSO(itemSelecionado as SistemasOperativos)
                idSOGlobal = getIDSO(itemSelecionado as SistemasOperativos).toLong()
                abrirActividadParametros(EditarSistemaOperativo::class.java)
                return true
            }
            R.id.it_eliminar_sistemas_operativos->{
                "${idItemSeleccionado}"
                val listView = findViewById<ListView>(R.id.lv_sistemas_operativos)
                val itemSelecionado = listView.getItemAtPosition(idItemSeleccionado)
                val idSOactual = getIDSO(itemSelecionado as SistemasOperativos)
                eliminarSO(idSOactual)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cargarSOInicio(){
        val db = Firebase.firestore
        val soRefUnico = db
            .collection("exameniib")
        soRefUnico
            .get()
            .addOnSuccessListener {
                arreglo = ArrayList<SistemasOperativos>()
                for (so in it) {
                    aniadirSO(arreglo, so)
                }
                actualizarListView()
    }

    }

    fun actualizarListView(){
        val db = Firebase.firestore
        val soRefUnico = db
            .collection("exameniib")
        soRefUnico
            .get()
            .addOnSuccessListener {
                arreglo = ArrayList<SistemasOperativos>()
                for (so in it) {
                    aniadirSO(arreglo, so)
                }
                actualizarVistaListView()
            }

    }

    fun actualizarVistaListView(){
        val listSistemas = findViewById<ListView>(R.id.lv_sistemas_operativos)
      //  cargarSO()
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listSistemas.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    fun aniadirSO(
        arregloNuevo: ArrayList<SistemasOperativos>,
        so: QueryDocumentSnapshot
    ) {
        arregloNuevo.add(
            SistemasOperativos(
                so.get("idSO") as Long?,
                so.get("nombre") as String?
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
        intentExplicito.putExtra("nombreSO",nombreSOGlobal)
        intentExplicito.putExtra("idSO",idSOGlobal)
        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)//Forma deprecada
    }

    fun eliminarSO(nombreso:String){
        val db = Firebase.firestore
        val so = db.collection("exameniib")
        so.document(nombreso).delete()
        actualizarListView()
    }

    fun getNombreSO(sistemas:SistemasOperativos):String{
        return sistemas.nombre.toString()
    }
    fun getIDSO(sistemas:SistemasOperativos):String{
        return sistemas.id.toString()
    }

    override fun onResume() {
        super.onResume()
        actualizarListView()
    }

}