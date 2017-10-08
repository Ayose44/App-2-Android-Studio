package com.example.ayose.proyecto2;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

public class Iniciar extends Activity {
EditText user,pass;
TextView msg;
Bundle b;
Button log,ba;
String username = "";
String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);

        log = (Button)findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = (EditText)findViewById(R.id.user);
                pass = (EditText)findViewById(R.id.pass);
                msg = (TextView) findViewById(R.id.msg);
                username = user.getText().toString();
                password= pass.getText().toString();
                if(username.equals("") || password.equals("")){
                   msg.setText(getResources().getString(R.string.comp));
                   msg.setTextColor(Color.YELLOW);
                }else{
                    DBShop psh = new DBShop(Iniciar.this);
                    SQLiteDatabase db = psh.getReadableDatabase();
                    String dbname="";
                    String dbpass="";
                    int error=0;
                    String fields[] = new String[]{"Nombre","Pass"};
                    String sql = "(Nombre like '" + username + "') and (Pass like '" + password + "')";
                    Cursor cr = db.query("Usuarios",fields,sql,null,null,null,null);
                    while(cr.moveToNext()){
                        dbname = cr.getString(cr.getColumnIndex("Nombre"));
                        dbpass = cr.getString(cr.getColumnIndex("Pass"));
                        if(username.equals(dbname)){
                            error=error+1;
                            if(password.equals(dbpass)){
                                error=error+1;
                            }
                        }

                    }
                    if(error==2){
                        if(username.equals("Admin")){
                            Intent ini = new Intent(Iniciar.this,PanelAdmin.class);
                            startActivity(ini);
                            finish();
                        }else{
                            Intent ini = new Intent(Iniciar.this,PanelUser.class);
                            Bundle b = new Bundle();
                            b.putString("nom",username);
                            ini.putExtras(b);
                            startActivity(ini);
                            finish();
                        }
                    }else{
                        msg.setText(getResources().getString(R.string.no));
                        msg.setTextColor(Color.RED);
                    }
                    db.close();
                    psh.close();
                }
            }
        });

        ba = (Button) findViewById(R.id.b);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ini = new Intent(Iniciar.this,MainActivity.class);
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
