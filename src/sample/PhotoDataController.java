package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class PhotoDataController {


    public Metadata ExtractMetadata(File selectedImage) {


        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(selectedImage);
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return metadata;

    }

    public ImageMetadata CreateImageMetadata(Metadata metadata) {
        ImageMetadata imageMetadata = new ImageMetadata();
        int i = 0;
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {

                imageMetadata.InsertImageMetadata(tag.getTagName(), tag.getDescription(), i);
                i++;
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
        return imageMetadata;
    }

    public Image getImageFromFile(File file) {
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("getImageFromFile could not read image");
        }

        return image;
    }


    public byte[] extractBytesFromImage(Image imageName) throws IOException {
        if (imageName == null) {
            return null;
        }
        BufferedImage bufferedImage = (BufferedImage) imageName;

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }
}
