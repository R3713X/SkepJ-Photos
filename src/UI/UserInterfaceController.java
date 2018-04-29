package UI;


import Model.ImageMetadata;
import Model.ProxyPhoto;
import Model.RealPhoto;
import Repository.FileManager;
import Repository.PrimaryController;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

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
    public void initialize() {
        PrimaryController primaryController = new PrimaryController();

        showPhotos();

        mainBorderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                tilePane.setPrefColumns((newSceneWidth.intValue() - 200) / 200);
            }
        });
    }

    @FXML
    private void selectPhoto() {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.fileGet();

        realPhoto = new RealPhoto();
        if (recentFile != null) {
            imageMetadata.extractImageMetadata(recentFile);
            realPhoto.setName(imageMetadata.getNameOfPhoto());
            realPhoto.setDate(imageMetadata.getDatePhotoCreated());
            realPhoto.setLatitude(imageMetadata.getLatitude());
            realPhoto.setLongitude(imageMetadata.getLongitude());
            uploadPhotoNameLabel.setText(imageMetadata.getNameOfPhoto());

        }
    }


    @FXML
    private void uploadPhoto() {
        primaryController.uploadNewPhoto(fileManager.createPhotoFromFile(recentFile));
        statusLabel.setText("RealPhoto uploaded successfully");
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

    private void showPhotos() {
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
                        selectedPhotoId=proxyPhoto.getId();

                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        System.out.println("Secondary");

                    }
                    System.out.println(proxyPhoto.getId());
                }
            });
            TilePane pane = new TilePane();
            pane.setAlignment(Pos.CENTER);
            pane.setMaxWidth(180);
            pane.setMaxHeight(180);
            pane.getChildren().add(0, imageView);
            pane.setStyle("-fx-background-color: #e2fffc");

            pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    pane.setStyle("-fx-background-color: #b2cfff");
                }
            });
            pane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    pane.setStyle("-fx-background-color: #e2fffc");
                }
            });
            tilePane.getChildren().add(pane);


        }


    }


}


