import java.util.*

//Main.kt
fun main(){
    println("Hola mundo")
    //Tipos de variables
    //Inmutables(No se puede reasginar "=")
    val inmutable: String = "Brandon"
    //inmutable="Martin" No se puede reasigmar

    //Mutables (Si se puede reasginar "=")
    var mutable: Int = 1
    mutable = 3

    //val>var

    //Sintaxis Duck typing
    val  ejemploVariavle = "Ejemplo"
    val edadEjemplo:Int = 4
    ejemploVariavle.toUpperCase()
    ejemploVariavle.trim()

    //Variables primitivas
    //Int
    val edadEjemplo1:Int = 4
    //Double
    val ejemploFloat:Double = 1.2
    //Char
    val ejemploChar:Char = 'B'
    //Boolean
    val ejemploBool:Boolean = true

    //Clases JAVA
    val fechaNacimiento: Date = Date()

    //Condicionales
    if(true){

    }else{

    }
    //Switch no existe si no When
    val opcion = "S"

    when(opcion){
        ("S") ->{
            println("Aio")
        }
        "C"-> {
            println("Oa")
        }
        "O"-> println("Talvez")
        else -> println("No hay")
    }

    val coqueteo = if(opcion == "S") "Si" else "No"

    imprimir("Brandon")
   println(calcularSueldo(20.00))
    println(Suma.elpi)
    println(Suma.cuadrado(3))

    val sumauno = Suma(1,2)
    val sumados = Suma(4,6)
    sumauno.sumar()
    println(Suma.historialsumas)
    sumados.sumar()
    println(Suma.historialsumas)

    //Arreglos
    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstatico)

    // Arreglo Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //Operadores
    //FOR EACH
    //Iterar un arreglo

    val respuestaForEach: Unit = arregloEstatico
        .forEach {
            valorActual: Int->
            println("Valor actual:  ${valorActual}")
        }
    arregloDinamico
        .forEachIndexed { indice:Int, valorActual: Int->
            println("Valor ${valorActual} Indice ${indice}")
        }



    //Map -> Muta(Modifica) el arreglo

    val respuestaMap: List<Double>  = arregloDinamico
        .map{ valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 150}
        println(respuestaMapDos)

    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorArctual:Int ->
            val mayoresACinco: Boolean = valorArctual >5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <=5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY
    //AMD -> ALL

    val respuestaAny: Boolean = arregloDinamico.any{
        valorActual: Int->
        return@any (valorActual>5)
    }
    println(respuestaAny)//true

    val respuestaAll: Boolean = arregloDinamico.all{
            valorActual: Int->
        return@all (valorActual>5)
    }
    println(respuestaAll)//false

    val respuestaReduce:Int = arregloDinamico
        .reduce{
            acumulado:Int, valorActial:Int->
            return@reduce (acumulado + valorActial)
        }
    println(respuestaReduce)
}

//Funciones void= Unit
fun imprimir(nombre:String): Unit{
    println("Esta es la funcion void : ${nombre}")
}

//Funcion con return

fun calcularSueldo(
    sueldo: Double,//Requerido
    tasa: Double=12.00,//Opcional(Defecto)
    bonoEspecial:Double? = null,//Opcional(Null) -> nullable
):Double{
    //
    if (bonoEspecial == null){
        return sueldo*(100/tasa)
    }else{
        return sueldo*(100/tasa)+bonoEspecial
    }
}

//Clases
abstract class NumerosJava{
    protected val num1:Int
    private val num2:Int

    constructor(
        uno:Int,
        dos:Int
    ){//Bloque de codigo
        this.num1=uno
        this.num2=dos
        println("Inicializando")
    }
}

abstract class NumeroKotlin(//Constructor primario
    protected var uno:Int,//Si no se agrega un modificador es solo un parametro, por defecto si se
    //escribe var es public
    //private val dos:Int
    protected val dos:Int
)
{
    init {//Bloque de codigo del constructor primario
        //this. es opcional
        uno//Propiedad de la clase
        dos
        println("Inicializando")
    }
}

class Suma(//Constructor primario
    uno:Int,
    dos:Int):NumeroKotlin(
    //Super constructor
    uno,
    dos
    ){
    init {
        this.uno
        this.dos
    }

    constructor(//Secunadrio
    uno: Int?,
    dos: Int
     ) : this(//Llamada constructor primario
        if (uno==null) 0 else uno,
        dos
     ){
         //Bloque Codigo
     }

    constructor(//Secunadrio
        uno: Int,
        dos: Int?
    ) : this(//Llamada constructor primario
        if (dos==null) 0 else dos,
        uno
    ){
        //Bloque Codigo
    }

    constructor(//Secunadrio
        uno: Int?,
        dos: Int?
    ) : this(//Llamada constructor primario
        if (dos==null) 0 else dos,
        if (uno==null) 0 else uno
    ){
        //Bloque Codigo
    }

    fun sumar():Int{
        val total = uno+dos
        agreggarhistorial(total)
        return total
    }
//Para tener elementos estaticos
    companion object{
        val elpi =3.14
        fun cuadrado(num:Int):Int{
            return num.times(num)
        }
        val historialsumas = arrayListOf<Int>()
        fun agreggarhistorial(valorNuevaSuma:Int){
            historialsumas.add(valorNuevaSuma)
        }
    }

}
