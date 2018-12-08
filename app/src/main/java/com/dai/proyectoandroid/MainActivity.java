package com.dai.proyectoandroid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private AdminSQLiteOpenHelper admin;
    public static SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Establece el nombre y la versión de la BD.
        admin= new AdminSQLiteOpenHelper(this, "Ejemplo", null, 1);

        //Permite leer y escribir con la BD.
        bd= admin.getWritableDatabase();
        Toast.makeText(this, "Abrí la BD", Toast.LENGTH_LONG).show();
    }
    public void pantallaAlumno(View v){
        Intent iPantalaAlumno = new Intent(this, ActividadAlumno.class);
        startActivity(iPantalaAlumno);
    }
    public void pantallaCarrera(View v){
        Intent iPantallaCarrera = new Intent(this, ActividadCarrera.class);
        startActivity(iPantallaCarrera);
    }
    public void pantallaUniversidad(View v){
        Intent iPantallaUniv = new Intent(this, ActividadUniversidad.class);
        startActivity(iPantallaUniv);
    }
    public void pantallaEstadisticas(View v){
        Intent iPantallaEst = new Intent(this, ActividadEstadisticas.class);
        startActivity(iPantallaEst);
    }

}