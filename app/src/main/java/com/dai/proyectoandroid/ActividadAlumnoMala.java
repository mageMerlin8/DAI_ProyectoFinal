package com.dai.proyectoandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadAlumnoMala extends AppCompatActivity {
  //Variables de clase.
  private EditText etClave, etNombre, etCarrera, etUniv;
  private AdminSQLiteOpenHelper admin;
  public static SQLiteDatabase bd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_alumno_mala);

    Intent i3 = getIntent();

    bd = MainActivity.bd;
    /*
    //Establece el nombre y la versión de la BD.
    admin= new AdminSQLiteOpenHelper(this, "Ejemplo", null, 1);

    //Permite leer y escribir con la BD.
    bd= admin.getWritableDatabase();
    Toast.makeText(this, "Abrí la BD", Toast.LENGTH_LONG).show();
    */
    //Asociar las cajas de texto a las variables correspondientes.
    etClave= (EditText)findViewById(R.id.etAlumnoClave);
    etNombre= (EditText)findViewById(R.id.etAlumnoNombre);
    etCarrera= (EditText)findViewById(R.id.etAlumnoCarrera);
    etUniv= (EditText)findViewById(R.id.etAlumnoUniv);
  }

  /**
   * Da de alta un alumno en la tabla respectiva.
   */
  public void alta(View v) {

    //Obtiene los datos tecleados.
    String cu= etClave.getText().toString();
    String nombre= etNombre.getText().toString();
    String carr= etCarrera.getText().toString();
    String univ= etUniv.getText().toString();

    //En ContentValues se guardan los valores a insertar.
    ContentValues reg= new ContentValues();
    reg.put("cu", cu);
    reg.put("nombre", nombre);
    reg.put("carrera", carr);
    reg.put("universidad", univ);

    //Hace la inserción de los datos en la tabla.
    try {
      if (bd != null) {
        long resul= bd.insert("alumnos", null, reg);
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
  public void consulta(View v) {

    //Obtiene la clave única del alumno.
    String cu= etClave.getText().toString();

    //Ejecuta la consulta.
    Cursor fila= bd.rawQuery("select * from alumnos where cu=" +
                              cu, null);

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
  public void baja(View v) {

    //Se lee la clave única del alumno a dar de baja de la BD.
    String cu = etClave.getText().toString();

    //Se ejecuta la baja.
    int cant = bd.delete("alumnos", "cu=" + cu, null);

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
  public void modificacion(View v) {

    //Toma el contenido de las cajas de texto.
    String cu = etClave.getText().toString();
    String nombre = etNombre.getText().toString();
    String carrera = etCarrera.getText().toString();
    String universidad = etUniv.getText().toString();

    //Se reunen los datos como un conjunto.
    ContentValues registro = new ContentValues();
    registro.put("nombre", nombre);
    registro.put("carrera", carrera);
    registro.put("universidad", universidad);

    //Se hace la actualización en la BD.
    int cant = bd.update("alumnos", registro, "cu=" +  cu, null);

    if (cant == 1)
      Toast.makeText(this, "Se modificaron los datos del alumno",
          Toast.LENGTH_LONG).show();
    else
      Toast.makeText(this, "Error: no existe un alumno con esta "+
          "clave única", Toast.LENGTH_LONG).show();
  }

  /**
   * Limpia las cajas de texto
   */
  public void limpia(View v) {
    etClave.setText("");
    etNombre.setText("");
    etCarrera.setText("");
    etUniv.setText("");
  }

}










