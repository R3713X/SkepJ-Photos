package Repository;

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

    public HashMap<String,ImageView> getAllImages(HashMap<String,byte[]> hashMap){

        HashMap<String,ImageView> imageList = new HashMap<>();


        for(Map.Entry<String,byte[]> data : hashMap.entrySet()) {

            try {
                ImageView imageView = new ImageView();
//                imageView.setFitHeight(150);
//                imageView.setFitWidth(150);
//                imageView.preserveRatioProperty( );
                imageView.smoothProperty();
                imageView.setImage(createThumbnail(ImageIO.read(new ByteArrayInputStream(data.getValue()))));
                imageList.put(data.getKey(),imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return imageList;
    }


    public Image createThumbnail(BufferedImage img) {
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.QUALITY,
                150, 150, Scalr.OP_ANTIALIAS);
        return SwingFXUtils.toFXImage(scaledImg, null);
    }
}
