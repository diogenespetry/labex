package br.com.dirs.joaoemaria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;



public class InicialActivity extends Activity {

	public static final int REQUISICAO_CAMERA = 100;
	public static final int REQUISICAO_GALERIA = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicial);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicial, menu);
		return true;
	}
	
	
	public void abrefotografar(View v){
		Intent it = new Intent(getBaseContext(), Fotografar.class);
		startActivity(it);		
	}
	
	public void abreVerlocal(View v){
		Intent it = new Intent(getBaseContext(), ActivityListaFotos.class);
		startActivity(it);		
	}

}
