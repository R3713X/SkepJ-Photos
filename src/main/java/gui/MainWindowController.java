package gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.stage.Stage;
import model.Photo;
import model.ProxyPhoto;
import other.GuiControllers;
import repository.FileManager;
import repository.PrimaryController;
import sun.applet.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {


    private FileManager fileManager = new FileManager();
    private PrimaryController primaryController = new PrimaryController();
    private File recentFile = null;
    private Photo selectedPhoto;


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
    private Button chooseAlbumButton, choosePhotoButton, uploadPhotoButton, showOnMapButton;


    @FXML
    private void selectPhoto() {
        recentFile = fileManager.fileGet();
        if (recentFile != null)
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
        fxmlLoader.setLocation(Main.class.getResource("/views/createAlbumDialog.fxml"));
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
    private void showAlbumPickerDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Choose your album");
        dialog.setHeaderText("Use this dialog to choose an Album");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/albumPickerDialog.fxml"));
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
    private void showPreviewPhotoDialog(Photo proxyPhoto) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        dialog.setTitle("Photo Preview");
        fxmlLoader.setLocation(getClass().getResource("/views/previewPhotoDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = proxyPhoto.getCompleteImage();

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(600);
        dialog.setGraphic(imageView);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

    }

    @FXML
    private void showMapLocationDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/showPhotoLocationDialog.fxml"));
        ShowPhotoLocationController showPhotoLocationController = new ShowPhotoLocationController();
        showPhotoLocationController.setPhoto(selectedPhoto);
        fxmlLoader.setController(showPhotoLocationController);

        dialog.setTitle("Map Location");
        fxmlLoader.setLocation(getClass().getResource("/views/showPhotoLocationDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

    }


    private void showPhotos() {
        tilePane.getChildren().clear();
        List<Photo> proxyPhotos = primaryController.getAllPhotos();
        for (Photo proxyPhoto : proxyPhotos) {
            ImageView imageView = new ImageView();
            imageView.prefHeight(100);
            imageView.prefWidth(100);
            imageView.smoothProperty();
            imageView.setImage(proxyPhoto.getThumbnailImage());
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setAlignment(Pos.CENTER);
            vbox.setMaxWidth(180);
            vbox.setMaxHeight(180);
            vbox.getChildren().add(0, imageView);
            vbox.setStyle("-fx-background-color: #dbe9ff");
            vbox.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    System.out.println("primary");
                    displayImage(proxyPhoto.getId());
                    chooseAlbumButton.setVisible(true);
                    showOnMapButton.setVisible(false);
                    System.out.println(proxyPhoto.getLatitude());
                    if (proxyPhoto.getLongitude() != 0 && proxyPhoto.getLatitude() != 0) {
                        selectedPhoto = proxyPhoto;
                        showOnMapButton.setVisible(true);
                    }

                    selectedPhotoId = proxyPhoto.getId();

                } else if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Secondary");
                    showPreviewPhotoDialog(proxyPhoto);
                }
                System.out.println(proxyPhoto.getId());
                vbox.setStyle("-fx-background-color: #7c7cff");

            });
            vbox.getChildren().add(1, new Label(proxyPhoto.getName()));
            vbox.setOnMouseEntered(event -> vbox.setStyle("-fx-background-color: #b2cfff"));
            vbox.setOnMouseExited(event -> vbox.setStyle("-fx-background-color: #dbe9ff"));
            tilePane.getChildren().add(vbox);


        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiControllers.setMainController(this);
        choosePhotoButton.setOnAction(event -> selectPhoto());
        uploadPhotoButton.setOnAction(event -> uploadPhoto());
        chooseAlbumButton.setOnAction(event -> showAlbumPickerDialog());
        showOnMapButton.setOnAction(event -> showMapLocationDialog());
        System.out.println("Running");
        showPhotos();

        mainBorderPane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> tilePane.setPrefColumns((newSceneWidth.intValue() - 200) / 200));

    }
    private static Stage stage;

    public static void setStage(Stage stage) {
        MainWindowController.stage = stage;
    }
}


