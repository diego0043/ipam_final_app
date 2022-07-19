package com.example.tareafinal

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import com.example.tareafinal.database.DatabaseHelper
import com.example.tareafinal.model.StudentModel

class AddStudent : AppCompatActivity() {

    lateinit var btn_save : Button
    lateinit var btn_del : Button
    lateinit var et_name : EditText
    lateinit var et_license : EditText
    lateinit var et_career : EditText
    lateinit var et_year : EditText
    var dbHandler : DatabaseHelper ? = null
    var isEditMode : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        btn_save = findViewById(R.id.btn_save)
        btn_del = findViewById(R.id.btn_del)
        et_name = findViewById(R.id.et_name)
        et_license = findViewById(R.id.et_license)
        et_career = findViewById(R.id.et_career)
        et_year = findViewById(R.id.et_year)
        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E"){
            //update data
            isEditMode = true
            btn_save.text = "GUARDAR"
            btn_del.visibility = View.VISIBLE
            val student : StudentModel = dbHandler!!.getStudent(intent.getIntExtra("Id", 0))
            et_name.setText(student.name)
            et_license.setText(student.license)
            et_career.setText(student.career)
            et_year.setText(student.year)
        }else{
            //insert data
            isEditMode = false
            btn_save.text = "GUARDAR"
            btn_del.visibility = View.GONE
        }

        btn_save.setOnClickListener {
            var success : Boolean = false
            val student : StudentModel = StudentModel()
            if(isEditMode){
                //Update data
                student.id = intent.getIntExtra("Id", 0)
                student.name = et_name.text.toString()
                student.license = et_license.text.toString()
                student.career = et_career.text.toString()
                student.year = et_year.text.toString()

                success = dbHandler?.updateStudent(student) as Boolean

            }else{
                //insert
                if(et_name.text.toString().isEmpty() || et_license.text.toString().isEmpty() || et_career.text.toString().isEmpty() || et_year.text.toString().isEmpty()){
                    Toast.makeText(applicationContext, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
                }else{

                    student.name = et_name.text.toString()
                    student.license = et_license.text.toString()
                    student.career = et_career.text.toString()
                    student.year = et_year.text.toString()
                    success = dbHandler?.addStudent(student) as Boolean

                    if (success){
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "Algo salio mal!!", Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }

        btn_del.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("¿Esta seguro que desea eliminar el registo?")
                .setPositiveButton("Si",  {dialog, i ->
                    val success = dbHandler?.deleteStudent(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                        dialog.dismiss()
                })
                .setNegativeButton("No", {dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        }
    }
}