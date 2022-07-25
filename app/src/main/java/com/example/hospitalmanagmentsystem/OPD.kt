package com.example.hospitalmanagmentsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firestorekotlin.PatientData
import com.example.firestorekotlin.com.example.hospitalmanagmentsystem.MyAdapter
import com.example.hospitalmanagmentsystem.data.model.OPDAdd
import com.example.hospitalmanagmentsystem.databinding.ActivityOpdBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class OPD : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var patientArray: ArrayList<PatientData>
    private lateinit var myAdapter: MyAdapter
    private lateinit var mAMSbinder : ActivityOpdBinding
    private lateinit var mFirebase: FirebaseFirestore
    private lateinit var plusButton: Button
    private lateinit var logoutButton: Button
    private lateinit var delButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAMSbinder = ActivityOpdBinding.inflate(layoutInflater)
        setContentView(mAMSbinder.root)
        setContentView(R.layout.activity_opd)
        connector()
        fetchData()
        plusButton.setOnClickListener()
        {
            val obj=Intent(this,OPDAdd::class.java)
            startActivity(obj)
        }
      logoutButton.setOnClickListener()
      {
          logoutUser()
      }
        delButton.setOnClickListener()
        {
            val obj=Intent(this,OPDDel::class.java)
            startActivity(obj)
       }
    }
    private fun logoutUser() {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser!=null)
            {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(applicationContext, "User is logged out", Toast.LENGTH_SHORT).show()
                val obj=Intent(this,MainActivity::class.java)
                startActivity(obj);
            }
    }

    private fun fetchData() {
       mFirebase = FirebaseFirestore.getInstance()
       mFirebase.collection("Patients").addSnapshotListener(object : EventListener<QuerySnapshot> {
           override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
               if (error != null) {
                   Log.e("Firestore Error", error.message.toString())
               }
               for (dc: DocumentChange in value?.documentChanges!!) {
                   if (dc.type == DocumentChange.Type.ADDED) {
                       patientArray.add(dc.document.toObject(PatientData::class.java))

                   }
               }
               myAdapter.notifyDataSetChanged()
           }
       })
   }
    private fun connector() {
         try {
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager=LinearLayoutManager(this)
             recyclerView.setHasFixedSize(true)
            patientArray = ArrayList<PatientData>()
             myAdapter= MyAdapter(patientArray)
             recyclerView.adapter=myAdapter
             plusButton=findViewById(R.id.plusbtn)
             logoutButton=findViewById(R.id.logout_button)
            delButton=findViewById(R.id.del_rec)
         }
         catch (ex:Exception)
         {
             Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
         }
    }
}

