package service;

import java.sql.Date;
import java.util.List;

public class AlbumAndPhotoConnectionService {
    PhotoService photoService = new PhotoService();
    AlbumService albumService = new AlbumService();

    public void connectPhotosToAlbumByDate(Date startingDate, Date endingDate, String albumId){
        List<String> photosIds = photoService.getPhotoIdsBetweenDates(startingDate,endingDate);
        System.out.println(photosIds);
        albumService.connectPhotosToAlbumFromIds(photosIds,albumId);
    }


}
