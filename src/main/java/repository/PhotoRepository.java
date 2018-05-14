package repository;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.Photo;
import model.ProxyPhoto;
import model.RealPhoto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PhotoRepository {


    public Image getPhotosImageById(String uuid, Connection con) {
        byte[] bytes = null;
        String selectTableSQL = "SELECT CompleteData"
                + " FROM photos"
                + " WHERE PhotoID= (?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectTableSQL);
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            Blob blob;

            if (resultSet.next()) {
                blob = resultSet.getBlob("CompleteData");
                int blobLength = (int) blob.length();
                bytes = blob.getBytes(1, blobLength);
                System.out.println("ALL GOOD");

            }


        } catch (SQLException e) {
            System.out.println("BAAAAAAAAAAD");
            e.printStackTrace();
        }
        return byteArrayToImage(bytes);
    }

    public List<String> getPhotosIdsFromSpecifiedDates(Date startingDate, Date endingDate, Connection con) {
        List<String> photoIds = new ArrayList<>();
        String selectTableSQL = "SELECT PhotoID"
                + " FROM photos"
                + " and Date between (?) and (?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectTableSQL);
            preparedStatement.setDate(1, startingDate);
            preparedStatement.setDate(2, endingDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            String photoId;
            if (resultSet.next()) {
                photoId = resultSet.getString("PhotoID");
                photoIds.add(photoId);
                System.out.println("ALL GOOD with getting the photoIds from the dates");
            }
        } catch (SQLException e) {
            System.out.println("ALL BAD with getting the photoIds from the dates");
            e.printStackTrace();
        }
        return photoIds;
    }

    public void uploadPhotoToDB(RealPhoto realPhoto, Connection con) {
        String insertTableSQL = "INSERT INTO photos"
                + "(PhotoID, UserID, Name, Date, Latitude, Longitude,ThumbnailData,CompleteData) VALUES"
                + "(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
            //UUID creates a random ID check the docs for more info
            preparedStatement.setString(1, realPhoto.getId());
            preparedStatement.setString(2, "1");
            preparedStatement.setString(3, realPhoto.getName());
            preparedStatement.setString(4, realPhoto.getDate());
            preparedStatement.setDouble(5, realPhoto.getLatitude());
            preparedStatement.setDouble(6, realPhoto.getLongitude());
            try {
                preparedStatement.setBytes(7, imageToByteArray(realPhoto.getThumbnailImage()));
                preparedStatement.setBytes(8, imageToByteArray(realPhoto.getCompleteImage()));
            } catch (IOException e) {
                System.out.println("The imageToByteArray Problem.");
            }


            preparedStatement.executeUpdate();
            System.out.println("The realPhoto uploaded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Photo> getAllPhotos(Connection con) {
        List<Photo> proxyPhotos = new LinkedList<>();
        BufferedImage bufferedImage;
        int blobLength;
        byte[] bytes;
        ProxyPhoto proxyPhoto;
        Image image = null;
        String selectTableSQL = "SELECT *"
                + " FROM Photos";


        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectTableSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            Blob blob;

            while (resultSet.next()) {

                blob = resultSet.getBlob("ThumbnailData");
                blobLength = (int) blob.length();
                bytes = blob.getBytes(1, blobLength);
                try {
                    bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                proxyPhoto = new ProxyPhoto(resultSet.getString("Name"),
                        resultSet.getString("Date"),
                        resultSet.getString("PhotoID"),
                        0,
                        0,
                        image);
                // Double.parseDouble(resultSet.getString("Latitude"))
                if (!resultSet.getString("Latitude").isEmpty()) {
                    proxyPhoto.setLatitude(Double.parseDouble(resultSet.getString("Latitude")));
                    proxyPhoto.setLongitude(Double.parseDouble(resultSet.getString("Longitude")));
                }
                proxyPhotos.add(proxyPhoto);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proxyPhotos;
    }

    private Image byteArrayToImage(byte[] bytes) {
        BufferedImage bufferedImage;
        Image image = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    private byte[] imageToByteArray(javafx.scene.image.Image image) throws IOException {

        byte[] imageInByte;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage img = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }
        return imageInByte;
    }
}
