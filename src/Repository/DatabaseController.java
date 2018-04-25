package Repository;


import Model.Album;
import Model.Photo;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseController {
    /**
     * @var con is a string type var that takes the DB info to log
     */
    private Connection con;

    /**
     * @param DBName Database's name that you want to connect
     * @param DBUser Database's username
     * @param DBPwd  Database's password, If there isn't any password put null
     */
    public void connectToMySqlDB(String DBName, String DBUser, String DBPwd) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName, DBUser, DBPwd);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void uploadPhotoToDB(Photo photo) {
        String insertTableSQL = "INSERT INTO photos"
                + "(PhotoID, UserID, Name, Date, Latitude, Longitude,ThumbnailData,CompleteData) VALUES"
                + "(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, "1");
            preparedStatement.setString(3, photo.getPhotoName());
            preparedStatement.setString(4, photo.getDateCreated());
            preparedStatement.setString(5, photo.getLatitude());
            preparedStatement.setString(6, photo.getLongitude());
            preparedStatement.setBytes(7, photo.getThumbnailData());
            preparedStatement.setBytes(8, photo.getCompleteData());


            preparedStatement.executeUpdate();
            System.out.println("The photo uploaded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSpecificPhotoFromDB(String uuid) {
        byte[] bytes = null;
        String selectTableSQL = "SELECT DataBytes"
                + " FROM PhotoAlbum"
                + " WHERE PhotoID= (?)";
        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(selectTableSQL);
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            Blob blob;

            if (resultSet.next()) {
                blob = resultSet.getBlob("DataBytes");
                int blobLength = (int) blob.length();
                bytes = blob.getBytes(1, blobLength);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public HashMap<String, byte[]> getAllPhotosFromDB() {
        byte[] bytes;
        HashMap<String, byte[]> hashMap = new HashMap<>();
        String selectTableSQL = "SELECT ThumbnailData,PhotoID"
                + " FROM Photos";

        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(selectTableSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            Blob blob;

            while (resultSet.next()) {
                blob = resultSet.getBlob("ThumbnailData");
                int blobLength = (int) blob.length();
                bytes = blob.getBytes(1, blobLength);
                hashMap.put(resultSet.getString("PhotoID"), bytes);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public void createAlbum(Album album) {

        String insertTableSQL = "INSERT INTO albums"
                + "(AlbumID, Name, Date) VALUES"
                + "(?,?,?)";
        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, album.getAlbumId());
            preparedStatement.setString(2, album.getTitle());
            preparedStatement.setString(3, album.getDate().toString());

            preparedStatement.executeUpdate();
            System.out.println("The album created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private Connection getCon() {
        return con;
    }
}