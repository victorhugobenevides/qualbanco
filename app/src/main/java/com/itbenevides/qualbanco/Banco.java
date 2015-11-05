package com.itbenevides.qualbanco;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 27/10/2015.
 */
public class Banco implements DAO.IDAO{
    public long codigo;
    public String nome;
    public String codigobanco;


    public Banco() {
        this.codigo=0;
        this.nome = "";
        this.codigobanco="";
    }

    @Override
    public ContentValues getContentValues() {
        // TODO Auto-generated method stub
        ContentValues contentvalues = new ContentValues();


        contentvalues.put("nome", nome);
        contentvalues.put("codigobanco", codigobanco);

        return contentvalues;
    }


    public static void onCreate(SQLiteDatabase sqlitedatabase, int versao) {
        sqlitedatabase.execSQL("drop table if exists banco");
        String sql = "CREATE TABLE banco (codigo integer primary key" +
                ",nome text null, codigobanco text null)";

        sqlitedatabase.execSQL(sql);
    }

    public static List<Banco> consultar(DAO dao) {
        List<Banco> list = null;
        Cursor cursor = null;

        try {
            String sql = "select * from banco order by nome";
            cursor = dao.getWritableDatabase().rawQuery(sql, null);

            list = new ArrayList<Banco>();
            while (cursor.moveToNext()) {
                list.add(carregar(cursor));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }


    private static Banco carregar(Cursor cursor) {
        Banco mes = new Banco();

        mes.setId_(cursor.getInt(0));
        mes.setNome(cursor.getString(1));
        mes.setCodigobanco(cursor.getString(2));

        return mes;
    }

    public String getCodigobanco() {
        return codigobanco;
    }

    public void setCodigobanco(String codigobanco) {
        this.codigobanco = codigobanco;
    }

    @Override
    public String getTabela() {
        return "banco";
    }

    @Override
    public long getId_() {
        return codigo;
    }

    @Override
    public void setId_(long id_) {
        this.codigo=id_;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
