package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static javafx.scene.paint.Color.*;

public class Main extends Application {
    private DataAccessTeacher dbaccessTeacher;
    private ObservableList<Teacher> teachers;
    private ListView<Teacher> listView;

    @Override
    public  void init() {
        try  {
            dbaccessTeacher = new  DataAccessTeacher();
        }
        catch (Exception e) {
            displayException(e);
        }
    }
    @Override
    public  void stop() {
        try {
            dbaccessTeacher.closeDb();
        }
        catch (Exception e) {
            displayException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("School Statistics");

        // objekte und elemente erstellen
        Label lblHeadTeacher = new Label("Teachers:");
            lblHeadTeacher.setStyle("-fx-font-weight:bold");
        Label lblHeadDetails = new Label("Details to this Teacher:");
            lblHeadDetails.setStyle("-fx-font-weight:bold");
        Label lblTeacherID = new Label("ID:");
            lblTeacherID.setMinWidth(55);
            lblTeacherID.setAlignment(Pos.BASELINE_RIGHT);
        TextField txtID = new TextField();
        Label lblTeacherName = new Label("Name:");
            lblTeacherName.setMinWidth(55);
        TextField txtName = new TextField();
        Label lblTeacherSurname = new Label("Surname:");
            lblTeacherSurname.setMinWidth(55);
        TextField txtSurname = new TextField();
        Label lblTeacherEmail = new Label("Email:");
            lblTeacherEmail.setMinWidth(55);
        TextField txtEmail = new TextField();

        listView = new ListView<>();
        //listView.getSelectionModel().selectedIndexProperty().addListener(new  ListSelectChangeListener());
        teachers = getDbData();
        listView.setItems(teachers);

// Scene Zusammenbauen ---------------------------------------------------------------------------

        VBox vBoxLeft = new VBox(lblHeadTeacher,listView);
        vBoxLeft.setPadding(new Insets(10,10,10,10));
        HBox hBoxLblId = new HBox(lblTeacherID,txtID);
        HBox hBoxLblName = new HBox(lblTeacherName,txtName);
        HBox hBoxLblSurname = new HBox(lblTeacherSurname,txtSurname);
        HBox hBoxLblEmail = new HBox(lblTeacherEmail,txtEmail);
        VBox vBoxmiddle = new VBox(lblHeadDetails,hBoxLblId,hBoxLblName,hBoxLblSurname,hBoxLblEmail);
        vBoxmiddle.setPadding(new Insets(10,10,10,10));

        HBox mainBox = new HBox(vBoxLeft,vBoxmiddle);
        Scene sceneTeacher = new Scene(mainBox);
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
            list = dbaccessTeacher.getAllRows();
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
