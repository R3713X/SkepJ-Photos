package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class Controller {


    @FXML
    private void GetPath(ActionEvent event) {

        FileManager fileManager = new FileManager();
        File selectedFile = fileManager.FileGet();

        Export ex = new Export();
        ex.Extract(selectedFile);

        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.ShowBasicTags();

    }

    @FXML
    private void ShowMetada(ActionEvent event1) {



    }


}
