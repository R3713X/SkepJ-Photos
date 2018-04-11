package Model;

import Controller.AwtToFx;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class PhotoController {

    public HashMap<String,Image> getAllImages(HashMap<String,byte[]> photoData){

        HashMap<String,Image> imageList = new HashMap<>();

        AwtToFx awtToFx = new AwtToFx();
        for(Map.Entry<String,byte[]> data : photoData.entrySet()) {
            imageList.put(data.getKey(), awtToFx.getJavaFXImage(data.getValue(), 3000, 3000));
        }
        return imageList;

    }
}
