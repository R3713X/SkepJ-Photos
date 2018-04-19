package Controller;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import Model.FileManager;
import Model.ImageMetadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class UserInterfaceController {


    private File recentFile =null;
    private String nameOfPhoto ="";
    private String datePhotoCreated ="";
    private ImageMetadata imageMetadata = new ImageMetadata();
    @FXML
    ImageView displayImageView;
    @FXML
    Label nameLabel;

    @FXML
    private void selectPhoto() throws IOException {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.fileGet();
        displayImage();
        imageMetadata.extractImageMetadata(recentFile);
        nameOfPhoto = imageMetadata.getNameOfPhoto();
        datePhotoCreated = imageMetadata.getDatePhotoCreated();
        nameLabel.setText(nameOfPhoto);

    }

    @FXML
    private void uploadPhoto() {
        FileManager fileManager = new FileManager();
        fileManager.saveToDB(recentFile,nameOfPhoto,datePhotoCreated,imageMetadata.getLatitude(),imageMetadata.getLongitude());
    }


    private void displayImage(){
        FileManager fileManager = new FileManager();
        BufferedImage bufferedImage = (BufferedImage)fileManager.getImage(recentFile);
        WritableImage image;
        image = SwingFXUtils.toFXImage(bufferedImage, null);
        displayImageView.setImage(image);
    }




}
