package main.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**Application Class in which main() is located in and application loads the initial scene from MainForm.fxml*/
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 450);
        stage.setScene(scene);
        stage.show();
    }
    /**main() method in which program starts off on, launches the application*
     * Javadocs is located in "\Javadocs\index.html"
     */
    public static void main(String[] args) {

       launch();
    }
}