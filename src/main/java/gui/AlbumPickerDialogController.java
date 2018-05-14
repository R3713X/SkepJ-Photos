package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import model.Album;
import services.AlbumService;

import java.util.List;

public class AlbumPickerDialogController {

    @FXML
    private ChoiceBox<String> albumChoiceBox;

    private ObservableList<String> albumList = FXCollections.observableArrayList();
    private AlbumService albumService = new AlbumService();
    private List<Album> albums = albumService.getAlbums();
    private String albumId;
    @FXML
    public void initialize() {


        for (Album album : albums) {
            albumList.add(album.getName());
        }
        albumId=albums.get(0).getAlbumId();
        albumChoiceBox.setItems(albumList);
        albumChoiceBox.getSelectionModel().select(0);
        albumChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedAlbumName = albumChoiceBox.getSelectionModel().getSelectedItem();

            System.out.println(selectedAlbumName);
            for (Album album : albums) {
               if (selectedAlbumName.equals(album.getName())){
                    albumId = album.getAlbumId();
                }
            }
        });



    }

    public void processResults(String photoId) {
        System.out.println(albumId);
        System.out.println(photoId);
        albumService.createConnectionForAlbumAndPhotoTable(albumId,photoId);
    }
}
