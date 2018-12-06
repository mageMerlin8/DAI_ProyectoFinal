package com.dai.proyectoandroid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActividadAlumno extends AppCompatActivity {

    //Variables de clase
    private EditText etClave, etNombre, etCarrera, etUniv;
    public static SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_alumno);

        //se toma control del programa
        Intent intent = getIntent();

        //toma la base de datos de la actividad principal
        bd = MainActivity.bd;

        //asocia las cajas de texto con las de la vista
        etClave = (EditText) findViewById(R.id.etAlumnoCu);
        etNombre = (EditText) findViewById(R.id.etAlumnoNom);
        etCarrera = (EditText) findViewById(R.id.etAlumnoCar);
        etUniv = (EditText) findViewById(R.id.etAlumnoUniversidad);


    }



    /*
    Metodo para regresar a la pantalla principal
    */
    public void pantallaInicio(View v){
        Intent iPantallaInicio = new Intent(this, MainActivity.class);
        startActivity(iPantallaInicio);
    }
    /**
     * Método para limpiar los datos
     */
    public void limpia(View v){
        etClave.setText("");
        etNombre.setText("");
        etCarrera.setText("");
        etUniv.setText("");
    }
}
