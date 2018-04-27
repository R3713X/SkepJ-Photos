package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainwindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Skepj-PhotoAlbum");
        primaryStage.setScene(new Scene(root, 850, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
