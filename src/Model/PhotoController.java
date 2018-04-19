package Model;

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

    public HashMap<String,ImageView> getAllImages(HashMap<String,byte[]> photoData){

        HashMap<String,ImageView> imageList = new HashMap<>();
        ImageView[] imageView = new ImageView[200];

        int i =0;
        for(Map.Entry<String,byte[]> data : photoData.entrySet()) {

            try {
                imageView[i].setImage(createThumbnail(ImageIO.read(new ByteArrayInputStream(data.getValue()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageList.put(data.getKey(),imageView[i]);
            i++;
        }
        return imageList;

    }


    public Image createThumbnail(BufferedImage img) throws IOException {
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.QUALITY,
                100, 100, Scalr.OP_ANTIALIAS);
        return SwingFXUtils.toFXImage(scaledImg, null);
    }
}
