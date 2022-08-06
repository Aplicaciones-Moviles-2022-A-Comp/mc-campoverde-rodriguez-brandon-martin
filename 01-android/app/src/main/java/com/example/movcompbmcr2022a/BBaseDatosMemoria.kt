package com.example.movcompbmcr2022a

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrador = arrayListOf<BEntrenador>()
        val arregloFirebase = arrayListOf<ICitiesDTO>()
        init {
            arregloBEntrador.add(
                BEntrenador(1,"Brandon", "No hay")
            )
            arregloBEntrador.add(
                BEntrenador(2,"Martin", "No hay2")
            )
            arregloBEntrador.add(
                BEntrenador(3,"Juan", "No hay3")
            )
        }
    }
}