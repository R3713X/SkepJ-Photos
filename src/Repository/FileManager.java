package Repository;

import Model.ImageMetadata;
import Model.RealPhoto;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {

    public File fileGet() {
        FileChooser fc = new FileChooser();

        return fc.showOpenDialog(null);
    }




    public RealPhoto createPhotoFromFile(File recentFile) {
        ImageMetadata imageMetadata = new ImageMetadata();

        imageMetadata.extractImageMetadata(recentFile);
        RealPhoto realPhoto = null;
        try {
            realPhoto = new RealPhoto(imageMetadata.getNameOfPhoto()
                    , imageMetadata.getDatePhotoCreated()
                    , UUID.randomUUID().toString()
                    , imageMetadata.getLatitude()
                    , imageMetadata.getLongitude()
                    , new Image(recentFile.toURI().toURL().toString(), 150, 150, true, false)
                    ,new Image(recentFile.toURI().toURL().toString()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return realPhoto;
    }



}
