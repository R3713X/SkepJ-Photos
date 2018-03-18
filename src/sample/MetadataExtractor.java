package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

public class MetadataExtractor {


    public ImageMetadata Extract ( File selectedImage) {
     
            int i = 0;
            ImageMetadata imageMetadata = new ImageMetadata();
            try {

                Metadata metadata = ImageMetadataReader.readMetadata(selectedImage);
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                           
                           imageMetadata.InsertImageMetadata( tag.getTagName() , tag.getDescription() , i );
                        i++;
                    }
                    if (directory.hasErrors()) {
                        for (String error : directory.getErrors()) {
                            System.err.format("ERROR: %s", error);
                        }
                    }
                }

            } catch (ImageProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            
        }
        return imageMetadata;

    }
}
