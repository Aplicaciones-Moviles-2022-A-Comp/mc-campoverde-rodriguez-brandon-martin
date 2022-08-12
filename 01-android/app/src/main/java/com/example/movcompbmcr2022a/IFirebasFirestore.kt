package com.example.movcompbmcr2022a

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class IFirebasFirestore : AppCompatActivity() {
    //var arreglo: ArrayList<ICitiesDTO> = BBaseDatosMemoria.arregloFirebase
      var arreglo:ArrayList<ICitiesDTO> = ArrayList<ICitiesDTO>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebas_firestore)
        val listView = findViewById<ListView>(R.id.lv_firestoreN)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        val BotonDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        BotonDatosPrueba.setOnClickListener {
            crearDatosPrueba()
        }
        val db = Firebase.firestore
        val citiesRefUnico = db
            .collection("cities")

        val BotonOrderBy = findViewById<Button>(R.id.btn_fs_ordenby)
        BotonOrderBy.setOnClickListener {
            citiesRefUnico
                .orderBy("population")
                .get()
                .addOnSuccessListener {
                    arreglo = ArrayList<ICitiesDTO>()
                    for (ciudad in it) {
                        aniadiraArreglo(arreglo, ciudad)
                    }
                   // val listView = findViewById<ListView>(R.id.lv_firestoreN)
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                }
        }

        val ObtenerDocumento = findViewById<Button>(R.id.btn_fs_odoc)
        ObtenerDocumento.setOnClickListener {
            arreglo= ArrayList<ICitiesDTO>()
            citiesRefUnico
                .document("BJ")
                .get().addOnSuccessListener {

                    arreglo.add(
                        ICitiesDTO(it.get("name") as String?
                            ,it.get("state") as String?
                            ,it.get("country") as String?
                            ,it.get("capital") as Boolean?
                            ,it.get("population") as Long?
                            ,it.get("regions") as ArrayList<String> )
                    )

                   // abrirDialogo(arreglo.toString())
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                   // listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                }

        }

        val BotonIndiceCompuesto = findViewById<Button>(R.id.btn_fs_odoc2)
        BotonIndiceCompuesto.setOnClickListener {
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
            citiesRefUnico
                .whereEqualTo("capital",false)
                .whereLessThanOrEqualTo("population",4000000)
                .orderBy("population", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    for(ciudad in it) {
                        aniadiraArreglo(arreglo,ciudad)
                    }
                    val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arreglo
                )
                    listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                }
        }

        val btnCrear = findViewById<Button>(R.id.btn_fs_crear)
        btnCrear.setOnClickListener {
            val db = Firebase.firestore
            val referenciaEjemploEstudiante = db.collection("ejemplo")
                .document("123456789")
                .collection("estudiante")

            val identificador = Date().time

            val data1 = hashMapOf(
                "nombre" to "Brandon",
                "graduado" to false,
                "promedio" to "0.00",
                "direccion" to hashMapOf(
                    "direccion" to "Zabala",
                    "numCalle" to 4321
                ),
                "materias" to listOf("Web","Moviles")
            )
            //Con identificador quemado
            referenciaEjemploEstudiante
                .document("123456789")
                .set(data1)
                .addOnCompleteListener{}
                .addOnFailureListener{}
            //Con identificador generado
            referenciaEjemploEstudiante
                .document(identificador.toString())
                .set(data1)
                .addOnCompleteListener{}
                .addOnFailureListener{}
            //Sin Indetificador
            referenciaEjemploEstudiante
                .add(data1)
                .addOnCompleteListener{}
                .addOnFailureListener{}
        }



        val btnEliminar = findViewById<Button>(R.id.btn_fs_eliminar)
        btnEliminar.setOnClickListener {
            val db = Firebase.firestore
            val referenciaEstudiante = db.collection("ejemplo")
                .document("123456789")
                .collection("estudiante")

            referenciaEstudiante.document("123456789")
                .delete()
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
        }
    }

    fun aniadiraArreglo(
        arregloNuevo: ArrayList<ICitiesDTO>,
        ciudad: QueryDocumentSnapshot
    ) {
        arregloNuevo.add(
            ICitiesDTO(
                ciudad.get("name") as String?,
                ciudad.get("state") as String?,
                ciudad.get("country") as String?,
                ciudad.get("capital") as Boolean?,
                ciudad.get("population") as Long?,
                ciudad.get("regions") as ArrayList<String>
            )
        )
    }

    fun crearDatosPrueba() {
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)
    }

    override fun onResume() {
        super.onResume()
        val listView = findViewById<ListView>(R.id.lv_firestoreN)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        //abrirDialogo(arreglo.toString())
    }

    fun abrirDialogo(asd:String?){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton(
            "Aceptar "+asd,null

        )

        val dialog = builder.create()
        dialog.show()
    }

    fun limpiarArreglo(){
        arreglo.clear()
    }
    //<

    //<=

    //==

    //limit, limita el numero de registros

}
