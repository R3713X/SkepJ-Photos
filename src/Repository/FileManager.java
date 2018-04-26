package Repository;

import Model.Photo;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
            if (file!=null)
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
        BufferedImage img = SwingFXUtils.fromFXImage(image,null);
        ImageIO.write( img, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }


    public void saveToDB(File recentFile , Photo photo)
    {
        if (recentFile!=null){
            try {
                photo.setCompleteData(extractBytesFromImage(recentFile));
                PhotoController photoController = new PhotoController();

                photo.setThumbnailData(extractBytesFromImage(photoController.createThumbnail(getImage(photo.getCompleteData()))));

            } catch (IOException e) {
                System.out.println("ByteExtraction didn't work");
            }

            DatabaseController a = new DatabaseController();
            a.connectToMySqlDB("photo", "root", "");
            a.uploadPhotoToDB(photo);
        }else {
            System.out.println("Please choose an Image to Upload First");
        }
    }
}
