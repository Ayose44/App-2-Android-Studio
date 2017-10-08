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

public class DetailsModify extends Activity {
TextView serie;
    EditText name,desc,imag,pri;
    Spinner spi,spi2;
    Button add,del,back;
    int gener;
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
    int  cods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_modify);
         cods =Integer.parseInt(getIntent().getStringExtra("CodS"));
        psh =  new DBShop(this);
        SQLiteDatabase bd = psh.getReadableDatabase();
        String[] campos = new String[]{"NombreS","Precio","Genero","Oferta","Descripcion","Imagen"};
        String sql = "(CodS like  '" + cods + "')";
        Cursor cr = bd.query("Series", campos, sql, null, null, null, null);
        cr.moveToNext();
        String nombre = cr.getString(0);
        String gen = cr.getString(2);
        String ofe = cr.getString(3);
        String desche = cr.getString(4);
        int  priz = cr.getInt(1);
        String imagen = cr.getString(5);
        name = (EditText)findViewById(R.id.name);
        desc= (EditText) findViewById(R.id.desc);
        imag = (EditText)findViewById(R.id.ima);
        pri = (EditText)findViewById(R.id.prize);
        name.setText(nombre);
        desc.setText(desche);
        pri.setText(String.valueOf(priz));
        imag.setText(imagen);

        generos[0] = "Comedy";
        generos[1] = "Action";
        generos[2] = "Fantasy";
        generos[3] = "Terror";
        generos[4] = "Drama";

        spi = (Spinner) findViewById(R.id.spi);
        spi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos));
        ArrayAdapter myAdap = (ArrayAdapter) spi.getAdapter();
        int spinnerPosition = myAdap.getPosition(gen);
        spi.setSelection(spinnerPosition);
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
        ArrayAdapter myAdap2 = (ArrayAdapter) spi2.getAdapter();
        int spinnerPosition2 = myAdap2.getPosition(ofe);
        spi2.setSelection(spinnerPosition2);
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

        serie = (TextView)findViewById(R.id.serie);
        serie.setText(nombre);

        add=(Button)findViewById(R.id.correct);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalname = name.getText().toString();
                finaldesc = desc.getText().toString();
                finalimag = imag.getText().toString();
                String stringpri;
                stringpri = pri.getText().toString();
                if (!stringpri.equals("")) {
                    finalpri = Integer.parseInt(stringpri);
                }
                if (finaldesc.equals("") || finalname.equals("") || finalpri == 0 || finalimag.equals("")) {
                    ToastFill();
                } else {
                    SQLiteDatabase bd = psh.getWritableDatabase();
                    ContentValues insertar = new ContentValues();
                    insertar.put("NombreS", finalname);
                    insertar.put("Descripcion", finaldesc);
                    insertar.put("Imagen", finalimag);
                    insertar.put("Genero", finalgen);
                    insertar.put("Oferta", finaloffer);
                    insertar.put("Precio", finalpri);
                    bd.update("Series",insertar,"CodS="+cods,null);
                    psh.close();
                    bd.close();
                    ToastModify();
                    Intent ini = new Intent(DetailsModify.this, PanelAdmin.class);
                    startActivity(ini);
                    finish();
                }
            }
        });

        del = (Button)findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase bd = psh.getWritableDatabase();
                bd.delete("Series","CodS Like '"+cods+"'",null);
                bd.close();
                psh.close();
                ToastDeleted();
                Intent ini = new Intent(DetailsModify.this, PanelAdmin.class);
                startActivity(ini);
                finish();
            }
        });

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(DetailsModify.this,PanelAdmin.class);
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

    public void ToastModify() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastmodifiedserie,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void ToastDeleted() {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastdeleteserie,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

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
