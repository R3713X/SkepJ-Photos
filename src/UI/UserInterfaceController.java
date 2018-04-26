package UI;


import Model.ImageMetadata;
import Model.Photo;
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
import java.util.Map;
import java.util.Optional;


public class UserInterfaceController {


    private File recentFile = null;
    private ImageMetadata imageMetadata = new ImageMetadata();
    private Photo photo;

    private PrimaryController primaryController = new PrimaryController();
    private String selectedPhotoId;
    @FXML
    private ImageView displayImageView;

    @FXML
    private TilePane tilePane;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label uploadPhotoNameLabel,statusLabel;

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

        photo = new Photo();
        if (recentFile != null) {
            imageMetadata.extractImageMetadata(recentFile);
            photo.setPhotoName(imageMetadata.getNameOfPhoto());
            photo.setDateCreated(imageMetadata.getDatePhotoCreated());
            photo.setLatitude(imageMetadata.getLatitude());
            photo.setLongitude(imageMetadata.getLongitude());
            uploadPhotoNameLabel.setText(imageMetadata.getNameOfPhoto());

        }
    }


    @FXML
    private void uploadPhoto() {
        FileManager fileManager = new FileManager();
        fileManager.saveToDB(recentFile, photo);
        statusLabel.setText("Photo uploaded successfully");
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
            statusLabel.setText("Photo inserted to album successfully");
        }
    }

    private void showPhotos() {
        Map<String, ImageView> map = primaryController.getAllImages();
        if (map != null) {
            tilePane.getChildren().clear();
            for (Map.Entry<String, ImageView> entry : map.entrySet()) {

                ImageView imageView = entry.getValue();


                imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            System.out.println("primary");
                            displayImage(entry.getKey());
                            chooseAlbumButton.setVisible(true);

                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            System.out.println("Secondary");

                        }
                        System.out.println(entry.getKey());
                        selectedPhotoId=entry.getKey();
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


}
