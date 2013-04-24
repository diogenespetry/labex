package br.com.dirs.joaoemaria;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorListaFotos extends BaseAdapter{
	Context ctx;
	ArrayList<Fotos> lista;
	
	public AdaptadorListaFotos(Context ctx, ArrayList<Fotos> lista) {
		this.ctx = ctx;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.activity_lista_fotos , null);
		
		TextView txtNome = (TextView) convertView.findViewById(R.id.txtNomeFotos);
		TextView txtUrl = (TextView) convertView.findViewById(R.id.txtUrlFotos);
		TextView txtLatitude = (TextView) convertView.findViewById(R.id.txtLatitudeFotos);
		TextView txtLongitude = (TextView) convertView.findViewById(R.id.txtLongitudeFotos);
		TextView txtId = (TextView) convertView.findViewById(R.id.txtIdFotos);
		
		Fotos c = lista.get(position);
		
		txtNome.setText(c.getNome());
		txtUrl.setText(c.getUrl());
		txtLatitude.setText(c.getLatitude());
		txtLongitude.setText(c.getLongitude());
		txtId.setText(c.getId() + "");
		
		return convertView;
	}
	
}
