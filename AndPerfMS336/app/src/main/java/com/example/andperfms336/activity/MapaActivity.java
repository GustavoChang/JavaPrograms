package com.example.andperfms336.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.andperfms336.R;
import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.dao.VwVarreduraDAO;
import com.example.andperfms336.modelo.TbEquipe;
import com.example.andperfms336.modelo.VwVarredura;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaActivity extends FragmentActivity implements OnMarkerClickListener, OnMapReadyCallback {

    VwVarreduraDAO vwVarreduraDAO;
    GoogleMap mMap;
    private Marker marker;
    VwVarredura modelo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_varredura);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @Override
    public void onMapReady(GoogleMap mMap) {
        this.mMap = mMap;
        try {
            Intent it = getIntent();
            vwVarreduraDAO = new VwVarreduraDAO(BaseDados.getBD(this));

            LatLng latLng = null;

            List<VwVarredura> lista = vwVarreduraDAO.listar();

            for(VwVarredura list: lista) {
                latLng = new LatLng(Double.parseDouble(list.getCoordX()), Double.parseDouble(list.getCoordY()));

                String bacia = "";
                String area = "";
                String nome = "";
                if(list.getBacia() != null) {
                    bacia = list.getBacia();
                }
                if(list.getArea() != null) {
                    area = list.getArea();
                }
                if(list.getNome() != null) {
                    nome = list.getNome();
                }

                int imagem = 0;

                if(list.getIdPvTp() == 1){
                    //ESGOTO
                    if(list.getRecebidoServidor().equals("N")){
                        imagem = R.drawable.ic_pv_r; //

                    }
                    else if (list.getRecebidoServidor().equals("A")){
                        imagem = R.drawable.ic_pv_b; //
                    }
                    else if (list.getRecebidoServidor().equals("P")){
                        imagem = R.drawable.ic_pv_y; //
                    }
                    else {
                        imagem = R.drawable.ic_pv_g; //
                    }
                }
                else if(list.getIdPvTp() == 2){
                    //GAP
                    if(list.getRecebidoServidor().equals("N")){
                        imagem = R.drawable.ic_gap_r; //

                    }
                    else if (list.getRecebidoServidor().equals("A")){
                        imagem = R.drawable.ic_gap_y; //
                    }
                    else if (list.getRecebidoServidor().equals("P")){
                        imagem = R.drawable.ic_gap_b; //
                    }
                    else {
                        imagem = R.drawable.ic_gap_g; //
                    }
                }
                else{
                    //TL
                    if(list.getRecebidoServidor().equals("N")){
                        imagem = R.drawable.ic_tl_r; //

                    }
                    else if (list.getRecebidoServidor().equals("A")){
                        imagem = R.drawable.ic_tl_y; //
                    }
                    else if (list.getRecebidoServidor().equals("P")){
                        imagem = R.drawable.ic_tl_b; //
                    }
                    else {
                        imagem = R.drawable.ic_tl_g; //
                    }
                }


                marker = mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title("Singularidade " + nome)
                                .snippet("Bacia: " + bacia + ""+" Area: " + area)
                                .icon(BitmapDescriptorFactory.fromResource(imagem))

                );
                //marker.setSnippet();
                marker.setTag(list);

            }


            if(latLng != null) {

                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng)
                        .zoom(16.0f) // Zoom
                        .bearing(90) // Orientation of the camera to east
                        .tilt(30) // Tilt of the camera to 30 degrees
                        .build(); // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.setOnMarkerClickListener(this);
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    modelo = (VwVarredura) marker.getTag();
                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa mto god = "+modelo.getIdPv()+"|"+modelo.getNome());
                    return false;
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if(modelo.getIdPvTp() == 1){
                        //PV
                        Intent i = new Intent(MapaActivity.this, ListaPvActivity.class);
                        i.putExtra("vwVarredura", modelo);
                        startActivity(i);
                    }
                    else if(modelo.getIdPvTp() == 2){
                        //GAP
                        Intent i = new Intent(MapaActivity.this, ListaGapActivity.class);
                        i.putExtra("vwVarredura", modelo);
                        startActivity(i);
                    }
                    else{
                        //TL - FAZ NADA
                    }

                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa mto god demaisssssss = "+modelo.getIdPv()+"|"+modelo.getNome());
                }
            });

//            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
//                @Override
//                public void onPolylineClick(Polyline polyline) {
//
//                    AlertDialog.Builder dialogo = new AlertDialog.Builder(AppMapaObra.this);
//                    dialogo.setTitle("Coordenada");
//                    dialogo.setMessage("" + polyline.getPoints());
//                    dialogo.setNeutralButton("OK", null);
//                    dialogo.show();
//                }
//            });
        }
        catch (Exception e) {
            System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii errrrrro"+e.toString());
            //exibirMensagem("Falha(2)", e.toString());
        }

    }


//    public void onInfoWindowClick(Marker marker) {
//
//        AlertDialog.Builder dialogo = new AlertDialog.Builder(AppMapaObra.this);
//        dialogo.setTitle("Coordenada");
//        dialogo.setMessage("" + marker.getPosition() + " titulo " + marker.getTitle());
//        dialogo.setNeutralButton("OK", null);
//        dialogo.show();
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {

        }
        else if(resultCode == 1) {
            setResult(1, new Intent());
            finish();
        }
        else if(resultCode == 100) {
            setResult(100);
            finish();
        }
    }

//    public void exibirMensagem(String titulo, String texto) {
//
//        AlertDialog.Builder dialogo = new AlertDialog.Builder(AppMapaObra.this);
//        dialogo.setTitle(titulo);
//        dialogo.setMessage(texto);
//        dialogo.setNeutralButton("OK", null);
//        dialogo.show();
//    }

    @Override
    public boolean onMarkerClick(Marker marker) {

//        AlertDialog.Builder dialogo = new AlertDialog.Builder(AppMapaObra.this);
//        dialogo.setTitle("Coordenada");
//        dialogo.setMessage("" + marker.getPosition());
//        dialogo.setNeutralButton("OK", null);
//        dialogo.show();

        return false;
    }


}
