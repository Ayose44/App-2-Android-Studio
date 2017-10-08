package com.example.ayose.proyecto2;

/**
 * Created by ayose on 31/01/2017.
 */
    import android.content.Context;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.database.sqlite.SQLiteDatabase;
    import android.widget.AdapterView;

    public class DBShop extends SQLiteOpenHelper {
        private static final String DATABASE_NAME="DBShop.db";
        private static final int DATABASE_VERSION=1;
        private static final String DATABASE_CREATE = "CREATE TABLE Usuarios(CodU  Numeric(4) NOT NULL UNIQUE, Nombre VARCHAR(60) NOT NULL UNIQUE, Preferencias VARCHAR(60) NOT NULL,Pass VARCHAR(20) NOT NULL, Tarjeta NUMERIC(20) NOT NULL,CONSTRAINT PK_Usuarios PRIMARY KEY(CodU)); ";
        private static final String DATABASE_INSERT = "INSERT INTO Usuarios (CodU,Nombre,Preferencias,Pass,Tarjeta) VALUES (1,'Admin','comedia','android1234',1);";
        private static final String DATABASE_CREATE2 = "CREATE TABLE Series(CodS NUMERIC (4) NOT NULL UNIQUE, NombreS VARCHAR (100) NOT NULL , Genero VARCHAR (100), Oferta VARCHAR(2),Descripcion VARCHAR(500),Precio NUMERIC(10) NOT NULL,Imagen VARCHAR(100),CONSTRAINT PK_Series PRIMARY KEY(CodS));";
        private static final String DATABASE_CREATE3 = "CREATE TABLE Carrito (CodC NUMERIC(4) NOT NULL UNIQUE,Fecha DATE NOT NULL,Producto VARCHAR(200) NOT NULL,Cantidad NUMERIC(3) NOT NULL,Precio NUMERIC(4,2) NOT NULL,CONSTRAINT PK_Carrito PRIMARY KEY(CodC));";
        public DBShop(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }



        @Override
        public void onCreate (SQLiteDatabase database){
            database.execSQL(DATABASE_CREATE);
            database.execSQL(DATABASE_INSERT);
            database.execSQL(DATABASE_CREATE2);
            database.execSQL(DATABASE_CREATE3);
        }


        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion , int newVersion){


        }
}
