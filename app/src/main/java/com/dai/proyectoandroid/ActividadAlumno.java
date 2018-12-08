package com.dai.proyectoandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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


    /**
     * Da de alta un alumno en la tabla respectiva.
     */
    public void altaAl(View v) {

        //Obtiene los datos tecleados.
        String cu= etClave.getText().toString();
        String nombre= etNombre.getText().toString();
        String carr= etCarrera.getText().toString();
        String univ= etUniv.getText().toString();

        //En ContentValues se guardan los valores a insertar.
        ContentValues reg= new ContentValues();
        reg.put("cu", cu);
        reg.put("nombre", nombre);
        reg.put("idCar", carr);
        reg.put("idUniv", univ);

        //Hace la inserción de los datos en la tabla.
        try {
            if (bd != null) {
                long resul= bd.insert("alumno", null, reg);
                Toast.makeText(this, "Se insertó en alumnos", Toast.LENGTH_LONG).show();
                limpia(v);
            }
            else
                Toast.makeText(this, "BD cerrada", Toast.LENGTH_LONG).show();
        }
        catch (SQLException err) {
            Toast.makeText(this, "Error: "+err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Consulta a SQLite.
     */
    public void consultaAl(View v) {

        //Obtiene la clave única del alumno.
        String cu= etClave.getText().toString();

        //Ejecuta la consulta.
        Cursor fila= bd.rawQuery("select * from alumno where cu=" +
                cu + " ", null);

        //Recorre el cursor para acceder al resultado de la consulta.
        if (fila.moveToFirst()) {
            etClave.setText(fila.getString(0));
            etNombre.setText(fila.getString(1));
            etCarrera.setText(fila.getString(2));
            etUniv.setText(fila.getString(3));
            fila.close();
        }
        else
            Toast.makeText(this, "Error: no existe la cu= "+cu,
                    Toast.LENGTH_LONG).show();
    }

    /**
     * Da de baja al alumno cuyo clave única se especificó en la caja de texto.
     */
    public void bajaAl(View v) {

        //Se lee la clave única del alumno a dar de baja de la BD.
        String cu = etClave.getText().toString();

        //Se ejecuta la baja.
        int cant = bd.delete("alumno", "cu=" + cu, null);

        //Se limpian las cajas de texto.
        limpia(v);

        if (cant == 1)
            Toast.makeText(this, "Se borraron los datos del alumno con la clave única: "+cu,
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error: no existe un alumno con la clave única: "+cu,
                    Toast.LENGTH_LONG).show();
    }

    /**
     * Modifica el nombre, la carrera o la univ. del alumno dado por su clave.
     */
    public void modificacionAl(View v) {

        //Toma el contenido de las cajas de texto.
        String cu = etClave.getText().toString();
        String nombre = etNombre.getText().toString();
        String carrera = etCarrera.getText().toString();
        String universidad = etUniv.getText().toString();

        //Se reunen los datos como un conjunto.
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("idCar", carrera);
        registro.put("idUniv", universidad);

        //Se hace la actualización en la BD.
        int cant = bd.update("alumno", registro, "cu=" +  cu, null);

        if (cant == 1)
            Toast.makeText(this, "Se modificaron los datos del alumno",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error: no existe un alumno con esta "+
                    "clave única", Toast.LENGTH_LONG).show();
    }

}
