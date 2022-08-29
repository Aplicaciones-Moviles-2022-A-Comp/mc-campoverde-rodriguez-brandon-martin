package com.example.movcompbmcr2022a

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions


class FGoogleMaps : AppCompatActivity(){

    private lateinit var mMap: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        solicitarPermisos()
        iniciarLogicaMapa()
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            if (googleMap != null){
                mMap = googleMap
                establecerConfiguracionMapa()

                val quicentro = LatLng(-0.176125, -78.480208)
                val titulo = "Quicentro"
                val zoom = 17f

                anadirMarcador(quicentro,titulo)
                moverCamaraConZoom(quicentro,zoom)

                // LINEA
                val poliLineaUno = googleMap
                    .addPolyline(
                        PolylineOptions()
                            .clickable(true)
                            .add(
                                LatLng(-0.1759187040647396, -78.48506472421384),
                                LatLng(-0.17632468492901104, -78.48265589308046),
                                LatLng(-0.17746143130181483, -78.4770533307815)
                            )
                    )


                poliLineaUno.tag = "linea-1" // <- ID

                //Poligono
                val poligonoUno = googleMap
                    .addPolygon(
                        PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1771546902239471, -78.48344981495214),
                            LatLng(-0.17968981486125768, -78.48269198043828),
                            LatLng(-0.17710958124147777, -78.48142892291516)
                        )
                    )
                poligonoUno.fillColor = -0xc771c4
                poligonoUno.tag = "poligono-2" // <- ID)
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mMap.setOnPolygonClickListener {
            Log.i("mapa", "setOnPolygonClickListener ${it}")
        }

        mMap.setOnPolylineClickListener {
            Log.i("mapa", "setOnPolylineClickListener ${it}")
        }

        mMap
            .setOnMarkerClickListener {
                Log.i("mapa", "setOnMarkerClickListener ${it}")
                return@setOnMarkerClickListener true
            }

        mMap
            .setOnCameraMoveListener {
                Log.i("mapa", "setOnCameraMoveListener")
            }

        mMap
            .setOnCameraMoveStartedListener {
                Log.i("mapa", "setOnCameraMoveStartedListener ${it}")
            }

        mMap
            .setOnCameraIdleListener {
                Log.i("mapa", "setOnCameraIdleListener")
            }
    }

    fun anadirMarcador(latLng: LatLng, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun moverCamaraConZoom(latLng: LatLng,zoom:Float = 10f){
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mMap){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mMap.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )

        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED

        if (tienePermisos){
            permisos=true
        } else {
            ActivityCompat.requestPermissions(
                this,
                //Arreglo de permisos
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

}