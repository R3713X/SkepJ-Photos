package UI;


import Repository.FileManager;
import Model.ImageMetadata;
import Model.Photo;
import Repository.PhotoController;
import Repository.DatabaseController;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


public class UserInterfaceController {


    private File recentFile = null;
    private ImageMetadata imageMetadata = new ImageMetadata();
    private Photo photo;
    @FXML
    private ImageView displayImageView;

    @FXML
    private TilePane tilePane;

    @FXML
    private BorderPane mainBorderPane;





    @FXML
    public void initialize() {
        PhotoController photoController = new PhotoController();
        DatabaseController databaseController = new DatabaseController();
        databaseController.connectToMySqlDB("photo", "root", "");

        showPhotos(photoController.getAllImages(databaseController.getAllPhotosFromDB()));

    }

    @FXML
    private void selectPhoto() {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.fileGet();
        displayImage();
        photo = new Photo();
        if (recentFile != null) {
            imageMetadata.extractImageMetadata(recentFile);
            photo.setPhotoName(imageMetadata.getNameOfPhoto());
            photo.setDateCreated(imageMetadata.getDatePhotoCreated());
            photo.setLatitude(imageMetadata.getLatitude());
            photo.setLongitude(imageMetadata.getLongitude());
        }
    }


    @FXML
    private void uploadPhoto() {
        FileManager fileManager = new FileManager();
        fileManager.saveToDB(recentFile, photo);
        PhotoController photoController = new PhotoController();
        DatabaseController databaseController = new DatabaseController();
        databaseController.connectToMySqlDB("photo","root","");

        showPhotos(photoController.getAllImages(databaseController.getAllPhotosFromDB()));

    }


    private void displayImage() {
        FileManager fileManager = new FileManager();
        BufferedImage bufferedImage = (BufferedImage) fileManager.getImage(recentFile);
        WritableImage image;
        if (bufferedImage != null) {
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            displayImageView.setImage(image);
        }
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

        }
    }

    private void showPhotos(Map<String, ImageView> map){
        if (map != null) {
            tilePane.getChildren().clear();
            for (Map.Entry<String, ImageView> entry : map.entrySet()) {

                ImageView imageView = entry.getValue();
                imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        if(event.getButton()==MouseButton.PRIMARY){
                            System.out.println("primary");

                        }else if (event.getButton()==MouseButton.SECONDARY){
                            System.out.println("Secondary");

                        }
                        System.out.println(entry.getKey());
                    }
                });
                TilePane pane = new TilePane();
                pane.setAlignment(Pos.CENTER);
                pane.setMaxWidth(150);
                pane.setMaxHeight(150);
                pane.getChildren().add(imageView);

                pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pane.setStyle("-fx-background-color: #b2cfff");
                    }
                });
                pane.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pane.setStyle("-fx-background-color: #ffffff");
                    }
                });
                tilePane.getChildren().add(pane);


            }



        }


    }


}
