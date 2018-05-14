package model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ImageMetadata {

    private HashMap<String, String> tagMap = new HashMap<>();
    private String nameOfPhoto = "";
    private String datePhotoCreated ;
    private double longitude ;
    private double latitude ;
    private Date date;
    private java.sql.Date sDate;


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

            ExifSubIFDDirectory idir_exif_sub_dir = metadata.getFirstDirectoryOfType( com.drew.metadata.exif.ExifSubIFDDirectory.class );
            date =idir_exif_sub_dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED, TimeZone.getDefault());
            sDate = convertUtilToSql(date);
            System.out.println(sDate);

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
                default: {
                    break;
                }
            }
        }
    }
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return  new java.sql.Date(uDate.getTime());
    }



    public String getDatePhotoCreated() {
            return sDate.toString();
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

