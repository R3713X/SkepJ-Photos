package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Album;
import services.AlbumService;


public class CreateAlbumDialogController {
    @FXML
    private TextField albumTitle;


    @FXML
    public void processResults(){
        Album newAlbum = new Album(albumTitle.getText());
        AlbumService albumService =new AlbumService();
        albumService.createNewAlbum(newAlbum);
    }





}
