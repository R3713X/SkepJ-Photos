package Model;

import Repository.DatabaseController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhotoController {

    public HashMap<String,ImageView> getAllImages(){

        HashMap<String,ImageView> imageList = new HashMap<>();
        DatabaseController databaseController = new DatabaseController();
        databaseController.connectToMySqlDB("photo","root","sky1997");


        for(Map.Entry<String,byte[]> data : databaseController.getAllPhotosFromDB().entrySet()) {

            try {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.preserveRatioProperty();
                imageView.setImage(createThumbnail(ImageIO.read(new ByteArrayInputStream(data.getValue()))));
                imageList.put(data.getKey(),imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return imageList;

    }


    public Image createThumbnail(BufferedImage img) throws IOException {
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.QUALITY,
                100, 100, Scalr.OP_ANTIALIAS);
        return SwingFXUtils.toFXImage(scaledImg, null);
    }
}
