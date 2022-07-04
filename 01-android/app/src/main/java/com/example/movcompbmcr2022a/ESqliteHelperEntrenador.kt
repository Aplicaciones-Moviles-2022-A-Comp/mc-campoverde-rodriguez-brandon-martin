package com.example.movcompbmcr2022a

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class ESqliteHelperEntrenador(contexto:Context?): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptsqlCrearTablaUsuario= """
            CREATE TABLE entrenador(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            descripcion VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(scriptsqlCrearTablaUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearEmtrador(
      nombre:String,
      descripcion:String
    ):Boolean{
        //this.readableDatabase
        //this.writableDatabase
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)
        val resultadoGuardar = basedatosEscritura.insert(
            "entrador",
            null,
            valoresAGuardar
        )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() ==-1) false else true
    }

}