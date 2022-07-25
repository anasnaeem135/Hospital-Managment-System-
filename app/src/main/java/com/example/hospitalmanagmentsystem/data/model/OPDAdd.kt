package com.example.hospitalmanagmentsystem.data.model

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firestorekotlin.PatientData
import com.example.hospitalmanagmentsystem.OPD
import com.example.hospitalmanagmentsystem.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OPDAdd : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var name: TextView
    private lateinit var inputName: EditText
    private lateinit var age: TextView
    private lateinit var inputAge: EditText
    private lateinit var bloodGroup: TextView
    private lateinit var inputBloodGroup: EditText
    private lateinit var disease: TextView
    private lateinit var inputDisease: EditText
    private lateinit var btn: Button
   private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mFirebaseFireStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opdadd)
        mProgressDialog=ProgressDialog(this)
        mFirebaseFireStore = FirebaseFirestore.getInstance()
        connector()

        btn.setOnClickListener() {
            addPatientData()
        }
    }

    private fun addPatientData() {
        try
        {
            mProgressDialog.show()
            var userHashMap=HashMap<String,Any>()
            userHashMap["name"]=inputName.text.toString()
            userHashMap["age"]=inputAge.text.toString()
            userHashMap["bloodGroup"]=inputBloodGroup.text.toString()
            userHashMap["disease"]=inputDisease.text.toString()

            mFirebaseFireStore.collection("Patients")
                .document(inputName.text.toString())
                .set(userHashMap)
                .addOnSuccessListener {_:Void?->
                    mProgressDialog.dismiss()
                    Toast.makeText(applicationContext,"Record Inserted",Toast.LENGTH_SHORT).show()
                    val obj=Intent(this,OPD::class.java)
                    startActivity(obj)
                }
                .addOnFailureListener {ex->
                    mProgressDialog.dismiss()
                    Toast.makeText(applicationContext,"Record Not Inserted:${ex.message}",Toast.LENGTH_SHORT).show()
                }
        }
        catch(ex:Exception)
        {
            mProgressDialog.dismiss()
            Toast.makeText(applicationContext,ex.message,Toast.LENGTH_SHORT).show()
        }
    }
    private fun connector() {
        try {
            title = findViewById(R.id.add_patient)
            name = findViewById(R.id.patient_name)
            inputName = findViewById(R.id.input_name)
            age = findViewById(R.id.age)
            inputAge = findViewById(R.id.input_age)
            bloodGroup = findViewById(R.id.blood_group)
            inputBloodGroup = findViewById(R.id.input_bloodgroup)
            disease=findViewById(R.id.disease)
            inputDisease=findViewById(R.id.input_disease)
            btn = findViewById(R.id.add_button)

        }
        catch(ex:Exception)
        {
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }
}
