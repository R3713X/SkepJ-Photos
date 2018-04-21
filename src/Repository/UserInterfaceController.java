package Repository;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import Model.FileManager;
import Model.ImageMetadata;
import javafx.scene.layout.HBox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class UserInterfaceController {


    private File recentFile =null;
    private String nameOfPhoto ="";
    private String datePhotoCreated ="";
    private ImageMetadata imageMetadata = new ImageMetadata();
    @FXML
    private ImageView displayImageView;
    @FXML
    private Label nameLabel;

    @FXML
    private HBox hBox;

    @FXML
    private void selectPhoto() throws IOException {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.fileGet();
        displayImage();
        if (recentFile!=null) {
            imageMetadata.extractImageMetadata(recentFile);
            nameOfPhoto = imageMetadata.getNameOfPhoto();
            datePhotoCreated = imageMetadata.getDatePhotoCreated();
            nameLabel.setText(nameOfPhoto);
        }
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
        if (bufferedImage!=null) {
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            displayImageView.setImage(image);
        }
    }


}
