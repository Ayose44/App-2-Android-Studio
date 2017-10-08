package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PanelAdmin extends Activity {
Button add,mod,exit;
TextView users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);
        add = (Button)findViewById(R.id.add);
        mod = (Button)findViewById(R.id.mod);
        users=(TextView)findViewById(R.id.user);
        exit=(Button)findViewById(R.id.exit);
        DBShop psh = new DBShop(this);
        SQLiteDatabase db = psh.getReadableDatabase();
        long cant = DatabaseUtils.queryNumEntries(db,"Usuarios")-1;
        db.close();
        psh.close();
        users.setText(getResources().getString(R.string.usuregi) + " " + cant + " " + getResources().getString(R.string.usuregi2));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(PanelAdmin.this,AddSerie.class);
                startActivity(ini);
            }
        });

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(PanelAdmin.this,ModifySerie.class);
                startActivity(ini);
                finish();
            }
        });



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(1);
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
