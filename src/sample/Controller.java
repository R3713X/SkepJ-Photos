package sample;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller{


    File recentFile =null;
    @FXML
    ImageView displayImageView;

    @FXML
    private void getPath(ActionEvent event) {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.fileGet();
        PhotoDataController photoDataController = new PhotoDataController();
        displayImage();
        ImageMetadata imageMetadata = photoDataController.createImageMetadata(photoDataController.extractMetadata(recentFile));

        imageMetadata.ShowBasicTags();
        //imageMetadata.ShowAllTags();

    }

    @FXML
    private void uploadPhoto(ActionEvent event) {
        if (recentFile!=null){
        PhotoDataController photoDataController = new PhotoDataController();
        Image image = photoDataController.getImageFromFile(recentFile);
        byte[] myByteArray = null;
        try {
            myByteArray = photoDataController.extractBytesFromImage(image);
            if (myByteArray == null) {
                System.out.println("myByteArray is null");
            }
        } catch (IOException e) {
            System.out.println("ByteExtraction didn't work");
        }
//        //prints the byte[] use it for testing the byte array//TODO: REMOVE
//        for (int i = 0; i < 200; i++) {
//            System.out.print(myByteArray[i]);
//
//        }
//        System.out.println();
//        System.out.println(myByteArray.length);
        DatabaseController a = new DatabaseController();
        a.connectToMySqlDB("photo", "root", "");
        a.uploadPhotoToDB(myByteArray);
        }else {
            System.out.println("Please choose an Image to Upload First");
        }

    }
    private void displayImage(){
        PhotoDataController photoDataController = new PhotoDataController();
        BufferedImage bufferedImage = (BufferedImage)photoDataController.getImageFromFile(recentFile);
        WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        displayImageView.setImage(image);
    }




}
