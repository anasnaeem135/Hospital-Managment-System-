package com.example.hospitalmanagmentsystem

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var hospitalManagement:TextView
    private lateinit var enterUsername:TextView
    private lateinit var inputUsername: EditText
    private lateinit var enterPassword: TextView
    private lateinit var inputPassword: EditText
    private lateinit var reEnterPassword: TextView
    private lateinit var reInputPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var mProgress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mFirebaseAuth = Firebase.auth
        mProgress= ProgressDialog(this)
        connector()
        signupButton.setOnClickListener()
        {
            if(inputUsername.text.isNotBlank() && inputPassword.text.isNotBlank() && inputPassword.text.toString()==reInputPassword.text.toString())
            {
                signUp(inputUsername.text.toString(),inputPassword.text.toString())

            }
            else
            {
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun signUp(email:String,password:String) {
        try {
            mProgress.show()
            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    mProgress.dismiss()

                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Signed Up Successfully", Toast.LENGTH_LONG).show()
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        if(currentUser!=null) {
                            FirebaseAuth.getInstance().signOut()
                        }
                        val obj= Intent(this,MainActivity::class.java)
                        startActivity(obj)
                    }
                    else {
                        Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        catch (ex:Exception)
        {
            mProgress.dismiss()
            Toast.makeText(applicationContext, "${ex.message}", Toast.LENGTH_LONG).show();
        }
    }
    private fun connector() {
        try {
            hospitalManagement=findViewById(R.id.hospital_management)
            enterUsername=findViewById(R.id.enter_username)
            inputUsername=findViewById(R.id.input_username)
            enterPassword=findViewById(R.id.enter_password)
            inputPassword=findViewById(R.id.input_password)
            reEnterPassword=findViewById(R.id.re_enter_password)
            reInputPassword=findViewById(R.id.input_re_enter_password)
            signupButton=findViewById(R.id.signup_button)
        }
        catch (ex:Exception)
        {
            Toast.makeText(this,ex.message, Toast.LENGTH_LONG).show()
        }
    }
}