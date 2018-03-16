package sample;

import java.io.File;

public class FileChooser {

    private String path = "";

    public void FileGet() {
        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            path = selectedFile.getAbsolutePath();
        } else {
            System.out.println("Error");
        }

    }

    public String getPath() {
        return path;
    }
}
