package UI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PreviewPhotoDialogController {

    @FXML
    private ImageView previewImageView;

    public void setImageView(Image image) {
        previewImageView.setImage(image);
    }

}
