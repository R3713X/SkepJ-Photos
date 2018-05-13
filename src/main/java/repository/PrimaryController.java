package repository;

import model.Album;
import model.ProxyPhoto;
import model.RealPhoto;
import javafx.scene.image.Image;

import java.util.List;

public class PrimaryController {
    private PhotoRepository photoRepository = new PhotoRepository();
    private AlbumRepository albumRepository = new AlbumRepository();
    private DatabaseController databaseController = new DatabaseController();


    public List<ProxyPhoto> getAllPhotos() {
        List<ProxyPhoto> proxyPhotos;
        proxyPhotos = photoRepository.getAllPhotos(databaseController.getConnection());
        databaseController.closeConnection();
        return proxyPhotos;
    }

    public void createConnectionForAlbumAndPhotoTable(String photoId, String albumId) {
        albumRepository.connectPhotoToAlbumFromId(photoId, albumId, databaseController.getConnection());
        databaseController.closeConnection();
    }

    public Image getPhotoById(String id) {
        Image image = photoRepository.getPhotoById(id, databaseController.getConnection());
        databaseController.closeConnection();
        return image;
    }


    public List<Album> getAlbums() {
        List<Album> albums= albumRepository.getAllAlbums(databaseController.getConnection());

        databaseController.closeConnection();
        return albums;
    }

    public void uploadNewPhoto(RealPhoto realPhoto){
        photoRepository.uploadPhotoToDB(realPhoto,databaseController.getConnection());
        databaseController.closeConnection();
    }
    public void createNewAlbum(Album album){
        albumRepository.createAlbum(album,databaseController.getConnection());
        databaseController.closeConnection();
    }

}
