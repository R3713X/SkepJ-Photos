package Repository;

import Model.ImageMetadata;
import Model.RealPhoto;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {

    public File fileGet() {
        FileChooser fc = new FileChooser();

        return fc.showOpenDialog(null);
    }
//
//    public Image getImage(File file) {
//        Image image = null;
//        try {
//            if (file!=null)
//            image = ImageIO.read(file);
//        } catch (IOException e) {
//            System.out.println("getImageFromFile could not read image");
//
//        }
//
//        return image;
//    }

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


    public RealPhoto createPhotoFromFile(File recentFile) {
        ImageMetadata imageMetadata = new ImageMetadata();

        imageMetadata.extractImageMetadata(recentFile);
        RealPhoto realPhoto = new RealPhoto();
        if (recentFile!=null){

            try {
                realPhoto.setCompleteImage(byteArrayToImage(extractBytesFromImage(recentFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            realPhoto.setThumbnailImage(createThumbnail(realPhoto.getCompleteImage()));
                realPhoto.setName(imageMetadata.getNameOfPhoto());
                realPhoto.setDate(imageMetadata.getDatePhotoCreated());
                realPhoto.setId(UUID.randomUUID().toString());
                realPhoto.setLatitude(imageMetadata.getLatitude());
                realPhoto.setLongitude(imageMetadata.getLongitude());
            }

        return realPhoto;
    }

    private javafx.scene.image.Image byteArrayToImage(byte[] bytes){
        BufferedImage bufferedImage;
        javafx.scene.image.Image image = new javafx.scene.image.Image("");
        try{
            bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            image = SwingFXUtils.toFXImage(bufferedImage,null);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  image;
    }
    private byte[] imageToByteArray(javafx.scene.image.Image image) throws IOException {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        BufferedImage img = SwingFXUtils.fromFXImage(image,null);
        ImageIO.write( img, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    public Image createThumbnail(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY,
                150, 150, Scalr.OP_ANTIALIAS);


        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
