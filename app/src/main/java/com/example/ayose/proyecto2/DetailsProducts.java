package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsProducts extends Activity {
TextView nom,gen,precio,desc;
ImageView img;
Spinner cant;
Button aña,vol;
String cods ;
int codsi;
int canfinal;
String nombres;
    int precios;
String generos;
String imagens;
String descrips;
Bundle b;
String codu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_products);
        if(b==null){
            b = new Bundle();
        }
        b = getIntent().getExtras();
        codu = b.getString("nom");
        cods = b.getString("Cods");
        codsi = Integer.parseInt(cods);
        String sql = "CodS Like '" + codsi + "'";
        DBShop psh = new DBShop(DetailsProducts.this);
        SQLiteDatabase db = psh.getReadableDatabase();
        String [] campos = new String[]{"NombreS","Precio","Genero","Imagen","Descripcion"};
        Cursor cr = db.query("Series",campos,sql,null,null,null,null,null);
        cr.moveToNext();
        nombres = cr.getString(cr.getColumnIndex("NombreS"));
        precios = cr.getInt(cr.getColumnIndex("Precio"));
        generos = cr.getString(cr.getColumnIndex("Genero"));
        imagens = cr.getString(cr.getColumnIndex("Imagen"));
        descrips = cr.getString(cr.getColumnIndex("Descripcion"));
        nom = (TextView) findViewById(R.id.nom);
        nom.setText(nombres);
        gen = (TextView) findViewById(R.id.gen);
        gen.setText(generos);
        precio = (TextView)findViewById(R.id.precio);
        precio.setText(String.valueOf(precios)+"€");
        desc = (TextView)findViewById(R.id.desc);
        desc.setText(descrips);
        if(imagens.contains("http://")){
                img = (ImageView)findViewById(R.id.foto);
                new ImageDownloader(img).execute(imagens);

        } else {
            img = (ImageView)findViewById(R.id.foto);
            img.setImageResource(R.drawable.series);
        }
        db.close();
        psh.close();
        String[] items = new String[]{"1","2","3","4","5"};
        cant = (Spinner)findViewById(R.id.cant);
        cant.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items));
        cant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String canfalso = (String) adapterView.getItemAtPosition(position);
                canfinal = Integer.parseInt(canfalso.trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });
        aña = (Button)findViewById(R.id.carr);
        aña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBShop psh = new DBShop(DetailsProducts.this);
                SQLiteDatabase db = psh.getWritableDatabase();
                ContentValues insertar = new ContentValues();
                String[] campos = new String[]{"Count(*)", "CodC"};
                Cursor cr = db.query("Carrito", campos, null, null, null, null, null);
                cr.moveToLast();
                int a = cr.getInt(1);
                if (a >= 1) {
                    insertar.put("CodC", a + 1);
                } else {
                    insertar.put("CodC", 1);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                insertar.put("Fecha", dateFormat.format(date));
                insertar.put("Producto",nombres);
                insertar.put("Cantidad",canfinal);
                insertar.put("Precio", precios);
                long error =  db.insert("Carrito", null, insertar);
                cr.close();
                psh.close();
                db.close();
                Intent ini = new Intent(DetailsProducts.this,CatalogUser.class);
                b.putString("nom",codu);
                ini.putExtras(b);
                startActivity(ini);
                finish();
            }
        });

        vol = (Button)findViewById(R.id.can);
        vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(DetailsProducts.this,CatalogUser.class);
                b.putString("nom",codu);
                ini.putExtras(b);
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
