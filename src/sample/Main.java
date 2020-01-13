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

public class Main extends Application {
    private DataAccessTeacher dbaccessTeacher;
    private DataAccessKlassen dbaccessKlassen;
    private ObservableList<Teacher> teachers;
    private ListView<Teacher> listViewTeacher;
    private ObservableList<Klassen> klassens;
    private ListView<Klassen> listViewKlassen;
    private int tID;

    @Override
    public  void init() {
        try  {
            dbaccessTeacher = new  DataAccessTeacher();
            dbaccessKlassen = new DataAccessKlassen(0);
        }
        catch (Exception e) {
            displayException(e);
        }
    }
    @Override
    public  void stop() {
        try {
            dbaccessTeacher.closeDb();
            dbaccessKlassen.closeDb();
        }
        catch (Exception e) {
            displayException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("School Statistics");
// objekte und elemente erstellen--------------------------------------------------------------------------------------
        Label lblHeadTeacher = new Label("Teachers:");
            lblHeadTeacher.setStyle("-fx-font-weight:bold");
        Label lblHeadDetails = new Label("Details to this Teacher:");
            lblHeadDetails.setStyle("-fx-font-weight:bold");
        Label lblHeadClasses = new Label("teaches this classes:");
            lblHeadClasses.setStyle("-fx-font-weight:bold");
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

        listViewTeacher = new ListView<>();
        teachers = getDbData();
        listViewTeacher.setItems(teachers);

        listViewKlassen = new ListView<>();
        klassens = getKlassenData();
        listViewKlassen.setItems(klassens);
// Actions -----------------------------------------------------------------------------------------------
        listViewTeacher.getSelectionModel().selectedItemProperty().addListener(((observableValue, teacher, t1) -> {
            int selIdx = listViewTeacher.getSelectionModel().getSelectedIndex();
            if(selIdx!=-1){
                txtID.setText(Integer.toString(t1.getId()));
                txtName.setText(t1.getName());
                txtSurname.setText(t1.getSurename());
                txtEmail.setText(t1.getEmail());
                tID = t1.getId();

                dbaccessKlassen.setTeacherID(tID);

                klassens = getKlassenData();
                listViewKlassen.setItems(klassens);

            }
        }));


// Scene Zusammenbauen ---------------------------------------------------------------------------

        VBox vBoxLeft = new VBox(lblHeadTeacher,listViewTeacher);
        vBoxLeft.setPadding(new Insets(10,10,10,10));
        vBoxLeft.setMaxHeight(150);
        HBox hBoxLblId = new HBox(lblTeacherID,txtID);
        HBox hBoxLblName = new HBox(lblTeacherName,txtName);
        HBox hBoxLblSurname = new HBox(lblTeacherSurname,txtSurname);
        HBox hBoxLblEmail = new HBox(lblTeacherEmail,txtEmail);
        VBox vBoxmiddle = new VBox(lblHeadDetails,hBoxLblId,hBoxLblName,hBoxLblSurname,hBoxLblEmail);
        vBoxmiddle.setPadding(new Insets(10,10,10,10));
        VBox vBoxRight = new VBox(lblHeadClasses, listViewKlassen);
        vBoxRight.setPadding(new Insets(10,10,10,10));

        HBox mainBox = new HBox(vBoxLeft,vBoxmiddle,vBoxRight);
        Scene sceneTeacher = new Scene(mainBox);
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

    private  ObservableList<Klassen> getKlassenData() {
        List<Klassen> listKlassen = null ;
        try  {
              listKlassen = dbaccessKlassen.getAllRows();
        }
        catch  (Exception e) {
            displayException(e);
        }
        ObservableList<Klassen> dbData1 = FXCollections.observableList(listKlassen);
        return  dbData1;
    }

    private   void  displayException(Exception e) {
        System.out.println( "###### Exception ######" );
        e.printStackTrace();
        System.exit( 0 );
    }
}
