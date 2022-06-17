package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AClase09 : AppCompatActivity() {
    var numeroGlobal="0"
    var textoamostrarclase = findViewById<TextView>(R.id.textmostrarnum09)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aclase09)
        val button09 = findViewById<Button>(R.id.btn_a09)

        button09.setOnClickListener {
            textoamostrarclase.setText(aumentar(textoamostrarclase.text.toString().toInt()).toString())
            numeroGlobal=textoamostrarclase.text.toString()
        }
    }
    fun aumentar(aumentar:Int):Int{
        var numero=aumentar
        numero++
        return numero
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("textoGuardado",numeroGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        textoamostrarclase.setText(numeroGlobal.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado:String? = savedInstanceState.getString("textoGuardado")
        if(textoRecuperado != null){
            numeroGlobal=textoRecuperado
        }
    }
}