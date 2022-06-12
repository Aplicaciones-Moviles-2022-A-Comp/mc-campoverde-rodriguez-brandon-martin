import com.sun.jdi.IntegerType
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import javax.swing.JOptionPane
import java.util.*
import kotlin.collections.ArrayList

//Nombre:Brandon Campoverde

fun main(){
    var opcion = -1
    do {
    try {
        opcion=JOptionPane.showInputDialog(null,"Escoja una opcion" +
                "\n1.-Guardar Programa\n2.-Editar Programa\n3.-Eliminar Programa\n4.-Ver Programas" +
                "\n5.-Guardar Programa en un Archivo de Texto\n6.-Salir","Ménu",-1).toInt()
        when(opcion){
            (1)->{
                var nombrep = JOptionPane.showInputDialog(null,"Ingrese el nombre del Programa",
                "Nombre Programa",-1)
                var arregloaux=ArrayList<String>()
                var opcionso:Int=-1
                while(opcionso!=1){
                    opcionso=JOptionPane.showInputDialog(null,"Ingrese una opción\n0.-Agregar Sistema Operativo"+
                      "\n1.-Continuar" ,"SO",-1).toInt()
                    when(opcionso){
                        (0)->{
                            var nombreso = JOptionPane.showInputDialog(null,"Ingrese el nombre del SO",
                                "Nombre SO",-1)
                            arregloaux.add(nombreso)
                        }
                        (1)->{
                            JOptionPane.showMessageDialog(null,"SO Guardados","Mensaje",1)
                        }
                        else->{
                        JOptionPane.showMessageDialog(null,"Opcion Incorrecta","Error",0)
                        }
                    }
                }
                var anio= JOptionPane.showInputDialog(null,"Ingrese el año","Año salida",-1).toInt()
                var precio = JOptionPane.showInputDialog(null,"Ingrese el precio de licencia","Precio Licencia",-1).toFloat()
                var conjunto= Programas(nombrep,arregloaux,anio,precio)
                listaProgramas.aniadirPrograma(conjunto)
            }
            (2)->{
                var editarop=JOptionPane.showInputDialog(null,"| ID | Nombre Programa | SO | Año | Precio |\n"+listaProgramas.imprimirLista()+"" +
                        "\nIngrese el ID del Programa a editar",
                "Editar",-1,).toInt()
                if (editarop>=0 && editarop<listaProgramas.listaprog.size){
                    var nuevoPrograma:Programas?
                    nuevoPrograma=listaProgramas.obtenerElemento(editarop)
                    var opcionedit=JOptionPane.showInputDialog(null,"| Nombre Programa | SO | Año | Precio |\n"+nuevoPrograma.toString()+"" +
                            "\nSeleccione una opcion" +
                            "\n1.-Editar Nombre Programa\n2.-Editar Nombre SO\n3.-Agregar SO\n4.-Eliminar SO" +
                            "\n5.-Editar Año\n6.-Editar Precio","Modificar",-1).toInt()
                    when(opcionedit){
                        (1)->{
                            var nuevoNombre=JOptionPane.showInputDialog(null,"Ingrese El Nuevo Nombre del Programa",
                            "Nuevo Nombre",-1)
                            if( nuevoNombre!=""){
                            if (nuevoPrograma != null) {
                                nuevoPrograma.nombre = nuevoNombre
                            }
                            }else{
                                JOptionPane.showMessageDialog(null,"Nombre Invalido","Error",0)
                            }
                            listaProgramas.modificarElemento(editarop,nuevoPrograma)
                        }
                        (2)->{
                            var arreglosoaux= nuevoPrograma?.so
                            var salida:String=""
                            arreglosoaux?.forEachIndexed{
                                    indice:Int, valorActual->
                                salida+=("|${indice}|${valorActual}|\n")
                            }
                            var indiceSO=JOptionPane.showInputDialog(null,"|ID|SO|\n"+salida +
                                    "Seleccione el ID del Sistema Operativo a Cambiar","Arreglo SO",-1).toInt()
                            if(indiceSO>=0 && indiceSO< arreglosoaux?.size!!) {
                                var nuevoSO=JOptionPane.showInputDialog(null,"Ingrese el Nuevo Nombre del SO",
                                    "Nuevo SO",-1)
                                arreglosoaux?.set(indiceSO,nuevoSO)
                                if (nuevoPrograma != null && nuevoSO!="") {
                                    nuevoPrograma.so=arreglosoaux
                                    listaProgramas.modificarElemento(editarop,nuevoPrograma)
                                }else{
                                    JOptionPane.showMessageDialog(null,"Nombre de SO invalido","Error",0)
                                }

                            }else{
                                JOptionPane.showMessageDialog(null,"Indice invalido","Error",0)
                            }
                        }
                        (3)->{
                            var arreglosoaux= nuevoPrograma?.so
                            var salida:String=""
                            arreglosoaux?.forEachIndexed{
                                    indice:Int, valorActual->
                                salida+=("|${indice}|${valorActual}|\n")
                            }
                                var nuevoSO=JOptionPane.showInputDialog(null,"Ingrese el Nuevo SO",
                                    "Nuevo SO",-1)
                                arreglosoaux?.add(nuevoSO)
                                if (nuevoPrograma != null && nuevoSO!="") {
                                    nuevoPrograma.so= arreglosoaux!!
                                    listaProgramas.modificarElemento(editarop,nuevoPrograma)
                                }else{
                                    JOptionPane.showMessageDialog(null,"Nombre de SO invalido","Error",0)
                                }
                        }
                        (4)->{
                            var arreglosoaux= nuevoPrograma?.so
                            var salida:String=""
                            arreglosoaux?.forEachIndexed{
                                    indice:Int, valorActual->
                                salida+=("|${indice}|${valorActual}|\n")
                            }
                            var indiceSO=JOptionPane.showInputDialog(null,"|ID|SO|\n"+salida +
                                    "Ingre el ID del SO  a Eliminar","Arreglo SO",-1).toInt()
                            if(indiceSO>=0 && indiceSO< arreglosoaux?.size!!) {
                                arreglosoaux?.removeAt(indiceSO)
                                if (nuevoPrograma != null) {
                                    nuevoPrograma.so=arreglosoaux
                                    listaProgramas.modificarElemento(editarop,nuevoPrograma)
                                }else{
                                    JOptionPane.showMessageDialog(null,"Nombre de SO invalido","Error",0)
                                }

                            }else{
                                JOptionPane.showMessageDialog(null,"Indice invalido","Error",0)
                            }
                        }
                        (5)->{
                            var nuevoanio=JOptionPane.showInputDialog(null,"Ingrese El Año de Salida del Programa",
                                "Nuevo Año",-1).toInt()
                            if( nuevoanio!=null){
                                if (nuevoPrograma != null) {
                                    nuevoPrograma.aniosalida = nuevoanio
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Año Invalido","Error",0)
                            }
                            listaProgramas.modificarElemento(editarop,nuevoPrograma)
                        }
                        (6)->{
                            var nuevoprecio=JOptionPane.showInputDialog(null,"Ingrese El Nuevo Precio de Licencia del Programa",
                                "Nuevo Precio",-1).toFloat()
                            if( nuevoprecio!=null){
                                if (nuevoPrograma != null) {
                                    nuevoPrograma.preciolicencia = nuevoprecio
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Precio Invalido","Error",0)
                            }
                            listaProgramas.modificarElemento(editarop,nuevoPrograma)
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Indice Invalido","Error",0)
                }
            }
            (3)->{
               var opcioneliminar=JOptionPane.showInputDialog(null,"| ID | Nombre Programa | SO | Año | Precio |\n"+listaProgramas.imprimirLista()+"" +
                        "\nIngrese el ID del Programa a Eliminar",
                    "Eliminar",-1,).toInt()
                if (opcioneliminar>=0 && opcioneliminar<listaProgramas.listaprog.size) {
                    listaProgramas.eliminarElemento(opcioneliminar)
                    JOptionPane.showMessageDialog(null,"Programa Eliminado","Eliminar",1)
                }
            }
            (4)->{
                JOptionPane.showMessageDialog(null,"| ID | Nombre Programa | SO | Año | Precio |\n"+listaProgramas.imprimirLista(),
                    "Visualizar",-1,)
            }
            (5)->{
                listaProgramas.guardarArchivo()
            }
        }
    }catch (e: java.lang.NumberFormatException){
        JOptionPane.showMessageDialog(null,"Porfavor Ingrese Un Numero","Error",0)
    }
    }while (opcion !=6)
}

class Programas (var nombre:String,var so:ArrayList<String>,var aniosalida:Int,var preciolicencia:Float){
    override fun toString(): String {
        return "| $nombre | $so | $aniosalida | $preciolicencia |"
    }
}
//Clase para crear lista de programas
class listaProgramas{
    companion object{
        var listaprog=ArrayList<Programas?>()
        fun aniadirPrograma(programa:Programas){
            listaprog.add(programa)
        }
        fun imprimirLista():String{
            var salida:String=""
            listaprog.forEachIndexed{
                indice:Int, valorActual->
                salida+=("|${indice} ${valorActual}\n")
            }
            return salida
        }
        fun modificarElemento(indice:Int,programa:Programas?){
            listaprog.set(indice,programa)
        }
        fun obtenerElemento(indice: Int):Programas?{
            var programa:Programas?
            programa=listaprog.get(indice)
            return programa
        }
        fun eliminarElemento(indice: Int){
            listaprog.removeAt(indice)
        }
        fun guardarArchivo(){
            val nombreArchivo =  "C:/Users/brandon.campoverde/Desktop/test.txt"
            val file = File(nombreArchivo)
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            var salida:String=""
            listaprog.forEachIndexed{
                    indice:Int, valorActual->
                salida+=("| ${indice} ${valorActual}\n")
            }
            bufferedWriter.write("| ID | Nombre Programa | SO | Año | Precio |\n"+salida)//
            JOptionPane.showMessageDialog(null,"Se ha guardado exitosamente","Guardar",-1)
            bufferedWriter.close()
        }
        }
}