import java.sql.*;

public class JdbcConnectivity {

    public boolean JdbcConnectivity() {
        // TODO code application logic here
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.print("Successfully Connected");
            return true;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        JdbcConnectivity myObj = new JdbcConnectivity();
    }

}
