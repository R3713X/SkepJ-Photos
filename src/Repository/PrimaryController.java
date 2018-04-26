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

public class PrimaryController {
    private DatabaseController databaseController = new DatabaseController();

    public PrimaryController() {
        databaseController.connectToMySqlDB("photo","root","");
    }

    public HashMap<String,ImageView> getAllImages(){
        HashMap<String,byte[]> hashMap = databaseController.getAllPhotosFromDB();
        HashMap<String,ImageView> imageList = new HashMap<>();


        for(Map.Entry<String,byte[]> data : hashMap.entrySet()) {

            try {
                ImageView imageView = new ImageView();
                imageView.prefHeight(100);
                imageView.prefWidth(100);
                imageView.setImage(createThumbnail(ImageIO.read(new ByteArrayInputStream(data.getValue()))));
                imageList.put(data.getKey(),imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return imageList;
    }

    public void createConnectionForAlbumAndPhotoTable(String photoId,String albumId){
        databaseController.connectPhotoToAlbumFromId(photoId,albumId);
    }
    public Image getPhotoById(String id){
        BufferedImage image =null;
        try {
             image = ImageIO.read(new ByteArrayInputStream(databaseController.getSpecificPhotoFromDB(id)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  SwingFXUtils.toFXImage(image,null);
    }

    public HashMap<String,String> getAlbums(){
        return databaseController.getAllAlbums();
    }


    public Image createThumbnail(BufferedImage img) {
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.QUALITY,
                150, 150, Scalr.OP_ANTIALIAS);
        return SwingFXUtils.toFXImage(scaledImg, null);
    }
}
