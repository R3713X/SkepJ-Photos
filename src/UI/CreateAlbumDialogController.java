package UI;

import Model.Album;
import Repository.DatabaseController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.time.LocalDate;


public class CreateAlbumDialogController {
    @FXML
    private TextField albumTitle;


    @FXML
    public void processResults(){
        Album newAlbum = new Album(albumTitle.getText());
        DatabaseController databaseController = new DatabaseController();
        databaseController.connectToMySqlDB("photo","root","");
        databaseController.createAlbum(newAlbum);
    }





}
