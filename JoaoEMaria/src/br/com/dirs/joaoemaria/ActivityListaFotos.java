package br.com.dirs.joaoemaria;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

public class ActivityListaFotos extends Activity implements OnItemClickListener, OnItemLongClickListener{
	
	ListView listFotoss;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_fotos);
		
		listFotoss = (ListView) findViewById(R.id.listFotos);
		
		FotosDBAdapter db = new FotosDBAdapter(getBaseContext());
		db.open();
		ArrayList<Fotos> lista = db.listaFotos();
		db.close();
		
		AdaptadorListaFotos adaptador = new AdaptadorListaFotos(getBaseContext(), lista);
		
		listFotoss.setAdapter(adaptador);
		listFotoss.setOnItemClickListener(this);
		listFotoss.setOnItemLongClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lista_fotos, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		TextView txtIdFotos = (TextView) arg1.findViewById(R.id.txtIdFotos);
		
		Intent it = new Intent(getBaseContext(), ActivityListaFotos.class);
	    it.putExtra("ID", txtIdFotos.getText().toString());
		finish();
		startActivity(it);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		TextView txtIdFotos = (TextView) arg1.findViewById(R.id.txtIdFotos);
		
		int id = Integer.parseInt(txtIdFotos.getText().toString());
		
		FotosDBAdapter db = new FotosDBAdapter(getBaseContext());
		db.open();
		db.deletarFoto(id);
		db.close();
		
		finish();
		startActivity(getIntent());
		
		return false;
	}

}
