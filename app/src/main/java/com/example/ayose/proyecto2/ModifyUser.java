package com.example.ayose.proyecto2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
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

public class ModifyUser extends AppCompatActivity {
    EditText nom,pass,tarjeta;
    Spinner pref;
    String recogida;
    Bundle b;
    int codu,tar;
    Button correct,delete,back;
    String prefe,passw;
    String [] generos = new String[5];
    String finalgen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        if(b==null){
            b = new Bundle();
        }
        b = getIntent().getExtras();
        recogida = b.getString("nom");
        DBShop psh = new DBShop(ModifyUser.this);
        SQLiteDatabase bd = psh.getReadableDatabase();
        String sql = "(Nombre like  '" + recogida + "')";
        String[] campos = new String[]{"CodU","Preferencias","Pass","Tarjeta"};
        Cursor cr = bd.query("Usuarios", campos, sql, null, null, null, null);
        cr.moveToNext();
         codu = cr.getInt(cr.getColumnIndex("CodU"));
         prefe = cr.getString(cr.getColumnIndex("Preferencias"));
         passw = cr.getString(cr.getColumnIndex("Pass"));
         tar = cr.getInt(cr.getColumnIndex("Tarjeta"));
        nom = (EditText)findViewById(R.id.nom);
        pass= (EditText) findViewById(R.id.pass);
        tarjeta = (EditText) findViewById(R.id.tarjeta);
        nom.setText(recogida);
        pass.setText(passw);
        tarjeta.setText(String.valueOf(tar));
        generos[0] = "Comedy";
        generos[1] = "Action";
        generos[2] = "Fantasy";
        generos[3] = "Terror";
        generos[4] = "Drama";

        pref = (Spinner) findViewById(R.id.spi);
        pref.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos));
        ArrayAdapter myAdap = (ArrayAdapter) pref.getAdapter();
        int spinnerPosition = myAdap.getPosition(prefe);
        pref.setSelection(spinnerPosition);
        pref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                finalgen = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });

        correct = (Button)findViewById(R.id.correct);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String  nombre2 = nom.getText().toString();
             String   contra2 = pass.getText().toString();
             String  recogida2 = tarjeta.getText().toString();
              int tar2 = 0;
                if(!recogida2.equals("")) {
                    tar2 = Integer.parseInt(recogida2);
                }
                if(nombre2.equals("") || contra2.equals("") || tar2==0){
                    Toast.makeText(ModifyUser.this,"Debes de rellenar los campos",Toast.LENGTH_LONG).show();
                } else{
                    DBShop psh2 = new DBShop(ModifyUser.this);
                    SQLiteDatabase bd2 = psh2.getWritableDatabase();
                    ContentValues insertar = new ContentValues();
                    insertar.put("Nombre", nombre2);
                    insertar.put("Pass", contra2);
                    insertar.put("Tarjeta", tar2);
                    insertar.put("Preferencias", finalgen);
                    bd2.update("Usuarios",insertar,"Nombre like '"+recogida + "'",null);
                    psh2.close();
                    bd2.close();
                    Toast.makeText(ModifyUser.this, "Usuario modificado", Toast.LENGTH_LONG).show();
                    Intent ini = new Intent(ModifyUser.this, MainActivity.class);
                    startActivity(ini);
                    finish();
                }
            }
        });

        delete = (Button)findViewById(R.id.del);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBShop psh2 = new DBShop(ModifyUser.this);
                SQLiteDatabase bd2 = psh2.getWritableDatabase();
                bd2.delete("Usuarios","Nombre Like '"+recogida+"'",null);
                bd2.close();
                psh2.close();
                Toast.makeText(ModifyUser.this, "Usuario eliminado", Toast.LENGTH_LONG).show();
                Intent ini = new Intent(ModifyUser.this, MainActivity.class);
                startActivity(ini);
                finish();
            }
        });

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(ModifyUser.this,PanelUser.class);
                startActivity(ini);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.my_custom_toast,
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
