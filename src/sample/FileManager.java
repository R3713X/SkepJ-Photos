package sample;

import javafx.stage.FileChooser;

import java.io.File;

public class FileManager {

    public File fileGet() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        return selectedFile;

    }
}
