package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    private DataAccessTeacher dbaccessTeacher;
    private DataAccessKlassen dbaccessKlassen;
    private DataAccessStudent dbaccessStudent;
    private ObservableList<Teacher> teachers;
    private ListView<Teacher> listViewTeacher;
    private ObservableList<Klassen> klassens;
    private ListView<Klassen> listViewKlassen;
    private ObservableList<Student> students;
    private ListView<Student> listViewStudent;
    private int tID;
    private int cID;
    private int from;

    @Override
    public  void init() {
        try  {
            dbaccessTeacher = new DataAccessTeacher();
            dbaccessKlassen = new DataAccessKlassen(0);
            dbaccessStudent = new DataAccessStudent(0);
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
            dbaccessStudent.closeDb();
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
        Label lblHeadDetails = new Label("Details to selected Person:");
            lblHeadDetails.setStyle("-fx-font-weight:bold");
        Label lblHeadClasses = new Label("teaches this classes:");
            lblHeadClasses.setStyle("-fx-font-weight:bold");
        Label lblClassList = new Label("Students:");
            lblClassList.setStyle("-fx-font-weight:bold");
            lblClassList.setPadding(new Insets(20,0,0,0));
        Label lblTeacherID = new Label("ID:");
            lblTeacherID.setMinWidth(55);
            lblTeacherID.setAlignment(Pos.BASELINE_RIGHT);
        TextField txtID = new TextField();
            txtID.setDisable(true);
        Label lblTeacherName = new Label("Name:");
            lblTeacherName.setMinWidth(55);
        TextField txtName = new TextField();
        Label lblTeacherSurname = new Label("Surname:");
            lblTeacherSurname.setMinWidth(55);
        TextField txtSurname = new TextField();
        Label lblTeacherEmail = new Label("Email:");
            lblTeacherEmail.setMinWidth(55);
        TextField txtEmail = new TextField();

        Button btnUpdate = new Button("update");

        listViewTeacher = new ListView<>();
        teachers = getDbData();
        listViewTeacher.setItems(teachers);

        listViewKlassen = new ListView<>();
        klassens = getKlassenData();
        listViewKlassen.setItems(klassens);

        listViewStudent = new ListView<>();
        students = getStudentsData();
        listViewStudent.setItems(students);
// Actions -----------------------------------------------------------------------------------------------
        listViewTeacher.getSelectionModel().selectedItemProperty().addListener(((observableValue, teacher, t1) -> {
            int selIdx = listViewTeacher.getSelectionModel().getSelectedIndex();
            if(selIdx!=-1){
                txtID.setText(Integer.toString(t1.getId()));
                txtName.setText(t1.getName());
                txtSurname.setText(t1.getSurename());
                txtEmail.setText(t1.getEmail());
                from = 0;
                lblHeadDetails.setText("Details of this Teacher:");
                tID = t1.getId();
                dbaccessKlassen.setTeacherID(tID);
                klassens = getKlassenData();
                listViewKlassen.setItems(klassens);
            }
        }));

        listViewKlassen.getSelectionModel().selectedItemProperty().addListener(((observableValue, klasse, k1)->{
            int selIdx = listViewKlassen.getSelectionModel().getSelectedIndex();
            if(selIdx!=-1){
                cID = k1.getId();
                dbaccessStudent.setClassID(cID);
                System.out.println(dbaccessStudent.getClassID());
                students = getStudentsData();
                listViewStudent.setItems(students);
                lblClassList.setText("Students of Class: "+k1.getClassname());
            }
        } ));

        listViewStudent.getSelectionModel().selectedItemProperty().addListener(((observableValue, student, s1) ->{
            int selIdx = listViewStudent.getSelectionModel().getSelectedIndex();
            if(selIdx!=-1){
                txtID.setText(Integer.toString(s1.getId()));
                txtName.setText(s1.getName());
                txtSurname.setText(s1.getSurename());
                txtEmail.setText(s1.getEmail());
                from = 1;
                lblHeadDetails.setText("Details of this Student:");
            }
        } ));
        btnUpdate.setOnAction(actionEvent -> {
            String table = "";
            if (from == 0){table = "teacher";}else{ table = "student";}
            String updateString = "name = '"+txtName.getText()+"', surname = '"+txtSurname.getText()+"', email = '"+txtEmail.getText()+"'";
            try {
                DBUpdater btnUpdateObj = new DBUpdater(table,Integer.parseInt(txtID.getText()),updateString);
                btnUpdateObj.updateRow();
             // refresh

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


// Scene Zusammenbauen ---------------------------------------------------------------------------

        VBox vBoxLeft = new VBox(lblHeadTeacher,listViewTeacher,lblClassList,listViewStudent);
        vBoxLeft.setPadding(new Insets(10,10,10,10));
        vBoxLeft.setMaxHeight(500);
        HBox hBoxLblId = new HBox(lblTeacherID,txtID);
        HBox hBoxLblName = new HBox(lblTeacherName,txtName);
        HBox hBoxLblSurname = new HBox(lblTeacherSurname,txtSurname);
        HBox hBoxLblEmail = new HBox(lblTeacherEmail,txtEmail);
        VBox vBoxmiddle = new VBox(lblHeadDetails,hBoxLblId,hBoxLblName,hBoxLblSurname,hBoxLblEmail,btnUpdate);
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

    private  ObservableList<Student> getStudentsData(){
        List<Student> listStudent = null;
        try {
            listStudent = dbaccessStudent.getAllRows();
        }
        catch (Exception e) {
            displayException(e);
        }
        ObservableList<Student> dbData2 = FXCollections.observableList(listStudent);
        return dbData2;
    }

    private   void  displayException(Exception e) {
        System.out.println( "###### Exception ######" );
        e.printStackTrace();
        System.exit( 0 );
    }
}
