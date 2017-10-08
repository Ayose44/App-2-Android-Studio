package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPass extends Activity {
String [] nuevapass = new String[]{"negan","rick","morgan","heisenberg","jonsnow","khaleesi","macmillan","gordon","cameron","bosworth","earl","randy","elliot","donna","pickman","cersei","stark","erlich","richard","dinesh","gilfoyle"};
int tar;
String tarfal="";
String nom="";
EditText tarje,nombre;
TextView renovado;
Button ren,vol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        tarje = (EditText)findViewById(R.id.tar);
        nombre = (EditText)findViewById(R.id.nom);
        ren = (Button)findViewById(R.id.ren);
        vol = (Button)findViewById(R.id.volv);
        renovado = (TextView)findViewById(R.id.fina);
        ren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarfal = tarje.getText().toString();
                nom = nombre.getText().toString();
                if (tarfal.equals("") || nom.equals("")){
                    renovado.setText(getResources().getString(R.string.comp));
                    renovado.setTextColor(Color.YELLOW);
                } else{
                    tar = Integer.parseInt(tarfal.trim());
                    DBShop psh = new DBShop(ForgotPass.this);
                    int ale = (int)(Math.random()*nuevapass.length);
                    String finalpass = nuevapass[ale];
                    SQLiteDatabase db = psh.getReadableDatabase();
                    ContentValues insertar = new ContentValues();
                    insertar.put("Pass",finalpass);
                    long error = db.update("Usuarios",insertar,"Nombre like '"+nom + "' and Tarjeta="+tar,null);
                    if(error!=1){
                        renovado.setText(getResources().getString(R.string.olvi2));
                        renovado.setTextColor(Color.RED);
                    }else{
                        renovado.setText(getResources().getString(R.string.newpa) + finalpass);
                        renovado.setTextColor(Color.GREEN);
                    }
                }
            }
        });

        vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(ForgotPass.this,MainActivity.class);
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
