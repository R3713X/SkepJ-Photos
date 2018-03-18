package sample;

import java.io.File;

public class FileManager{

    

    public File FileGet() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            selectedFile.getAbsolutePath();
        } else {
            System.out.println("Error");
        }
        
        return selectedFile;

    }


}
