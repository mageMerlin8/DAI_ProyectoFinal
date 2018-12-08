package com.dai.proyectoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadEstadisticas extends AppCompatActivity {

    private EditText et1;
    private TextView tw1;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_estadisticas);
        //toma control de la aplicacion
        Intent intent = getIntent();
        //toma la base de datos de la actividad principal
        bd = MainActivity.bd;
        et1=(EditText)findViewById(R.id.info);
        tw1=(TextView)findViewById(R.id.respText);
    }

    public void buscaUniversidades(View v){
        //toma un string del edit text
        String est=et1.getText().toString();
        Cursor fila=bd.rawQuery("select * from univ where estado = '"+est+"'", null);
        String resp="Las universidades de ese estado son: ";
        int fin;

        if(fila.getCount() > 0 && fila.moveToFirst()){
            fin=fila.getCount();
            for ( int i=0;i<fin-1;i++ ){
                fila.move(i);
                resp=resp+fila.getString(1)+" ,";
            }
            fila.moveToLast();
            resp=resp+fila.getString(1)+".";
            tw1.setVisibility(View.VISIBLE);
            tw1.setText(resp.toString());
        }
        else{
            if(est==""){
                Toast.makeText(this, "Favor de escribir un estado",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"No se encontraron universidades en ese estado",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void buscarCarreras(View v){
        String carrera =et1.getText().toString();
        Cursor fila=bd.rawQuery("select * from univ where idUniv not in(select idUniv from alumno where idCar in(select idCar from carrera where nombre ='"+ carrera+"'))",null);
        String resp= "Las carrera no es impartida por las universidades: ";
        int fin;

        if(fila.moveToFirst()){
            fin=fila.getCount();
            for(int i=0;i<fin-1;i++){
                fila.move(i);
                resp=resp+fila.getString(1)+" ,";
            }
            fila.moveToLast();
            resp=resp+fila.getString(1)+".";
            tw1.setVisibility(View.VISIBLE);
            tw1.setText(resp.toString());
        }
        else{
            if(carrera==""){
                Toast.makeText(this, "Favor de escribir una carrera",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"No se encontraron universidades que no impartan la carrera",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void totalCarreras(View v){
        String universidad=et1.getText().toString();
        Cursor fila=bd.rawQuery("select * from carrera where idCar in(select idCar from alumno where idUniv in(select idUniv from univ where nombre='"+universidad +"'))",null);
        String resp="El numero de carrareras que imparte esa universidad es ";

        if(universidad !=""){
            resp=resp+fila.getCount();
            tw1.setVisibility(View.VISIBLE);
            tw1.setText(resp.toString());
        }
        else{
            Toast.makeText(this,"Favor de ingresar una universidad",Toast.LENGTH_LONG).show();
        }
    }

    public void topNumAlumnos(View v){
        Cursor f2 = bd.rawQuery("select * from alumno where cu > 0", null);
        Cursor fila=bd.rawQuery("select nombre from univ where idUniv in(select idUniv alumnos from alumno group by idUniv order by count(cu) desc limit 3 )",null);
        String resp= "Las 3 universidades con mayor numero de alumnos son: ";

        for(int i=0; i<2; i++){
            fila.move(i);
            resp=resp+fila.getString(1)+" ,";
        }
        fila.moveToLast();
        resp=resp+fila.getString(1)+".";
        tw1.setVisibility(View.VISIBLE);
        tw1.setText(resp.toString());
    }
}
