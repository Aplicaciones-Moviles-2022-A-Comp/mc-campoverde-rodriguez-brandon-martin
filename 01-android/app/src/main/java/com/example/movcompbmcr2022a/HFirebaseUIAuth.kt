package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HFirebaseUIAuth : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode== RESULT_OK){
            if (res.idpResponse!=null){
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebaseuiauth)

        val btnLogin = findViewById<Button>(R.id.btn_intent_firebase_ui)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

// Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)

        }

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    seDeslogueo()
                }
        }
    }

    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser==true){
           registarusuarioPrimeraVez(res)
        }
    }

    fun registarusuarioPrimeraVez(
        usuario:IdpResponse
    ){
        val usuarioLogeado = FirebaseAuth.getInstance().currentUser
        if(usuario.email != null && usuarioLogeado != null){
            val db = Firebase.firestore
            val roles = arrayListOf("usuario")
            val email = usuario.email
            val uid = usuarioLogeado.uid
            val nuevoUsuario = hashMapOf<String,Any>(
                "roles" to roles,
                "uid" to uid,
                "email" to email.toString()
            )
            db.collection("usuario").document(email.toString())
                .set(nuevoUsuario).addOnSuccessListener {

                }.addOnFailureListener{

                }
        }
    }

    fun seDeslogueo(){
        val btnLogin = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
    }
}