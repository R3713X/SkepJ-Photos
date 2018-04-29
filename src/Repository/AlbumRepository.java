package Repository;

import Model.Album;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlbumRepository {

    public void connectPhotoToAlbumFromId(String photoId, String albumId, Connection con) {
        String insertTableSQL = "INSERT INTO albumandphotos"
                + "(albumID, photoID) VALUES"
                + "(?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, photoId);
            preparedStatement.setString(2, albumId);

            preparedStatement.execute();
            System.out.println("The connection had been created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAlbum(Album album, Connection con) {

        String insertTableSQL = "INSERT INTO albums"
                + "(AlbumID, Name, Date) VALUES"
                + "(?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, album.getAlbumId());
            preparedStatement.setString(2, album.getName());
            preparedStatement.setDate(3, album.getDate());

            preparedStatement.executeUpdate();
            System.out.println("The album created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Album> getAllAlbums(Connection con) {
        List<Album> albums = new LinkedList<>();
        Album album;
        String selectTableSQL = "SELECT *"
                + "FROM Albums";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectTableSQL);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                album = new Album(resultSet.getString("Name"), resultSet.getString("AlbumID"), resultSet.getDate("Date"));
                albums.add(album);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;

    }
}
