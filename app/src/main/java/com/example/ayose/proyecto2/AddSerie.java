package com.example.ayose.proyecto2;

import android.app.Activity;
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

public class AddSerie extends Activity {
    EditText name,desc,imag,pri;
    Spinner spi,spi2;
    Button add;
    String [] generos = new String[5];
    String [] oferta = new String[2];
    String finalname="";
    String finaldesc="";
    String finalimag="";
    String finalgen="";
    String newname="";
    String finaloffer="";
    int finalpri=0;
    DBShop psh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);
        generos[0] = "Comedy";
        generos[1] = "Action";
        generos[2] = "Fantasy";
        generos[3] = "Terror";
        generos[4] = "Drama";
        spi = (Spinner) findViewById(R.id.spi);
        spi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos));

        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                finalgen = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });

        oferta[0]="Yes";
        oferta[1]="No";
        spi2 = (Spinner)findViewById(R.id.spi2);
        spi2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oferta));

        spi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                finaloffer = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });

        add = (Button)findViewById(R.id.but);
        psh =  new DBShop(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = (EditText)findViewById(R.id.name);
                desc= (EditText) findViewById(R.id.desc);
                pri = (EditText) findViewById(R.id.prize);
                imag = (EditText)findViewById(R.id.ima);
                finalname = name.getText().toString();
                finaldesc = desc.getText().toString();
                finalimag=imag.getText().toString();
                String stringpri;
                stringpri= pri.getText().toString();
                if(!stringpri.equals("")) {
                    finalpri = Integer.parseInt(stringpri);
                }
                if(finaldesc.equals("") || finalname.equals("") || finalpri==0 || finalimag.equals("")){
                    ToastFill();
                } else{
                    SQLiteDatabase bd = psh.getWritableDatabase();
                    ContentValues insertar = new ContentValues();
                    String[] campos = new String[]{"Count(*)", "CodS"};
                    Cursor cr = bd.query("Series", campos, null, null, null, null, null);
                    cr.moveToLast();
                    int a = cr.getInt(1);
                    if (a >= 1) {
                        insertar.put("CodS", a + 1);
                    } else {
                        insertar.put("CodS", 1);
                    }


                        insertar.put("NombreS", finalname);
                        insertar.put("Descripcion",finaldesc);
                        insertar.put("Imagen",finalimag);
                        insertar.put("Genero", finalgen);
                        insertar.put("Oferta", finaloffer);
                        insertar.put("Precio", finalpri);
                        long error =  bd.insert("Series", null, insertar);
                        cr.close();
                        psh.close();
                        bd.close();
                        Intent ini = new Intent(AddSerie.this,PanelAdmin.class);
                        startActivity(ini);
                        finish();
                    }


                }



        });


    }

    public void ToastFill() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastfill,
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
