import java.sql.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class reservationsInsertion {
    String classType, dateOfJourney, source, destination, confirmation;
    Scanner myObj = new Scanner(System.in);

    public void ReservationsInsertion(String userName, Long phoneNumber, String email, String password) {

        try {
            String url = "jdbc:mysql://localhost:3306/onlinereservationsystem";
            Connection con = DriverManager.getConnection(url, "root", "");
            String query = "INSERT INTO records (Full_Name,Phone_Number,Email,Password) VALUES (?,?,?,md5(?))";
            PreparedStatement stmnt = con.prepareStatement(query); // prepared statement method

            stmnt.setString(1, userName); // 1 specifies the first parameter in the query
            stmnt.setLong(2, phoneNumber);
            stmnt.setString(3, email);
            stmnt.setString(4, password);

            stmnt.executeUpdate();
            con.close();
            System.out.print("\n********************************\n* Account Successfully created *");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    public void buyTickets(String email) throws ParseException {
        Random rand = new Random();
        String[] train_names = { "Alpha", "Beta", "Express", "Shaheen" };
        String PNR;
        char randomChar, randomChar2, randomChar3;

        System.out.println("\n\n ******* DASHBOARD *******\n");

        System.out.print("\nIn Which class would u like to travel? (Economy,lower,Air-C,Sleeper) ");
        classType = myObj.next(); // Read user input

        System.out.print("\nStarting place of ur journey is?  ");
        source = myObj.next(); // Read user input

        System.out.print("\nUr destination is?  ");
        destination = myObj.next(); // Read user input

        myObj.nextLine();

        System.out.print("\nEnter the date for ur journey: in format(DD/MM/YYYY) *use '/' ");
        dateOfJourney = myObj.nextLine();

        // Date date = new Date(); // this three line code is for current time and day
        // java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        // java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());

        java.util.Date sqlDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfJourney);
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(sqlDate.getTime());

        randomChar = (char) (Math.random() * (123 - 97 + 1) + 97);
        randomChar2 = (char) (Math.random() * (123 - 97 + 1) + 97);
        randomChar3 = (char) (Math.random() * (123 - 97 + 1) + 97);

        PNR = "" + randomChar + rand.nextInt(10) + randomChar2 + rand.nextInt(10) + randomChar3 + rand.nextInt(10) + "";

        System.out.print("\nAre u ready to book ur ticket (y/n)?  ");
        confirmation = myObj.next(); // Read user input

        if (confirmation.toLowerCase().equals("y")) {
            try {
                String url = "jdbc:mysql://localhost:3306/onlinereservationsystem";
                Connection con = DriverManager.getConnection(url, "root", "");

                String query = "INSERT INTO ticketsrecord (Email,TrainNumber,TrainName,Class,Source,Destination,JourneyDate,PNR) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement stmnt = con.prepareStatement(query); // prepared statement method

                stmnt.setString(1, email); // 1 specifies the first parameter in the query
                stmnt.setInt(2, rand.nextInt(10)); // for TimeStamp (DD/MM/YYYY hh:mm::ss)
                stmnt.setString(3, train_names[rand.nextInt(4)]); // for TimeStamp (DD/MM/YYYY hh:mm::ss)
                stmnt.setString(4, classType);
                stmnt.setString(5, source);
                stmnt.setString(6, destination);
                // stmnt.setDate(7, sqlDate); // for Date (DD/MM/YYYY)
                stmnt.setTimestamp(7, sqlTime); // for TimeStamp (DD/MM/YYYY hh:mm::ss)
                stmnt.setString(8, PNR);

                stmnt.executeUpdate();
                con.close();

                System.out.println("\n\n*Memorize your 6-digits PNR Number= " + PNR);

                System.out.println("\n*************************\n* Ticket is confirmed   *\n*************************");

            } catch (Exception e) {
                // TODO: handle exception
                System.out.print(e.getMessage());
            }
        } else
            System.out.println("\n<<<<<<< Ticket isn't booked >>>>>>>\n");

    }

    public void ticketCancel() {
        String PNR, selection;
        System.out.print("\n\nEnter ur PNR for Cancel the Ticket: ");
        PNR = myObj.next();
        try {
            String url = "jdbc:mysql://localhost:3306/onlinereservationsystem";
            Connection con = DriverManager.getConnection(url, "root", "");

            String query = "select * from ticketsrecord where PNR=?";
            PreparedStatement stmnt = con.prepareStatement(query); // prepared statement method
            stmnt.setString(1, PNR); // 1 specifies the first parameter in the query
            ResultSet rs = stmnt.executeQuery();
            if (rs.next() == true) {

                System.out.println(
                        "\n\t\t\tYour Information is\n\nEmail ID\t  Train Number\tTrain Name\tClass\t\tSource\t   Destination\tJourney Date\t\t\tPNR");
                rs = stmnt.executeQuery();
                while (rs.next()) {

                    System.out.print(rs.getString(1));
                    System.out.print("\t  " + rs.getInt(2));
                    System.out.print("\t\t" + rs.getString(3));
                    System.out.print("\t\t" + rs.getString(4));
                    System.out.print("\t\t" + rs.getString(5));
                    System.out.print("\t   " + rs.getString(6));
                    System.out.print("\t\t" + rs.getTimestamp(7));
                    System.out.println("\t\t" + rs.getString(8));

                }
                System.out.print("\n\nDo you want to cancel your ticket? (y/n) ");
                selection = myObj.next();

                if (selection.equals("y")) {
                    try {

                        query = "DELETE FROM ticketsrecord WHERE PNR=?";
                        stmnt = con.prepareStatement(query); // prepared statement method
                        stmnt.setString(1, PNR); // 1 specifies the first parameter in the query
                        stmnt.executeUpdate();
                        System.out.println("\n<<<<<<< Cancelation Successfull Thanks for your precious time >>>>>>>\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("\n<<<<<<< Your ticket is safe >>>>>>>\n");

                }

            } else
                System.out.println("\n<<<<<<< You haven't book any ticket yet >>>>>>>\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public static void main(String[] args) {

    }

}
