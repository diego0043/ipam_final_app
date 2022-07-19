package com.example.tareafinal.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.number.IntegerWidth
import androidx.core.content.contentValuesOf
import com.example.tareafinal.model.StudentModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION ) {
    companion object{
        private val DB_NAME = "student"
        private val DB_VERSION = 4
        private val TABLE_NAME = "infoStudent"
        private val ID = "id"
        private val NAME = "name"
        private val LICENSE = "license"
        private val CAREER = "career"
        private val YEAR = "year"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $LICENSE TEXT, $CAREER TEXT, $YEAR TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllStudents(): List<StudentModel> {
        val studentList = ArrayList<StudentModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val student = StudentModel()
                    student.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    student.name = cursor.getString(cursor.getColumnIndex(NAME))
                    student.license = cursor.getString(cursor.getColumnIndex(LICENSE))
                    student.career = cursor.getString(cursor.getColumnIndex(CAREER))
                    student.year = cursor.getString(cursor.getColumnIndex(YEAR))
                    studentList.add(student)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return studentList
    }

    //insert
    fun addStudent(student : StudentModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, student.name)
        values.put(LICENSE, student.license)
        values.put(CAREER, student.career)
        values.put(YEAR, student.year)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    //select the data for particular id
    @SuppressLint("Range")
    fun getStudent(_id: Int) : StudentModel{
        val student = StudentModel()
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        student.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        student.name = cursor.getString(cursor.getColumnIndex(NAME))
        student.license = cursor.getString(cursor.getColumnIndex(LICENSE))
        student.career = cursor.getString(cursor.getColumnIndex(CAREER))
        student.year = cursor.getString(cursor.getColumnIndex(YEAR))
        cursor.close()
        return student
    }

    //Delete item
    fun deleteStudent(_id: Int): Boolean{
        val db = this.writableDatabase
        var _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    //Update student
    fun updateStudent(student: StudentModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, student.name)
        values.put(LICENSE, student.license)
        values.put(CAREER, student.career)
        values.put(YEAR, student.year)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(student.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1

    }



}