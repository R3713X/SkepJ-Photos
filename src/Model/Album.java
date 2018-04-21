package Model;

import java.sql.Date;
import java.util.UUID;

public class Album {
    private String title;
    private String albumId;
    private Date date;

    public Album(String title, Date date) {
        this.title = title;
        this.albumId = UUID.randomUUID().toString();
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public Date getDate() {
        return date;
    }
}
