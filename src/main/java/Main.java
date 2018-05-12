import other.ApplicationPaths;
import gui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get(ApplicationPaths.RESOURCES_VIEWS,"mainWindow.fxml").toFile().toURI().toURL());
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../../resources/mainWindow.fxml"));

//        Parent root = fxmlLoader.load();
//////        primaryStage.setTitle("Skepj-PhotoAlbum");
//////        primaryStage.setScene(new Scene(root, 850, 600));
//////        primaryStage.show();

        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SkepJ Photos");
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        MainWindowController.setStage(primaryStage);

    }


    public static void main(String[] args) throws IOException {
        ApplicationPaths.setApplicationPaths();
        launch(args);
    }
}
