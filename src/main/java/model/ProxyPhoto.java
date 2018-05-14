package model;

import javafx.scene.image.Image;

public class ProxyPhoto implements Photo {

    @Override
    public Image getCompleteImage() {
        if (completeImage == null) {
            RealPhoto realPhoto = new RealPhoto(this);
            return completeImage = realPhoto.getCompleteImage();
        }
        return completeImage;

    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setThumbnailImage(Image thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    @Override
    public void setCompleteImage(Image completeImage) {
        this.completeImage = completeImage;
    }

    private String name;
    private String date;
    private String id;
    private double latitude;
    private double longitude;
    private Image thumbnailImage;
    private Image completeImage;

    public ProxyPhoto(String name, String date, String id, double latitude, double longitude, Image thumbnailData) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbnailImage = thumbnailData;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public Image getThumbnailImage() {
        return thumbnailImage;
    }


}
