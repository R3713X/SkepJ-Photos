package UI;

import Model.Album;
import Repository.PrimaryController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumPickerDialogController {
    @FXML
    private ChoiceBox<String> albumChoiceBox;

    private ObservableList<String> albumList = FXCollections.observableArrayList();
    private PrimaryController primaryController = new PrimaryController();
    private List<Album> albums = primaryController.getAlbums();
    private String albumId;
    @FXML
    public void initialize() {


        for (Album album : albums) {
            albumList.add(album.getName());
        }
        albumChoiceBox.setItems(albumList);
        albumChoiceBox.getSelectionModel().select(0);

        albumChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedAlbumName = albumChoiceBox.getSelectionModel().getSelectedItem();
                for (Album album : albums) {
                   if (selectedAlbumName.equals(album.getName())){
                        albumId = album.getAlbumId();
                    }
                }
            }
        });

    }

    public void processResults(String photoId) {
        primaryController.createConnectionForAlbumAndPhotoTable(albumId,photoId);
    }
}
