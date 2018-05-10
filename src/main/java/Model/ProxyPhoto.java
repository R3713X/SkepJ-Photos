package Model;

import javafx.scene.image.Image;

public class ProxyPhoto implements Photo {

    @Override
    public Image getCompleteImage() {
        if (completeImage==null) {
            RealPhoto  realPhoto = new RealPhoto(this);
            return completeImage=realPhoto.getCompleteImage();
        }
        return completeImage;

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private String name;
    private String date;
    private String id;
    private double latitude;
    private double longitude;
    private Image thumbnailData;
    private Image completeImage;

    public ProxyPhoto(String name, String date, String id, double latitude, double longitude, Image thumbnailData) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbnailData = thumbnailData;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Image getThumbnail() {
        return thumbnailData;
    }


}
