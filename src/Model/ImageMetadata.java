package Model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageMetadata {

    private HashMap<String, String> tagMap = new HashMap<>();
    private String nameOfPhoto = "";
    private String datePhotoCreated ;
    private double longitude ;
    private double latitude ;


    public void extractImageMetadata(File file) {

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    tagMap.put(tag.getTagName(), tag.getDescription());

                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
            getUsefulTags(file);
        } catch (ImageProcessingException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void getUsefulTags (File file) {

        try {
            javaxt.io.Image image = new javaxt.io.Image(file);
            double[] gps = image.getGPSCoordinate();
            try {
                longitude = Double.valueOf(gps[0]);
                latitude = Double.valueOf(gps[1]);
            } catch (Exception e) {
                System.out.println("There is not gps data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : tagMap.entrySet()) {

            switch (entry.getKey()) {
                case "File Name": {
                    nameOfPhoto = entry.getValue();
                    break;
                }

                case "File Modified Date": {
                    datePhotoCreated = entry.getValue();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

        public String getDatePhotoCreated() {
            return datePhotoCreated;
        }

        public String getNameOfPhoto() {
            return nameOfPhoto;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
}

