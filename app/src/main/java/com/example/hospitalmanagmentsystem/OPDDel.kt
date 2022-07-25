package com.example.hospitalmanagmentsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract.deleteDocument
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class OPDDel : AppCompatActivity() {
    private lateinit var title:TextView
    private lateinit var name:TextView
    private lateinit var inputName:EditText
    private lateinit var delBtn: Button
    private lateinit var mFirebaseFireStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opddel)
        mFirebaseFireStore = FirebaseFirestore.getInstance()
        connector()
        delBtn.setOnClickListener()
        {
            deleteRecord()
        }
    }
    private fun deleteRecord() {
        mFirebaseFireStore.collection("Patients").document(inputName.text.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(this,"Record Deleted Successfully",Toast.LENGTH_LONG).show()
                val obj= Intent(this,OPD::class.java)
                startActivity(obj)
            }
            .addOnFailureListener{
                Toast.makeText(this,"Record Not Deleted",Toast.LENGTH_LONG).show()
            }
    }
    private fun connector() {
        try {
            title=findViewById(R.id.del_patient)
            name=findViewById(R.id.patient_name)
            inputName=findViewById(R.id.input_name)
            delBtn=findViewById(R.id.del_btn)
        }
        catch (ex:Exception){
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }
}