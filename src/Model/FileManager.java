package Model;

import Repository.DatabaseController;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public File fileGet() {
        FileChooser fc = new FileChooser();

        return fc.showOpenDialog(null);
    }

    public Image getImage(File file) {
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("getImageFromFile could not read image");
        }

        return image;
    }

    public BufferedImage getImage(byte [] photoData) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(photoData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


    private byte[] extractBytesFromImage(File imageFile) throws IOException {

        Path path = Paths.get(imageFile.getAbsolutePath());
        return Files.readAllBytes(path);
    }

    private byte[] extractBytesFromImage(javafx.scene.image.Image image) throws IOException {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //ImageIO.write(image, "jpg", baos );
        byte[] imageInByte=baos.toByteArray();
        return imageInByte;
    }


    public void saveToDB(File recentFile , String nameOfPhoto , String datePhotoCreated ,String latitude,String longitude)
    {
        if (recentFile!=null){
            byte[] complete_Data = null;
            byte[] thumbnail_Data = null;
            try {
                complete_Data = extractBytesFromImage(recentFile);
                PhotoController photoController = new PhotoController();

                thumbnail_Data = extractBytesFromImage(photoController.createThumbnail(getImage(complete_Data)));

            } catch (IOException e) {
                System.out.println("ByteExtraction didn't work");
            }

            DatabaseController a = new DatabaseController();
            a.connectToMySqlDB("photo", "root", "nikolakis12");
            a.uploadPhotoToDB(nameOfPhoto,datePhotoCreated,latitude,longitude,thumbnail_Data,complete_Data);
        }else {
            System.out.println("Please choose an Image to Upload First");
        }
    }
}
