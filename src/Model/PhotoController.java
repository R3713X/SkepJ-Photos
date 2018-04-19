package Model;

import Controller.AwtToFx;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class PhotoController {

    public HashMap<String,ImageView> getAllImages(HashMap<String,byte[]> photoData){

        HashMap<String,ImageView> imageList = new HashMap<>();
        ImageView[] imageView = new ImageView[200];

        AwtToFx awtToFx = new AwtToFx();
        int i =0;
        for(Map.Entry<String,byte[]> data : photoData.entrySet()) {

            imageView[i].setImage(awtToFx.getJavaFXImage(data.getValue(), 3000, 3000));
            imageList.put(data.getKey(),imageView[i]);
            i++;
        }
        return imageList;

    }
}
