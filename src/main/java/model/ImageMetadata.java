package model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageMetadata {

    private HashMap<String, String> tagMap = new HashMap<>();
    private String nameOfPhoto = "";
    private String datePhotoCreated ;
    private double longitude ;
    private double latitude ;
    private Date date;


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
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();


        }
    }
        private void getUsefulTags (File file) {

        try {
            javaxt.io.Image image = new javaxt.io.Image(file);
            double[] gps = image.getGPSCoordinate();
            try {
                longitude = gps[0];
                latitude = gps[1];
                System.out.println(latitude+" "+longitude);
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
//                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                    datePhotoCreated = entry.getValue();
//                    try {
//                        java.sql.Date a = java.sql.Date.valueOf(String.valueOf(sdf.parse(datePhotoCreated)));
//
//                        System.out.println(a);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
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

