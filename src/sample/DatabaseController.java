package sample;

import java.sql.*;
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

    /**
     * For this to actually work you need to create or have a table "UploadedPhotos" in phpMyAdmin
     * It needs to have 2 Fields PhotoID Varchar(32) and DataBytes LONGBLOB
     * you also will need to change the my.ini usually found in "C:\xampp\mysql\bin\"
     * Open it up with notepad++.
     * Change the fields:
     * 1)  [mysqld]    innodb_log_file_size=256M
     * 2)  [mysqld]    max_allowed_packet = 512M
     * 3)  [mysqldump] max_allowed_packet = 512M
     * Save the file and restart mySql.
     */
    public void uploadPhotoToDB(byte[] photoBytes) {
        String insertTableSQL = "INSERT INTO UploadedPhotos"
                + "(PhotoID, DataBytes) VALUES"
                + "(?,?)";
        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setBytes(2, photoBytes);
            preparedStatement.executeUpdate();
            System.out.println("The photo uploaded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPhotoFromDB(String uuid) {
        byte[] bytes = null;
        String insertTableSQL = "SELECT DataBytes"
                + "FROM UploadedPhotos"
                + "WHERE PhotoID=(?)";
        try {
            PreparedStatement preparedStatement = this.getCon().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            Blob blob = resultSet.getBlob("DataBytes");
            int blobLength = (int) blob.length();
            bytes = blob.getBytes(1, blobLength);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    public Connection getCon() {
        return con;
    }
}