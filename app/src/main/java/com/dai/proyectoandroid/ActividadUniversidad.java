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

public class ActividadUniversidad extends AppCompatActivity {

    //Variables de la actividad

    private EditText etNombre, etClave, etEstado;
    public SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_universidad);


        //Permite leer y escribir en la BD.
        bd = MainActivity.bd;

        //Asocia las cajas de texto a las variables correspondientes.
        etNombre= (EditText)findViewById(R.id.editText);
        etClave= (EditText)findViewById(R.id.editText2);
        etEstado= (EditText)findViewById(R.id.editText3);
    }

    //Método para dar de alta los datos de una Universidad
    public void  alta(View v){
        //Obtiene y almacena los datos tecleados.
        String nom= etNombre.getText().toString();
        String id= etClave.getText().toString();
        String est= etEstado.getText().toString();


        //En ContentValues se guardan los valores para contruir la tabla de la base de datos.
        ContentValues reg= new ContentValues();
        reg.put("nombre", nom);
        reg.put("idUniv", id);
        reg.put("estado", est);


        //Hace la inserción de los datos en la tabla.
        try {
            if (bd != null) {
                long resul= bd.insert("univ", null, reg); //Se hace la inserción
                Toast.makeText(this, "Se insertó en universidad", Toast.LENGTH_LONG).show(); //Se avisa al usuario si la inserción fue exitosa
                limpia(v); // Se limpian las cajas de texto
            }
            else
                Toast.makeText(this, "BD cerrada", Toast.LENGTH_LONG).show(); //Se avisa al usuario que la inserción falló
        }
        catch (SQLException err) {
            Toast.makeText(this, "Error: "+err.getMessage(), Toast.LENGTH_LONG).show(); //Se avisa al usuario que la inserción falló
        }
    }

    /*
    Metodo para regresar a la pantalla principal
    */
    public void pantallaInicio(View v){
        Intent iPantallaInicio = new Intent(this, MainActivity.class);
        startActivity(iPantallaInicio);
    }

    // Método para modificar los datos de una Universidad que ya está en la BD
    public void mod(View v){
        //Toma el contenido de las cajas de texto.
        String nom= etNombre.getText().toString();
        String id= etClave.getText().toString();
        String est= etEstado.getText().toString();

        //Se reunen los datos como un conjunto.
        ContentValues registro = new ContentValues();
        registro.put("nombre", nom);
        registro.put("Estado", est);


        //Se hace la actualización en la BD.
        int cant = bd.update("univ", registro, "idUniv=" +  id, null);
        //Se revisa si la actualización fue exitosa
        if (cant == 1)
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_LONG).show();//Se avisa que la actualización fue exitosa
        else
            Toast.makeText(this, "Error: no existe la universidad con esta "+id +"clave", Toast.LENGTH_LONG).show(); //Se avisa que la actualización falló
    }

    // Método para obtener los datos almacenados de una Universidad a partir de su clave
    public void  cons(View v){

        //Obtiene la clave única de la universidad.
        String id= etClave.getText().toString();

        //Ejecuta la consulta.
        Cursor fila= bd.rawQuery("select * from univ where idUniv=" + id, null);

        //Recorre el cursor para acceder al resultado de la consulta
        if (fila.moveToFirst()) { //Escribe los datos en su respectivo lugar.
            etClave.setText(fila.getString(0));
            etNombre.setText(fila.getString(1));
            etEstado.setText(fila.getString(2));
            fila.close();
        }
        else
            Toast.makeText(this, "Error: no existe la id= "+id, Toast.LENGTH_LONG).show(); //Avisa que la clave no es corecta
    }

    //Método para limpiar las cajas de texto
    public void limpia(View v) {
        etNombre.setText("");
        etClave.setText("");
        etEstado.setText("");

    }
}
