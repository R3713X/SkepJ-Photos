package UI;

import Model.Album;
import Repository.PrimaryController;
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
