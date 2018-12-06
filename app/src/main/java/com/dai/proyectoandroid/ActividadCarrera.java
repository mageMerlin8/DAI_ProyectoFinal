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

public class ActividadCarrera extends AppCompatActivity {

    //Variables de clase
    private SQLiteDatabase bd;
    private EditText etClave,etNombre,etCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_carrera);

        //Toma el control del programa
        Intent i2 = getIntent();

        //Asigna a la variable local la referencia para usar la base de datos
        bd = MainActivity.bd;

        //Asocia las cajas de texto a las variables locales
        etClave = (EditText) findViewById(R.id.etCarreraClave); //Clave
        etNombre = (EditText) findViewById(R.id.etCarreraNombre); //Nombre
        etCred = (EditText) findViewById(R.id.etCarreraCred); //Creditos

    }

    /**
     * Método para hacer la consulta de la carrera dada la clave de la universidad
     */

    public void consultaC(View v){

        //Recupera la clave del campo de texto
        String clave = etClave.getText().toString();

        //Ejecuta la consulta
        Cursor fila = bd.rawQuery("select * from carrera where idCar = " + clave + " ", null);

        //Recorre el cursor para acceder al resultado de la consulta
        if (fila.moveToFirst()) {
            etClave.setText(fila.getString(0));
            etNombre.setText(fila.getString(1));
            etCred.setText(fila.getString(2));
        } else
            Toast.makeText(this, "Error: no existe la clave = " + clave + " ", Toast.LENGTH_LONG).show();
    }

    /**
     * Método para hacer la alta de una carrera
     */

    public void altaC(View v){
        //Obtener los datos tecleados
        String clave = etClave.getText().toString();
        String nombre = etNombre.getText().toString();
        String creditos = etCred.getText().toString();

        //En ContentValues se guardan los valores a insertar
        ContentValues reg = new ContentValues();
        reg.put("idCar", clave);
        reg.put("nombre", nombre);
        reg.put("creds", creditos);

        //Hace la inserción de los datos en la tabla
        try {
            if (bd != null){
                long resul = bd.insert("carrera", null, reg);
                Toast.makeText(this, "Se insertó en carrera", Toast.LENGTH_LONG).show();
                limpia(v);
            } else
                Toast.makeText(this, "BD Cerrada", Toast.LENGTH_LONG).show();
        } catch (SQLException err){
            Toast.makeText(this, "Error: " + err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Método para hacer la modificación de una carrera
     */

    public void modificaC(View v){

        //Obtenemos la clave única de la carrera
        String clave = etClave.getText().toString();
        String nombre = etNombre.getText().toString();
        String creds = etCred.getText().toString();

        //En ContentValues se guardan los valores a insertar
        ContentValues reg = new ContentValues();
        reg.put("idCar", clave);
        reg.put("nombre", nombre);
        reg.put("creds", creds);

        //Modifca los datos en la tabla carrera
        try {
            if (bd != null){
                int resul = bd.update("carrera", reg, "idCar = " + clave + " ",null);
                Toast.makeText(this, "Se modificaron los datos en la BD", Toast.LENGTH_LONG).show();
                limpia(v);
            } else
                Toast.makeText(this, "BD Cerrada", Toast.LENGTH_LONG).show();
        } catch (SQLException err){
            Toast.makeText(this, "Error: " + err.getMessage(),Toast.LENGTH_LONG).show();
        }
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
        etCred.setText("");
    }
}