package UI;


import Model.ProxyPhoto;
import Repository.FileManager;
import Repository.PrimaryController;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import sun.applet.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserInterfaceController implements Initializable, MapComponentInitializedListener {


    private FileManager fileManager = new FileManager();
    private PrimaryController primaryController = new PrimaryController();
    private File recentFile = null;



    private GoogleMap map;

    @FXML
    public GoogleMapView mapView;

    private String selectedPhotoId;

    @FXML
    private ImageView displayImageView;

    @FXML
    private TilePane tilePane;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label uploadPhotoNameLabel, statusLabel;

    @FXML
    private Button chooseAlbumButton;

    @FXML
    private void selectPhoto() {
        recentFile = fileManager.fileGet();
        uploadPhotoNameLabel.setText(recentFile.getName());
    }


    @FXML
    private void uploadPhoto() {
        try {
            primaryController.uploadNewPhoto(fileManager.createPhotoFromFile(recentFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        statusLabel.setText(recentFile.getName() + " has been uploaded successfully");
        showPhotos();
    }


    private void displayImage(String id) {
        displayImageView.setImage(null);
        displayImageView.setImage(primaryController.getPhotoById(id));
    }

    @FXML
    public void showCreateAlbumDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create a new Album");
        dialog.setHeaderText("Use this dialog to create a new Album");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/createAlbumDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            CreateAlbumDialogController dialogController = fxmlLoader.getController();
            dialogController.processResults();
            statusLabel.setText("Album created successfully.");
        }
    }

    @FXML
    public void showAlbumPickerDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Choose your album");
        dialog.setHeaderText("Use this dialog to choose an Album");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/albumPickerDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            AlbumPickerDialogController albumPickerDialogController = fxmlLoader.getController();
            albumPickerDialogController.processResults(selectedPhotoId);
            statusLabel.setText("RealPhoto inserted to album successfully");
        }
    }

    @FXML
    private void showPreviewPhotoDialog(ProxyPhoto proxyPhoto) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.setTitle("Photo Preview");
        fxmlLoader.setLocation(getClass().getResource("/previewPhotoDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = proxyPhoto.getCompleteImage();
        image.isPreserveRatio();

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(600);
        dialog.setGraphic(imageView);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();

    }

    private void showPhotos() {
        tilePane.getChildren().clear();
        List<ProxyPhoto> proxyPhotos = primaryController.getAllPhotos();
        for (ProxyPhoto proxyPhoto : proxyPhotos) {
            ImageView imageView = new ImageView();
            imageView.prefHeight(100);
            imageView.prefWidth(100);
            imageView.smoothProperty();
            imageView.setImage(proxyPhoto.getThumbnail());
            imageView.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    System.out.println("primary");
                    displayImage(proxyPhoto.getId());
                    chooseAlbumButton.setVisible(true);
                    selectedPhotoId = proxyPhoto.getId();

                } else if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Secondary");
                    showPreviewPhotoDialog(proxyPhoto);
                }
                System.out.println(proxyPhoto.getId());

            });
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setMaxWidth(180);
            vbox.setMaxHeight(180);
            vbox.getChildren().add(0, imageView);
            vbox.setStyle("-fx-background-color: #e2fffc");

            vbox.getChildren().add(1, new Label(proxyPhoto.getName()));
            vbox.setOnMouseEntered(event -> vbox.setStyle("-fx-background-color: #b2cfff"));
            vbox.setOnMouseExited(event -> vbox.setStyle("-fx-background-color: #e2fffc"));
            tilePane.getChildren().add(vbox);


        }


    }


    @Override
    public void mapInitialized() {
        LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);

        Marker joeSmithMarker = new Marker(markerOptions1);
        Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker= new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);

        map.addMarker( joeSmithMarker );
        map.addMarker( joshAndersonMarker );
        map.addMarker( bobUnderwoodMarker );
        map.addMarker( tomChoiceMarker );
        map.addMarker( fredWilkieMarker );

//        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
//        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
//                + "Current Location: Safeway<br>"
//                + "ETA: 45 minutes" );
//
//        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
//        fredWilkeInfoWindow.open(map, fredWilkieMarker);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        PrimaryController primaryController = new PrimaryController();

        showPhotos();

        mainBorderPane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> tilePane.setPrefColumns((newSceneWidth.intValue() - 200) / 200));

    }
}


