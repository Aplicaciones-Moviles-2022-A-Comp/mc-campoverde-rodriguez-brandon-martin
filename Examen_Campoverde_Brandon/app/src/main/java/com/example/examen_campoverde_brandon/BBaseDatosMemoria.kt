package com.example.examen_campoverde_brandon

class BBaseDatosMemoria {
    companion object{
        var ListaProgramas= arrayListOf<SistemasOperativos>()
    init {
        ListaProgramas.add(SistemasOperativos("Ubuntu", arrayListOf("Libre Office","Jhon")))
    }
    }
}