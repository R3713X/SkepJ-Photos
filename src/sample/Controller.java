package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

    private String filePath = "";

    @FXML
    private void GetPath(ActionEvent event) {
        sample.FileChooser fileChooser = new sample.FileChooser();
        fileChooser.FileGet();
        filePath = fileChooser.getPath();

    }

    @FXML
    private void ShowMetada(ActionEvent event1) {
        Export ex = new Export();
        ex.Extract(filePath);


    }


}
