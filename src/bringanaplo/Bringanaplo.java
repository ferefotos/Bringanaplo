package bringanaplo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Fere
 */


public class Bringanaplo extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setMinHeight(850);
        stage.setMinWidth(1450);
        stage.setMaxHeight(990);
        stage.setMaxWidth(1550);
        //stage.setResizable(false);
        stage.setTitle("Bringanapló");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("bike.png")));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
