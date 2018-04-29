package Repository;

import Model.ProxyPhoto;
import Model.RealPhoto;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PhotoRepository {



    public Image getPhotoById(String uuid,Connection con) {
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

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return byteArrayToImage(bytes);
    }
    public void uploadPhotoToDB(RealPhoto realPhoto,Connection con) {
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
            }catch (IOException e){
                System.out.println("The imageToByteArray Problem.");
            }



            preparedStatement.executeUpdate();
            System.out.println("The realPhoto uploaded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProxyPhoto> getAllPhotos(Connection con) {
        List<ProxyPhoto> proxyPhotos = new LinkedList<>();
        BufferedImage bufferedImage;
        int blobLength;
        byte[] bytes;
        ProxyPhoto proxyPhoto;
        Image image = null ;
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
                try{
                    bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
                    image = SwingFXUtils.toFXImage(bufferedImage,null);
                }catch (IOException e) {
                    e.printStackTrace();
                }

                proxyPhoto = new ProxyPhoto(resultSet.getString("Name"),
                        resultSet.getString("Date"),
                        resultSet.getString("PhotoID"),
                        Double.parseDouble(resultSet.getString("Latitude")),
                        Double.parseDouble(resultSet.getString("Longitude")),
                        image);

                proxyPhotos.add(proxyPhoto);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proxyPhotos;
    }

    private Image byteArrayToImage(byte[] bytes){
        BufferedImage bufferedImage;
        Image image = null;
        try{
            bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            image = SwingFXUtils.toFXImage(bufferedImage,null);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  image;
    }
    private byte[] imageToByteArray(Image image) throws IOException {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        BufferedImage img = SwingFXUtils.fromFXImage(image,null);
        ImageIO.write( img, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}