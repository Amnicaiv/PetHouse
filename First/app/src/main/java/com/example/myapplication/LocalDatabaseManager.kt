package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class LocalDatabaseManager (context:Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AppDataBase"
        private const val TABLE_LOGGED_USER = "UsuarioLogeado"

        //Varibles tabla usuario logeado
        private const val KEY_ID = "_id"
        private const val KEY_IDUSER = "userId"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_APELLIDOP = "apellidoP"
        private const val KEY_APELLIDOM = "apellidoM"
        private const val KEY_CORREO = "correo"
        private const val KEY_FECHA_NACIMIENTO = "fechaNacimiento"
        private const val KEY_PASSWORD = "comtrasena"
        private const val KEY_TELEFONO = "telefono"
        private const val KEY_FOTO = "foto"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_LOGGED_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IDUSER + "INTEGER," + KEY_NOMBRE + " TEXT," + KEY_APELLIDOP + " TEXT," + KEY_APELLIDOM + " TEXT," + KEY_CORREO +
                " TEXT," + KEY_FECHA_NACIMIENTO + "TEXT," + KEY_PASSWORD + " TEXT," + KEY_TELEFONO + " TEXT," + KEY_FOTO + "BLOB" + ")")
        p0?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGED_USER)
            onCreate(p0)
        }
    }

    fun InsertLoggedUser(loggedUser:Persona):Long{
        val db = this.writableDatabase

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGED_USER)
        onCreate(db)

        val contentValues = ContentValues()
        contentValues.put(KEY_IDUSER, loggedUser.id)
        contentValues.put(KEY_NOMBRE, loggedUser.nombre)
        contentValues.put(KEY_APELLIDOP, loggedUser.apellidoPaterno)
        contentValues.put(KEY_APELLIDOM, loggedUser.apellidoMaterno)
        contentValues.put(KEY_FECHA_NACIMIENTO, loggedUser.fechaNacimiento)
        contentValues.put(KEY_CORREO, loggedUser.correoElectronico)
        contentValues.put(KEY_PASSWORD, loggedUser.contrasena)
        contentValues.put(KEY_FOTO, loggedUser.foto)

        val success = db.insert(TABLE_LOGGED_USER, null, contentValues)

        db.close()
        return success
    }

    fun GetLoggedUser() : ArrayList<Persona>{
        var personaDummy: Persona? = null
        val userList: ArrayList<Persona> = ArrayList<Persona>()

        val selecQuery = "SELECT * FROM $TABLE_LOGGED_USER"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selecQuery, null)
        } catch (e: SQLException){
            db.execSQL(selecQuery)
            return ArrayList()
        }
        if(cursor.moveToFirst()) {

            do {
                if (personaDummy != null) {
                    personaDummy.id = cursor.getInt(cursor.getColumnIndex(KEY_IDUSER))
                }
                if (personaDummy != null) {
                    personaDummy.nombre = cursor.getString(cursor.getColumnIndex(KEY_NOMBRE))
                }
                if (personaDummy != null) {
                    personaDummy.apellidoPaterno = cursor.getString(cursor.getColumnIndex(KEY_APELLIDOP))
                }
                if (personaDummy != null) {
                    personaDummy.apellidoMaterno = cursor.getString(cursor.getColumnIndex(KEY_APELLIDOM))
                }
                if (personaDummy != null) {
                    personaDummy.fechaNacimiento = cursor.getString(cursor.getColumnIndex(KEY_FECHA_NACIMIENTO))
                }
                if (personaDummy != null) {
                    personaDummy.correoElectronico = cursor.getString(cursor.getColumnIndex(KEY_CORREO))
                }
                if (personaDummy != null) {
                    personaDummy.contrasena = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                }
                if (personaDummy != null) {
                    personaDummy.foto = 0
                }

                if (personaDummy != null) {
                    userList.add(personaDummy)
                }
            }while (cursor.moveToNext())
        }
        return userList

        }
    }

}