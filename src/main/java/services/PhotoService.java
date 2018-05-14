package services;

import javafx.scene.image.Image;
import model.Photo;
import model.RealPhoto;
import repository.DatabaseController;
import repository.PhotoRepository;

import java.util.List;

public class PhotoService {
    private PhotoRepository photoRepository = new PhotoRepository();
    private DatabaseController databaseController = new DatabaseController();


    public List<Photo> getAllPhotos() {
        List<Photo> proxyPhotos;
        proxyPhotos = photoRepository.getAllPhotos(databaseController.getConnection());
        databaseController.closeConnection();
        return proxyPhotos;
    }



    public Image getPhotoById(String id) {
        Image image = photoRepository.getPhotoById(id, databaseController.getConnection());
        databaseController.closeConnection();
        return image;
    }



    public void uploadNewPhoto(RealPhoto realPhoto){
        photoRepository.uploadPhotoToDB(realPhoto,databaseController.getConnection());
        databaseController.closeConnection();
    }


}
