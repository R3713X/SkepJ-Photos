package model;

import javafx.scene.image.Image;


public interface Photo {



    String getName();

    String getDate();

    String getId();

    double getLatitude();

    double getLongitude();

    Image getThumbnailImage();

    Image getCompleteImage();



    void setLatitude(double latitude);

    void setLongitude(double longitude);

    void setName(String name);

    void setDate(String date);

    void setId(String id);

    void setThumbnailImage(Image thumbnailImage);

    void setCompleteImage(Image completeImage);
}
