package UI;

import Repository.PrimaryController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.HashMap;
import java.util.Map;

public class AlbumPickerDialogController {
    @FXML
    private ChoiceBox<String> albumChoiceBox;

    private ObservableList<String> albumList = FXCollections.observableArrayList();
    private PrimaryController primaryController = new PrimaryController();
    private HashMap<String, String> albums = primaryController.getAlbums();
    private String albumId;
    @FXML
    public void initialize() {


        for (Map.Entry<String, String> a : albums.entrySet()) {
            albumList.add(a.getKey());
        }
        albumChoiceBox.setItems(albumList);

        albumChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedAlbumName = albumChoiceBox.getSelectionModel().getSelectedItem();
                for (Map.Entry<String, String> a : albums.entrySet()) {
                   if (selectedAlbumName.equals(a.getKey())){
                        albumId = a.getValue();
                    }
                }
            }
        });

    }

    public void processResults(String photoId) {
        System.out.println(photoId);
        System.out.println(albumId);
        primaryController.createConnectionForAlbumAndPhotoTable(albumId,photoId);
    }
}
