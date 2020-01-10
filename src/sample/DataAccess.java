package sample;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public  class DataAccess {

    private Connection conn;
    private static final String teacherTable = "teacher";

    public DataAccess()
            throws SQLException, ClassNotFoundException {
        Class.forName( "com.mysql.cj.jdbc.Driver");
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cr6" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                "root",
                "");
        conn.setAutoCommit(true);
        conn.setReadOnly(false);
    }

    public void closeDb() throws SQLException {
        conn.close();
    }

    /**
     * Get all db records
     * @return
     * @throws SQLException
     */
    public List<Teacher> getAllRows()   throws SQLException {

        String sql = "SELECT * FROM " + teacherTable + " ORDER BY name" ;
        PreparedStatement pstmnt = conn.prepareStatement(sql);
        ResultSet rs = pstmnt.executeQuery();
        List<Teacher> list = new  ArrayList<>();

        while  (rs.next()) {
            int i = rs.getInt("id" );
            String name = rs.getString("name" );
            String surename = rs.getString("surename" );
            String email = rs.getString("email");
            list.add( new Teacher(i, name, surename, email));
        }

        pstmnt.close();
        return list;
    }


    public  boolean nameExists(Teacher teacher) throws SQLException {

        String sql = "SELECT COUNT(id) FROM " + teacherTable + " WHERE name = ? AND id <> ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(sql);
        pstmnt.setString( 1 , teacher.getName());
        pstmnt.setInt( 2 , teacher.getId());
        ResultSet rs = pstmnt.executeQuery();
        rs.next();
        int  count = rs.getInt( 1 );
        pstmnt.close();

        if  (count > 0 ) {
            return   true ;
        }

        return   false ;
    }

    public   int  insertRow(Teacher teacher)
            throws  SQLException {

        String dml = "INSERT INTO "  + teacherTable + " VALUES (DEFAULT, ?, ?)" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml,
                PreparedStatement.RETURN_GENERATED_KEYS);
        pstmnt.setString( 1 , teacher.getName());
        pstmnt.setString( 2 , teacher.getSurename());
        pstmnt.executeUpdate(); // returns insert count

        // get identity column value
        ResultSet rs = pstmnt.getGeneratedKeys();
        rs.next();
        int  id = rs.getInt( 1 );

        pstmnt.close();
        return  id;
    }

    public   void  updateRow(Teacher teacher)
            throws  SQLException {

        String dml = "UPDATE "  + teacherTable +
                " SET name = ?, description = ? "  + " WHERE id = ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml);
        pstmnt.setString( 1 , teacher.getName());
        pstmnt.setString( 2 , teacher.getSurename());
        pstmnt.setInt( 3 , teacher.getId());
        pstmnt.executeUpdate(); // returns update count
        pstmnt.close();
    }

    public   void  deleteRow(Teacher teacher)
            throws  SQLException {

        String dml = "DELETE FROM "  + teacherTable + " WHERE id = ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml);
        pstmnt.setInt( 1 , teacher.getId());
        pstmnt.executeUpdate(); // returns delete count (0 for none)
        pstmnt.close();
    }
}