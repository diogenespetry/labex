package br.com.dirs.joaoemaria;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost.Settings;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Fotografar extends Activity {

	public static final int REQUISICAO_CAMERA = 100;
	public static final int REQUISICAO_GALERIA = 200;
	ImageView imgFotoCamera = null;
	ProgressBar barra = null;
	EditText ednome = null;
	LocationManager locManager = null;
	static String longi = null;
	static String lati = null;
	static String url = "nada";
	Boolean mostrou = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografar);
		
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		imgFotoCamera = (ImageView) findViewById(R.id.imageViewretorno);
		ednome = (EditText) findViewById(R.id.etLocal);
		barra = (ProgressBar) findViewById(R.id.progress);
		
			LocationListener ouvinteGPS = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				if(mostrou){
					Context context = getBaseContext();
					final Intent poke = new Intent();
		            poke.setClassName("com.android.settings", 
		                "com.android.settings.widget.SettingsAppWidgetProvider");
		            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		            poke.setData(Uri.parse("3"));
		            context.sendBroadcast(poke);
				}
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				lati = String.valueOf( location.getLatitude());
				longi = String.valueOf(location.getLongitude());
				if(lati!=null && mostrou){
					Toast toast = Toast.makeText(getBaseContext(), "Localização recebida", Toast.LENGTH_LONG);
					toast.show();
					barra.setProgress(100);
					mostrou= true;
				}
			}
		};
		
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ouvinteGPS);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotografar, menu);
		return true;
	}
	
	public void iniciaCamera(View v){
		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(it, REQUISICAO_CAMERA);
	}
	
	public void salvar(View v){
		if(lati == null){
			Toast toast = Toast.makeText(getBaseContext(), "Aguarde GPS encontrar a localização", Toast.LENGTH_LONG);
			toast.show();
			barra.setProgress(50);
		}else{
			Fotos f = new Fotos();
			f.nome = ednome.getText().toString();
			f.url = url;
			f.latitude = lati;
			f.longitude = longi;
			ednome.setText("");
			FotosDBAdapter db = new FotosDBAdapter(this);
			db.open();
			db.inserirFoto(f);
			db.close();
			
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode != RESULT_OK){
			return;
		}
		
		if(requestCode == REQUISICAO_CAMERA){
			Toast.makeText(getBaseContext(), "Veio Foto!", Toast.LENGTH_SHORT).show();
			Bitmap imagemCamera = (Bitmap) data.getExtras().get("data");
			imgFotoCamera.setImageBitmap(imagemCamera);
			
			try{
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				imagemCamera.compress(Bitmap.CompressFormat.PNG, 80, bytes);
				
				File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fotos");
				if(!dir.exists()){
					dir.mkdir();
				}
				
				Random r5 = new Random(); 
				url = String.valueOf(r5.nextInt(49));
				
				File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fotos/"+ url +".png");
				FileOutputStream out = new FileOutputStream(arquivo);
				out.write(bytes.toByteArray());
				out.close();
				Log.d("file", "Salvo");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}

}
