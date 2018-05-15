package service;

import model.Album;
import repository.AlbumRepository;
import repository.DatabaseController;

import java.util.List;

public class AlbumService {
    private AlbumRepository albumRepository = new AlbumRepository();
    private DatabaseController databaseController = new DatabaseController();

    public void connectPhotosToAlbumFromIds(List<String> photoIds, String albumId){
        if (!photoIds.isEmpty()){
        for(String photoId:  photoIds){
            createConnectionForAlbumAndPhotoTable(photoId,albumId);
        }}else {
            System.out.println("there are not photos between these dates");
        }
    }

    public void createConnectionForAlbumAndPhotoTable(String photoId, String albumId) {
        albumRepository.connectPhotoToAlbumFromId(photoId, albumId, databaseController.getConnection());
        databaseController.closeConnection();
    }
    public List<Album> getAlbums() {
        List<Album> albums= albumRepository.getAllAlbums(databaseController.getConnection());

        databaseController.closeConnection();
        return albums;
    }
    public void createNewAlbum(Album album){
        albumRepository.createAlbum(album,databaseController.getConnection());
        databaseController.closeConnection();
    }
}
