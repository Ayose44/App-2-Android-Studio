package com.example.ayose.proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PanelUser extends Activity {
TextView wel;
Button catalog,modify,logoff,shop;
String usu;
Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_user);
        wel = (TextView)findViewById(R.id.text);
        catalog = (Button) findViewById(R.id.ver);
        logoff = (Button)findViewById(R.id.exit);
        modify = (Button)findViewById(R.id.mod);
        shop = (Button)findViewById(R.id.carr);
        if(b==null){
            b = new Bundle();
        }
        b = getIntent().getExtras();
        usu = b.getString("nom");
        wel.setText(getResources().getString(R.string.wel) + " " +  usu);
        catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(PanelUser.this,CatalogUser.class);
                b.putString("nom",usu);
                ini.putExtras(b);
                startActivity(ini);
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(1);
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(PanelUser.this,ModifyUser.class);
                b.putString("nom",usu);
                ini.putExtras(b);
                startActivity(ini);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(PanelUser.this,ShopUser.class);
                b.putString("nom",usu);
                ini.putExtras(b);
                startActivity(ini);
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
