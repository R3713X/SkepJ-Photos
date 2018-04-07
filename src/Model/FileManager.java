package Model;

import Controller.AwtToFx;
import Controller.DatabaseController;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
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

    public javafx.scene.image.Image getImage(byte [] photoData) {
        javafx.scene.image.Image img;

        AwtToFx awtToFx = new AwtToFx();
        img = awtToFx.getJavaFXImage(photoData,3000,3000);

        return img;
    }


    private byte[] extractBytesFromImage(File imageFile) throws IOException {

        Path path = Paths.get(imageFile.getAbsolutePath());
        return Files.readAllBytes(path);
    }

    public void saveToDB(File recentFile , String nameOfPhoto , String datePhotoCreated ,String latitude,String longitude)
    {
        if (recentFile!=null){
            byte[] myByteArray = null;
            try {
                myByteArray = extractBytesFromImage(recentFile);

            } catch (IOException e) {
                System.out.println("ByteExtraction didn't work");
            }

            DatabaseController a = new DatabaseController();
            a.connectToMySqlDB("photo", "piper006", "sky1997");
            a.uploadPhotoToDB(myByteArray,nameOfPhoto,datePhotoCreated,latitude,longitude);
        }else {
            System.out.println("Please choose an Image to Upload First");
        }
    }
}
