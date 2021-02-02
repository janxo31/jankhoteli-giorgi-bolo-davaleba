package com.example.bolodavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class Register : AppCompatActivity() {
    private lateinit var Registracia: EditText
    private lateinit var EmailChawera: EditText
    private lateinit var RegButon: Button
    private lateinit var LoginButon: Button


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        Registracia = findViewById(R.id.Registracia)
        EmailChawera = findViewById(R.id.EmailChawera)
        RegButon = findViewById(R.id.RegButon)
        LoginButon = findViewById(R.id.LoginButon)


        LoginButon.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
        }



        RegButon.setOnClickListener {
            val email: String = EmailChawera.text.toString()
            val password: String = Registracia.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {

                Toast.makeText(this, "Enter Email & Password", Toast.LENGTH_SHORT).show()
            }
            else{

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(email)
                                .build()


                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener(OnCompleteListener<Void?> { userUpdated ->
                                if (userUpdated.isSuccessful){
                                    navigateToMain()
                                }
                            })

                        } else {


                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }


                    }
            }



        }


    }


    override fun onStart(){
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            navigateToMain()
        }
    }

    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java )
        startActivity(intent)
        finish()

    }

}

