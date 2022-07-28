package com.example.examen_campoverde_brandon

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperSO (contexto: Context?): SQLiteOpenHelper(
    contexto,
    "so",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptsqlCrearTablaSO= """
            CREATE TABLE so(
            id_so INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreso VARCHAR(50)
            )
        """.trimIndent()
        val scriptsqlCrearTablaProgrma= """
            CREATE TABLE programas(
            id_prog INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreprog VARCHAR(50),
            id_so INTEGER
            )
        """.trimIndent()
        db?.execSQL(scriptsqlCrearTablaProgrma)
        db?.execSQL(scriptsqlCrearTablaSO)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearSO(
        nombre:String
    ):Boolean{
        //this.readableDatabase
        //this.writableDatabase
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreso",nombre)
        val resultadoGuardar = basedatosEscritura.insert(
            "so",
            null,
            valoresAGuardar
        )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() ==-1) false else true
    }


    fun obtenerSO(): ArrayList<SistemasOperativos> {
        var sistemasoperativos = ArrayList<SistemasOperativos>()
        var sistemasoperativosEncontrados = SistemasOperativos(0, "")
        val baseDatosLectura = readableDatabase
        val scriptConsultarFacultad = "SELECT * FROM so"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarFacultad,
            null
        )

        if (resultadoConsultaLectura != null && resultadoConsultaLectura.count != 0) {
            resultadoConsultaLectura.moveToFirst()
            do {
                val cod = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE

                if (cod != null) {
                    sistemasoperativosEncontrados.id = cod
                    sistemasoperativosEncontrados.nombre = nombre
                }
                sistemasoperativos.add(sistemasoperativosEncontrados)
                sistemasoperativosEncontrados = SistemasOperativos(0, "")

            } while (resultadoConsultaLectura.moveToNext())

        }else{
            sistemasoperativos = ArrayList<SistemasOperativos>()
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sistemasoperativos
    }

    fun eliminarSO(nombre: String): Boolean{
        //        val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        // "SELECT * FROM USUARIO WHERE ID = ?"
        // arrayOf(
        //    id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "so",
                "nombreso=?",
                arrayOf(
                    nombre.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarSO(
        nombre: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombreso", nombre)
        val resultadoActualizacion = conexionEscritura
            .update(
                "so", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true

    }

    fun crearPrograma(
        nombre:String,
        id_so:Int
    ):Boolean{
        //this.readableDatabase
        //this.writableDatabase
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreprog",nombre)
        valoresAGuardar.put("id_so",id_so)
        val resultadoGuardar = basedatosEscritura.insert(
            "programas",
            null,
            valoresAGuardar
        )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() ==-1) false else true
    }
    fun obtenerProg(idSO:Int): ArrayList<Programas> {
        var programas = ArrayList<Programas>()
        var programasEncontrados = Programas(0, "", 0)
        val baseDatosLectura = readableDatabase
        val scriptConsultarFacultad = "SELECT * FROM programas WHERE id_so=${idSO}"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarFacultad,
            null
        )

        if (resultadoConsultaLectura != null && resultadoConsultaLectura.count != 0) {
            resultadoConsultaLectura.moveToFirst()
            do {
                val id = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val id_so = resultadoConsultaLectura.getInt(2)
                if (id != null) {
                    programasEncontrados.id = id
                    programasEncontrados.nombreprog = nombre
                    programasEncontrados.id_so=id_so
                }
                programas.add(programasEncontrados)
                programasEncontrados = Programas(0, "",0)

            } while (resultadoConsultaLectura.moveToNext())

        }else{
            programas = ArrayList<Programas>()
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return programas
    }

    fun eliminarPrograma(nombre: String): Boolean{
        val conexionEscritura = writableDatabase

        val resultadoEliminacion = conexionEscritura
            .delete(
                "programas",
                "nombreprog=?",
                arrayOf(
                    nombre.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarProg(
        nombre: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombreprog", nombre)
        val resultadoActualizacion = conexionEscritura
            .update(
                "programas", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id_prog=?", // Clausula Where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true

    }

}