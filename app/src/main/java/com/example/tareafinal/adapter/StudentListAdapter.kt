package com.example.tareafinal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal.AddStudent
import com.example.tareafinal.MainActivity
import com.example.tareafinal.R
import com.example.tareafinal.model.StudentModel

class StudentListAdapter(studentList: List<StudentModel>, internal var context : Context)
    : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>()
{
    internal var studentList : List<StudentModel> = ArrayList()
    init {
        this.studentList = studentList
    }


    inner class StudentViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var name : TextView = view.findViewById(R.id.txt_name)
        var license : TextView = view.findViewById(R.id.txt_license)
        var career : TextView = view.findViewById(R.id.txt_career)
        var year : TextView = view.findViewById(R.id.txt_year)
        var btn_edit : Button = view.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_student_list, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val students = studentList[position]
        holder.name.text = students.name
        holder.license.text = students.license
        holder.career.text = students.career
        holder.year.text = students.year

        holder.btn_edit.setOnClickListener {
            val i = Intent(context, AddStudent::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("Mode", "E")
            i.putExtra("Id", students.id)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

}