package com.itbenevides.qualbanco.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 27/10/2015.
 */
public class Conta implements DAO.IDAO{
    public long codigo;
    public String nome;
    public String codigobanco;
    public String nomeBanco;
    public String codigoagencia;
    public String codigoconta;
    public String cpf;


    public Conta() {
        this.codigo=0;
        this.nome = "";
        this.codigobanco="";
        this.codigoagencia="";
        this.codigoconta="";
        this.cpf="";
    }

    @Override
    public ContentValues getContentValues() {
        // TODO Auto-generated method stub
        ContentValues contentvalues = new ContentValues();


        contentvalues.put("nome", nome);
        contentvalues.put("codigobanco", codigobanco);
        contentvalues.put("codigoagencia", codigoagencia);
        contentvalues.put("codigoconta", codigoconta);
        contentvalues.put("cpf", cpf);

        return contentvalues;
    }


    public static void onCreate(SQLiteDatabase sqlitedatabase, int versao) {
        sqlitedatabase.execSQL("drop table if exists conta");
        String sql = "CREATE TABLE conta (id_ integer primary key" +
                ",nome text null, " +
                "codigobanco text null, " +
                "codigoagencia text null, " +
                "codigoconta text null," +
                "cpf text null) ";

        sqlitedatabase.execSQL(sql);
    }

    public static List<Conta> consultar(DAO dao) {
        List<Conta> list = null;
        Cursor cursor = null;

        try {
            String sql = "select conta.id_,banco.nome as nomebanco,conta.codigobanco,conta.codigoagencia,conta.codigoconta,conta.cpf,conta.nome as nomepessoa from conta inner join banco on banco.codigobanco=conta.codigobanco group by conta.id_  order by nomebanco";
            cursor = dao.getWritableDatabase().rawQuery(sql, null);

            list = new ArrayList<Conta>();
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


    private static Conta carregar(Cursor cursor) {
        Conta conta = new Conta();

        conta.setId_(cursor.getInt(0));
        conta.setNomeBanco(cursor.getString(1));
        conta.setCodigobanco(cursor.getString(2));
        conta.setCodigoagencia(cursor.getString(3));
        conta.setCodigoconta(cursor.getString(4));
        conta.setCpf(cursor.getString(5));
        conta.setNome(cursor.getString(6));
        return conta;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodigoagencia() {
        return codigoagencia;
    }

    public void setCodigoagencia(String codigoagencia) {
        this.codigoagencia = codigoagencia;
    }

    public String getCodigoconta() {
        return codigoconta;
    }

    public void setCodigoconta(String codigoconta) {
        this.codigoconta = codigoconta;
    }

    public String getCodigobanco() {
        return codigobanco;
    }

    public void setCodigobanco(String codigobanco) {
        this.codigobanco = codigobanco;
    }

    @Override
    public String getTabela() {
        return "conta";
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
