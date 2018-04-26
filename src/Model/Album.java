package Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

public class Album {
    private String title;
    private String albumId;
    private String date;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Album(String title) {
        this.title = title;
        this.albumId = UUID.randomUUID().toString();
        Date date = Date.valueOf(LocalDate.now());
        this.date = sdf.format(date);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {

        this.date = sdf.format(date);
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getDate() {

        return this.date;
    }
}
