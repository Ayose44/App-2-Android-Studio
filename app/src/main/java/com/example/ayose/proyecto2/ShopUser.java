package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopUser extends Activity {
ListView list;
String codu;
Bundle b;
Button p,c;
int longitud;
Date fecha;
TextView pagar;
String [] producto;
int [] cantidad;
int [] precio;
int total = 0;
String fechafalsa;
String [] mostrar;
int codc[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_user);
        list = (ListView)findViewById(R.id.list);
        pagar= (TextView)findViewById(R.id.pagar);

        if(b==null){
            b = new Bundle();
        }
        b = getIntent().getExtras();
        codu = b.getString("nom");
        DBShop psh = new DBShop(ShopUser.this);
        SQLiteDatabase db = psh.getWritableDatabase();
        String[] campos = new String[]{"Fecha", "Producto" , "Cantidad" , "Precio","CodC" };
        Cursor cr = db.query("Carrito", campos, null, null, null, null, null);
        longitud = cr.getCount();
        int i = 0;
        producto = new String [longitud];
        cantidad = new int [longitud];
        precio = new int [longitud];
        codc = new int[longitud];
        mostrar = new String[longitud];
        while (cr.moveToNext()){
            fechafalsa = cr.getString(cr.getColumnIndex("Fecha"));
            producto[i] = cr.getString(cr.getColumnIndex("Producto"));
            cantidad[i] = cr.getInt(cr.getColumnIndex("Cantidad"));
            codc [i] = cr.getInt(cr.getColumnIndex("CodC"));
            precio[i] = cr.getInt(cr.getColumnIndex("Precio"));
            mostrar[i] = producto[i] + "   |" + "  " + String.valueOf(cantidad[i]) + "   |" + "  " + String.valueOf(precio[i])+"€";
            total = total + (precio[i]*cantidad[i]);
            i++;
        }
        db.close();
        cr.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShopUser.this, android.R.layout.simple_list_item_1, android.R.id.text1, mostrar);
        list.setAdapter(adapter);
        pagar.setText("Total a pagar:" + total+"€");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = position;
                int codci = codc[selected];
                DBShop psh = new DBShop(ShopUser.this);
                SQLiteDatabase db = psh.getWritableDatabase();
                db.delete("Carrito","CodC Like '"+codci+"'",null);
                String[] campos = new String[]{"Fecha", "Producto" , "Cantidad" , "Precio","CodC" };
                Cursor cr = db.query("Carrito", campos, null, null, null, null, null);
                longitud = cr.getCount();
                int i = 0;
                producto = new String [longitud];
                cantidad = new int [longitud];
                precio = new int [longitud];
                codc = new int[longitud];
                mostrar = new String[longitud];
                total=0;
                while (cr.moveToNext()){
                    fechafalsa = cr.getString(cr.getColumnIndex("Fecha"));
                    producto[i] = cr.getString(cr.getColumnIndex("Producto"));
                    cantidad[i] = cr.getInt(cr.getColumnIndex("Cantidad"));
                    codc [i] = cr.getInt(cr.getColumnIndex("CodC"));
                    precio[i] = cr.getInt(cr.getColumnIndex("Precio"));
                    mostrar[i] = producto[i] + "   |" + "  " + String.valueOf(cantidad[i]) + "   |" + "  " + String.valueOf(precio[i])+"€";
                    total = total + (precio[i]*cantidad[i]);
                    i++;
                }
                db.close();
                cr.close();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShopUser.this, android.R.layout.simple_list_item_1, android.R.id.text1, mostrar);
                list.setAdapter(adapter);
                pagar.setText(getResources().getString(R.string.total) + total+"€");

                p = (Button)findViewById(R.id.but);
                p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean sdDisponible = false;
                        boolean sdAccesoEscritura = false;
                        String estado = Environment.getExternalStorageState();
                        if (estado.equals(Environment.MEDIA_MOUNTED)) {
                            sdDisponible = true;
                            sdAccesoEscritura = true;
                        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                            sdDisponible = true;
                            sdAccesoEscritura = false;
                        } else {
                            sdDisponible = false;
                            sdAccesoEscritura = false;
                        }

                        if ((sdDisponible) && (sdAccesoEscritura)) {
                            try {
                                File ruta_sd = Environment.getExternalStorageDirectory();
                                File f = new File(ruta_sd.getAbsolutePath(), "Factura.txt");
                                f.createNewFile();
                                BufferedWriter buw = new BufferedWriter(new OutputStreamWriter(openFileOutput("Factura.txt", Context.MODE_PRIVATE)));
                                buw.write(getResources().getString(R.string.factu) + fechafalsa + "\n");
                                for (int i=0;i<producto.length;i++) {
                                    buw.write( producto[i] + "   |" + "  " + String.valueOf(cantidad[i]) + "   |" + "  " + String.valueOf(precio[i])+"€");
                                }
                                buw.write(getResources().getString(R.string.total) + total+"€");
                                buw.write(getResources().getString(R.string.suj)+codu);
                                buw.write(getResources().getString(R.string.problem));
                                buw.close();

                            } catch (Exception ex) {
                                Log.e("Ficheros", "Error al escribir fichero o leer la tarjeta SD");
                            }
                        } else {
                            Toast.makeText(ShopUser.this, "La tarjeta SD no esta o no se puede grabar en ella", Toast.LENGTH_LONG).show();

                        }
                        Toast.makeText(ShopUser.this,"Pago realizado con existo y factura creada en la sd",Toast.LENGTH_LONG).show();
                        DBShop psh = new DBShop(ShopUser.this);
                        SQLiteDatabase db = psh.getWritableDatabase();
                        db.execSQL("delete from Carrito");
                        psh.close();
                        db.close();
                        Intent ini = new Intent(ShopUser.this,MainActivity.class);
                        startActivity(ini);
                        finish();
                    }
                });


                c = (Button)findViewById(R.id.clean);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBShop psh = new DBShop(ShopUser.this);
                        SQLiteDatabase db = psh.getWritableDatabase();
                        db.execSQL("delete from Carrito");
                        psh.close();
                        db.close();
                    }
                });
            }
        });

        p = (Button)findViewById(R.id.but);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sdDisponible = false;
                boolean sdAccesoEscritura = false;
                String estado = Environment.getExternalStorageState();
                if (estado.equals(Environment.MEDIA_MOUNTED)) {
                    sdDisponible = true;
                    sdAccesoEscritura = true;
                } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                    sdDisponible = true;
                    sdAccesoEscritura = false;
                } else {
                    sdDisponible = false;
                    sdAccesoEscritura = false;
                }

                if ((sdDisponible) && (sdAccesoEscritura)) {
                    try {
                        File ruta_sd = Environment.getExternalStorageDirectory();
                        File f = new File(ruta_sd.getAbsolutePath(), "Factura.txt");
                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(f));
                        os.write(getResources().getString(R.string.factu)+ "  " + fechafalsa + "\n");
                        for (int i=0;i<producto.length;i++) {
                          os.write( producto[i] + "   |" + "  " + String.valueOf(cantidad[i]) + "   |" + "  " + String.valueOf(precio[i])+"€"+"\n");
                        }
                        os.write(getResources().getString(R.string.total) + total+"€"+"\n");
                        os.write(getResources().getString(R.string.suj)+codu+"\n");
                        os.write(getResources().getString(R.string.problem)+"\n");
                        os.flush();
                        os.close();

                    } catch (Exception ex) {
                        Log.e("Ficheros", "Error al escribir fichero o leer la tarjeta SD");
                    }
                } else {
                    Toast.makeText(ShopUser.this, "SD not found", Toast.LENGTH_LONG).show();

                }
                ToastPaid();
                DBShop psh = new DBShop(ShopUser.this);
                SQLiteDatabase db = psh.getWritableDatabase();
                db.execSQL("delete from Carrito");
                psh.close();
                db.close();
                Intent ini = new Intent(ShopUser.this,MainActivity.class);
                startActivity(ini);
                finish();
            }
        });


        c = (Button)findViewById(R.id.clean);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBShop psh = new DBShop(ShopUser.this);
                SQLiteDatabase db = psh.getWritableDatabase();
                db.execSQL("delete from Carrito");
                psh.close();
                db.close();
                Intent ini = new Intent(ShopUser.this,PanelUser.class);
                startActivity(ini);
                finish();
            }
        });
    }
    public void ToastPaid() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastpago,
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
