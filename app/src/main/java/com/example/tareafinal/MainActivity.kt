package com.example.tareafinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal.adapter.StudentListAdapter
import com.example.tareafinal.database.DatabaseHelper
import com.example.tareafinal.model.StudentModel

class MainActivity : AppCompatActivity() {

    lateinit var recycler_student : RecyclerView
    lateinit var btn_add : Button
    var studentlistAdapter : StudentListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var studentList : List<StudentModel> = ArrayList<StudentModel>()
    var linearlayoutManager : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_student = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.btn_add_student)
        dbHandler = DatabaseHelper(this)
        fetchlist()

        btn_add.setOnClickListener {
            val i = Intent(applicationContext, AddStudent::class.java)
            startActivity(i)
        }
    }

    private fun fetchlist(){
        studentList = dbHandler!!.getAllStudents()
        studentlistAdapter = StudentListAdapter(studentList, applicationContext)
        linearlayoutManager = LinearLayoutManager(applicationContext)
        recycler_student.layoutManager = linearlayoutManager
        recycler_student.adapter =  studentlistAdapter
        studentlistAdapter?.notifyDataSetChanged()

    }

}