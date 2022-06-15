import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Scanner;

public class signIn {

    public void log_In() {
        String password, emailId, selection;
        boolean success = false;

        reservationsInsertion reservation = new reservationsInsertion();
        Scanner myObj = new Scanner(System.in);

        System.out.print("\nEnter Email Id: ");
        emailId = myObj.next(); // Read user input

        System.out.print("\nEnter Password: ");
        password = myObj.next(); // Read user input
        try {

            String url = "jdbc:mysql://localhost:3306/onlinereservationsystem";
            Connection con = DriverManager.getConnection(url, "root", "");
            String query = "select Email,Password from records";

            Statement stmnt = con.createStatement(); // Statment method
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {

                // System.out.println(((Object)rs.getString("Email")).getClass().getSimpleName());
                // // find the data type of any value/varaible

                if (emailId.equals(rs.getString("Email"))) {
                    try {

                        MessageDigest md5 = MessageDigest.getInstance("MD5");
                        md5.update(password.getBytes(), 0, password.length());
                        // System.out.println(new BigInteger(1,md5.digest()).toString(16));
                        if ((new BigInteger(1, md5.digest()).toString(16)).equals(rs.getString("Password"))) {
                            System.out.println("\n\t\t*** Successfully logged In ***");
                            success = true;
                            System.out.println(
                                    "\n1.For Booking the ticket \n\n2.For Cancel the ticket\n\n3.Any button for exit");
                            System.out.print("\nYour Choice: ");

                            selection = myObj.next();
                            if (selection.equals("1"))
                                reservation.buyTickets(emailId);
                            else if (selection.equals("2"))
                                reservation.ticketCancel();
                            else {
                                System.out.println("\n**** bye bye ****\n");
                                break;
                            }
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (!success)
                System.out.println("\n*************************\n* Credentials Fail !!! *\n");

            con.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    public static void main(String args) {
    }
}
