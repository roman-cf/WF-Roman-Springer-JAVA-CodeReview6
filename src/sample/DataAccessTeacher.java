package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessTeacher {

    private Connection connectionT;
    private static final String teacherTable = "teacher";

    public DataAccessTeacher() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connectionT = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/cr6" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                   "root",
                   "");
        System.out.println("connected to db table teacher");
    }
    public void closeDb() throws SQLException {
        connectionT.close();
        System.out.println("cloesed Database");
    }
    public List<Teacher> getAllRows()  throws SQLException {
        String sql = "SELECT * FROM " + teacherTable + " ORDER BY surname";
        PreparedStatement pstmnt = connectionT.prepareStatement(sql);
        ResultSet rsTeacher = pstmnt.executeQuery();
        List<Teacher> list = new ArrayList<>();

        while (rsTeacher.next()) {
            int i = rsTeacher.getInt("id");
            String name = rsTeacher.getString("name");
            String surname = rsTeacher.getString("surname");
            String email = rsTeacher.getString("email");
            list.add(new Teacher(i, name, surname, email));
        }

        pstmnt.close();
        return list;
    }

}
