package backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig implements AutoCloseable {
    private Connection con;

    public DBConfig(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver missing!");
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/j148_testdb";

        try {
            this.con = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCon(){
        return this.con;
    }

    @Override
    public void close() throws Exception {
        if(this.con != null && !this.con.isClosed()){
            this.con.close();
        }
    }

}
