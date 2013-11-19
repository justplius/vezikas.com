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
	// S�ra�as kuriame bus laikomi kelio trajektorijos ta�kai
	// skirti kodavimui ir dekodavimui
      List<Overlay> mapOverlays;
    // Geo ta�kai reikalingi tiesiogiai ivertinti kryptingum�
      GeoPoint point1, point2;
    // Vietos nustatymas
      LocationManager locManager;
    // Apra�ymui pie�iamo objekto
      Drawable drawable;
    //Dokumento objektas skirtas pad�ti atlikti Http tipu mar�ruto apskai�iavimus
      Document document;
    // Klas�s objektas skirtas atlikti visus ma�ruto skai�iavimus
      GMapV2GetRouteDirection v2GetRouteDirection;
    // Ilguma ir platuma pradin�s pozicijos
      LatLng fromPosition;
    // Ilguma ir platuma galutin�s pozicijos
      LatLng toPosition;
    // Google �em�lapio pavir�ius
      GoogleMap mGoogleMap;
    // Markeriui apra�omi nustatymai
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
            // rodomas �em�lapio fragmentas, kad tikt� seniems �renginiams kartu su Support biblioteka
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.google_map_fragment);
          	// gaunamas �em�lapio fragmentas perteikiamas google �em�lapiui
            mGoogleMap = supportMapFragment.getMap();
            // �jungiame Mtlocation funkcij� Google �em�lapyje
            //mGoogleMap.setMyLocationEnabled(true);
            // �jungiame kameros priartinimo ir nutolinimo kontrol�
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            // �jungiame kompas� pagal GPS
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            // Aktyvinamas mygtukas nustatyti esamai vietai
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            // �jungiame visus �manomus gestus
            mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
            // 
            mGoogleMap.setTrafficEnabled(true);
            // Akryviname automatin� priartinim�
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            // Suteikiamos naujos opcijos markeriui
            markerOptions = new MarkerOptions();
            // PAVYZDINIAI DUOMENYS
            pirmasi=55.92941850113908;
            pirmasp= 23.322129249572754;
            antrasi=54.899513279793524;
            antrasp=23.9132022857666;
            // Pradin�s pozicijos duomenys
            fromPosition = new LatLng(pirmasi,pirmasp);
            // Galin�s pozicijos duomenys
            toPosition = new LatLng(antrasi, antrasp);
            // Centruojame kamer� ir nukreipiame j� � Lietuv�
            CameraUpdate center=
            CameraUpdateFactory.newLatLng(new LatLng(55.169438,
                    		23.881275));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(7);
            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);
 // I� TA�K� KOORDINA�I� SURANDAMAS VIETOV�S PAVADINIMAS 
 //------------------------------------------------------------------------           
myCity=GetCityName(pirmasi,pirmasp);
myCity2=GetCityName(antrasi,antrasp);
myAddress=GetAddress(pirmasi,pirmasp);
myAddress2=GetAddress(antrasi,antrasp);
//----------------------Apra�ome nauj� mar�ruto u�duot�
           GetRouteTask getRoute = new GetRouteTask();
           // Vykdome u�duot�
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
         // jei �vyko klaida
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
         // jei �vyko klaida
          catch (IOException e) 
         {
          // TODO Auto-generated catch block
          e.printStackTrace();
          AddressReturn = "Google maps neaptinka adreso";
         }
          return AddressReturn;
      }
  // Privati klas� skirta atlikti kelio brai�ymo funkcijas    
      class GetRouteTask extends AsyncTask<String, Void, String> {
            // Metamas prane�imas kol kraunamas kelias
            private ProgressDialog Dialog;
            String response = "";
            @Override
            protected void onPreExecute() {
                  Dialog = new ProgressDialog(GoogleMapsActivity.this);
                  Dialog.setMessage("Sudaromas kelias...");
                  Dialog.show();
            }
            // Perduodams prane�imas kad kelias s�kmingai sudarytas
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
                  // Pridedamas kelias � �em�lap�
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
                  // po sud�liojam�
                  Dialog.dismiss();
            }
      }
      @Override
      protected void onStop() {
            super.onStop();
            finish();
      }
      
      
}