package com.example.examen_campoverde_brandon

class SistemasOperativos(
    var id:Int?,
    var nombre:String?,
   // var programas: ArrayList<String> =arrayListOf<String>()
) {
    override fun toString(): String {
        return "${nombre}"
    }
}