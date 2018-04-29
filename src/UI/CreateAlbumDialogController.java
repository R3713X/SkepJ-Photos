package UI;

import Model.Album;
import Repository.DatabaseController;
import Repository.PrimaryController;
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
        PrimaryController primaryController =new PrimaryController();
        primaryController.createNewAlbum(newAlbum);
    }





}
