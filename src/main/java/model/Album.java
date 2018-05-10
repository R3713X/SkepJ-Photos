package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class Album {
    private String name;
    private String albumId;
    private Date date;


    public Album(String name) {
        this.name = name;
        this.albumId = UUID.randomUUID().toString();
        this.date = Date.valueOf(LocalDate.now());
    }

    public Album(String name, String albumId, Date date) {
        this.name = name;
        this.albumId = albumId;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {

        this.date = Date.valueOf(date);
    }

    public String getName() {
        return name;
    }

    public String getAlbumId() {
        return albumId;
    }

    public Date getDate() {

        return this.date;
    }
}
