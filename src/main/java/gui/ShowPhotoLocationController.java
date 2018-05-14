package gui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Photo;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowPhotoLocationController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;
    private Photo proxyPhoto;

    public boolean setPhoto(Photo proxyPhoto) {
        this.proxyPhoto = proxyPhoto;
        return true;
    }

    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(proxyPhoto.getLatitude(),proxyPhoto.getLongitude()))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(14);

        map = mapView.createMap(mapOptions);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(proxyPhoto.getLatitude(),proxyPhoto.getLongitude()));
        Marker photoMarker = new Marker(markerOptions);
        map.addMarker( photoMarker );

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>"+proxyPhoto.getName()+"</h2>"
                +"Date: "+proxyPhoto.getDate().replaceAll("\\?","")+"<br>"
                +"Location: "+String.format("%.2f",proxyPhoto.getLatitude())+" "+String.format("%.2f",proxyPhoto.getLongitude()));

        InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, photoMarker);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }
}
