package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessStudent {

    private Connection connectionS;
    private static final String studentTable = "student";
    private int classID = 0;
    private String queryTableWhere =  " WHERE class_id="+ String.valueOf(classID);

    public DataAccessStudent(int classID) throws ClassNotFoundException, SQLException {
        this.classID = classID;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connectionS = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cr6" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                "root",
                "");
        System.out.println("connected to db table student");
    }
    public void closeDb() throws SQLException {
        connectionS.close();
        System.out.println("cloesed Database");
    }
    public List<Student> getAllRows()  throws SQLException {
        if(classID==0){
            queryTableWhere = " ";
        }else{
            queryTableWhere = " WHERE class_id="+ String.valueOf(classID);
        }
        String sql = "SELECT *  FROM "+ studentTable + queryTableWhere; //" WHERE class_id="+ classID;
        PreparedStatement pstmntS = connectionS.prepareStatement(sql);
        ResultSet rsStudent = pstmntS.executeQuery();
        List<Student> listStudent = new ArrayList<>();

        while (rsStudent.next()) {
            int i = rsStudent.getInt("id");
            String name = rsStudent.getString("name");
            String surname = rsStudent.getString("surname");
            String email = rsStudent.getString("email");
            int class_id = rsStudent.getInt("class_id");
            listStudent.add(new Student(i, name,surname,email,class_id));
        }

        pstmntS.close();
        return listStudent;
    }

    public List<Student> setClassID(int classID) {
        this.classID = classID;
        return null;
    }

    public int getClassID() {
        return classID;
    }
}
