package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.File;


public class Controller {


    @FXML
    private void GetPath(ActionEvent event) {

        FileManager fileManager = new FileManager();
        File selectedFile = fileManager.FileGet();
        MetadataExtractor ex = new MetadataExtractor();
        ImageMetadata imageMetadata = ex.Extract(selectedFile);

        imageMetadata.ShowBasicTags();
        //imageMetadata.ShowAllTags();

    }




}
