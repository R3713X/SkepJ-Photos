package gui;

import model.Album;
import repository.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


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
