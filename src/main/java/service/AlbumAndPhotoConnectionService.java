package service;

import repository.DatabaseController;

import java.sql.Date;
import java.util.List;

public class AlbumAndPhotoConnectionService {
    PhotoService photoService = new PhotoService();
    AlbumService albumService = new AlbumService();
    DatabaseController databaseController;

    void connectPhotosToAlbumByDate(Date startingDate, Date endingDate, String albumId){
        List<String> photosIds = photoService.getPhotoIdsBetweenDates(startingDate,endingDate);
        albumService.connectPhotosToAlbumFromIds(photosIds,albumId);
        databaseController.closeConnection();
    }
}
