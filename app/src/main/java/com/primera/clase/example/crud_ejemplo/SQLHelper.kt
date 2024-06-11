package com.primera.clase.example.crud_ejemplo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class SQLHelper(context: MainActivity): SQLiteOpenHelper(context, DB_NAME,null,DB_VERSION) {
    //Restricciones para el acceso de la base de datos
    // Toda la informacion a esa base de datos siempre debe ir asegurado, dentor de un companion object(dedicarse a trabajar dentro de la clase SQLHelper)
    // Es un contenedor especial para guardar informacion de la base de datos
    companion object{
        // const val, para mayor integridad de la informacion sea mas dificil de alterar
        private const val DB_VERSION = 1
        private const val  DB_NAME = "students.db"
        private const val  DB_TABLE = "tbl_student"
        private const val  ID_EST = "id"
        private const val  NOMBRE = "nombre"
        private const val  CORREO = "correo"
        private const val  CURSO = "curso"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = (
                "CREATE TABLE $DB_TABLE(" +
                        "$ID_EST INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "$NOMBRE TEXT," +
                        "$CORREO TEXT," +
                        "$CURSO TEXT)"
                )
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $DB_TABLE")
        onCreate(db)
    }

    fun insertEst(est:EstModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_EST,est.id)
        contentValues.put(NOMBRE,est.nombre)
        contentValues.put(CORREO,est.correo)
        contentValues.put(CURSO,est.curso)

        val success = db.insert(DB_TABLE,null,contentValues)

        db.close()
        return success
    }

    @SuppressLint("Range")
    //Recorriendo informacion de un for que se sale del rango.
    fun getListEst(): ArrayList<EstModel> {
        val listaEst: ArrayList<EstModel> = ArrayList()
        val sql = "SELECT * FROM $DB_TABLE ORDER BY $ID_EST DESC"
        val db = this.readableDatabase
        val cursor: Cursor

        try {
            cursor = db.rawQuery(sql,null)
        }catch (err: Exception){
            err.printStackTrace()
            return ArrayList()
        }

        var id: Int
        var nombre: String
        var correo: String
        var curso: String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID_EST))
                nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                correo = cursor.getString(cursor.getColumnIndex(CORREO))
                curso = cursor.getString(cursor.getColumnIndex(CURSO))

                val est = EstModel(id, nombre, correo, curso)
                listaEst.add(est)
            }while (cursor.moveToNext())
        }
        return listaEst
    }
}