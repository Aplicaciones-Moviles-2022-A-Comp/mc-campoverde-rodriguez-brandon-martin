package com.example.examen_campoverde_brandon

class Programas(
    var id:Int?,
    var nombreprog:String?,
    var id_so:Int?
) {
    override fun toString(): String {
        return "${nombreprog}"
    }
}