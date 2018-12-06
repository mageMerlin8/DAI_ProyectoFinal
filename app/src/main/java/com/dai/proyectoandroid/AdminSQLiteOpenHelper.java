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
        sqLiteDatabase.execSQL("create table alumno(cu integer primary key, nombre text, idCar int references carrera, idUniv integer references univ) ");
    }

    //Este método se usa si se incrementa la versión de la BD en el código de la aplicación.
    //Una política al actualizar puede ser el borrar la BD anterior, para iniciar desde cero la nueva.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists alumnos");
        sqLiteDatabase.execSQL("create table alumnos(cu integer primary key, nombre text, carrera text, universidad integer)");
    }
}
