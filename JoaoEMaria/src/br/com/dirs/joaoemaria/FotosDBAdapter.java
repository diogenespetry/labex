package br.com.dirs.joaoemaria;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class FotosDBAdapter {

	private final Context ctx;
	private fotosDBHelper dbHelper;
	private SQLiteDatabase db;
	
	private static final String NOME_BANCO = "meusContatos.db";
	private static final String NOME_TABELA = "FOTOS";
	private static final int VERSAO_BANCO = 20;
	
	private static final String COL_ID = "ID";
	private static final String COL_NOME = "NOME";
	private static final String COL_URL = "URL";
	private static final String COL_LATITUDE = "LATITUDE";
	private static final String COL_LONGITUDE = "LONGITUDE";
	
	private static final String SQL_TABELA = "create table " + NOME_TABELA +
			"(" + COL_ID + " integer primary key autoincrement," +
			COL_NOME + " text null," +
			COL_URL + " text not null," +
			COL_LATITUDE + " text not null," +
			COL_LONGITUDE + " text not null)";
	
	public FotosDBAdapter(Context ctx) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		dbHelper = new fotosDBHelper(ctx, NOME_BANCO, null, VERSAO_BANCO);
	}
	
	public FotosDBAdapter open(){
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		db.close();
	}
	
	public int inserirFoto(Fotos c){
		ContentValues valores = new ContentValues();
		valores.put(COL_NOME, c.getNome());
		valores.put(COL_URL, c.getUrl());
		valores.put(COL_LATITUDE, c.getLatitude());
		valores.put(COL_LONGITUDE, c.getLongitude());
		return (int) db.insert(NOME_TABELA, null, valores);
	}
	
	public Fotos recuperarFoto(int id){
		Fotos c = new Fotos();
		
		String[] colunas = {COL_ID, COL_NOME, COL_URL, COL_LATITUDE,COL_LONGITUDE};
		Cursor resultados = db.query(NOME_TABELA, colunas, 
				COL_ID + " = " + id, null, null, null, COL_ID);
		
		resultados.moveToFirst();
		c.setId(resultados.getInt(resultados.getColumnIndex(COL_ID)));
		c.setNome(resultados.getString(resultados.getColumnIndex(COL_NOME)));
		c.setUrl(resultados.getString(resultados.getColumnIndex(COL_URL)));
		c.setLatitude(resultados.getString(resultados.getColumnIndex(COL_LATITUDE)));
		c.setLongitude(resultados.getString(resultados.getColumnIndex(COL_LONGITUDE)));
		return c;
	}
	
	public int editarFoto(int id, Fotos c){
		ContentValues valores = new ContentValues();
		valores.put(COL_NOME, c.getNome());
		valores.put(COL_URL, c.getUrl());
		valores.put(COL_LATITUDE, c.getLatitude());
		valores.put(COL_LONGITUDE, c.getLongitude());
		
		return db.update(NOME_TABELA, valores, COL_ID + " = " + id, null);
	}
	
	public int deletarTodos(){
		return db.delete(NOME_TABELA, null, null);
	}
	
	public int deletarFoto(int id){
		return db.delete(NOME_TABELA, COL_ID + " = " + id, null);
	}
	
	public ArrayList<Fotos> listaFotos(){
		ArrayList<Fotos> lista = new ArrayList<Fotos>();
		String[] colunas = {COL_ID, COL_NOME, COL_URL, COL_LATITUDE,COL_LONGITUDE};
		Cursor resultados = db.query(NOME_TABELA, colunas, null, null, null, null, COL_ID);
		Fotos c;
		if(resultados.getCount() > 0){
			resultados.moveToFirst();
			do{
				c = new Fotos();
				c.setId(resultados.getInt(resultados.getColumnIndex(COL_ID)));
				c.setNome(resultados.getString(resultados.getColumnIndex(COL_NOME)));
				c.setUrl(resultados.getString(resultados.getColumnIndex(COL_URL)));
				c.setLatitude(resultados.getString(resultados.getColumnIndex(COL_LATITUDE)));
				c.setLongitude(resultados.getString(resultados.getColumnIndex(COL_LONGITUDE)));
				
				lista.add(c);
			}while(resultados.moveToNext());
		}
		
		return lista;
	}
	
	private static class fotosDBHelper extends SQLiteOpenHelper{
		
		public fotosDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.d("DATABASE","onCreate!");
			db.execSQL(SQL_TABELA);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.d("DATABASE","onUpgrade!");
			db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
			onCreate(db);
		}
		
	}
	
	
	
}
