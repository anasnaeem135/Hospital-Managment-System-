package com.example.firestorekotlin.com.example.hospitalmanagmentsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firestorekotlin.PatientData
import com.example.hospitalmanagmentsystem.R
import com.google.firebase.firestore.auth.User

class MyAdapter(private val userList: ArrayList<PatientData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView=itemView.findViewById(R.id.name_data)
        val age: TextView=itemView.findViewById(R.id.age_data)
        val bloodGroup: TextView=itemView.findViewById(R.id.bloodgroup_data)
        val disease: TextView=itemView.findViewById(R.id.disease_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_patient,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj: PatientData=userList[position]
        holder.name.text = obj.name
        holder.age.text = obj.age.toString()
        holder.bloodGroup.text=obj.bloodGroup
        holder.disease.text=obj.disease
    }

    override fun getItemCount(): Int {
       return userList.size
    }

}