package sample;
import java.sql.*;

public class DBUpdater {
    private Connection connection;
    private String table;
    private int userID;
    private  String updateString;

    public DBUpdater(String table, int userID, String updateString) throws SQLException, ClassNotFoundException {
        this.table = table;
        this.userID = userID;
        this.updateString = updateString;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cr6" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                "root",
                "");
        System.out.println("connected to db table "+table);

    }
    public void updateRow() throws SQLException{
        String sql = "UPDATE "+table + " SET " + updateString +" WHERE id= " + userID;
        System.out.println(sql);
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.executeUpdate();
    }


    public void closeDb() throws SQLException {
        connection.close();
        System.out.println("cloesed Database");
    }

}
