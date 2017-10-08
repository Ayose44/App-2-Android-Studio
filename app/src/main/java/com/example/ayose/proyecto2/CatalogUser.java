package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CatalogUser extends Activity {
ListView todo,ofertas,recomienda;
    int [] cods;
    int [] cods2;
    int [] cods3;
    String codu;
    Bundle b;
    String [] mostrar2;
    String [] mostrar3;
    String [] mostrar;
    String [] totalnames3;
    String [] totalnames2;
    String [] totalnames;
    int [] totalprizes3;
    int [] totalprizes2;
    int [] totalprizes;
    String [] totalgenre3;
    String [] totalgenre;
    String [] totalgenre2;
    String [] totaloffers3;
    String [] totaloffers2;
    String [] totaloffers;
    String sprize;
    String sprize2;
    String sprize3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_user);
        Resources res = getResources();
        TabHost tabulado = (TabHost)findViewById(android.R.id.tabhost);
        tabulado.setup();
        if(b==null){
            b = new Bundle();
        }
        b = getIntent().getExtras();
        codu = b.getString("nom");
        TabHost.TabSpec spec = tabulado.newTabSpec("Todo");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getResources().getString(R.string.todo));
        DBShop psh = new DBShop(CatalogUser.this);
        SQLiteDatabase db = psh.getReadableDatabase();
        String [] campos = new String[]{"NombreS","Precio","Genero","Oferta","CodS"};
        Cursor cr = db.query("Series",campos,null,null,null,null,null,null);
        int i = 0;
        int total = cr.getCount();
        mostrar = new String[total];
        totalnames = new String[total];
        totalprizes = new int[total];
        totalgenre = new String[total];
        totaloffers = new String[total];
        cods = new int[total];
        //Toast.makeText(CatalogUser.this,String.valueOf(total),Toast.LENGTH_LONG).show();

        while(cr.moveToNext()){
            totalnames[i] = cr.getString(cr.getColumnIndex("NombreS"));
            totalprizes[i] = cr.getInt(cr.getColumnIndex("Precio"));
            totalgenre[i] = cr.getString(cr.getColumnIndex("Genero"));
            totaloffers[i] = cr.getString(cr.getColumnIndex("Oferta"));
            sprize = String.valueOf(totalprizes[i]);
            cods [i] = cr.getInt(cr.getColumnIndex("CodS"));
            mostrar [i] = cr.getString(cr.getColumnIndex("NombreS")) + "  " + "|  " + cr.getString(cr.getColumnIndex("Genero")) + "  " + "|  " + sprize+"€";
            i++;
        }
        tabulado.addTab(spec);
        todo = (ListView)findViewById(R.id.prod);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CatalogUser.this, android.R.layout.simple_list_item_1, android.R.id.text1, mostrar);
        todo.setAdapter(adapter);
        todo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = position;
                int codsi = cods[selected];
                String selec = String.valueOf(codsi);
                Intent ini = new Intent(CatalogUser.this,DetailsProducts.class);
                b.putString("nom",codu);
                b.putString("Cods",selec);
                ini.putExtras(b);
                startActivity(ini);
                finish();
            }
        });





        spec = tabulado.newTabSpec("Ofertas");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getResources().getString(R.string.ofer));
        String sql7 = "Oferta like 'Yes'";
        Cursor cr7 = db.query("Series",campos,sql7,null,null,null,null,null);
        int total2 = cr7.getCount();
        if (total2>0) {
            mostrar2 = new String[total2];
            cods2 = new int[total2];
            totalnames2 = new String[total2];
            totalgenre2 = new String[total2];
            totaloffers2 = new String [total2];
            totalprizes2 = new int [total2];
            i=0;
            while(cr7.moveToNext()){
                   totaloffers2[i] = cr7.getString(cr7.getColumnIndex("Oferta"));
                    totalnames2[i] = cr7.getString(cr7.getColumnIndex("NombreS"));
                    totalprizes2[i] = cr7.getInt(cr7.getColumnIndex("Precio"));
                    totalgenre2[i] = cr7.getString(cr7.getColumnIndex("Genero"));
                    sprize2 = String.valueOf(totalprizes2[i]);
                    cods2[i] = cr7.getInt(cr7.getColumnIndex("CodS"));
                    mostrar2[i] = cr7.getString(cr7.getColumnIndex("NombreS")) + "  " + "|  " + cr7.getString(cr7.getColumnIndex("Genero")) + "  " + "|  " + sprize2 + "€";

                i++;
            }

            ofertas = (ListView) findViewById(R.id.prod2);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CatalogUser.this, android.R.layout.simple_list_item_1, android.R.id.text1, mostrar2);
            ofertas.setAdapter(adapter2);
            ofertas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int selected2 = position;
                    int codsi2 = cods2[selected2];
                    String selec2 = String.valueOf(codsi2);
                    Intent ini = new Intent(CatalogUser.this, DetailsProducts.class);
                    b.putString("Cods",selec2);
                    b.putString("nom",codu);
                    ini.putExtras(b);
                    startActivity(ini);
                    finish();
                }
            });
        }else {
            ToastNoOffer();
        }

        tabulado.addTab(spec);




        spec = tabulado.newTabSpec("Recomendados");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getResources().getString(R.string.rec));


        String sql = "(Nombre like  '" + codu + "')";
        String [] campos2 = new String[]{"Preferencias"};
        Cursor cr2 = db.query("Usuarios",campos2,sql,null,null,null,null,null);
        cr2.moveToNext();
        String genusu = cr2.getString(cr2.getColumnIndex("Preferencias"));

        String sql2 = "(Genero like  '" + genusu + "')";
        Cursor cr3 = db.query("Series",campos,sql2,null,null,null,null,null);
        int total3 = cr3.getCount();
        if(total3>0) {
            mostrar3 = new String[total3];
            totalnames3 = new String[total3];
            totalprizes3 = new int[total3];
            totalgenre3 = new String[total3];
            totaloffers3 = new String[total3];
            cods3 = new int[total3];

            i = 0;
            while (cr3.moveToNext()) {
                totalnames3[i] = cr3.getString(cr3.getColumnIndex("NombreS"));
                totalprizes3[i] = cr3.getInt(cr3.getColumnIndex("Precio"));
                totalgenre3[i] = cr3.getString(cr3.getColumnIndex("Genero"));
                totaloffers3[i] = cr3.getString(cr3.getColumnIndex("Oferta"));
                sprize3 = String.valueOf(totalprizes3[i]);
                cods3[i] = cr3.getInt(cr3.getColumnIndex("CodS"));
                mostrar3[i] = cr3.getString(cr3.getColumnIndex("NombreS")) + "  " + "|  " + cr3.getString(cr3.getColumnIndex("Genero")) + "  " + "|  " + sprize3 + "€";
                i++;
            }
            recomienda = (ListView) findViewById(R.id.prod3);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(CatalogUser.this, android.R.layout.simple_list_item_1, android.R.id.text1, mostrar3);
            recomienda.setAdapter(adapter3);
            recomienda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int selected3 = position;
                    int codsi3 = cods[selected3];
                    String selec3 = String.valueOf(codsi3);
                    Intent ini = new Intent(CatalogUser.this, DetailsProducts.class);
                    b.putString("Cods",selec3);
                    b.putString("nom",codu);
                    ini.putExtras(b);
                    startActivity(ini);
                    finish();
                }
            });
        } else{
            ToastNoGenre();
        }

        tabulado.addTab(spec);

        tabulado.setCurrentTab(0);
    }

    public void ToastNoGenre(){
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastgenre,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);
        // Set the Text to show in TextView
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void ToastNoOffer(){
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toastoferta,
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
