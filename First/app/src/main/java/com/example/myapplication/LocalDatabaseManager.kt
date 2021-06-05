package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Models.Mascota

class LocalDatabaseManager (context:Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 12
        private const val DATABASE_NAME = "AppDataBase"
        private const val TABLE_LOGGED_USER = "UsuarioLogeado"

        private const val TABLE_USER_PETS = "MascotasUsuario"


        //Varibles tabla usuario logeado
        private const val KEY_ID = "_id"
        private const val KEY_IDUSER = "userId"
        private const val KEY_NICKNAME = "apodo"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_APELLIDOP = "apellidoP"
        private const val KEY_APELLIDOM = "apellidoM"
        private const val KEY_CORREO = "correo"
        private const val KEY_FECHA_NACIMIENTO = "fechaNacimiento"
        private const val KEY_PASSWORD = "comtrasena"
        private const val KEY_TELEFONO = "telefono"
        private const val KEY_FOTO = "foto"

        //Varibles tabla mascotas de usario
        private const val KEY_PET_OWNERID = "userId"
        private const val KEY_PET_NAME = "nombre"
        private const val KEY_PET_AGE = "edad"
        private const val KEY_PET_TYPE = "tipoMascotaId"
        private const val KEY_PET_CATEGORY = "categoriaMascotaId"
        private const val KEY_PET_FOTO = "imagen"
        private const val KEY_PET_CARNET = "cartillaPdf"
        private const val KEY_PET_ALIMENTO = "alimento"

        private const val KEY_CHANGE_UPLOADED = "isUpdated"

        //Reservaciones

        //Estancias

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE $TABLE_LOGGED_USER($KEY_ID INTEGER PRIMARY KEY,$KEY_IDUSER INTEGER,$KEY_NICKNAME TEXT,$KEY_NOMBRE TEXT,$KEY_APELLIDOP TEXT,$KEY_APELLIDOM TEXT,$KEY_CORREO TEXT,$KEY_FECHA_NACIMIENTO TEXT,$KEY_PASSWORD TEXT,$KEY_TELEFONO TEXT,$KEY_CHANGE_UPLOADED BOOL)")
        val CREATE_PET_TABLE = ("CREATE TABLE $TABLE_USER_PETS($KEY_ID INTEGER PRIMARY KEY, $KEY_PET_OWNERID INTEGER, $KEY_PET_NAME TEXT, $KEY_PET_AGE INTEGER, $KEY_PET_TYPE INTEGER, $KEY_PET_CATEGORY INTEGER, $KEY_PET_FOTO TEXT, $KEY_PET_CARNET TEXT, $KEY_PET_ALIMENTO TEXT)")
        p0?.execSQL(CREATE_USER_TABLE)
        p0?.execSQL(CREATE_PET_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGED_USER)
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PETS)
            onCreate(p0)
        }
    }




    fun InsertLoggedUser(loggedUser:Persona, wasUpdated:Boolean):Long{
        val db = this.writableDatabase

        db.execSQL("delete from "+ TABLE_LOGGED_USER)
        db.execSQL("delete from "+ TABLE_USER_PETS)

        val contentValues = ContentValues()
        contentValues.put(KEY_IDUSER, loggedUser.id)
        contentValues.put(KEY_NICKNAME, loggedUser.apodo)
        contentValues.put(KEY_NOMBRE, loggedUser.nombre)
        contentValues.put(KEY_APELLIDOP, loggedUser.apellidoPaterno)
        contentValues.put(KEY_APELLIDOM, loggedUser.apellidoMaterno)
        contentValues.put(KEY_FECHA_NACIMIENTO, loggedUser.fechaNacimiento)
        contentValues.put(KEY_CORREO, loggedUser.correoElectronico)
        contentValues.put(KEY_PASSWORD, loggedUser.contrasena)
        contentValues.put(KEY_CHANGE_UPLOADED, wasUpdated)
        contentValues.put(KEY_TELEFONO, loggedUser.numeroCelular.toString())
        //contentValues.put(KEY_FOTO, loggedUser.foto)

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

    fun updateUser(persona :Persona, wasUpdated:Boolean): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NOMBRE, persona.nombre)
        contentValues.put(KEY_APELLIDOP, persona.apellidoPaterno)
        contentValues.put(KEY_APELLIDOM, persona.apellidoMaterno)
        contentValues.put(KEY_CORREO, persona.correoElectronico)
        contentValues.put(KEY_PASSWORD, persona.contrasena)
        contentValues.put(KEY_TELEFONO, persona.numeroCelular)
        //contentValues.put(KEY_FOTO, persona.foto)
        contentValues.put(KEY_CHANGE_UPLOADED, wasUpdated)

        val succes = db.update(TABLE_LOGGED_USER, contentValues, KEY_IDUSER + "=" + persona.id, null)

        db.close()

        return succes
    }




    fun InsertPet(model: Mascota, wasUpdated:Boolean):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_PET_OWNERID, model.userId)
        contentValues.put(KEY_PET_NAME, model.nombre)
        contentValues.put(KEY_PET_AGE,model.edad)
        contentValues.put(KEY_PET_TYPE, model.tipoMascotaId)
        contentValues.put(KEY_PET_CATEGORY, model.categoriaMascotaId)
        contentValues.put(KEY_PET_FOTO, model.imagen)
        contentValues.put(KEY_PET_CARNET, model.cartillaPdf)
        contentValues.put(KEY_PET_ALIMENTO,model.alimento)



        val success = db.insert(TABLE_USER_PETS, null, contentValues)

        db.close()
        return success
    }

    fun GetUserPets(): ArrayList<Mascota> {
        val list: ArrayList<Mascota> = ArrayList<Mascota>()
        val selectQuery = "SELECT  * FROM $TABLE_USER_PETS"

        val db = this.readableDatabase
        try {
            val cursor = db.rawQuery(selectQuery, null)
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        val column1 = cursor.getString(cursor.getColumnIndex(KEY_PET_OWNERID))
                        val column2 = cursor.getString(cursor.getColumnIndex(KEY_PET_NAME))
                        val column3 = cursor.getString(cursor.getColumnIndex(KEY_PET_AGE)).toInt()
                        val column4 = cursor.getString(cursor.getColumnIndex(KEY_PET_TYPE)).toInt()
                        val column5 = cursor.getString(cursor.getColumnIndex(KEY_PET_CATEGORY)).toInt()
                        val column6 = cursor.getString(cursor.getColumnIndex(KEY_PET_FOTO))
                        val column7 = cursor.getString(cursor.getColumnIndex(KEY_PET_CARNET))
                        val column8 = cursor.getString(cursor.getColumnIndex(KEY_PET_ALIMENTO))

                        //you could add additional columns here..
                        list.add(Mascota(column1,column2,column3,column4,column5,column6,column7,column8,""))
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }

        return list
    }

    fun GetPetInfo (petName:String): ArrayList<Mascota> {
        val select = "SELECT * FROM $TABLE_USER_PETS WHERE $KEY_NOMBRE = '$petName'"
        val db = this.readableDatabase

        val selectedPet: ArrayList<Mascota> = ArrayList<Mascota>()


        val cursor = db.rawQuery(select, null)
        if (cursor.moveToFirst()) {


            val column1 = cursor.getString(cursor.getColumnIndex(KEY_PET_OWNERID))
            val column2 = cursor.getString(cursor.getColumnIndex(KEY_PET_NAME))
            val column3 = cursor.getString(cursor.getColumnIndex(KEY_PET_AGE)).toInt()
            val column4 = cursor.getString(cursor.getColumnIndex(KEY_PET_TYPE)).toInt()
            val column5 = cursor.getString(cursor.getColumnIndex(KEY_PET_CATEGORY)).toInt()
            val column6 = cursor.getString(cursor.getColumnIndex(KEY_PET_FOTO))
            val column7 = cursor.getString(cursor.getColumnIndex(KEY_PET_CARNET))
            //val column8 = cursor.getString(cursor.getColumnIndex(KEY_PET_ALIMENTO))


            selectedPet.add(Mascota(column1,column2,column3,column4,column5,column6,column7,"",""))
        }
        cursor.close()
        db.close()

        return selectedPet
    }

/*
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
*/



}

