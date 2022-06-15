import java.util.Scanner;
import java.sql.*;

public class signUp {
    String userName, emailId, pass, confirm_pass;
    long phoneNumber;
    boolean verification = true;
    JdbcConnectivity connectivity = new JdbcConnectivity();
    reservationsInsertion insertion = new reservationsInsertion();

    public void signUp() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("\n\t\t****** Create Your new Account ******");

        System.out.print("\nEnter Full Name: ");
        userName = myObj.nextLine(); // Read user input

        System.out.print("\nEnter Phone Number: ");
        phoneNumber = myObj.nextLong(); // Read user input

        while (verification) {
            System.out.print("\nEnter Email Id: ");
            emailId = myObj.next(); // Read user input
            try {
                String url = "jdbc:mysql://localhost:3306/onlinereservationsystem";
                Connection con = DriverManager.getConnection(url, "root", "");
                String query = "select Email from records";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    if (emailId.equals(rs.getString("Email")))
                        verification = false;

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
            if (verification == true) {

                while (verification) {
                    System.out.print("\nEnter Password: ");
                    pass = myObj.next(); // Read user input

                    System.out.print("\nEnter confirm Password: ");
                    confirm_pass = myObj.next(); // Read user input

                    if (pass.equals(confirm_pass))
                        verification = false;
                    else
                        System.out.println("\nwrong !!! Confirm pass: doesn't match\n");
                }
                insertion.ReservationsInsertion(userName, phoneNumber, emailId, pass);
            } else {
                System.out.println("!!! This Email is already exist, try another...");
                verification = true;
            }

        }
    }

    public static void main(String args[]) {
        signUp myObj = new signUp();
        if (myObj.connectivity.JdbcConnectivity() == true)
            myObj.signUp();
        else
            System.out.print("JDBC Connectivity Fail !!!");

    }
}
