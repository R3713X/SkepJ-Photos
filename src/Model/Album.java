package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class Album {
    private String title;
    private String albumId;
    private LocalDate date;

    public Album(String title) {
        this.title = title;
        this.albumId = UUID.randomUUID().toString();
        this.date = LocalDate.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {

        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public LocalDate getDate() {
        return date;
    }
}
