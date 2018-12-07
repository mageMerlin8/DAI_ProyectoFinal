package com.dai.proyectoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Se debe crear una subclase, de la clase SQLiteOpenHelper de Android, para hacer el manejo de la BD.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //En el constructor se especifica el nombre de la BD (2o. parámetro) y la versión de esta (4o. parámetro).
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Este método se puede usar para crear las tablas de la BD.
    //Cada tabla se crearía tal como se muestra para la tabla de alumnos.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table univ(idUniv integer primary key, nombre text, estado text) ");
        sqLiteDatabase.execSQL("create table carrera(idCar integer primary key , nombre text, creds integer)");
        sqLiteDatabase.execSQL("create table alumno(cu integer primary key, nombre text, idCar integer references carrera, idUniv integer references univ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
