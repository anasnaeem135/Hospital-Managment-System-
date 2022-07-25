package com.example.hospitalmanagmentsystem

import android.app.ProgressDialog
import android.content.Intent
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var hospitalManagement: TextView
    private lateinit var logoImg: ImageView
    private lateinit var enterUsername: TextView
    private lateinit var inputUsername: EditText
    private lateinit var enterPassword: TextView
    private lateinit var inputPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var or:TextView
    private lateinit var signupButton: TextView
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mProgress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mProgress = ProgressDialog(this)
        mFirebaseAuth = Firebase.auth
        val currentLoggedInUser = FirebaseAuth.getInstance().currentUser
        connector()
        if (currentLoggedInUser == null) {

            loginButton.setOnClickListener {
                if (inputUsername.text.isNotBlank() && inputPassword.text.isNotBlank()) {
                    login(inputUsername.text.toString(), inputPassword.text.toString())

                } else {
                    Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT)
                        .show();
                }
            }
        }
        else
        {
            val obj = Intent(this, OPD::class.java)
            startActivity(obj)
        }
        signupButton.setOnClickListener(){
            val obj=Intent(this,Signup::class.java)
            startActivity(obj)
        }
    }
    private fun login(email:String,password:String)
    {
        try
        {
            val currentLoggedInUser = FirebaseAuth.getInstance().currentUser
            mProgress.show()
            if(currentLoggedInUser==null)
            {
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        mProgress.dismiss()
                        if(it.isSuccessful)
                        {
                            val obj = Intent(this, OPD::class.java)
                            startActivity(obj)
                            Toast.makeText(applicationContext, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            else
            {
                mProgress.dismiss()
                Toast.makeText(applicationContext, "User is already logged in", Toast.LENGTH_SHORT).show();
            }
        }
        catch (ex: java.lang.Exception)
        {
            mProgress.dismiss()
            Toast.makeText(applicationContext, "${ex.message}", Toast.LENGTH_SHORT).show();
        }
    }
    private fun connector() {
        try {
            logoImg=findViewById(R.id.pic)
            hospitalManagement = findViewById(R.id.hospital_management)
            enterUsername = findViewById(R.id.enter_username)
            inputUsername = findViewById(R.id.input_username)
            enterPassword = findViewById(R.id.enter_password)
            inputPassword = findViewById(R.id.input_password)
            loginButton = findViewById(R.id.login_button)
            or=findViewById(R.id.or)
            signupButton=findViewById(R.id.start_button)
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}