package com.example.movcompbmcr2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

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

    }

    fun seDeslogueo(){
        val btnLogin = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
    }
}