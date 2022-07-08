package com.example.movcompbmcr2022a

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
   // val arreglo: ArrayList<Int> = arrayListOf(1,2,3,4,5)
    val arreglo : ArrayList<BEntrenador> = BBaseDatosMemoria.arregloBEntrador
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Como se va a ver el xml
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirListVies = findViewById<Button>(
            R.id.brt_anadir_list_view
        )
        botonAnadirListVies.setOnClickListener {
            anadirNumero(adaptador)
        }
        registerForContextMenu(listView)
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                dialog, which ->
                arreglo.removeAt(idItemSeleccionado)
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val opciones = resources.getStringArray(
            R.array.String_array_opciones_dialoho
        )
        val seleccionPrevia = booleanArrayOf(
            true,false
        )
        builder.setMultiChoiceItems(
            opciones,seleccionPrevia,{
            dialog,
                wich,
                isChecked->
            "Dio clcic en el item ${wich}"
        })

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar->{
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar->{
                abrirDialogo()
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun anadirNumero(
      //  adaptador: ArrayAdapter<Int>
    adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(0,"Brandon","Campoverde")
        )
        adaptador.notifyDataSetChanged()
    }
}