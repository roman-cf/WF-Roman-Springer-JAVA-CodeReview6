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
        System.out.println("connected to db");
    }
    public void closeDb() throws SQLException {
        connectionT.close();
        System.out.println("cloesed Database");
    }
    public List<Teacher> getAllRows()  throws SQLException {
        String sql = "SELECT * FROM " + teacherTable + " ORDER BY teacherName";
        PreparedStatement pstmnt = connectionT.prepareStatement(sql);
        ResultSet rs = pstmnt.executeQuery();
        List<Teacher> list = new ArrayList<>();

        while (rs.next()) {
            int i = rs.getInt("teacherId");
            String name = rs.getString("teacherName");
            String surname = rs.getString("teacherSurname");
            String email = rs.getString("teacherEmail");
            list.add(new Teacher(i, name, surname, email));
        }

        pstmnt.close();
        return list;
    }


}
