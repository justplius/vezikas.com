package com.justplius.vezikas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Document;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;


public class GoogleMapsActivity extends SherlockFragmentActivity  {

	ActionBar mActionBar;
	// Sàraðas kuriame bus laikomi kelio trajektorijos taðkai
	// skirti kodavimui ir dekodavimui
      List<Overlay> mapOverlays;
    // Geo taðkai reikalingi tiesiogiai ivertinti kryptingumà
      GeoPoint point1, point2;
    // Vietos nustatymas
      LocationManager locManager;
    // Apraðymui pieðiamo objekto
      Drawable drawable;
    //Dokumento objektas skirtas padëti atlikti Http tipu marðruto apskaièiavimus
      Document document;
    // Klasës objektas skirtas atlikti visus maðruto skaièiavimus
      GMapV2GetRouteDirection v2GetRouteDirection;
    // Ilguma ir platuma pradinës pozicijos
      LatLng fromPosition;
    // Ilguma ir platuma galutinës pozicijos
      LatLng toPosition;
    // Google þemëlapio pavirðius
      GoogleMap mGoogleMap;
    // Markeriui apraðomi nustatymai
      MarkerOptions markerOptions;
      MarkerOptions markerOptions2;
    // Vietos nustatymo duomenims laikyti
      Location location ;
    // Galutinio tasko adresas 
      String myAddress;
      String myAddress2;
    // Galutinio tasko miestas
      String myCity;
      String myCity2;
    // Pradzios tasko duomenys
      Double pirmasi;
      Double pirmasp;
    // Galutinio tasko duomenys
      Double antrasi;
      Double antrasp;
      
      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_google_map);            
                v2GetRouteDirection = new GMapV2GetRouteDirection();
            // rodomas þemëlapio fragmentas, kad tiktø seniems árenginiams kartu su Support biblioteka
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.google_map_fragment);
          	// gaunamas þemëlapio fragmentas perteikiamas google þemëlapiui
            mGoogleMap = supportMapFragment.getMap();
            // Ájungiame Mtlocation funkcijà Google þemëlapyje
            //mGoogleMap.setMyLocationEnabled(true);
            // Ájungiame kameros priartinimo ir nutolinimo kontrolæ
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            // Ájungiame kompasà pagal GPS
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            // Aktyvinamas mygtukas nustatyti esamai vietai
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            // Ájungiame visus ámanomus gestus
            mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
            // 
            mGoogleMap.setTrafficEnabled(true);
            // Akryviname automatiná priartinimà
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            // Suteikiamos naujos opcijos markeriui
            markerOptions = new MarkerOptions();
            // PAVYZDINIAI DUOMENYS
            pirmasi=55.92941850113908;
            pirmasp= 23.322129249572754;
            antrasi=54.899513279793524;
            antrasp=23.9132022857666;
            // Pradinës pozicijos duomenys
            fromPosition = new LatLng(pirmasi,pirmasp);
            // Galinës pozicijos duomenys
            toPosition = new LatLng(antrasi, antrasp);
            // Centruojame kamerà ir nukreipiame jà á Lietuvà
            CameraUpdate center=
            CameraUpdateFactory.newLatLng(new LatLng(55.169438,
                    		23.881275));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(7);
            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);
 // IÐ TAÐKØ KOORDINAÈIØ SURANDAMAS VIETOVËS PAVADINIMAS 
 //------------------------------------------------------------------------           
myCity=GetCityName(pirmasi,pirmasp);
myCity2=GetCityName(antrasi,antrasp);
myAddress=GetAddress(pirmasi,pirmasp);
myAddress2=GetAddress(antrasi,antrasp);
//----------------------Apraðome naujà marðruto uþduotá
           GetRouteTask getRoute = new GetRouteTask();
           // Vykdome uþduotá
           getRoute.execute();      
           
           String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
      }
// Suranda miesto varda      
      public String GetCityName(Double coord1,Double coord2)
      {  	 
    	  String CityReturn;
    	  Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
          try 
          {
          List<Address> addresses = geocoder.getFromLocation(coord1, coord2, 1);
          if(addresses != null) 
          {
       	// Nustatomas miestas
        	  CityReturn = addresses.get(0).getLocality();
          }
          // jei pnerado adreso
          else
          {
        	  CityReturn = "Google Maps neaptinka miesto";
          }
         } 
         // jei ávyko klaida
          catch (IOException e) 
         {
          // TODO Auto-generated catch block
          e.printStackTrace();
          CityReturn = "Google Maps neaptinka miesto";
         }
          return CityReturn;
      }
  // Suranda adresa
      public String GetAddress(Double coord1,Double coord2)
      {  	 
    	  String AddressReturn;
    	  Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
          try 
          {
          List<Address> addresses = geocoder.getFromLocation(coord1, coord2, 1);
          if(addresses != null) 
          {
       	// Nustatomas pilnas adresas
           Address returnedAddress = addresses.get(0);
           StringBuilder strReturnedAddress = new StringBuilder("Adresas:\n");
           for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) 
           {
            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
           }
           AddressReturn = strReturnedAddress.toString();
          }
          // jei pnerado adreso
          else
          {
        	  AddressReturn = "Google maps neaptinka adreso";
          }
         } 
         // jei ávyko klaida
          catch (IOException e) 
         {
          // TODO Auto-generated catch block
          e.printStackTrace();
          AddressReturn = "Google maps neaptinka adreso";
         }
          return AddressReturn;
      }
  // Privati klasë skirta atlikti kelio braiþymo funkcijas    
      class GetRouteTask extends AsyncTask<String, Void, String> {
            // Metamas praneðimas kol kraunamas kelias
            private ProgressDialog Dialog;
            String response = "";
            @Override
            protected void onPreExecute() {
                  Dialog = new ProgressDialog(GoogleMapsActivity.this);
                  Dialog.setMessage("Sudaromas kelias...");
                  Dialog.show();
            }
            // Perduodams praneðimas kad kelias sëkmingai sudarytas
            @Override
            protected String doInBackground(String... urls) {
                  //Get All Route values
                        document = v2GetRouteDirection.getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_DRIVING);
                        response = "Success";
                  return response;
            }

            @Override
            protected void onPostExecute(String result) {
                  mGoogleMap.clear();
                  if(response.equalsIgnoreCase("Success")){
                  ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
                  PolylineOptions rectLine = new PolylineOptions().width(7).color(
                              Color.RED);
                  for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                  }
                  // Pridedamas kelias á þemëlapá
                  mGoogleMap.addPolyline(rectLine);
                  markerOptions.position(toPosition);
                  markerOptions.draggable(false);
                  markerOptions.title(myCity2);
                  markerOptions.snippet(myAddress2);
                  mGoogleMap.addMarker(markerOptions);
                  markerOptions.position(fromPosition);
                  markerOptions.draggable(false);
                  markerOptions.title(myCity);
                  markerOptions.snippet(myAddress);
                  mGoogleMap.addMarker(markerOptions);
                  }
                  // po sudëliojamø
                  Dialog.dismiss();
            }
      }
      @Override
      protected void onStop() {
            super.onStop();
            finish();
      }
      
      
}