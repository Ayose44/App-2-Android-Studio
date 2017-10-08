package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ModifySerie extends Activity {
ListView lista;
    Bundle b ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_serie);
        DBShop psh = new DBShop(ModifySerie.this);
        SQLiteDatabase db = psh.getReadableDatabase();
        String [] campos = new String[]{"NombreS"};
        Cursor cr = db.query("Series",campos,null,null,null,null,null,null);
        int i = 0;
        int total = cr.getCount();
        String [] totalnames = new String[total];
        while(cr.moveToNext()){
            totalnames[i] = cr.getString(cr.getColumnIndex("NombreS"));
            i++;
        }
        cr.close();
        db.close();
        lista = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModifySerie.this, android.R.layout.simple_list_item_1, android.R.id.text1, totalnames);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)lista.getItemAtPosition(position);
                DBShop psh2 = new DBShop(ModifySerie.this);
                SQLiteDatabase db2 = psh2.getReadableDatabase();
                String [] campos2 = new String[]{"CodS"};
                String sql = "(NombreS like '" + selected + "')";
                Cursor cr2 = db2.query("Series",campos2,sql,null,null,null,null,null);
                cr2.moveToNext();
                int cods = cr2.getInt(0);
                String cods2 = String.valueOf(cods);
                Intent ini = new Intent(ModifySerie.this,DetailsModify.class);
                ini.putExtra("CodS",cods2);
                startActivity(ini);
                cr2.close();
                db2.close();
                psh2.close();
                finish();

            }
        });
    }
}
