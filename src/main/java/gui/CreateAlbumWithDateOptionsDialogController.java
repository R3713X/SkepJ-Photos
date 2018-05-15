package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Album;
import service.AlbumAndPhotoConnectionService;
import service.AlbumService;

import java.sql.Date;

public class CreateAlbumWithDateOptionsDialogController {

    @FXML
    private TextField albumTitle;


    @FXML
    private DatePicker startingDatePicker,endingDatePicker;

    @FXML
    public void processResults(){
        Album newAlbum = new Album(albumTitle.getText());
        AlbumService albumService =new AlbumService();
        albumService.createNewAlbum(newAlbum);
        Date startingDate =  Date.valueOf(startingDatePicker.getValue());
        Date endingDate =  Date.valueOf(endingDatePicker.getValue());
        System.out.println(startingDate);
        System.out.println(endingDate);
        AlbumAndPhotoConnectionService albumAndPhotoConnectionService = new AlbumAndPhotoConnectionService();
        albumAndPhotoConnectionService.connectPhotosToAlbumByDate(startingDate,endingDate,newAlbum.getAlbumId());
    }

}
