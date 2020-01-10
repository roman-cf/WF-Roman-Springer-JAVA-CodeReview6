package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static javafx.scene.paint.Color.*;

public class Main extends Application {
    private DataAccessTeacher dbaccess;
    private ObservableList<Teacher> teachers;
    private ListView<Teacher> listView;

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
        primaryStage.setTitle("School Statistics");

        listView = new ListView<>();
        //listView.getSelectionModel().selectedIndexProperty().addListener(new  ListSelectChangeListener());
        teachers = getDbData();
        listView.setItems(teachers);

        VBox vBoxMain = new VBox(listView);
        Scene sceneTeacher = new Scene(vBoxMain);
        sceneTeacher.setFill(GRAY);
        primaryStage.setScene(sceneTeacher);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private  ObservableList<Teacher> getDbData() {

        List<Teacher> list = null ;

        try  {
            list = dbaccess.getAllRows();
        }
        catch  (Exception e) {

            displayException(e);
        }

        ObservableList<Teacher> dbData = FXCollections.observableList(list);
        return  dbData;
    }

    private   void  displayException(Exception e) {
        System.out.println( "###### Exception ######" );
        e.printStackTrace();
        System.exit( 0 );
    }
}
