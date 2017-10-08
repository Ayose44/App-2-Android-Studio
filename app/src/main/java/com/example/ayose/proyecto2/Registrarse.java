package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registrarse extends Activity {
EditText nom,pass,tarjeta;
Spinner pref;
Button reg,ba;
String newname="";
String [] generos = new String[5];
String generoelegido;
String  nombre="" ;
    String contra="";
    int tar =0;
    String recogida="";
    DBShop psh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        reg = (Button)findViewById(R.id.re);
        generos[0] = "Comedy";
        generos[1] = "Action";
        generos[2] = "Fantasy";
        generos[3] = "Terror";
        generos[4] = "Drama";
        pref = (Spinner) findViewById(R.id.spi);
        pref.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos));

        pref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                generoelegido = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });



        reg = (Button)findViewById(R.id.re);
        psh =  new DBShop(this);

            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nom = (EditText)findViewById(R.id.nom);
                    pass= (EditText) findViewById(R.id.pass);
                    tarjeta = (EditText) findViewById(R.id.tarjeta);
                    nombre = nom.getText().toString();
                    contra = pass.getText().toString();
                    recogida = tarjeta.getText().toString();
                    if(!recogida.equals("")) {
                        tar = Integer.parseInt(recogida);
                    }
                    if(nombre.equals("") || contra.equals("") || tar==0){
                        Toast1();
                    } else{
                    SQLiteDatabase bd = psh.getWritableDatabase();
                    ContentValues insertar = new ContentValues();
                    String[] campos = new String[]{"Count(*)", "CodU"};
                    Cursor cr = bd.query("Usuarios", campos, null, null, null, null, null);
                    cr.moveToLast();
                    int a = cr.getInt(1);
                    if (a >= 1) {
                        insertar.put("CodU", a + 1);
                    } else {
                        insertar.put("CodU", 1);
                    }
                    String[]campos2 = new String[]{"Nombre"};
                    Cursor cr2 = bd.query("Usuarios",campos2,null,null,null,null,null);
                         newname="";
                        String dbname="";
                    while(cr2.moveToNext()){
                        dbname=cr2.getString(cr2.getColumnIndex("Nombre"));
                        if(dbname.equals(nombre)) {
                            newname="";
                        }else{
                            newname = nombre;

                        }
                    }
                        cr2.close();

                        if(newname.equals("")) {
                            Toast2();
                    }else{
                            insertar.put("Nombre", newname);
                            insertar.put("Preferencias", generoelegido);
                            insertar.put("Pass", contra);
                            insertar.put("Tarjeta", tar);
                            bd.insert("Usuarios", null, insertar);

                            cr.close();
                            psh.close();
                            bd.close();
                            Toast3();
                            Intent ini = new Intent(Registrarse.this,MainActivity.class);
                            startActivity(ini);
                            finish();
                    }


                }


        }
            });



    }


    public void Toast1() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toast2,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void Toast2() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toast3,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    public void Toast3() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toast4,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

}
