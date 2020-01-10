package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessKlassen {

    private Connection connectionK;
    private static final String klassenTable = "teaching";
    private int teacherID = 0;


    public DataAccessKlassen() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connectionK = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cr6" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                "root",
                "");
        System.out.println("connected to db");
    }
    public void closeDb() throws SQLException {
        connectionK.close();
        System.out.println("cloesed Database");
    }
    public List<Klassen> getAllRows()  throws SQLException {
        String sql = "SELECT  teaching.class_id , class.name FROM "+ klassenTable +
        " INNER JOIN teacher ON teaching.teacher_id = teacher.id INNER JOIN class on teaching.class_id = class.id WHERE teacher.id="+teacherID;
        PreparedStatement pstmntK = connectionK.prepareStatement(sql);
        ResultSet rsKlassen = pstmntK.executeQuery();
        List<Klassen> listKlassen = new ArrayList<>();

        while (rsKlassen.next()) {
            int i = rsKlassen.getInt("class_id");
            String classname = rsKlassen.getString("name");
            listKlassen.add(new Klassen(i, classname));
        }

        pstmntK.close();
        return listKlassen;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
