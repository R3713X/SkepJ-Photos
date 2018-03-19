package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Controller {

    File recentFile =null;

    @FXML
    private void GetPath(ActionEvent event) {

        FileManager fileManager = new FileManager();
        recentFile = fileManager.FileGet();
        PhotoDataController photoDataController = new PhotoDataController();
        ImageMetadata imageMetadata = photoDataController.CreateImageMetadata(photoDataController.ExtractMetadata(recentFile));

        imageMetadata.ShowBasicTags();
        //imageMetadata.ShowAllTags();

    }

    @FXML
    private void UploadPhoto(ActionEvent event) {
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
        a.ConnectToMySqlDB("photo", "root", "");
        a.uploadPhotoToDB(myByteArray);
        }else {
            System.out.println("Please choose an Image to Upload First");
        }

    }



}
