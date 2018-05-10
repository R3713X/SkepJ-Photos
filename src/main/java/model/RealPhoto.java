package model;

import repository.PrimaryController;
import javafx.scene.image.Image;

public class RealPhoto implements Photo {


    @Override
    public Image getCompleteImage() {
        return completeImage;
    }

    private String name;
    private String date;
    private String id;
    private double latitude;
    private double longitude;
    private Image thumbnailImage;
    private Image completeImage;

    public RealPhoto(ProxyPhoto proxyPhoto) {
        this.name = proxyPhoto.getName();
        this.date = proxyPhoto.getDate();
        this.id = proxyPhoto.getId();
        this.latitude = proxyPhoto.getLatitude();
        this.longitude = proxyPhoto.getLongitude();
        this.thumbnailImage = proxyPhoto.getThumbnail();
        PrimaryController primaryController = new PrimaryController();
        this.completeImage=primaryController.getPhotoById(this.getId());

    }

    public RealPhoto(String name, String date, String id, double latitude, double longitude, Image thumbnailImage, Image completeImage) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbnailImage = thumbnailImage;
        this.completeImage = completeImage;
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

    public Image getThumbnailImage() {
        return thumbnailImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setThumbnailImage(Image thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setCompleteImage(Image completeImage) {
        this.completeImage = completeImage;
    }
}
