package com.example.movcompbmcr2022a

class ICitiesDTO(
    var name:String?,
    var state:String?,
    var country:String?,
    var capital:Boolean?,
    var population:Long?,
    var regions: ArrayList<String>?
) {
    override fun toString(): String {
        return "${name} - ${country}"
    }
}