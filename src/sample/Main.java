package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.*;

public class Main extends Application {
    private DataAccessTeacher dbaccess;
    private ObservableList<Teacher> teachers;

    @Override
    public  void init() {
        try  {
            dbaccess = new  DataAccessTeacher();
        }
        catch (Exception e) {
            displayException(e);
        }
    }
    @Override
    public  void stop() {
        try {
            dbaccess.closeDb();
        }
        catch (Exception e) {
            displayException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
       //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("School Statistics");

        VBox vBoxMain = new VBox(400);
        Scene sceneTeacher = new Scene(vBoxMain);
        sceneTeacher.setFill(GRAY);
        primaryStage.setScene(sceneTeacher);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private   void  displayException(Exception e) {
        System.out.println( "###### Exception ######" );
        e.printStackTrace();
        System.exit( 0 );
    }
}
