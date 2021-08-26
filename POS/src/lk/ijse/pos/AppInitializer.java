package lk.ijse.pos;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/
public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {

            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"));

            Scene mainScene = new Scene(root);

            primaryStage.setTitle("IJSE FX POS - 2018 : DEP");
            primaryStage.setScene(mainScene);
            primaryStage.setResizable(false);

            primaryStage.show();


        } catch (IOException ex) {
            Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void navigateToHome(Node node, Stage mainStage) {


        TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
        tt.setFromX(0);
        tt.setToX(-node.getScene().getWidth());
        tt.play();

        Platform.runLater(()->{

            try {
                Parent root = FXMLLoader.load(AppInitializer.class.getResource("/lk/ijse/pos/view/MainForm.fxml"));
                Scene mainScene = new Scene(root);
                mainStage.setScene(mainScene);

                TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
                tt1.setToX(0);
                tt1.setFromX(-mainScene.getWidth());
                tt1.play();

                mainStage.centerOnScreen();
            } catch (IOException ex) {
                Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, null, ex);
            }

        });



    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
