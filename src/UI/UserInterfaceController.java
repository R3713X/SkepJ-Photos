package UI;


import Model.FileManager;
import Model.ImageMetadata;
import Model.Photo;
import Model.PhotoController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

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
    private Label label;
    @FXML
    private HBox hBox;

    @FXML
    private BorderPane mainBorderPane;




    @FXML
    public void initialize() {
        PhotoController photoController = new PhotoController();
        Map<String, ImageView> map = null;
        //map = photoController.getAllImages();
        if (map != null) {
            for (Map.Entry<String, ImageView> entry : map.entrySet()) {

                ImageView imageView = entry.getValue();
                imageView.setCursor(Cursor.HAND);
                hBox.getChildren().add(imageView);

            }

            for (Map.Entry<String, ImageView> entry : map.entrySet()) {
                entry.getValue().setOnMouseClicked(event ->
                        System.out.println(entry.getKey())
                );
            }
        }
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

        }
    }

}
