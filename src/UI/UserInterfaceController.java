package UI;


import Model.ImageMetadata;
import Model.ProxyPhoto;
import Model.RealPhoto;
import Repository.FileManager;
import Repository.PrimaryController;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class UserInterfaceController {


    FileManager fileManager = new FileManager();
    PrimaryController primaryController = new PrimaryController();
    private File recentFile = null;
    private ImageMetadata imageMetadata = new ImageMetadata();
    private RealPhoto realPhoto;

    @FXML
    public GoogleMapView mapView;


    private String selectedPhotoName;
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

    private GoogleMap map;
    private static Stage stage;

    @FXML
    public void initialize() {
        PrimaryController primaryController = new PrimaryController();

        showPhotos();

//        MapOptions mapOptions = new MapOptions();
//        LatLong serres = new LatLong(41.092083, 23.541016);
//        mapOptions.center(serres)
//                .mapType(MapTypeIdEnum.ROADMAP)
//                .overviewMapControl(false)
//                .panControl(false)
//                .rotateControl(false)
//                .scaleControl(false)
//                .streetViewControl(false)
//                .zoomControl(false)
//                .zoom(12);
//
//        map = mapView.createMap(mapOptions);

        mainBorderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                tilePane.setPrefColumns((newSceneWidth.intValue() - 200) / 200);
            }
        });
    }

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
        fxmlLoader.setLocation(getClass().getResource("createAlbumDialog.fxml"));
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
        fxmlLoader.setLocation(getClass().getResource("albumPickerDialog.fxml"));
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
    public void showPreviewPhotoDialog(ProxyPhoto proxyPhoto) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.setTitle("Photo Preview");
        fxmlLoader.setLocation(getClass().getResource("previewPhotoDialog.fxml"));
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
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        System.out.println("primary");
                        displayImage(proxyPhoto.getId());
                        chooseAlbumButton.setVisible(true);
                        selectedPhotoId = proxyPhoto.getId();
                        selectedPhotoName = proxyPhoto.getName();

                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        System.out.println("Secondary");
                        showPreviewPhotoDialog(proxyPhoto);
                    }
                    System.out.println(proxyPhoto.getId());

                }
            });
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setMaxWidth(180);
            vbox.setMaxHeight(180);
            vbox.getChildren().add(0, imageView);
            vbox.setStyle("-fx-background-color: #e2fffc");

            vbox.getChildren().add(1, new Label(proxyPhoto.getName()));
            vbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    vbox.setStyle("-fx-background-color: #b2cfff");
                }
            });
            vbox.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    vbox.setStyle("-fx-background-color: #e2fffc");
                }
            });
            tilePane.getChildren().add(vbox);


        }


    }


}


